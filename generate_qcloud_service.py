#!/usr/bin/env python3
"""
Generate QCloudService.java from QCloudClient.java methods and entity-key mappings.

Reads:
  - .specs/resource-coverage-100/qcloud-entity-keys.txt (entity -> unique key field)
  - backend/src/main/java/com/linjicong/cloud/stat/client/QCloudClient.java (client methods)
  - entity java files to check key field types

Generates:
  - backend/src/main/java/com/linjicong/cloud/stat/service/QCloudService.java
"""

import re
import os

PROJECT_ROOT = os.path.dirname(os.path.abspath(__file__))
ENTITY_KEYS_FILE = os.path.join(PROJECT_ROOT, ".specs", "resource-coverage-100", "qcloud-entity-keys.txt")
CLIENT_FILE = os.path.join(PROJECT_ROOT, "backend", "src", "main", "java", "com", "linjicong", "cloud", "stat", "client", "QCloudClient.java")
ENTITY_DIR = os.path.join(PROJECT_ROOT, "backend", "src", "main", "java", "com", "linjicong", "cloud", "stat", "dao", "entity", "qcloud")
OUTPUT_FILE = os.path.join(PROJECT_ROOT, "backend", "src", "main", "java", "com", "linjicong", "cloud", "stat", "service", "QCloudService.java")

# Entities to skip in syncEcs (special handling needed)
SKIP_ENTITIES = {
    "QCloudBillResourceSummary",  # needs month param
    "QCloudAccessKey",            # needs targetUin param
    "QCloudAccessKeyLastUsed",    # needs secretIdList param
    "QCloudResourceTag",          # tag data
    "QCloudUserToAccessKey",      # association table
}


def parse_entity_keys(filepath):
    """Parse entity -> key field mapping from file."""
    entity_keys = {}
    with open(filepath, "r", encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line or "|" not in line:
                continue
            entity, key = line.split("|", 1)
            entity_keys[entity.strip()] = key.strip()
    return entity_keys


def parse_client_methods(filepath):
    """Extract entity type -> actual method name from QCloudClient.java.

    Returns dict: entity_type -> full_method_name (e.g. 'QCloudUser' -> 'listUsers')
    """
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    # Match: public List<EntityType> methodName(...)
    pattern = r'public List<(QCloud\w+)>\s+(list\w+)\s*\('
    matches = re.findall(pattern, content)
    return {m[0]: m[1] for m in matches}


def get_entity_short_name(entity):
    """Remove QCloud prefix to get short name: QCloudCvm -> Cvm, QCloudSecurityGroup -> SecurityGroup."""
    return entity.replace("QCloud", "")


def make_sync_method_name(entity):
    """QCloudCvm -> syncCvm, QCloudSecurityGroup -> syncSecurityGroup."""
    short = get_entity_short_name(entity)
    return "sync" + short[0].upper() + short[1:]


def make_mapper_var(entity):
    """QCloudCvm -> qCloudCvmMapper."""
    return entity[0].lower() + entity[1:] + "Mapper"


def make_mapper_type(entity):
    """QCloudCvm -> QCloudCvmMapper."""
    return entity + "Mapper"


def make_getter(key_field):
    """Convert key field name to Lombok @Data getter.

    Lombok @Data generates getters as follows:
    - If the first TWO chars are uppercase (e.g. SdkAppId), the getter is getSdkAppId (same)
    - If the first char is lowercase (e.g. name), the getter is getName (capitalize first)
    - If the first char is uppercase (e.g. InstanceId), the getter is getInstanceId (same)
    """
    if len(key_field) >= 2 and key_field[0].isupper() and key_field[1].isupper():
        # Starts with two uppercase letters like "DK" in some hypothetical field
        # For SdkAppId: first char 'S' upper, second 'd' lower -> capitalize first only
        return "get" + key_field
    elif key_field[0].islower():
        # Lombok @Data: for a field named "name", the getter is "getName"
        # Lombok always capitalizes the first letter of the field name for the getter
        return "get" + key_field[0].upper() + key_field[1:]
    else:
        return "get" + key_field


def check_entity_has_key(entity, key_field):
    """Check if the entity java file actually has the key field declared."""
    filepath = os.path.join(ENTITY_DIR, entity + ".java")
    if not os.path.exists(filepath):
        return False
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    # Look for field declaration: private Type keyField;
    pattern = r'private\s+\w+\s+' + re.escape(key_field) + r'\s*;'
    return bool(re.search(pattern, content))


def get_key_field_type(entity, key_field):
    """Get the type of the key field in the entity class."""
    filepath = os.path.join(ENTITY_DIR, entity + ".java")
    if not os.path.exists(filepath):
        return "String"
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    pattern = r'private\s+(\w+)\s+' + re.escape(key_field) + r'\s*;'
    m = re.search(pattern, content)
    if m:
        return m.group(1)
    return "String"


def generate_sync_method(entity, key, getter, client_method_name, mapper_var, key_type):
    """Generate lines for a single sync method."""
    lines = []
    short_name = get_entity_short_name(entity)

    # Determine how to handle the key value for Map operations
    # If key_type is Long/Integer/etc, we need String.valueOf()
    is_numeric_key = key_type in ("Long", "Integer", "Long", "int", "long", "Integer")

    # For filter: e -> e.getter() != null
    filter_expr = f"e.{getter}()"

    # For toMap key: needs to produce a String
    if is_numeric_key:
        key_expr = f"e -> String.valueOf(e.{getter}())"
        key_ref = f"e -> String.valueOf(e.{getter}())"
        contains_key = f"String.valueOf(e.{getter}())"
    else:
        key_expr = f"{entity}::{getter}"
        key_ref = f"{entity}::{getter}"
        contains_key = f"e.{getter}()"

    lines.append("")
    lines.append(f"    // ==================== {short_name.upper()} ====================\n")
    lines.append(f"    private int sync{short_name}(QCloudClient qCloudClient, CloudConf cloudConf) {{")
    lines.append(f"        List<{entity}> apiList = qCloudClient.{client_method_name}();")
    lines.append(f"        List<{entity}> dbList = {mapper_var}.selectByConfName(cloudConf.getName());")
    lines.append("")

    if is_numeric_key:
        lines.append(f"        Map<String, {entity}> apiMap = apiList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null)")
        lines.append(f"                .collect(Collectors.toMap(e -> String.valueOf(e.{getter}()), e -> e, (a, b) -> a));")
        lines.append(f"        Map<String, {entity}> dbMap = dbList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null)")
        lines.append(f"                .collect(Collectors.toMap(e -> String.valueOf(e.{getter}()), e -> e, (a, b) -> a));")
        lines.append("")
        lines.append(f"        List<{entity}> toInsert = apiList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null && !dbMap.containsKey(String.valueOf(e.{getter}())))")
        lines.append(f"                .collect(Collectors.toList());")
    else:
        lines.append(f"        Map<String, {entity}> apiMap = apiList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null)")
        lines.append(f"                .collect(Collectors.toMap({key_expr}, e -> e, (a, b) -> a));")
        lines.append(f"        Map<String, {entity}> dbMap = dbList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null)")
        lines.append(f"                .collect(Collectors.toMap({key_expr}, e -> e, (a, b) -> a));")
        lines.append("")
        lines.append(f"        List<{entity}> toInsert = apiList.stream()")
        lines.append(f"                .filter(e -> {filter_expr} != null && !dbMap.containsKey({contains_key}))")
        lines.append(f"                .collect(Collectors.toList());")

    lines.append(f"        Set<String> toDeleteIds = dbMap.keySet().stream()")
    lines.append(f"                .filter(id -> !apiMap.containsKey(id))")
    lines.append(f"                .collect(Collectors.toSet());")
    lines.append("")
    lines.append("        int insertCount = 0;")
    lines.append("        if (!toInsert.isEmpty()) {")
    lines.append(f"            insertCount = {mapper_var}.insertBatch(toInsert);")
    lines.append("        }")
    lines.append("        if (!toDeleteIds.isEmpty()) {")
    lines.append(f"            LambdaUpdateWrapper<{entity}> uw = new LambdaUpdateWrapper<>();")
    lines.append(f"            uw.eq({entity}::getConfName, cloudConf.getName())")
    lines.append(f"                    .in({entity}::{getter}, toDeleteIds)")
    lines.append(f"                    .set({entity}::getDeleted, 1);")
    lines.append(f"            {mapper_var}.update(null, uw);")
    lines.append("        }")
    lines.append("        return insertCount;")
    lines.append("    }")
    return lines


def generate_service(entity_keys, client_methods):
    lines = []

    # License header
    lines.append("""/*
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */""")

    # Package and imports
    lines.append("""package com.linjicong.cloud.stat.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 腾讯云服务实现类
 * 实现腾讯云资源的同步功能
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudService implements CloudService {
""")

    # Collect all entities that exist
    all_entities = sorted(entity_keys.keys())

    # Generate mapper injections for ALL entities
    lines.append("    // ==================== Mapper Injections ====================\n")
    for entity in all_entities:
        var = make_mapper_var(entity)
        typ = make_mapper_type(entity)
        lines.append(f"    @Resource\n    private {typ} {var};")

    # Determine which entities get sync methods
    sync_entities = []
    for entity in all_entities:
        if entity not in client_methods:
            continue
        if entity in SKIP_ENTITIES:
            continue
        if entity not in entity_keys:
            continue
        key = entity_keys[entity]
        if not check_entity_has_key(entity, key):
            continue
        sync_entities.append(entity)

    # Generate syncEcs method
    lines.append("")
    lines.append("""    /**
     * 同步所有腾讯云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        QCloudClient qCloudClient = new QCloudClient(cloudConf);
        int total = 0;
""")

    for entity in sync_entities:
        method = make_sync_method_name(entity)
        lines.append(f"        total += {method}(qCloudClient, cloudConf);")

    lines.append("        return total;")
    lines.append("    }")

    # Generate individual sync methods
    for entity in sync_entities:
        key = entity_keys[entity]
        getter = make_getter(key)
        client_method_name = client_methods[entity]  # actual method name from client
        mapper_var = make_mapper_var(entity)
        key_type = get_key_field_type(entity, key)

        method_lines = generate_sync_method(entity, key, getter, client_method_name, mapper_var, key_type)
        lines.extend(method_lines)

    lines.append("}")
    return "\n".join(lines)


def main():
    print("Parsing entity-key mappings...")
    entity_keys = parse_entity_keys(ENTITY_KEYS_FILE)
    print(f"  Found {len(entity_keys)} entities")

    print("Parsing QCloudClient methods...")
    client_methods = parse_client_methods(CLIENT_FILE)
    print(f"  Found {len(client_methods)} client methods")

    print("Generating QCloudService.java...")
    content = generate_service(entity_keys, client_methods)

    os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)
    with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
        f.write(content)
    print(f"Written to {OUTPUT_FILE}")

    # Count sync methods
    sync_count = content.count("total += ")
    mapper_count = content.count("@Resource")
    print(f"Generated {mapper_count} mapper injections, {sync_count} sync methods")


if __name__ == "__main__":
    main()
