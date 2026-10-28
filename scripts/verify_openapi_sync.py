"""OpenAPI 同步一致性校验。

扫描 backend Controller 源码，与 docs/api/openapi.yaml 比对：
1) 接口集合（path + method）是否一致；
2) 各接口的 query/path 参数名是否一致；
3) 资源查询 type 枚举是否完整覆盖代码 case 分支；
4) 是否存在 YAML 误解析（flow mapping 值被解析成 None / dict 而非预期）。

用法：python scripts/verify_openapi_sync.py
"""
import re
import sys
from pathlib import Path

import yaml

ROOT = Path(__file__).resolve().parents[1]
CTRL_DIR = ROOT / "backend/src/main/java/com/linjicong/cloud/stat/controller"
SPEC = ROOT / "docs/api/openapi.yaml"

MAPPING_RE = re.compile(r"@(Get|Post|Put|Delete)Mapping(?:\(([^)]*)\))?")
CLASS_PREFIX_RE = re.compile(r'@RequestMapping\("([^"]*)"\)')
SIG_HEAD_RE = re.compile(r"public\s+ResponseEntity\b", re.S)
PARAM_RE = re.compile(r"@(RequestParam|PathVariable)(?:\(([^)]*)\))?\s+[\w<>\[\], .]+\s+(\w+)")


def extract_param_list(src, head_end):
    """从方法签名 'public ResponseEntity' 之后，按括号配对截取参数串。"""
    open_idx = src.index("(", head_end)
    depth = 0
    for i in range(open_idx, len(src)):
        ch = src[i]
        if ch == "(":
            depth += 1
        elif ch == ")":
            depth -= 1
            if depth == 0:
                return src[open_idx + 1: i]
    return ""


def scan_controllers():
    """返回 {(path, method): {'name':..., 'params':[...], }}。"""
    endpoints = {}
    for f in sorted(CTRL_DIR.glob("*.java")):
        src = f.read_text(encoding="utf-8")
        m = CLASS_PREFIX_RE.search(src)
        prefix = m.group(1) if m else ""
        for mm in re.finditer(r"@(Get|Post|Put|Delete)Mapping(?:\s*\(([^)]*)\))?", src):
            verb = mm.group(1).lower()
            args = (mm.group(2) or "").strip()
            path_part = ""
            if args:
                pm = re.search(r'"([^"]*)"', args)
                path_part = pm.group(1) if pm else ""
            head = SIG_HEAD_RE.search(src, mm.end())
            if not head:
                continue
            raw_params = extract_param_list(src, head.end())
            name_m = re.search(r"\s(\w+)\s*$", src[head.end(): src.index("(", head.end())])
            name = name_m.group(1) if name_m else ""
            params = [p[2] for p in PARAM_RE.findall(raw_params)]
            full = (prefix + path_part) or "/"
            full = re.sub(r"/{2,}", "/", full)
            endpoints[(full, verb)] = {"name": name, "params": params, "file": f.name}
    return endpoints


def type_cases():
    """从 ResourceController 抽取三个资源查询接口的 type 枚举。"""
    src = (CTRL_DIR / "ResourceController.java").read_text(encoding="utf-8")
    out = {}
    for meth, path in (("getHuaweiResources", "/huawei/{type}"),
                       ("getTencentResources", "/tencent/{type}"),
                       ("getAliyunResources", "/aliyun/{type}")):
        i = src.index(meth)
        j = src.index("default -> null;", i)
        out[path] = set(re.findall(r'case\s+"([a-z0-9_]+)"', src[i:j]))
    return out


def main():
    spec = yaml.safe_load(SPEC.read_text(encoding="utf-8"))
    paths = spec.get("paths", {})
    code = scan_controllers()

    spec_ops = {}
    for p, item in paths.items():
        for verb, op in item.items():
            if verb in ("get", "post", "put", "delete"):
                spec_ops[(p, verb)] = op

    problems = []

    # 1) 接口集合比对
    missing = sorted(set(code) - set(spec_ops))
    extra = sorted(set(spec_ops) - set(code))
    for k in missing:
        problems.append(f"[缺失] {k[1].upper()} {k[0]}（{code[k]['file']}#{code[k]['name']}）未写入 openapi.yaml")
    for k in extra:
        problems.append(f"[多余] {k[1].upper()} {k[0]} 存在于 openapi.yaml 但源码已不存在")

    # 2) 参数比对（仅比 query / path 参数名）
    for key in sorted(set(code) & set(spec_ops)):
        code_params = set(code[key]["params"])
        doc_params = set()
        for prm in spec_ops[key].get("parameters", []):
            if "$ref" in prm:
                ref_name = prm["$ref"].rsplit("/", 1)[-1]
                ref = spec.get("components", {}).get("parameters", {}).get(ref_name, {})
                if ref.get("name"):
                    doc_params.add(ref["name"])
            elif prm.get("name"):
                doc_params.add(prm["name"])
        if code_params != doc_params:
            only_code = code_params - doc_params
            only_doc = doc_params - code_params
            msg = f"[参数] {key[1].upper()} {key[0]}"
            if only_code:
                msg += f" 文档缺 {sorted(only_code)}"
            if only_doc:
                msg += f" 文档多 {sorted(only_doc)}"
            problems.append(msg)

    # 3) type 枚举比对
    for path, cases in type_cases().items():
        desc = spec_ops[(path, "get")].get("description", "")
        documented = set(re.findall(r"`([a-z0-9_]+)`", desc))
        # 剔除反引号里的非枚举词（如 unsupported type 之类已被正则过滤掉）
        lost = cases - documented
        if lost:
            problems.append(f"[枚举] {path} 文档缺 {len(lost)} 个 type：{sorted(lost)}")

    # 4) 误解析检查：example 中不应出现 None 值（原意是字符串 'null'）
    def walk(node, trail):
        if isinstance(node, dict):
            for k, v in node.items():
                if v is None and k in ("message", "remark", "productSub"):
                    problems.append(f"[YAML] {trail}.{k} 被解析为 null，疑似缺引号")
                walk(v, f"{trail}.{k}")
        elif isinstance(node, list):
            for idx, v in enumerate(node):
                walk(v, f"{trail}[{idx}]")

    for p, item in paths.items():
        for verb, op in item.items():
            if isinstance(op, dict):
                walk(op.get("responses", {}), f"paths.{p}.{verb}.responses")

    print(f"源码接口数: {len(code)}　文档 operation 数: {len(spec_ops)}")
    if problems:
        print("\n发现 %d 处差异：" % len(problems))
        for p in problems:
            print("  -", p)
        return 1
    print("接口集合 / 参数 / 枚举 / YAML 解析 全部一致")
    return 0


if __name__ == "__main__":
    sys.exit(main())
