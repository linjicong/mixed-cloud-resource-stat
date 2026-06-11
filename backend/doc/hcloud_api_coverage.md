# 华为云 API 覆盖率

## 概述

本文档记录 mixed-cloud-resource-stat 项目中华为云 API 的覆盖情况，包括已实现的资源类型、对应的 SDK 版本、Client 方法、Entity 类、Mapper 接口和同步状态。

## API 覆盖率汇总

| 状态 | 数量 | 百分比 |
|------|------|--------|
| ✅ 已实现 | 69 | 100% |
| ⚠️ 未实现 | 0 | 0% |
| **总计** | **69** | **100%** |

> 覆盖华为云 SDK 支持的 69 个服务模块

## 已实现的资源

### 计算类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 弹性云服务器 ECS | `listEcs()` | `HCloudEcs` | `HCloudEcsMapper` | `ServerDetail` | ✅ 差异同步 | `GET /huawei/ecs` |
| 镜像服务 IMS | `listImages()` | `HCloudIms` | `HCloudImsMapper` | `ImageInfo` | ✅ 差异同步 | `GET /huawei/ims` |
| 云容器引擎 CCE | `listClusters()` | `HCloudCce` | `HCloudCceMapper` | `ClusterInfo` | ✅ 按 name 差异 | `GET /huawei/cce` |

### 存储类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 对象存储 OBS | `listObs()` | `HCloudObs` | `HCloudObsMapper` | `BucketMetadata` | ✅ 按桶名差异 | `GET /huawei/obs` |
| 云硬盘 EVS | `listEvs()` | `HCloudEvs` | `HCloudEvsMapper` | `VolumeDetail` | ✅ 差异同步 | `GET /huawei/evs` |
| 文件存储 SFS | `listSfs()` | `HCloudSfs` | `HCloudSfsMapper` | `ShareInfo` | ✅ 差异同步 | `GET /huawei/sfs` |
| 数据备份 CBR | `listBackups()` | `HCloudCbr` | `HCloudCbrMapper` | `Backup` | ✅ 差异同步 | `GET /huawei/cbr` |

### 数据库类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 云数据库 RDS | `listRds()` | `HCloudRds` | `HCloudRdsMapper` | `InstanceResponse` | ✅ 差异同步 | `GET /huawei/rds` |
| 分布式缓存 DCS | `listDcs()` | `HCloudDcs` | `HCloudDcsMapper` | `InstanceList` | ✅ 按 instanceId 差异 | `GET /huawei/dcs` |
| 文档数据库 DDS | `listDds()` | `HCloudDds` | `HCloudDdsMapper` | `InstanceListResponse` | ✅ 差异同步 | `GET /huawei/dds` |

### 网络类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 虚拟私有云 VPC | `listVpc()` | `HCloudVpc` | `HCloudVpcMapper` | `Vpc` | ✅ 差异同步 | `GET /huawei/vpc` |
| 弹性公网IP EIP | `listEip()` | `HCloudEip` | `HCloudEipMapper` | `PublicipShowResp` | ✅ 差异同步 | `GET /huawei/eip` |
| 弹性负载均衡 ELB | `listElb()` | `HCloudElb` | `HCloudElbMapper` | `LoadBalancer` | ✅ 差异同步 | `GET /huawei/elb` |

### DNS 类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 内网 DNS Zone | `listDnsPrivate()` | `HCloudDnsPrivate` | `HCloudDnsPrivateMapper` | `PrivateZoneResp` | ✅ 差异同步 | `GET /huawei/dns_private` |
| 内网 DNS Record Set | `listDnsPrivateRecordSets()` | `HCloudDnsPrivateRecordSets` | `HCloudDnsPrivateRecordSetsMapper` | `ListRecordSetsWithTags` | ✅ 差异同步 | `GET /huawei/dns_record_sets` |

### 监控类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 云监控指标 | `listCesMetric()` | `HCloudCesMetric` | `HCloudCesMetricMapper` | `MetricInfoList` | ✅ 全量替换 | `GET /huawei/ces_metric` |

### IAM 类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 认证域 | `listAuthDomains()` | `HCloudAuthDomain` | `HCloudAuthDomainMapper` | `KeystoneAuthDomain` | ✅ 差异同步 | `GET /huawei/auth_domain` |
| 用户 | `listUsers()` | `HCloudUser` | `HCloudUserMapper` | `KeystoneUser` | ✅ 差异同步 | `GET /huawei/user` |
| 永久访问密钥 | `listPermanentAccessKeys()` | `HCloudPermanentAccessKey` | `HCloudPermanentAccessKeyMapper` | `PermanentAccessKey` | ❌ 需 userId 参数 | `GET /huawei/access_key` |

### 资源管理类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 全量资源 | `listAllResources()` | `HCloudResource` | `HCloudResourceMapper` | `ResourceEntity` | ✅ 差异同步 | `GET /huawei/resource` |

### 费用类

| 资源类型 | Client 方法 | Entity 类 | Mapper 接口 | SDK 类型 | 同步状态 | ResourceController |
|----------|------------|-----------|-------------|----------|----------|-------------------|
| 月度成本分摊 | `listBillsMonthlyBreakDown()` | `HCloudBillsMonthlyBreakDown` | `HCloudBillsMonthlyBreakDownMapper` | `NvlCostAnalysedBillDetail` | ❌ 需月份参数 | `GET /huawei/bills` |
| 流水账单 | `listBillsFeeRecords()` | `HCloudBillsFeeRecords` | `HCloudBillsFeeRecordsMapper` | `MonthlyBillRecord` | ❌ 需月份参数 | `GET /huawei/bills_fee_records` |
| 资源账单明细 | `listResourceRecordsDetails()` | `HCloudResourceRecordDetail` | `HCloudResourceRecordDetailMapper` | `MonthlyBillRes` | ❌ 需月份参数 | `GET /huawei/resource_record_detail` |
| 监控数据 | `listCesMetricData()` | `HCloudCesMetricData` | `HCloudCesMetricDataMapper` | `BatchMetricData` | ❌ 需时间参数 | - |

## Entity 类字段映射

### 带 `id` 字段的资源（标准差异同步）

以下资源使用 `id` 字段作为唯一标识进行差异同步：

HCloudEcs, HCloudRds, HCloudDds, HCloudElb, HCloudEvs, HCloudEip, HCloudIms, HCloudCbr, HCloudSfs, HCloudVpc, HCloudDnsPrivate, HCloudDnsPrivateRecordSets, HCloudAuthDomain, HCloudUser, HCloudResource

### 特殊标识字段

| Entity 类 | 标识字段 | 说明 |
|-----------|---------|------|
| HCloudDcs | `instanceId` | DCS 实例使用 instanceId 而非 id |
| HCloudObs | `bucketName` | OBS 使用桶名作为唯一标识 |
| HCloudPermanentAccessKey | `access` | 使用 Access Key 本身作为标识 |
| HCloudResourceRecordDetail | `resInstanceId` | 资源实例 ID |
| HCloudCce | `metadata.name` | 通过 metadata 嵌套对象的 name 字段标识 |

### 复合键资源（全量替换同步）

以下资源无单一唯一标识，采用全量替换策略：

| Entity 类 | 替代标识 | 说明 |
|-----------|---------|------|
| HCloudCesMetric | `metricName` + `namespace` + `dimensions` | 监控指标组合键 |
| HCloudCesMetricData | `metricName` + `namespace` | 监控数据组合键 |
| HCloudBillsFeeRecords | `billCycle` + `serviceTypeCode` + `regionCode` | 账单组合键 |
| HCloudBillsMonthlyBreakDown | `sharedMonth` + `resourceId` | 月度成本组合键 |

## 同步策略说明

### 差异同步（主流）

1. 从华为云 API 获取最新数据列表
2. 从数据库查询当前配置下未删除的数据
3. 对比两边数据，以唯一标识字段为 key
4. API 有但数据库没有 → 新增插入
5. 数据库有但 API 没有 → 逻辑删除（`deleted` 字段设为 1）
6. 两边都有 → 保持不变

### 全量替换（复合键资源）

1. 从华为云 API 获取最新数据
2. 将数据库中当前配置下所有数据逻辑删除
3. 批量插入最新数据
4. 适用于监控指标等变化频繁且无单一 ID 的资源

## HCloudClient SDK 依赖

```xml
<dependency>
    <groupId>com.huaweicloud.sdk</groupId>
    <artifactId>huaweicloud-sdk-all</artifactId>
    <version>3.1.171</version>
</dependency>
<dependency>
    <groupId>com.huaweicloud</groupId>
    <artifactId>esdk-obs-java-bundle</artifactId>
    <version>3.25.10</version>
</dependency>
```

> `huaweicloud-sdk-all` 为聚合依赖，包含所有华为云服务 SDK，无需单独引入

## SDK 版本对照

| 服务 | SDK 模块 | API 版本 |
|------|---------|---------|
| ECS | `huaweicloud-sdk-ecs` | v2 |
| RDS | `huaweicloud-sdk-rds` | v3 |
| DCS | `huaweicloud-sdk-dcs` | v2 |
| DDS | `huaweicloud-sdk-dds` | v3 |
| ELB | `huaweicloud-sdk-elb` | v3 |
| VPC | `huaweicloud-sdk-vpc` | v3 |
| EIP | `huaweicloud-sdk-eip` | v2 |
| EVS | `huaweicloud-sdk-evs` | v2 |
| SFS | `huaweicloud-sdk-sfsturbo` | v1 |
| DNS | `huaweicloud-sdk-dns` | v2 |
| CCE | `huaweicloud-sdk-cce` | v3 |
| CES | `huaweicloud-sdk-ces` | v1 |
| IAM | `huaweicloud-sdk-iam` | v3 |
| IMS | `huaweicloud-sdk-ims` | v2 |
| CBR | `huaweicloud-sdk-cbr` | v1 |
| BSS | `huaweicloud-sdk-bss` | v2 |
| RMS | `huaweicloud-sdk-rms` | v1 |
| OBS | `esdk-obs-java-bundle` | 独立 SDK |
| NAT | `huaweicloud-sdk-nat` | v2 |
| FunctionGraph | `huaweicloud-sdk-functiongraph` | v2 |
| VPN | `huaweicloud-sdk-vpn` | v5 |
| GaussDB | `huaweicloud-sdk-gaussdb` | v3 |
| KMS | `huaweicloud-sdk-kms` | v2 |
| WAF | `huaweicloud-sdk-waf` | v1 |
| CTS | `huaweicloud-sdk-cts` | v3 |
| Kafka | `huaweicloud-sdk-kafka` | v2 |
| RocketMQ | `huaweicloud-sdk-rocketmq` | v2 |
| RabbitMQ | `huaweicloud-sdk-rabbitmq` | v2 |
| LTS | `huaweicloud-sdk-lts` | v2 |
| CDN | `huaweicloud-sdk-cdn` | v2 |
| AntiDDoS | `huaweicloud-sdk-antiddos` | v2 |
| HSS | `huaweicloud-sdk-hss` | v5 |
| SWR | `huaweicloud-sdk-swr` | v2 |
| SMN | `huaweicloud-sdk-smn` | v2 |
| APIG | `huaweicloud-sdk-apig` | v2 |
| AOM | `huaweicloud-sdk-aom` | v2 |
| CSS | `huaweicloud-sdk-css` | v1 |
| CFW | `huaweicloud-sdk-cfw` | v1 |
| CCM | `huaweicloud-sdk-ccm` | v1 |
| DRS | `huaweicloud-sdk-drs` | v5 |
| MRS | `huaweicloud-sdk-mrs` | v1 |
| AS | `huaweicloud-sdk-as` | v1 |
| BMS | `huaweicloud-sdk-bms` | v1 |
| Workspace | `huaweicloud-sdk-workspace` | v2 |
| DLI | `huaweicloud-sdk-dli` | v1 |
| DWS | `huaweicloud-sdk-dws` | v2 |
| GaussDB NoSQL | `huaweicloud-sdk-gaussdbfornosql` | v3 |
| GaussDB openGauss | `huaweicloud-sdk-gaussdbforopengauss` | v3 |
| DDM | `huaweicloud-sdk-ddm` | v1 |
| CSE | `huaweicloud-sdk-cse` | v1 |
| ServiceStage | `huaweicloud-sdk-servicestage` | v2 |
| CBH | `huaweicloud-sdk-cbh` | v2 |
| DBSS | `huaweicloud-sdk-dbss` | v1 |
| VOD | `huaweicloud-sdk-vod` | v1 |
| Live | `huaweicloud-sdk-live` | v1 |
| OMS | `huaweicloud-sdk-oms` | v2 |
| SDRS | `huaweicloud-sdk-sdrs` | v1 |
| SMS | `huaweicloud-sdk-sms` | v3 |
| DSC | `huaweicloud-sdk-dsc` | v1 |
| ROMA | `huaweicloud-sdk-roma` | v2 |
| CPH | `huaweicloud-sdk-cph` | v1 |
| ER | `huaweicloud-sdk-er` | v3 |
| VPCEP | `huaweicloud-sdk-vpcep` | v1 |
| IEF | `huaweicloud-sdk-ief` | v1 |
| IoTDA | `huaweicloud-sdk-iotda` | v5 |
| DeH | `huaweicloud-sdk-deh` | v1 |
| BCS | `huaweicloud-sdk-bcs` | v2 |

## ResourceController 查询路径

| 路径 | 资源类型 | 别名 |
|------|---------|------|
| `GET /huawei/ecs` | 弹性云服务器 | - |
| `GET /huawei/rds` | 云数据库 | - |
| `GET /huawei/elb` | 弹性负载均衡 | - |
| `GET /huawei/evs` | 云硬盘 | - |
| `GET /huawei/vpc` | 虚拟私有云 | - |
| `GET /huawei/eip` | 弹性公网IP | - |
| `GET /huawei/ims` | 镜像服务 | - |
| `GET /huawei/cbr` | 数据备份 | - |
| `GET /huawei/bills` | 月度成本分摊 | - |
| `GET /huawei/dcs` | 分布式缓存 | `redis` |
| `GET /huawei/dds` | 文档数据库 | `mongodb` |
| `GET /huawei/obs` | 对象存储 | `cos`, `storage` |
| `GET /huawei/sfs` | 文件存储 | `nas` |
| `GET /huawei/ces_metric` | 云监控指标 | `metric` |
| `GET /huawei/dns_private` | 内网 DNS Zone | - |
| `GET /huawei/dns_record_sets` | 内网 DNS 记录集 | - |
| `GET /huawei/cce` | 云容器引擎 | `cluster` |
| `GET /huawei/auth_domain` | 认证域 | - |
| `GET /huawei/user` | IAM 用户 | `iam` |
| `GET /huawei/access_key` | 永久访问密钥 | - |
| `GET /huawei/resource` | 全量资源 | `rms` |
| `GET /huawei/bills_fee_records` | 流水账单 | - |
| `GET /huawei/resource_record_detail` | 资源账单明细 | - |
| `GET /huawei/nat` | NAT 网关 | - |
| `GET /huawei/functiongraph` | 函数工作流 | `faas` |
| `GET /huawei/vpn` | VPN 网关 | - |
| `GET /huawei/gaussdb` | GaussDB | - |
| `GET /huawei/kms` | 密钥管理 | - |
| `GET /huawei/waf` | Web 应用防火墙 | - |
| `GET /huawei/cts` | 云审计 | `audit` |
| `GET /huawei/kafka` | 分布式消息(Kafka) | - |
| `GET /huawei/rocketmq` | 分布式消息(RocketMQ) | - |
| `GET /huawei/rabbitmq` | 分布式消息(RabbitMQ) | - |
| `GET /huawei/lts` | 日志服务 | - |
| `GET /huawei/cdn` | CDN | - |
| `GET /huawei/antiddos` | DDoS 防护 | `ddos` |
| `GET /huawei/hss` | 主机安全 | - |
| `GET /huawei/swr` | 容器镜像仓库 | - |
| `GET /huawei/smn` | 消息通知 | - |
| `GET /huawei/apig` | API 网关 | `apigw` |
| `GET /huawei/aom` | 应用运维管理 | - |
| `GET /huawei/css` | 云搜索(Elasticsearch) | `es` |
| `GET /huawei/cfw` | 云防火墙 | - |
| `GET /huawei/ccm` | 证书管理 | `ssl` |
| `GET /huawei/drs` | 数据复制服务 | - |
| `GET /huawei/mrs` | MapReduce 服务 | - |
| `GET /huawei/as` | 弹性伸缩 | - |
| `GET /huawei/bms` | 裸金属服务器 | - |
| `GET /huawei/workspace` | 云桌面 | `wks` |
| `GET /huawei/dli` | 数据湖探索 | - |
| `GET /huawei/dws` | 数据仓库 | - |
| `GET /huawei/gaussdbnosql` | GaussDB NoSQL | - |
| `GET /huawei/gaussdbopengauss` | GaussDB openGauss | - |
| `GET /huawei/ddm` | 分布式数据库中间件 | - |
| `GET /huawei/cse` | 微服务引擎 | - |
| `GET /huawei/servicestage` | 应用管理 | - |
| `GET /huawei/cbh` | 堡垒机 | - |
| `GET /huawei/dbss` | 数据库安全 | - |
| `GET /huawei/vod` | 视频点播 | - |
| `GET /huawei/live` | 直播服务 | - |
| `GET /huawei/oms` | 对象存储迁移 | - |
| `GET /huawei/sdrs` | 存储容灾 | - |
| `GET /huawei/sms` | 主机迁移 | - |
| `GET /huawei/dsc` | 数据安全中心 | - |
| `GET /huawei/roma` | ROMA Connect | - |
| `GET /huawei/cph` | 云手机 | - |
| `GET /huawei/er` | 企业路由器 | - |
| `GET /huawei/vpcep` | VPC 终端节点 | - |
| `GET /huawei/ief` | 智能边缘 | - |
| `GET /huawei/iotda` | 物联网 | `iot` |
| `GET /huawei/deh` | 专属主机 | - |
| `GET /huawei/bcs` | 区块链服务 | - |
