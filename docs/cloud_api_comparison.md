# 混合云资源统计 — 三云接口覆盖对比

> 最后更新: 2026-06-12
>
> 本文档对比华为云、腾讯云、阿里云三个云供应商的 API 接口覆盖情况，
> 为后续扩展优先级提供决策依据。

---

## 1. 总览对比

### 核心指标

| 指标 | 华为云 | 腾讯云 | 阿里云 | 三云合计 |
|------|--------|--------|--------|----------|
| 官方产品总数 | 167 | 287 | 206 | 660 |
| ✅ 已实现 Entity | 69 | 169 | 38 | **276** |
| Entity 类总数 | 69 | 190 | 39 | **298** |
| Mapper 接口数 | 69 | 190 | 39 | **298** |
| 📊 子产品/用量统计 | 24 | 53 | 22 | 99 |
| 🔌 无状态 API | 19 | 14 | 24 | 57 |
| ☁️ SaaS 产品 | 7 | 19 | 25 | 51 |
| 🔧 工具/平台 | 45 | 33 | 96 | 174 |

### 覆盖率对比

| 指标 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 资源型产品覆盖率 | **75.8%** | **79.0%** | **64.4%** |
| 总覆盖率 | 41.3% | **58.9%** | **18.4%** |
| 可采集率 | 54.5% | **74.6%** | **25.2%** |

> **资源型产品** = 已实现 + 子产品（有实例可跟踪的产品）
>
> 腾讯云覆盖率最高，华为云次之，阿里云第二批扩展后覆盖率显著提升。

### 覆盖率可视化

```
资源型产品覆盖率（已实现 / 可采集产品）

腾讯云  ████████████████████████████████████████░░░░░░░░░░  79.0%  (169/214)
华为云  ████████████████████████████████████░░░░░░░░░░░░░░  75.8%  (69/91)
阿里云  █████████████████████████████████░░░░░░░░░░░░░░░░░  64.4%  (38/59)

总覆盖率（已实现 / 全部产品）

腾讯云  █████████████████████████████░░░░░░░░░░░░░░░░░░░░░  58.9%  (169/287)
华为云  █████████████████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░  41.3%  (69/167)
阿里云  █████████░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░  18.4%  (38/206)
```

---

## 2. 按业务域横向对比

以下将三云的产品按统一业务域归类，对比各域的实现情况。

### 计算

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 云服务器 | ✅ HCloudEcs | ✅ QCloudCvm | ✅ ACloudEcs |
| 裸金属 | ✅ HCloudBms | ✅ QCloudBMS | 📊 共享 ECS |
| GPU 服务器 | 📊 共享 ECS | 📊 共享 CVM | 📊 共享 ECS |
| 弹性伸缩 | ✅ HCloudAs | ✅ QCloudAS | ✅ ACloudEss |
| 镜像服务 | ✅ HCloudIms | — | — |
| 专属主机 | ✅ HCloudDeH | — | — |
| 函数计算 | ✅ HCloudFunctionGraph | ✅ QCloudScf | ✅ ACloudFc |
| 云手机 | ✅ HCloudCph | ✅ QCloudCloudPhone | — |
| 容器引擎 | ✅ HCloudCce | ✅ QCloudTKE | ✅ ACloudAck |
| 容器镜像 | ✅ HCloudSwr | ✅ QCloudTCR | ✅ ACloudAcr |

### 存储

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 对象存储 | ✅ HCloudObs | ✅ QCloudCos | ✅ ACloudOss |
| 云硬盘/块存储 | ✅ HCloudEvs | ✅ QCloudCbs | ✅ ACloudDisk |
| 文件存储 | ✅ HCloudSfs | ✅ QCloudCfs | ✅ ACloudNas |
| 数据备份 | ✅ HCloudCbr | — | — |
| 存储容灾 | ✅ HCloudSdrs | — | — |
| 存储迁移 | ✅ HCloudOms | — | — |

### 数据库

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 关系型 RDS | ✅ HCloudRds | ✅ QCloudCdb | ✅ ACloudRds |
| Redis 缓存 | ✅ HCloudDcs | ✅ QCloudRedis | ✅ ACloudRedis |
| MongoDB | ✅ HCloudDds | ✅ QCloudMongoDb | ✅ ACloudMongoDb |
| PostgreSQL | 📊 共享 RDS | ✅ QCloudPostgresql | 📊 共享 RDS |
| SQL Server | 📊 共享 RDS | ✅ QCloudSqlserver | 📊 共享 RDS |
| GaussDB/PolarDB | ✅ HCloudGaussDb (3 型) | — | ✅ ACloudPolarDb |
| 分布式数据库 | ✅ HCloudDdm | ✅ QCloudDCDB | — |
| 数据复制/迁移 | ✅ HCloudDrs | — | 🔧 待实现 |

### 网络

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| VPC | ✅ HCloudVpc | ✅ QCloudVpc | ✅ ACloudVpc |
| 负载均衡 | ✅ HCloudElb | ✅ QCloudClb | ✅ ACloudSlb |
| 弹性公网 IP | ✅ HCloudEip | ✅ QCloudEip | ✅ ACloudEip |
| NAT 网关 | ✅ HCloudNat | ✅ QCloudNatGateway | ✅ ACloudNatGateway |
| 安全组 | 📊 共享 VPC | ✅ QCloudSecurityGroup | ✅ ACloudSecurityGroup |
| VPN | ✅ HCloudVpn | 📊 共享网络 | 🔧 待实现 |
| 企业路由器 | ✅ HCloudEr | — | — |
| VPC 终端节点 | ✅ HCloudVpcep | — | — |
| DNS | ✅ HCloudDnsPrivate (2) | ✅ QCloudDnsDomain | ✅ ACloudDnsDomain (2) |
| 云专线 | 🔧 待实现 | ✅ QCloudDC | 🔧 待实现 |

### 安全

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| WAF 防火墙 | ✅ HCloudWaf | ✅ QCloudWAF | ✅ ACloudWaf |
| DDoS 防护 | ✅ HCloudAntiDdos | ✅ QCloudDDoS | ✅ ACloudDdos |
| 云防火墙 | ✅ HCloudCfw | — | ✅ ACloudCloudFirewall |
| 主机安全 | ✅ HCloudHss | ✅ QCloudCWP | ✅ ACloudHss |
| 密钥管理 KMS | ✅ HCloudKms | ✅ QCloudKMS | ✅ ACloudKms |
| SSL 证书 | ✅ HCloudCcm | ✅ QCloudSSL | ✅ ACloudSsl |
| 堡垒机 | ✅ HCloudCbh | ✅ QCloudBastion | 🔧 待实现 |
| 数据安全 | ✅ HCloudDsc | ✅ QCloudDataSafeGov | ✅ ACloudDsc |
| 数据库安全 | ✅ HCloudDbss | ✅ QCloudDataAudit | 🔧 待实现 |
| 区块链 | ✅ HCloudBcs | ✅ QCloudTBAAS | — |

### 中间件与消息

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| Kafka | ✅ HCloudKafka | ✅ QCloudCkafka | ✅ ACloudKafka |
| RocketMQ | ✅ HCloudRocketMq | ✅ QCloudRocketMQ | ✅ ACloudRocketMQ |
| RabbitMQ | ✅ HCloudRabbitMq | ✅ QCloudRabbitMQ | 🔧 待实现 |
| API 网关 | ✅ HCloudApig | ✅ QCloudAPIGW | ✅ ACloudApiGateway |
| 微服务引擎 | ✅ HCloudCse | ✅ QCloudTSE | 🔧 待实现 |
| 消息通知 | ✅ HCloudSmn | — | — |

### 大数据与搜索

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| Elasticsearch | ✅ HCloudCss | ✅ QCloudES | ✅ ACloudElasticsearch |
| 大数据平台 | ✅ HCloudMrs | ✅ QCloudEMR | ✅ ACloudEmr |
| 数据仓库 | ✅ HCloudDws | — | 🔧 待实现 |
| 数据湖探索 | ✅ HCloudDli | — | 🔧 待实现 |
| 日志服务 | ✅ HCloudLts | ✅ QCloudCLS | ⚠️ 已定义 |

### CDN 与媒体

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| CDN | ✅ HCloudCdn | ✅ QCloudCdnDomain | ✅ ACloudCdn |
| 视频直播 | ✅ HCloudLive | ✅ QCloudLive | ✅ ACloudLive |
| 视频点播 | ✅ HCloudVod | ✅ QCloudVOD | ✅ ACloudVod |

### 运维管理

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|------|
| IAM 身份 | ✅ HCloudAuthDomain/User/Key (3) | — | 🔧 待实现 |
| 云监控 | ✅ HCloudCesMetric | ✅ QCloudMonitor | ✅ ACloudCms |
| 云审计 | ✅ HCloudCts | ✅ QCloudAudit | ✅ ACloudActionTrail |
| 资源管理 | ✅ HCloudResource | — | — |
| 账单 | ✅ HCloudBills (3) | ✅ QCloudBillResourceSummary | — |
| 云桌面 | ✅ HCloudWorkspace | — | — |

### 物联网与边缘

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 物联网平台 | ✅ HCloudIotDa | ✅ QCloudIoT | ✅ ACloudIoT |
| 智能边缘 | ✅ HCloudIef | — | — |
| 应用集成 | ✅ HCloudRoma | — | — |

---

## 3. 能力矩阵（三云均有 vs 仅部分有）

### 三云均已实现（核心能力，30 项）

| 能力 | 华为云 Entity | 腾讯云 Entity | 阿里云 Entity |
|------|--------------|--------------|--------------|
| 云服务器 | HCloudEcs | QCloudCvm | ACloudEcs |
| 关系型数据库 | HCloudRds | QCloudCdb | ACloudRds |
| Redis 缓存 | HCloudDcs | QCloudRedis | ACloudRedis |
| VPC 专有网络 | HCloudVpc | QCloudVpc | ACloudVpc |
| 负载均衡 | HCloudElb | QCloudClb | ACloudSlb |
| 弹性公网 IP | HCloudEip | QCloudEip | ACloudEip |
| NAT 网关 | HCloudNat | QCloudNatGateway | ACloudNatGateway |
| 对象存储 | HCloudObs | QCloudCos | ACloudOss |
| CDN | HCloudCdn | QCloudCdnDomain | ACloudCdn |
| WAF 防火墙 | HCloudWaf | QCloudWAF | ACloudWaf |
| DDoS 防护 | HCloudAntiDdos | QCloudDDoS | ACloudDdos |
| 云防火墙 | HCloudCfw | — | ACloudCloudFirewall |
| KMS 密钥管理 | HCloudKms | QCloudKMS | ACloudKms |
| SSL 证书 | HCloudCcm | QCloudSSL | ACloudSsl |
| 数据安全 | HCloudDsc | QCloudDataSafeGov | ACloudDsc |
| 容器引擎 | HCloudCce | QCloudTKE | ACloudAck |
| 容器镜像 | HCloudSwr | QCloudTCR | ACloudAcr |
| DNS | HCloudDnsPrivate | QCloudDnsDomain | ACloudDnsDomain |
| MongoDB | HCloudDds | QCloudMongoDb | ACloudMongoDb |
| Kafka | HCloudKafka | QCloudCkafka | ACloudKafka |
| RocketMQ | HCloudRocketMq | QCloudRocketMQ | ACloudRocketMQ |
| 云硬盘 | HCloudEvs | QCloudCbs | ACloudDisk |
| Elasticsearch | HCloudCss | QCloudES | ACloudElasticsearch |
| 弹性伸缩 | HCloudAs | QCloudAS | ACloudEss |
| 主机安全 | HCloudHss | QCloudCWP | ACloudHss |
| API 网关 | HCloudApig | QCloudAPIGW | ACloudApiGateway |
| 云监控 | HCloudCesMetric | QCloudMonitor | ACloudCms |
| 物联网 | HCloudIotDa | QCloudIoT | ACloudIoT |
| 视频直播 | HCloudLive | QCloudLive | ACloudLive |
| 视频点播 | HCloudVod | QCloudVOD | ACloudVod |
| 大数据平台 | HCloudMrs | QCloudEMR | ACloudEmr |

### 两云已实现（补齐优先级高）

| 能力 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 日志服务 | ✅ HCloudLts | ✅ QCloudCLS | ⚠️ ACloudSls |
| 堡垒机 | ✅ HCloudCbh | ✅ QCloudBastion | ❌ |

---

## 4. SDK 架构对比

| 维度 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| SDK 风格 | 统一聚合 SDK | 统一聚合 SDK | 每服务独立 SDK |
| Maven 依赖 | 1 个 (`sdk-all`) | 1 个 (`sdk-java`) | 11+ 个独立 artifact |
| 版本管理 | 统一版本 `3.1.171` | 统一版本 `3.1.1355` | 各自独立版本 |
| Client 实例化 | `XxxClient.newBuilder().build()` | `new XxxClient(credential, region)` | `new Client(config)` |
| 分页模式 | `marker` / `offset` | `offset` + `limit` | `pageNumber` + `pageSize` |
| 异常类型 | `ServiceException` | `TencentCloudSDKException` | `TeaException` / `Exception` |
| Bean 拷贝 | CGLib BeanCopier | CGLib BeanCopier | CGLib BeanCopier |

> **阿里云 SDK 碎片化问题**：每个服务有独立的 artifactId（如 `ecs20140526`、`rds20140815`），且命名不统一（有下划线 `r_kvstore20150101`、有连字符 `cdn20180510`），增加了依赖管理复杂度。

---

## 5. 已实现资源清单（三云对齐视角）

### 计算与容器（7 项三云能力）

```
               华为云      腾讯云       阿里云
云服务器       ECS ✅      CVM ✅       ECS ✅
裸金属         BMS ✅      BMS ✅       —
弹性伸缩       AS  ✅      AS  ✅       AS  ✅
镜像服务       IMS ✅      —            —
容器引擎       CCE ✅      TKE ✅       ACK ✅
容器镜像       SWR ✅      TCR ✅       ACR ✅
函数计算       FG  ✅      SCF ✅       FC  ✅
```

### 存储（4 项三云能力）

```
               华为云      腾讯云       阿里云
对象存储       OBS ✅      COS ✅       OSS ✅
云硬盘         EVS ✅      CBS ✅       Disk ✅
文件存储       SFS ✅      CFS ✅       NAS  ✅
数据备份       CBR ✅      —            —
```

### 数据库（5 项三云能力）

```
               华为云      腾讯云       阿里云
关系型 RDS     RDS ✅      CDB ✅       RDS ✅
Redis          DCS ✅      Redis ✅     Redis ✅
MongoDB        DDS ✅      MongoDb ✅   MongoDb ✅
GaussDB 系列   GaussDb×3   —            —
PostgreSQL     共享RDS     PG   ✅      共享RDS
```

### 网络（7 项三云能力）

```
               华为云      腾讯云       阿里云
VPC            VPC ✅      VPC ✅       VPC ✅
负载均衡       ELB ✅      CLB ✅       SLB ✅
弹性 IP        EIP ✅      EIP ✅       EIP ✅
NAT 网关       NAT ✅      NAT ✅       NAT ✅
安全组         共享VPC     SG   ✅      SG   ✅
DNS            DNS  ✅     DNS  ✅      DNS  ✅
VPN            VPN  ✅     —            —
```

### 安全（5 项三云能力）

```
               华为云      腾讯云       阿里云
WAF            WAF ✅      WAF ✅       WAF ✅
KMS            KMS ✅      KMS ✅       KMS ✅
DDoS           DDoS ✅     DDoS ✅      DDoS ✅
主机安全       HSS ✅      CWP ✅       HSS  ✅
防火墙         CFW ✅      —            CFW ✅
```

### 中间件（4 项三云能力）

```
               华为云      腾讯云       阿里云
Kafka          Kafka ✅    CKafka ✅    Kafka ✅
RocketMQ       RocketMQ ✅ RocketMQ ✅  RocketMQ ✅
RabbitMQ       RabbitMQ ✅ RabbitMQ ✅  —
API 网关       APIG ✅     APIGW ✅     ApiGateway ✅
```

### CDN 与媒体（3 项三云能力）

```
               华为云      腾讯云       阿里云
CDN            CDN ✅      CDN  ✅      CDN ✅
视频直播       Live ✅     Live ✅      Live ✅
视频点播       VOD  ✅     VOD  ✅      VOD  ✅
```

---

## 6. 扩展优先级建议

### P0 — 阿里云补齐（三云对齐核心能力，最大 ROI）—— 已完成 ✅

> 以下 8 项已全部完成，阿里云第二批扩展后三云对齐核心能力已达 25 项。

| 优先级 | 资源类型 | 状态 |
|--------|---------|------|
| 1 | MongoDB | ✅ ACloudMongoDb |
| 2 | Kafka | ✅ ACloudKafka |
| 3 | RocketMQ | ✅ ACloudRocketMQ |
| 4 | 云硬盘 EBS/EVS/CBS | ✅ ACloudDisk |
| 5 | Elasticsearch | ✅ ACloudElasticsearch |
| 6 | 函数计算 FC | ✅ ACloudFc |
| 7 | 云监控 | ✅ ACloudCms |
| 8 | 文件存储 NAS | ✅ ACloudNas |

### P1 — 三云共同补齐 —— 阿里云部分已完成 ✅

> 阿里云部分（弹性伸缩、主机安全、云审计、API 网关、物联网）已完成。

| 优先级 | 资源类型 | 华为云 | 腾讯云 | 阿里云 |
|--------|---------|--------|--------|--------|
| 1 | 弹性伸缩 | ✅ | ✅ | ✅ ACloudEss |
| 2 | 主机安全 | ✅ | ✅ | ✅ ACloudHss |
| 3 | 堡垒机 | ✅ | ✅ | ❌ |
| 4 | 云审计 | ✅ | ✅ | ✅ ACloudActionTrail |
| 5 | API 网关 | ✅ | ✅ | ✅ ACloudApiGateway |
| 6 | 物联网 | ✅ | ✅ | ✅ ACloudIoT |

### P2 — 特色能力扩展（部分已完成 ✅）

| 方向 | 华为云 | 腾讯云 | 阿里云 |
|------|--------|--------|--------|
| 堡垒机 | ✅ HCloudCbh | ✅ QCloudBastion | ❌ |
| 数据库 | GaussDB 三型 | 更多 RDS 引擎独立支持 | ✅ ACloudPolarDb |
| 大数据 | DLI/DWS/MRS 三件套 | EMR | MaxCompute |
| AI/ML | ModelArts | AI Kits 较全 | 百炼/PAI |
| 混合云 | ER/VPN/DC 完整 | — | CEN |
| 视频点播 | ✅ HCloudVod | ✅ QCloudVOD | ✅ ACloudVod |
| DDoS 防护 | ✅ HCloudAntiDdos | ✅ QCloudDDoS | ✅ ACloudDdos |
| 云防火墙 | ✅ HCloudCfw | — | ✅ ACloudCloudFirewall |
| SSL 证书 | ✅ HCloudCcm | ✅ QCloudSSL | ✅ ACloudSsl |
| 数据安全 | ✅ HCloudDsc | ✅ QCloudDataSafeGov | ✅ ACloudDsc |
| 容器镜像 | ✅ HCloudSwr | ✅ QCloudTCR | ✅ ACloudAcr |
| RabbitMQ | ✅ HCloudRabbitMq | ✅ QCloudRabbitMQ | ❌ |

---

## 7. 详细文档索引

| 文档 | 路径 | 产品数 |
|------|------|--------|
| 华为云覆盖详情 | [hcloud_api_coverage.md](./hcloud_api_coverage.md) | 167 |
| 腾讯云覆盖详情 | [tencent_api_coverage.md](./tencent_api_coverage.md) | 287 |
| 阿里云覆盖详情 | [aliyun_api_coverage.md](./aliyun_api_coverage.md) | 206 |
