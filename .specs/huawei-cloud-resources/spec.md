# 华为云资源统计 - 补齐所有资源 Entity/Mapper/Sync/Controller/文档

## 问题陈述

HCloudClient 已实现 20+ 个 API 调用方法，但配套的 Entity 类、Service 同步方法、Controller 查询接口大量缺失。当前仅 ECS 和 EIP 有完整的 Entity→Mapper→Sync→Controller 链路，其余资源仅在 Client 层和 Mapper 层有代码，中间的 Entity 定义和 Service 同步逻辑缺失，无法实际使用。

## 范围

### 范围内

补齐以下 17 个缺失的 Entity 类（含对应 Mapper 已存在的确认）、18 个 Sync 方法、16 个 ResourceController case、SyncController 增强、以及 API 覆盖文档。

### 缺失 Entity 列表（17 个）

| # | Entity 类名 | 对应 Client 方法 | SDK 源类型 | DB 表名 | Mapper 是否已有 |
|---|---|---|---|---|---|
| 1 | HCloudRds | listRds() | InstanceResponse | h_cloud_rds | ✅ HCloudRdsMapper |
| 2 | HCloudDcs | listDcs() | InstanceList | h_cloud_dcs | ✅ HCloudDcsMapper |
| 3 | HCloudDds | listDds() | InstanceListResponse | h_cloud_dds | ✅ HCloudDdsMapper |
| 4 | HCloudObs | listObs() | BucketMetadata | h_cloud_obs | ✅ HCloudObsMapper |
| 5 | HCloudSfs | listSfs() | ShareInfo | h_cloud_sfs | ✅ HCloudSfsMapper |
| 6 | HCloudElb | listElb() | LoadBalancer | h_cloud_elb | ✅ HCloudElbMapper |
| 7 | HCloudEvs | listEvs() | VolumeDetail | h_cloud_evs | ✅ HCloudEvsMapper |
| 8 | HCloudCesMetric | listCesMetric() | MetricInfoList | h_cloud_ces_metric | ✅ HCloudCesMetricMapper |
| 9 | HCloudCesMetricData | listCesMetricData() | MetricData | h_cloud_ces_metric_data | ✅ HCloudCesMetricDataMapper |
| 10 | HCloudDnsPrivate | listDnsPrivate() | Zone | h_cloud_dns_private | ✅ HCloudDnsPrivateMapper |
| 11 | HCloudDnsPrivateRecordSets | listDnsPrivateRecordSets() | ShowRecordSet | h_cloud_dns_private_record_sets | ✅ HCloudDnsPrivateRecordSetsMapper |
| 12 | HCloudCce | listClusters() | ClusterInfo | h_cloud_cce | ✅ HCloudCceMapper |
| 13 | HCloudAuthDomain | listAuthDomains() | KeystoneAuthDomain | h_cloud_auth_domain | ✅ HCloudAuthDomainMapper |
| 14 | HCloudUser | listUsers() | KeystoneUser | h_cloud_user | ✅ HCloudUserMapper |
| 15 | HCloudPermanentAccessKey | listPermanentAccessKeys() | PermanentAccessKey | h_cloud_permanent_access_key | ✅ HCloudPermanentAccessKeyMapper |
| 16 | HCloudBillsMonthlyBreakDown | listBillsMonthlyBreakDown() | NvlCostAnalysedBillDetail | h_cloud_bills_monthly_break_down | ✅ HCloudBillsMonthlyBreakDownMapper |
| 17 | HCloudResource | listAllResources() | ResourceEntity | h_cloud_resource | ✅ HCloudResourceMapper |

### 额外已存在但未纳入同步的 Entity/Mapper（4 个）

| # | Entity 类名 | Mapper | 状态 |
|---|---|---|---|
| 1 | HCloudBillsFeeRecords | HCloudBillsFeeRecordsMapper | Entity 待确认 |
| 2 | HCloudResourceRecordDetail | HCloudResourceRecordDetailMapper | Entity 待确认 |
| 3 | HCloudCesMetric | HCloudCesMetricMapper | 需新建 Entity |
| 4 | HCloudCesMetricData | HCloudCesMetricDataMapper | 需新建 Entity |

### 已有 Entity 但缺失 Sync 方法（3 个）

| # | Entity 类名 | Client 方法 | 状态 |
|---|---|---|---|
| 1 | HCloudVpc | listVpc() | Entity ✅ Mapper ✅ Sync ❌ Controller ✅ |
| 2 | HCloudEcs | listEcs() | 全链路 ✅ (参考实现) |
| 3 | HCloudEip | listEip() | 全链路 ✅ (参考实现) |

### 范围外

- 新增 HCloudClient 方法（如 NAT Gateway、FunctionGraph 等未实现的 API）
- QCloud / ACloud 相关改动
- 数据库 Schema DDL 自动生成
- 前端页面适配

## 验收标准

### Given/When/Then

1. **Given** HCloudClient 已有 listRds() 方法, **When** 执行 syncRds(), **Then** 能从华为云 API 拉取 RDS 实例数据并存入 h_cloud_rds 表
2. **Given** 数据库中已有旧的 ECS 数据但 API 中已删除, **When** 执行 syncEcs(), **Then** 旧数据被逻辑删除(deleted=1)
3. **Given** 所有 Entity 类已创建, **When** 访问 GET /huawei/{type}, **Then** 支持所有 20 种资源类型查询
4. **Given** API 覆盖文档已生成, **When** 查看文档, **Then** 包含每个 API 的覆盖状态、Client 方法名、Entity 类名、SDK 类型映射

### 具体交付物

1. **17 个 Entity 类** — `backend/src/main/java/com/linjicong/cloud/stat/dao/entity/hcloud/` 下
2. **18 个 sync 方法** — 在 HCloudService.java 中新增
3. **ResourceController 扩展** — 增加所有 HCloud 资源的查询 case
4. **SyncController 扩展** — 支持同步所有资源类型（不仅 ECS）
5. **API 覆盖文档** — `backend/doc/hcloud_api_coverage.md`
