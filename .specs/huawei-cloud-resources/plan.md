# 华为云资源统计 - 补齐 Sync/Controller/文档 实现计划

## 差距修正

经代码核实，**所有 24 个 Entity 类和 24 个 Mapper 接口均已存在**。实际缺失仅为：

1. **HCloudService** — 22 个 sync 方法（当前仅 syncEcs, syncEip）
2. **ResourceController** — 15 个查询 case（当前仅 9 个）
3. **hcloud_api_coverage.md** — API 覆盖文档

## 任务拆解

### Phase 1: HCloudService — 新增 22 个 sync 方法

参考 syncEcs/syncEip 的差异同步模式，为每个资源新增 sync 方法：

| # | 方法名 | Client 方法 | Entity 类型 | id 字段 |
|---|---|---|---|---|
| 1 | syncRds | listRds() | HCloudRds | id |
| 2 | syncDcs | listDcs() | HCloudDcs | instanceId |
| 3 | syncDds | listDds() | HCloudDds | id |
| 4 | syncObs | listObs() | HCloudObs | name (桶名) |
| 5 | syncSfs | listSfs() | HCloudSfs | id |
| 6 | syncElb | listElb() | HCloudElb | id |
| 7 | syncVpc | listVpc() | HCloudVpc | id |
| 8 | syncEvs | listEvs() | HCloudEvs | id |
| 9 | syncCesMetric | listCesMetric() | HCloudCesMetric | (需确认) |
| 10 | syncDnsPrivate | listDnsPrivate() | HCloudDnsPrivate | id |
| 11 | syncDnsPrivateRecordSets | listDnsPrivateRecordSets() | HCloudDnsPrivateRecordSets | id |
| 12 | syncCce | listClusters() | HCloudCce | metadata.uid |
| 13 | syncAuthDomain | listAuthDomains() | HCloudAuthDomain | id |
| 14 | syncUser | listUsers() | HCloudUser | id |
| 15 | syncIms | listImages() | HCloudIms | id |
| 16 | syncCbr | listBackups() | HCloudCbr | id |
| 17 | syncResource | listAllResources() | HCloudResource | id |
| 18 | syncBillsMonthlyBreakDown | listBillsMonthlyBreakDown(month) | HCloudBillsMonthlyBreakDown | (需确认) |
| 19 | syncBillsFeeRecords | listBillsFeeRecords(month) | HCloudBillsFeeRecords | (需确认) |
| 20 | syncResourceRecordDetail | listResourceRecordsDetails(month) | HCloudResourceRecordDetail | (需确认) |
| 21 | syncCesMetricData | listCesMetricData(metrics,start,end) | HCloudCesMetricData | (特殊：需要参数) |

> 注：syncCesMetricData 和账单类方法需要额外参数，需特殊处理

### Phase 2: ResourceController — 新增查询 case

在 `getHuaweiResources` 的 switch 中增加：

rds, dcs, dds, obs, sfs, elb, evs, ces_metric, ces_metric_data, dns_private, dns_private_record_sets, cce, auth_domain, user, ims, cbr, resource, bills_fee_records, resource_record_detail

### Phase 3: hcloud_api_coverage.md

参考 tencent_api_coverage.md 格式生成。

### Phase 4: SyncController 增强

将 syncEcs 扩展为同步全部资源类型。

## 依赖顺序

Phase 1 → Phase 2 → Phase 4 → Phase 3（文档最后）

## 风险

- CES Metric Data 和账单类同步需要特殊参数（月份、时间范围），不适合简单的无参同步
- OBS 的 id 字段是桶名（name），非标准 id
- 需要确认每个 Entity 的主键字段名用于差异同步
