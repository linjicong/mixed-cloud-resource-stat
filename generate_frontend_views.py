#!/usr/bin/env python3
"""
批量生成前端 Vue 视图文件、路由和 Store。
从 ResourceController.java 提取所有 type 值，按云厂商分组生成前端代码。
"""
import os
import re
from pathlib import Path

# ============================================================
# 1. 从 ResourceController.java 提取 type 值
# ============================================================

CONTROLLER_PATH = r"D:\project-java\mixed-cloud-resource-stat\backend\src\main\java\com\linjicong\cloud\stat\controller\ResourceController.java"

with open(CONTROLLER_PATH, "r", encoding="utf-8") as f:
    controller_content = f.read()

def extract_cases(content, provider_path):
    """提取指定 provider 路径下的所有 case 值"""
    # 找到对应 provider 的 switch 块
    pattern = rf'@GetMapping\("/{provider_path}/\{{type\}}"\).*?switch \(type\.toLowerCase\(\)\) \{{(.*?)default ->'
    match = re.search(pattern, content, re.DOTALL)
    if not match:
        return []

    switch_body = match.group(1)
    # 提取所有 case "xxx" 值（只取第一个值，忽略别名）
    cases = re.findall(r'case\s+"([^"]+)"', switch_body)
    # 去重但保持顺序
    seen = set()
    unique_cases = []
    for c in cases:
        if c not in seen:
            seen.add(c)
            unique_cases.append(c)
    return unique_cases

huawei_types = extract_cases(controller_content, "huawei")
tencent_types = extract_cases(controller_content, "tencent")
aliyun_types = extract_cases(controller_content, "aliyun")

print(f"华为云类型数: {len(huawei_types)}")
print(f"腾讯云类型数: {len(tencent_types)}")
print(f"阿里云类型数: {len(aliyun_types)}")

# ============================================================
# 2. 资源类型中文名称映射
# ============================================================

HUAWEI_NAMES = {
    "ecs": "云服务器 ECS",
    "rds": "云数据库 RDS",
    "elb": "弹性负载均衡 ELB",
    "evs": "云硬盘 EVS",
    "vpc": "虚拟私有云 VPC",
    "eip": "弹性公网IP EIP",
    "ims": "镜像服务 IMS",
    "cbr": "云备份 CBR",
    "bills": "账单",
    "dcs": "分布式缓存 DCS",
    "dds": "文档数据库 DDS",
    "obs": "对象存储 OBS",
    "sfs": "弹性文件服务 SFS",
    "ces_metric": "云监控指标 CES",
    "dns_private": "私有DNS",
    "dns_record_sets": "DNS记录集",
    "cce": "云容器引擎 CCE",
    "auth_domain": "认证域名",
    "user": "IAM用户",
    "access_key": "访问密钥",
    "resource": "资源管理 RMS",
    "bills_fee_records": "费用记录",
    "resource_record_detail": "资源记录详情",
    "nat": "NAT网关",
    "functiongraph": "函数工作流 FunctionGraph",
    "vpn": "虚拟专用网络 VPN",
    "gaussdb": "高斯数据库 GaussDB",
    "kms": "密钥管理 KMS",
    "waf": "Web应用防火墙 WAF",
    "cts": "云审计 CTS",
    "kafka": "分布式消息 Kafka",
    "rocketmq": "消息队列 RocketMQ",
    "rabbitmq": "消息队列 RabbitMQ",
    "lts": "云日志服务 LTS",
    "cdn": "内容分发 CDN",
    "antiddos": "Anti-DDoS",
    "hss": "主机安全 HSS",
    "swr": "容器镜像 SWR",
    "smn": "消息通知 SMN",
    "apig": "API网关 APIG",
    "aom": "应用运维管理 AOM",
    "css": "云搜索服务 CSS",
    "cfw": "云防火墙 CFW",
    "ccm": "证书管理 CCM",
    "drs": "数据复制 DRS",
    "mrs": "MapReduce MRS",
    "as": "弹性伸缩 AS",
    "bms": "裸金属 BMS",
    "workspace": "云桌面 Workspace",
    "dli": "数据湖 DLI",
    "dws": "数据仓库 DWS",
    "gaussdbnosql": "GaussDB NoSQL",
    "gaussdbopengauss": "GaussDB OpenGauss",
    "ddm": "分布式数据库 DDM",
    "cse": "微服务引擎 CSE",
    "servicestage": "ServiceStage",
    "cbh": "云堡垒机 CBH",
    "dbss": "数据库安全 DBSS",
    "vod": "视频点播 VOD",
    "live": "视频直播 Live",
    "oms": "对象存储迁移 OMS",
    "sdrs": "容灾 SDRS",
    "sms": "短信服务 SMS",
    "dsc": "数据安全中心 DSC",
    "roma": "应用集成 ROMA",
    "cph": "云手机 CPH",
    "er": "企业路由器 ER",
    "vpcep": "VPC终端节点 VPCEP",
    "ief": "智能边缘 IEF",
    "iotda": "物联网 IoTDA",
    "deh": "专属主机 DEH",
    "bcs": "区块链 BCS",
    "dc": "云专线 DC",
    "ga": "全球加速 GA",
    "eg": "事件网格 EG",
    "apm": "应用性能 APM",
    "cloudtable": "表格存储 CloudTable",
    "dataartsstudio": "数据工坊 DataArts",
    "dis": "数据接入 DIS",
    "mas": "多活 MAS",
    "mpc": "媒体处理 MPC",
    "clouddc": "云化数据中心 CloudDC",
    "kvs": "键值存储 KVS",
    "eds": "云桌面 EDS",
    "tics": "威胁情报 TICS",
    "organizations": "组织 Organizations",
    "ram": "资源访问管理 RAM",
    "coc": "运维编排 COC",
}

TENCENT_NAMES = {
    "cvm": "云服务器 CVM",
    "cdb": "云数据库 CDB",
    "clb": "负载均衡 CLB",
    "cbs": "云硬盘 CBS",
    "bills": "账单",
    "vpc": "私有网络 VPC",
    "subnet": "子网",
    "eip": "弹性公网IP EIP",
    "sg": "安全组",
    "cdn": "CDN",
    "redis": "云数据库 Redis",
    "mongodb": "云数据库 MongoDB",
    "cynosdb": "TDSQL-C CynosDB",
    "postgresql": "云数据库 PostgreSQL",
    "sqlserver": "云数据库 SQL Server",
    "nat": "NAT网关",
    "cfs": "文件存储 CFS",
    "dns": "DNS",
    "cos": "对象存储 COS",
    "scf": "无服务器函数 SCF",
    "mariadb": "云数据库 MariaDB",
    "dcdb": "分布式数据库 DCDB",
    "ckafka": "消息队列 CKafka",
    "rocketmq": "消息队列 RocketMQ",
    "ssl": "SSL证书",
    "waf": "Web应用防火墙 WAF",
    "cls": "日志服务 CLS",
    "monitor": "云监控",
    "domain": "域名",
    "tke": "容器服务 TKE",
    "tcr": "容器镜像 TCR",
    "es": "Elasticsearch",
    "memcached": "云缓存 Memcached",
    "keewidb": "轻量数据库 KeeWiDB",
    "ctsdb": "时序数据库 CTSDB",
    "chdfs": "云HDFS",
    "as": "弹性伸缩 AS",
    "lighthouse": "轻量应用服务器",
    "dc": "云专线 DC",
    "rabbitmq": "消息队列 RabbitMQ",
    "apigw": "API网关",
    "bms": "黑石物理服务器",
    "tdmq": "TDMQ",
    "oceanus": "流计算 Oceanus",
    "emr": "弹性 MapReduce",
    "gaap": "全球应用加速 GAAP",
    "agentgw": "Agent网关",
    "agentplatform": "Agent平台",
    "apprender": "应用渲染",
    "asr": "语音识别 ASR",
    "audit": "云审计",
    "bi": "商业智能 BI",
    "bastion": "堡垒机",
    "bizprocess": "流程服务",
    "cacert": "CA证书",
    "captcha": "验证码",
    "chc": "黑石 CHC",
    "clbgw": "CLB网关",
    "cloudbase": "云开发 CloudBase",
    "cloudcontact": "云联络中心",
    "cloudhsm": "云HSM",
    "cloudphone": "云手机",
    "cloudstudio": "Cloud Studio",
    "cmq": "消息队列 CMQ",
    "codingdevops": "CODING DevOps",
    "config": "配置管理",
    "contentrecognize": "内容识别",
    "contentsafe": "内容安全",
    "controlcenter": "控制中心",
    "csp": "云服务代理",
    "cspgateway": "CSP网关",
    "css": "云直播 CSS",
    "cwp": "主机安全",
    "cwp3": "主机安全V3",
    "ddos": "DDoS防护",
    "dlc": "数据湖 DLC",
    "dnsprivate": "私有DNS",
    "dnssec": "DNSSEC",
    "dataaudit": "数据审计",
    "datasafegov": "数据安全治理",
    "devicesafety": "设备安全",
    "distid": "分发ID",
    "docprocess": "文档处理",
    "docs": "腾讯文档",
    "domainreg": "域名注册",
    "eo": "边缘安全 EO",
    "esign": "电子签名",
    "engwrite": "智能写作",
    "eventbus": "事件总线",
    "exposedmgr": "暴露面管理",
    "face": "人脸识别",
    "facefusion": "人脸融合",
    "facemakeup": "人脸美妆",
    "faceswap": "人脸换脸",
    "gameantiace": "游戏反作弊",
    "gamedb": "游戏数据库",
    "gameserver": "游戏服务器",
    "gamevoice": "游戏语音",
    "gse": "游戏服务器引擎",
    "gtm": "流量管理 GTM",
    "ghphone": "GHPhone",
    "hbase": "HBase",
    "hsm": "密钥管理 HSM",
    "healthdash": "健康看板",
    "healthomics": "健康组学",
    "healthreport": "健康报告",
    "icpbeian": "ICP备案",
    "ioa": "零信任 IOA",
    "imageprocess": "图像处理",
    "imagesearch": "图片搜索",
    "iot": "物联网 IoT",
    "iotdevice": "IoT设备",
    "iothub": "IoT Hub",
    "kms": "密钥管理 KMS",
    "knowledgeengine": "知识引擎",
    "live": "直播",
    "live2": "直播V2",
    "mail": "邮件推送",
    "malltraffic": "商城流量",
    "mathgrade": "数学评分",
    "mediaasset": "媒体资产管理",
    "meeting": "腾讯会议",
    "microweda": "微搭低代码",
    "minisafe": "小程序安全",
    "mongodbckafka": "MongoDB-Kafka",
    "nmt": "机器翻译 NMT",
    "nativebuild": "云原生构建",
    "ocr": "OCR识别",
    "org": "组织管理",
    "pentest": "渗透测试",
    "privdns": "私有DNS",
    "regionmgr": "区域管理",
    "riskidentify": "风险识别",
    "rtiedu": "教育RTI",
    "rtiindustrial": "工业RTI",
    "safeaudio": "音频安全",
    "safecenter": "安全中心",
    "safedoc": "文档安全",
    "safeguard": "安全卫士",
    "safeimage": "图片安全",
    "safemonitor": "安全监控",
    "safeplatform": "安全平台",
    "safetext": "文本安全",
    "safevideo": "视频安全",
    "seccredential": "安全凭证",
    "secretmgr": "密钥管理",
    "ses": "简单邮件服务",
    "smartadvisor": "智能顾问",
    "smartguide": "智能导览",
    "smartview": "智能视图",
    "sms": "短信服务",
    "smssign": "短信签名",
    "smstemplate": "短信模板",
    "spokeneval": "口语评测",
    "sslpod": "SSL监测",
    "tapd": "TAPD",
    "tbaas": "TBaaS区块链",
    "tcb": "云开发 TCB",
    "tcaplusdb": "TcaplusDB",
    "tcrent": "TCR企业版",
    "tencentconnect": "企业微信",
    "tendis": "Tendis",
    "tchousec": "TCHouse-C",
    "tchoused": "TCHouse-D",
    "tchousep": "TCHouse-P",
    "ti": "钛",
    "tihai": "TI Hai",
    "tokenhub": "Token Hub",
    "tourismbigdata": "旅游大数据",
    "trtc": "实时音视频 TRTC",
    "trtcroom": "TRTC房间",
    "tse": "微服务引擎 TSE",
    "tsf": "微服务平台 TSF",
    "tts": "语音合成 TTS",
    "vod": "视频点播 VOD",
    "vodmedia": "VOD媒体",
    "vodprocess": "VOD处理",
    "voiceclone": "声音复刻",
    "voicemsg": "语音消息",
    "vulnmgr": "漏洞管理",
    "wedata": "WeData",
    "welink": "WeLink",
    "websearch": "网络搜索",
    "weda": "微搭 WeDa",
}

ALIYUN_NAMES = {
    "dns": "DNS",
}

# ============================================================
# 3. 资源类型列配置
# ============================================================

COMMON_COLUMNS = [
    {"prop": "id", "label": "ID"},
    {"prop": "name", "label": "名称"},
    {"prop": "status", "label": "状态", "type": "status"},
    {"prop": "createTime", "label": "创建时间", "type": "date"},
]

HUAWEI_COLUMN_CONFIGS = {
    "ecs": [
        {"prop": "serverId", "label": "ID"},
        {"prop": "name", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "flavorName", "label": "规格"},
        {"prop": "ipAddress", "label": "IP地址", "type": "ip"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "rds": [
        {"prop": "id", "label": "ID"},
        {"prop": "name", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "engine", "label": "引擎"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "elb": [
        {"prop": "id", "label": "ID"},
        {"prop": "name", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "evs": [
        {"prop": "id", "label": "ID"},
        {"prop": "name", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "size", "label": "容量(GB)"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "vpc": [
        {"prop": "id", "label": "ID"},
        {"prop": "name", "label": "名称"},
        {"prop": "cidr", "label": "CIDR"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "bills": [
        {"prop": "id", "label": "ID"},
        {"prop": "resourceTypeCode", "label": "资源类型"},
        {"prop": "amount", "label": "金额"},
        {"prop": "currency", "label": "货币"},
        {"prop": "billDate", "label": "账单日期", "type": "date"},
    ],
}

TENCENT_COLUMN_CONFIGS = {
    "cvm": [
        {"prop": "instanceId", "label": "ID"},
        {"prop": "instanceName", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "instanceType", "label": "规格"},
        {"prop": "privateIpAddresses", "label": "内网IP", "type": "ip"},
        {"prop": "createdTime", "label": "创建时间", "type": "date"},
    ],
    "cdb": [
        {"prop": "instanceId", "label": "ID"},
        {"prop": "instanceName", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "engineVersion", "label": "版本"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "clb": [
        {"prop": "loadBalancerId", "label": "ID"},
        {"prop": "loadBalancerName", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "cbs": [
        {"prop": "diskId", "label": "ID"},
        {"prop": "diskName", "label": "名称"},
        {"prop": "status", "label": "状态", "type": "status"},
        {"prop": "diskSize", "label": "容量(GB)"},
        {"prop": "createTime", "label": "创建时间", "type": "date"},
    ],
    "bills": [
        {"prop": "businessCodeName", "label": "资源类型"},
        {"prop": "totalCost", "label": "总费用"},
        {"prop": "realTotalCost", "label": "实际费用"},
        {"prop": "billDate", "label": "账单日期", "type": "date"},
    ],
}

def get_columns(provider, resource_type):
    """获取资源类型的列配置"""
    if provider == "huawei" and resource_type in HUAWEI_COLUMN_CONFIGS:
        return HUAWEI_COLUMN_CONFIGS[resource_type]
    if provider == "tencent" and resource_type in TENCENT_COLUMN_CONFIGS:
        return TENCENT_COLUMN_CONFIGS[resource_type]
    return COMMON_COLUMNS


# ============================================================
# 4. 文件路径配置
# ============================================================

FRONTEND_DIR = Path(r"D:\project-java\mixed-cloud-resource-stat\frontend")
VIEWS_DIR = FRONTEND_DIR / "src" / "views"
ROUTER_FILE = FRONTEND_DIR / "src" / "router" / "index.js"
STORE_FILE = FRONTEND_DIR / "src" / "stores" / "cloud.js"


# ============================================================
# 5. 辅助函数
# ============================================================

def to_type_name(resource_type):
    """将 resource_type 转换为视图类名，如 cvm -> CvmList"""
    # 处理下划线命名如 ces_metric -> CesMetricList
    parts = resource_type.split("_")
    return "".join(p.capitalize() for p in parts) + "List"


def generate_vue_template(provider, resource_type, title, columns):
    """生成 Vue 视图文件内容"""
    # 生成 columns JSON
    def col_to_json(col):
        parts = [f"prop: '{col['prop']}'", f"label: '{col['label']}'"]
        if "type" in col:
            parts.append(f"type: '{col['type']}'")
        if "width" in col:
            parts.append(f"width: '{col['width']}'")
        return "    { " + ", ".join(parts) + " }"

    cols_str = ",\n".join(col_to_json(c) for c in columns)

    return f'''<template>
  <ResourceTable
    provider="{provider}"
    resource-type="{resource_type}"
    title="{title}"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
{cols_str}
]
</script>
'''


# ============================================================
# 6. 生成 Vue 视图文件
# ============================================================

created_files = []
skipped_files = []

def create_view(provider, resource_type, names_map):
    """创建单个视图文件（如果不存在）"""
    provider_dir = VIEWS_DIR / provider
    provider_dir.mkdir(parents=True, exist_ok=True)

    type_name = to_type_name(resource_type)
    file_path = provider_dir / f"{type_name}.vue"

    if file_path.exists():
        skipped_files.append(str(file_path))
        return

    title = names_map.get(resource_type, f"{resource_type.upper()}")
    columns = get_columns(provider, resource_type)
    content = generate_vue_template(provider, resource_type, title, columns)

    file_path.write_text(content, encoding="utf-8")
    created_files.append(str(file_path))


# 华为云
for t in huawei_types:
    create_view("huawei", t, HUAWEI_NAMES)

# 腾讯云
for t in tencent_types:
    create_view("tencent", t, TENCENT_NAMES)

# 阿里云
for t in aliyun_types:
    create_view("aliyun", t, ALIYUN_NAMES)

print(f"\n已创建 {len(created_files)} 个视图文件")
print(f"已跳过 {len(skipped_files)} 个已有文件")
for f in created_files:
    print(f"  + {f}")
for f in skipped_files:
    print(f"  ~ {f}")


# ============================================================
# 7. 更新路由文件
# ============================================================

def build_route_entries(provider, types, names_map):
    """构建路由条目列表"""
    entries = []
    for t in types:
        type_name = to_type_name(t)
        title = names_map.get(t, t.upper())
        route_name = f"{provider.capitalize()}{type_name.replace('List', '')}"
        meta_title = f"{'华为云' if provider == 'huawei' else '腾讯云' if provider == 'tencent' else '阿里云'}{title}"
        entries.append({
            "path": t,
            "name": route_name,
            "component": f"@/views/{provider}/{type_name}.vue",
            "meta_title": meta_title,
        })
    return entries

def find_existing_routes(content, provider):
    """提取路由文件中某个 provider 下已有的路由 path"""
    pattern = rf"path:\s*'/{provider}'.*?children:\s*\[(.*?)\]"
    match = re.search(pattern, content, re.DOTALL)
    if not match:
        return set()
    children_block = match.group(1)
    paths = re.findall(r"path:\s*'([^']+)'", children_block)
    return set(paths)


with open(ROUTER_FILE, "r", encoding="utf-8") as f:
    router_content = f.read()

# 检查已存在的路由
existing_huawei = find_existing_routes(router_content, "huawei")
existing_tencent = find_existing_routes(router_content, "tencent")
existing_aliyun = find_existing_routes(router_content, "aliyun")

print(f"\n已有路由: huawei={existing_huawei}, tencent={existing_tencent}, aliyun={existing_aliyun}")


def generate_route_block(provider, types, names_map, existing):
    """生成一个 provider 的完整路由块"""
    entries = build_route_entries(provider, types, names_map)
    lines = []
    for entry in entries:
        if entry["path"] in existing:
            continue
        lines.append(f"""      {{
        path: '{entry["path"]}',
        name: '{entry["name"]}',
        component: () => import('{entry["component"]}'),
        meta: {{ title: '{entry["meta_title"]}' }}
      }}""")
    return lines


# 生成新的路由条目
new_huawei_routes = generate_route_block("huawei", huawei_types, HUAWEI_NAMES, existing_huawei)
new_tencent_routes = generate_route_block("tencent", tencent_types, TENCENT_NAMES, existing_tencent)
new_aliyun_routes = generate_route_block("aliyun", aliyun_types, ALIYUN_NAMES, existing_aliyun)


def insert_routes_into_provider(content, provider, new_routes):
    """在路由文件中某个 provider 的 children 数组末尾插入新路由"""
    if not new_routes:
        return content

    # 找到该 provider 的 children 块的结束 ] 位置
    pattern = rf"(path:\s*'/{provider}'.*?children:\s*\[)"
    match = re.search(pattern, content, re.DOTALL)
    if not match:
        print(f"WARNING: 找不到 {provider} 的路由块")
        return content

    # 找到该 provider children 数组的结束位置
    start_pos = match.end()
    bracket_count = 1
    pos = start_pos
    while pos < len(content) and bracket_count > 0:
        if content[pos] == '[':
            bracket_count += 1
        elif content[pos] == ']':
            bracket_count -= 1
        pos += 1
    # pos 现在在 ] 的后面
    insert_pos = pos - 1

    new_routes_str = ",\n".join(new_routes)
    # 如果 children 数组不为空，在前面加上逗号和换行
    before = content[:insert_pos]
    after = content[insert_pos:]
    # 检查 ] 前面是否有内容
    if before.rstrip().endswith('}'):
        # 已有子路由，加逗号
        return before + ",\n" + new_routes_str + after
    else:
        return before + new_routes_str + after


# 按顺序插入路由
updated_router = router_content
updated_router = insert_routes_into_provider(updated_router, "huawei", new_huawei_routes)
updated_router = insert_routes_into_provider(updated_router, "tencent", new_tencent_routes)
updated_router = insert_routes_into_provider(updated_router, "aliyun", new_aliyun_routes)

if updated_router != router_content:
    with open(ROUTER_FILE, "w", encoding="utf-8") as f:
        f.write(updated_router)
    total_new_routes = len(new_huawei_routes) + len(new_tencent_routes) + len(new_aliyun_routes)
    print(f"\n路由文件已更新，新增 {total_new_routes} 条路由")
else:
    print("\n路由文件无需更新")


# ============================================================
# 8. 更新 Store 文件
# ============================================================

# 读取当前 store 内容
with open(STORE_FILE, "r", encoding="utf-8") as f:
    store_content = f.read()

# 检查 store 是否已经使用动态方式
if "reactive({})" in store_content or "ref({})" in store_content:
    print("\nStore 已使用动态方式，无需修改")
else:
    # 将固定字段的 ref({}) 改为响应式 Map 结构
    # 华为云资源
    old_huawei = """  const huaweiResources = ref({
    ecs: [],
    rds: [],
    elb: [],
    evs: [],
    vpc: [],
    bills: []
  })"""
    new_huawei = "  const huaweiResources = ref({})"

    old_tencent = """  const tencentResources = ref({
    cvm: [],
    cdb: [],
    clb: [],
    cbs: [],
    bills: []
  })"""
    new_tencent = "  const tencentResources = ref({})"

    old_aliyun = """  const aliyunResources = ref({
    dns: []
  })"""
    new_aliyun = "  const aliyunResources = ref({})"

    updated_store = store_content
    if old_huawei in updated_store:
        updated_store = updated_store.replace(old_huawei, new_huawei)
    if old_tencent in updated_store:
        updated_store = updated_store.replace(old_tencent, new_tencent)
    if old_aliyun in updated_store:
        updated_store = updated_store.replace(old_aliyun, new_aliyun)

    # 更新 fetchHuaweiResources 使其动态初始化
    old_fetch_hw = """  const fetchHuaweiResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getHuaweiResources(resourceType)
      huaweiResources.value[resourceType] = response.data"""
    new_fetch_hw = """  const fetchHuaweiResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getHuaweiResources(resourceType)
      // 动态初始化资源类型（使用展开运算符创建新对象触发响应式更新）
      huaweiResources.value = { ...huaweiResources.value, [resourceType]: response.data }"""

    old_fetch_tx = """  const fetchTencentResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getTencentResources(resourceType)
      tencentResources.value[resourceType] = response.data"""
    new_fetch_tx = """  const fetchTencentResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getTencentResources(resourceType)
      // 动态初始化资源类型
      tencentResources.value = { ...tencentResources.value, [resourceType]: response.data }"""

    old_fetch_ali = """  const fetchAliyunResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getAliyunResources(resourceType)
      aliyunResources.value[resourceType] = response.data"""
    new_fetch_ali = """  const fetchAliyunResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getAliyunResources(resourceType)
      // 动态初始化资源类型
      aliyunResources.value = { ...aliyunResources.value, [resourceType]: response.data }"""

    if old_fetch_hw in updated_store:
        updated_store = updated_store.replace(old_fetch_hw, new_fetch_hw)
    if old_fetch_tx in updated_store:
        updated_store = updated_store.replace(old_fetch_tx, new_fetch_tx)
    if old_fetch_ali in updated_store:
        updated_store = updated_store.replace(old_fetch_ali, new_fetch_ali)

    # 简化 refreshAllData - 不再硬编码所有资源类型
    old_refresh = """  const refreshAllData = async () => {
    await Promise.all([
      fetchCloudConfigs(),
      fetchHuaweiResources('ecs'),
      fetchHuaweiResources('rds'),
      fetchHuaweiResources('elb'),
      fetchHuaweiResources('evs'),
      fetchHuaweiResources('vpc'),
      fetchTencentResources('cvm'),
      fetchTencentResources('cdb'),
      fetchTencentResources('clb'),
      fetchTencentResources('cbs'),
      fetchAliyunResources('dns')
    ])
  }"""
    new_refresh = """  const refreshAllData = async () => {
    await fetchCloudConfigs()
    // 资源类型按需加载，由各视图组件自行触发
  }"""

    if old_refresh in updated_store:
        updated_store = updated_store.replace(old_refresh, new_refresh)

    if updated_store != store_content:
        with open(STORE_FILE, "w", encoding="utf-8") as f:
            f.write(updated_store)
        print("\nStore 文件已更新为动态资源类型支持")
    else:
        print("\nStore 文件无需更新")


print("\n========== 完成 ==========")
print(f"共创建 {len(created_files)} 个 Vue 视图文件")
print(f"跳过 {len(skipped_files)} 个已有文件")
