#!/usr/bin/env python3
"""
QCloudService 拆分脚本
将 7416 行的 QCloudService.java 按资源类型拆分为多个子服务类。

拆分策略：
- QCloudService (主服务) 保留 syncEcs 方法，委托调用子服务
- 按资源类型分为约 10 个子服务类
- 每个子服务注入自己需要的 Mapper
"""

import re
import os
from collections import defaultdict, OrderedDict

SOURCE_FILE = r"D:\project-java\mixed-cloud-resource-stat\backend\src\main\java\com\linjicong\cloud\stat\service\QCloudService.java"
OUTPUT_DIR = r"D:\project-java\mixed-cloud-resource-stat\backend\src\main\java\com\linjicong\cloud\stat\service\qcloud"

# 资源类型分类映射: resource_name -> category
CATEGORY_MAP = {
    # ---- Compute (计算) ----
    "Cvm": "Compute", "GpuCvm": "Compute", "FpgaCvm": "Compute",
    "CvmDedicated": "Compute", "HpcCluster": "Compute", "HpcPlatform": "Compute",
    "AS": "Compute", "Lighthouse": "Compute", "BMS": "Compute",
    "EMR": "Compute", "TKE": "Compute", "Scf": "Compute",
    "NativeBuild": "Compute", "TSF": "Compute", "TCB": "Compute",
    "Ecm": "Compute", "Desktop": "Compute",

    # ---- Storage (存储) ----
    "Cbs": "Storage", "Cos": "Storage", "Cfs": "Storage",
    "CHDFS": "Storage", "GooseFS": "Storage", "Ci": "Storage",

    # ---- Network (网络) ----
    "Vpc": "Network", "VpcSubnet": "Network", "Eip": "Network",
    "Clb": "Network", "CLB_gw": "Network", "NatGateway": "Network",
    "CdnDomain": "Network", "Gaap": "Network", "GaapV2": "Network",
    "DC": "Network", "GTM": "Network", "EO": "Network",
    "FlowLog": "Network", "BandwidthPackage": "Network",
    "TrafficPackage": "Network", "Ipv6": "Network", "Cc": "Network",
    "Vpn": "Network", "Peering": "Network", "Sdwan": "Network",
    "WsA": "Network", "Scdn": "Network", "DedicatedZone": "Network",
    "EdgeZone": "Network", "DistID": "Network", "RegionMgr": "Network",

    # ---- Database (数据库) ----
    "Cdb": "Database", "CynosDB": "Database", "DCDB": "Database",
    "MongoDb": "Database", "MongoDB_CKafka": "Database", "Redis": "Database",
    "Tendis": "Database", "MariaDb": "Database", "Postgresql": "Database",
    "Sqlserver": "Database", "ES": "Database", "CTSDB": "Database",
    "HBase": "Database", "KeeWiDB": "Database", "Memcached": "Database",
    "Ckafka": "Database", "TDMQ": "Database", "RabbitMQ": "Database",
    "RocketMQ": "Database", "TCHouseC": "Database", "TCHouseD": "Database",
    "TCHouseP": "Database", "Oceanus": "Database", "DLC": "Database",
    "TcaplusDB": "Database", "TdsqlBoundless": "Database",
    "TdsqlDistributed": "Database", "VectorDb": "Database",

    # ---- Security (安全) ----
    "DDoS": "Security", "WAF": "Security", "SecurityGroup": "Security",
    "KMS": "Security", "HSM": "Security", "CloudHSM": "Security",
    "SSL": "Security", "SSLPod": "Security", "CACert": "Security",
    "SafeAudio": "Security", "SafeCenter": "Security", "SafeDoc": "Security",
    "SafeGuard": "Security", "SafeImage": "Security", "SafeMonitor": "Security",
    "SafePlatform": "Security", "SafeText": "Security", "SafeVideo": "Security",
    "ContentSafe": "Security", "VulnMgr": "Security", "PenTest": "Security",
    "RiskIdentify": "Security", "ExposedMgr": "Security", "DeviceSafety": "Security",
    "DataAudit": "Security", "DataSafeGov": "Security", "SecCredential": "Security",
    "SecretMgr": "Security", "MiniSafe": "Security", "CAPTCHA": "Security",
    "ThreatIntel": "Security", "AntiFraud": "Security", "Cfw": "Security",

    # ---- AI (人工智能) ----
    "ASR": "AI", "Face": "AI", "FaceDeform": "AI", "FaceFusion": "AI",
    "FaceMakeup": "AI", "FaceSwap": "AI", "ImageProcess2": "AI",
    "ImageSearch": "AI", "OCR": "AI", "NMT": "AI", "TTS": "AI",
    "TI": "AI", "TIHai": "AI", "SpokenEval": "AI", "ContentRecognize": "AI",
    "ESign": "AI", "MathGrade": "AI", "KnowledgeEngine": "AI",
    "EngWrite": "AI", "VoiceClone": "AI", "SmartMedia": "AI", "MediaAi": "AI",

    # ---- Media (媒体) ----
    "CSS": "Media", "Live": "Media", "Live2": "Media", "VOD": "Media",
    "VODMedia": "Media", "VODProcess": "Media", "TRTC": "Media",
    "TRTCRoom": "Media", "MediaAsset": "Media", "Mps": "Media",
    "EnhanceMedia": "Media", "AppRender": "Media", "GHPhone": "Media",

    # ---- Application (应用服务) ----
    "APIGW": "Application", "AgentGW": "Application", "AgentPlatform": "Application",
    "Audit": "Application", "BI": "Application", "Bastion": "Application",
    "BizProcess": "Application", "CHC": "Application", "CSP": "Application",
    "CSPGateway": "Application", "CWP": "Application", "CWP3": "Application",
    "CloudBase": "Application", "CloudContact": "Application",
    "CloudPhone": "Application", "CloudStudio": "Application",
    "Cmq": "Application", "CodingDevops": "Application", "Config": "Application",
    "ControlCenter": "Application", "DnsDomain": "Application",
    "DNSPrivate": "Application", "DNSSec": "Application", "PrivDNS": "Application",
    "DocProcess": "Application", "Docs": "Application", "Domain": "Application",
    "DomainReg": "Application", "EventBus": "Application",
    "GameAntiACE": "Application", "GameDB": "Application",
    "GameServer": "Application", "GameVoice": "Application", "GSE": "Application",
    "HealthDash": "Application", "HealthOmics": "Application",
    "HealthReport2": "Application", "ICPBeian": "Application", "IOA": "Application",
    "IoT": "Application", "IoTDevice": "Application", "IoTHub": "Application",
    "Mail": "Application", "MallTraffic": "Application", "Meeting": "Application",
    "MicroWeda": "Application", "Monitor": "Application", "Org": "Application",
    "RTIEdu": "Application", "RTIIndustrial": "Application",
    "SES": "Application", "SMS": "Application", "SmartAdvisor": "Application",
    "SmartGuide": "Application", "SmartView": "Application",
    "SmsSign": "Application", "SmsTemplate": "Application",
    "TAPD": "Application", "TBAAS": "Application", "TCR": "Application",
    "TcrEnt": "Application", "TSE": "Application", "TencentConnect": "Application",
    "TokenHub": "Application", "TourismBigdata": "Application",
    "User": "Application", "VoiceMsg": "Application", "WeData": "Application",
    "WeLink": "Application", "WebSearch": "Application", "Weda": "Application",
    "Im": "Application",
}

LICENSE_HEADER = """/*
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
 */
"""

# Max lines per sub-service file
MAX_LINES = 480
# Overhead lines (package, imports, class decl, mappers, javadoc, closing brace)
OVERHEAD_LINES = 30
# Average lines per sync method (including section comment)
LINES_PER_METHOD = 35
# Methods per file based on budget
METHODS_PER_FILE = max(1, (MAX_LINES - OVERHEAD_LINES) // LINES_PER_METHOD)


def parse_mapper_fields(content):
    """Extract all @Resource mapper field declarations."""
    pattern = r'@Resource\s+private\s+(QCloud\w+Mapper)\s+(\w+);'
    return re.findall(pattern, content)


def parse_sync_methods(content):
    """Extract all private sync methods with their full body."""
    all_starts = []
    for m in re.finditer(r'private int (sync\w+)\(QCloudClient qCloudClient, CloudConf cloudConf\) \{', content):
        all_starts.append((m.group(1), m.start()))

    methods = []
    for i, (method_name, start_pos) in enumerate(all_starts):
        brace_start = content.index('{', start_pos)
        depth = 1
        pos = brace_start + 1
        while depth > 0 and pos < len(content):
            if content[pos] == '{':
                depth += 1
            elif content[pos] == '}':
                depth -= 1
            pos += 1

        # Grab section comment before method
        prev_end = content.rfind('}', 0, start_pos - 1)
        comment_line = ""
        if prev_end > 0:
            between = content[prev_end + 1:start_pos]
            comment_match = re.search(r'(// =+ \w+ =+\s*\n)', between)
            if comment_match:
                comment_line = comment_match.group(1)

        method_body = (comment_line + content[start_pos:pos]).rstrip()
        methods.append((method_name, method_body))

    return methods


def extract_resource_name(method_name):
    return method_name[4:]


def determine_mapper_for_method(method_body):
    mapper_match = re.search(r'(qCloud\w+Mapper)\.', method_body)
    return mapper_match.group(1) if mapper_match else None


def categorize_resource(resource_name):
    if resource_name in CATEGORY_MAP:
        return CATEGORY_MAP[resource_name]
    for key, cat in CATEGORY_MAP.items():
        if key in resource_name or resource_name in key:
            return cat
    return "Application"


def build_service_class_content(class_name, category_label, methods, mapper_fields_needed):
    """Generate a sub-service Java class content string."""
    mapper_lines = []
    for mapper_type, field_name in sorted(set(mapper_fields_needed)):
        mapper_lines.append(f"    @Resource")
        mapper_lines.append(f"    private {mapper_type} {field_name};")
    mapper_block = "\n".join(mapper_lines)

    method_bodies = []
    for method_name, method_body in methods:
        modified_body = method_body.replace("private int ", "public int ", 1)
        lines = modified_body.split('\n')
        fixed_lines = []
        for line in lines:
            stripped = line.strip()
            if stripped.startswith('// ===='):
                fixed_lines.append('    ' + stripped)
            elif stripped == '':
                fixed_lines.append('')
            elif stripped.startswith('public int '):
                fixed_lines.append('    ' + stripped)
            else:
                if line and not line.startswith('    '):
                    fixed_lines.append('    ' + line)
                else:
                    fixed_lines.append(line)
        method_bodies.append('\n'.join(fixed_lines))

    methods_block = "\n\n".join(method_bodies)

    imports = sorted({
        "import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;",
        "import com.linjicong.cloud.stat.client.QCloudClient;",
        "import com.linjicong.cloud.stat.dao.entity.CloudConf;",
        "import com.linjicong.cloud.stat.dao.entity.qcloud.*;",
        "import com.linjicong.cloud.stat.dao.mapper.qcloud.*;",
        "import org.springframework.stereotype.Service;",
        "import jakarta.annotation.Resource;",
        "import java.util.List;",
        "import java.util.Map;",
        "import java.util.Set;",
        "import java.util.stream.Collectors;",
    })
    imports_block = "\n".join(imports)

    resource_names = [extract_resource_name(m[0]) for m in methods]
    javadoc = f"""/**
 * 腾讯云{category_label}资源同步服务
 * 包含以下资源类型的同步方法: {', '.join(resource_names[:10])}{'...' if len(resource_names) > 10 else ''}
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */"""

    return f"""{LICENSE_HEADER}package com.linjicong.cloud.stat.service.qcloud;

{imports_block}

{javadoc}
@Service
public class {class_name} {{

{mapper_block}

{methods_block}
}}
"""


def generate_sub_services(category_methods, mapper_map, method_mapper_map):
    """Generate sub-service files. Returns dict: class_name -> [method_names]."""
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    # class_name -> list of method_names for main service delegation
    service_method_map = OrderedDict()

    for category, cat_methods in sorted(category_methods.items()):
        # Build full class to check line count
        mappers_needed = []
        for method_name, _ in cat_methods:
            mf = method_mapper_map.get(method_name)
            if mf and mf in mapper_map:
                mappers_needed.append((mapper_map[mf], mf))

        full_content = build_service_class_content(
            f"QCloud{category}Service", category, cat_methods, mappers_needed
        )
        full_lines = full_content.count('\n') + 1

        if full_lines <= MAX_LINES:
            # Single file
            class_name = f"QCloud{category}Service"
            output_file = os.path.join(OUTPUT_DIR, f"{class_name}.java")
            with open(output_file, 'w', encoding='utf-8') as f:
                f.write(full_content)
            print(f"  {class_name}.java ({full_lines} lines, {len(cat_methods)} methods)")
            service_method_map[class_name] = [m[0] for m in cat_methods]
        else:
            # Split into parts
            parts = [cat_methods[i:i + METHODS_PER_FILE]
                     for i in range(0, len(cat_methods), METHODS_PER_FILE)]
            for part_idx, part_methods in enumerate(parts):
                part_mappers = []
                for method_name, _ in part_methods:
                    mf = method_mapper_map.get(method_name)
                    if mf and mf in mapper_map:
                        part_mappers.append((mapper_map[mf], mf))

                if len(parts) > 1:
                    class_name = f"QCloud{category}ServicePart{part_idx + 1}"
                    cat_label = f"{category} Part{part_idx + 1}"
                else:
                    class_name = f"QCloud{category}Service"
                    cat_label = category

                part_content = build_service_class_content(
                    class_name, cat_label, part_methods, part_mappers
                )
                output_file = os.path.join(OUTPUT_DIR, f"{class_name}.java")
                with open(output_file, 'w', encoding='utf-8') as f:
                    f.write(part_content)
                actual_lines = part_content.count('\n') + 1
                print(f"  {class_name}.java ({actual_lines} lines, {len(part_methods)} methods)")
                service_method_map[class_name] = [m[0] for m in part_methods]

    return service_method_map


def generate_main_service(service_method_map):
    """Generate the main QCloudService.java with delegation to sub-services."""
    import_lines = []
    field_lines = []
    call_lines = []

    for class_name, method_names in service_method_map.items():
        import_lines.append(f"import com.linjicong.cloud.stat.service.qcloud.{class_name};")
        field_name = class_name[0].lower() + class_name[1:]
        field_lines.append(f"    @Resource")
        field_lines.append(f"    private {class_name} {field_name};")
        for method_name in method_names:
            call_lines.append(f"        total += {field_name}.{method_name}(qCloudClient, cloudConf);")

    imports_block = "\n".join(import_lines)
    fields_block = "\n".join(field_lines)
    calls_block = "\n".join(call_lines)

    return f"""{LICENSE_HEADER}package com.linjicong.cloud.stat.service;

import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
{imports_block}
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 腾讯云服务实现类
 * 实现腾讯云资源的同步功能，通过委托子服务类实现具体同步逻辑
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudService implements CloudService {{

    // ==================== Sub-Service Injections ====================

{fields_block}

    /**
     * 同步所有腾讯云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {{
        QCloudClient qCloudClient = new QCloudClient(cloudConf);
        int total = 0;

{calls_block}

        return total;
    }}
}}
"""


def main():
    with open(SOURCE_FILE, 'r', encoding='utf-8') as f:
        content = f.read()

    mapper_fields = parse_mapper_fields(content)
    mapper_map = {field_name: mapper_type for mapper_type, field_name in mapper_fields}

    methods = parse_sync_methods(content)
    print(f"Parsed {len(methods)} sync methods")

    category_methods = defaultdict(list)
    method_mapper_map = {}

    for method_name, method_body in methods:
        resource_name = extract_resource_name(method_name)
        category = categorize_resource(resource_name)
        category_methods[category].append((method_name, method_body))
        mapper = determine_mapper_for_method(method_body)
        if mapper:
            method_mapper_map[method_name] = mapper

    print(f"Categories: {sorted(category_methods.keys())}")
    for cat in sorted(category_methods.keys()):
        print(f"  {cat}: {len(category_methods[cat])} methods")

    # Generate sub-service files
    print("\nGenerating sub-service files:")
    service_method_map = generate_sub_services(category_methods, mapper_map, method_mapper_map)

    # Generate main service
    main_content = generate_main_service(service_method_map)

    # Backup original
    backup_file = SOURCE_FILE + ".bak"
    if not os.path.exists(backup_file):
        with open(backup_file, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"\nBacked up original to {backup_file}")

    main_file = os.path.join(os.path.dirname(SOURCE_FILE), "QCloudService.java")
    with open(main_file, 'w', encoding='utf-8') as f:
        f.write(main_content)
    main_lines = main_content.count('\n') + 1
    print(f"\nGenerated QCloudService.java ({main_lines} lines)")

    # Verify counts
    total_calls = sum(len(m) for m in service_method_map.values())
    print(f"\n=== Summary ===")
    print(f"Total sync methods: {len(methods)}")
    print(f"Sub-service files: {len(service_method_map)}")
    print(f"Delegation calls in main: {total_calls}")
    print(f"Output directory: {OUTPUT_DIR}")


if __name__ == "__main__":
    main()
