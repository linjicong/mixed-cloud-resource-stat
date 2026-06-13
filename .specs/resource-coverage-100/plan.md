# Plan: 资源型产品覆盖率达到 100%

> Feature ID: `resource-coverage-100`
> Trace ID: `sc-20260612-7406a5`
> 创建时间: 2026-06-12
> 依据: design.md

---

## 总览

| Phase | 内容 | 任务数 | 依赖 | 预估代码行数 |
|-------|------|--------|------|------------|
| 1 | QCloudService 修复 | 9 | 无 | ~2000 |
| 2 | QCloud Client 新增 | 17 | Phase 1 | ~3000 |
| 3 | ACloud Stub 修复 | 1 | 无 | ~500 |
| 4 | HCloud 新增 22 个 | 5 | 无 | ~3000 |
| 5 | ACloud 新增 21 个 | 5 | Phase 3 | ~3000 |
| 6 | 前端全面适配 | 4 | Phase 1-5 | ~40000 |

**总计: 41 个任务**

---

## Phase 1: QCloudService 修复（激活 66 个已有 client 方法）

> 改动文件: `QCloudService.java`
> 依赖: 无
> 验证: 编译通过 + 代码模式一致性

### Task 1.1: QCloudService 基础结构搭建
- **描述**: 创建 QCloudService 完整骨架，注入全部 185 个 QCloud Mapper，实现 `syncEcs(CloudConf)` 主方法框架
- **文件**: `QCloudService.java`
- **验收**: 编译通过，syncEcs 方法可被调用

### Task 1.2: 计算与容器类资源同步（8 个）
- **描述**: 实现 CVM、Lighthouse、Scf、TKE、TCR、TcrEnt、CloudPhone、CHC 的 sync 方法
- **唯一键**: CVM→InstanceId, Lighthouse→InstanceId, Scf→FunctionName, TKE→ClusterId, TCR→RegistryId, TcrEnt→RegistryId, CloudPhone→?, CHC→?
- **依赖**: Task 1.1

### Task 1.3: 数据库类资源同步（10 个）
- **描述**: 实现 Cdb、CynosDB、Postgresql、Sqlserver、MariaDb、DCDB、Memcached、Tendis、TcaplusDB、HBase 的 sync 方法
- **唯一键**: Cdb→InstanceId, CynosDB→ClusterId, Postgresql→DBInstanceId, Sqlserver→InstanceId, MariaDb→InstanceId, DCDB→InstanceId, Memcached→InstanceId, Tendis→InstanceId, TcaplusDB→ClusterId, HBase→?
- **依赖**: Task 1.1

### Task 1.4: 网络类资源同步（8 个）
- **描述**: 实现 Vpc、VpcSubnet、Eip、SecurityGroup、NatGateway、CdnDomain、DC、Gaap 的 sync 方法
- **唯一键**: Vpc→VpcId, Subnet→SubnetId, Eip→AddressId, SG→SecurityGroupId, NAT→NatGatewayId, CDN→Domain, DC→DirectConnectId, Gaap→ProxyId
- **依赖**: Task 1.1

### Task 1.5: 存储类资源同步（5 个）
- **描述**: 实现 Cbs、Cfs、CHDFS、Cos（需新增 client）、DLC 的 sync 方法
- **唯一键**: Cbs→DiskId, Cfs→FileSystemId, CHDFS→FileSystemId, Cos→BucketName?, DLC→DatabaseName
- **依赖**: Task 1.1
- **注意**: Cos 可能需要从 cos_api SDK 调用而非 tencentcloud-sdk-java

### Task 1.6: 安全类资源同步（10 个）
- **描述**: 实现 KMS、SecretMgr、SSL、WAF、CWP、CWP3、CloudHSM、HSM、SSLPod、DataSafeGov 的 sync 方法
- **唯一键**: KMS→KeyId, SecretMgr→SecretName, SSL→CertificateId, WAF→Domain, CWP→Quuid, CWP3→Quuid, HSM→ResourceId, SSLPod→Domain, DataSafeGov→?
- **依赖**: Task 1.1

### Task 1.7: 中间件与消息类资源同步（7 个）
- **描述**: 实现 Ckafka、TDMQ、EventBus、APIGW（需新增 client）、TSE（需新增 client）、TSF（需新增 client）、Cmq（需新增 client）的 sync 方法
- **唯一键**: Ckafka→InstanceId, TDMQ→ClusterId, EventBus→EventBusId, APIGW→ApiId?, TSE→?, TSF→?, Cmq→?
- **依赖**: Task 1.1

### Task 1.8: 大数据与搜索类资源同步（5 个）
- **描述**: 实现 ES、EMR、Oceanus、CTSDB（需新增 client）、WeData（需新增 client）的 sync 方法
- **唯一键**: ES→InstanceId, EMR→ClusterId, Oceanus→JobId?, CTSDB→?, WeData→?
- **依赖**: Task 1.1

### Task 1.9: 运维与监控类资源同步（3 个）
- **描述**: 实现 Monitor（需新增 client）、Audit（需新增 client）、Config（需新增 client）的 sync 方法
- **唯一键**: Monitor→?, Audit→?, Config→?
- **依赖**: Task 1.1

---

## Phase 2: QCloud Client 新增（~119 个新方法）+ Service 注册

> 改动文件: `QCloudClient.java`, `QCloudService.java`
> 依赖: Phase 1
> 验证: 编译通过

### Task 2.1: 核心基础设施 Client 方法新增（15 个）
- **描述**: 为 Cos、APIGW、BMS、Bastion、Monitor、Audit、Config、ControlCenter、SafeCenter、SafeGuard、SafeMonitor、IoT、IoTDevice、IoTHub、AgentGW 添加 client 方法
- **依赖**: Task 1.1

### Task 2.2: AI/ML 服务 Client 方法新增（25 个）
- **描述**: 为 OCR、ASR、TTS、NMT、TI、TIHai、Face、FaceFusion、FaceDeform、FaceMakeup、FaceSwap、VoiceClone、ContentRecognize、ContentSafe、SafeImage、SafeText、SafeAudio、SafeVideo、SafeDoc、ImageSearch、ImageProcess2、WebSearch、KnowledgeEngine、SmartView、SmartGuide 添加 client 方法
- **依赖**: Task 1.1

### Task 2.3: 游戏服务 Client 方法新增（6 个）
- **描述**: 为 GSE、GameAntiACE、GameDB、GameServer、GameVoice、GHPhone 添加 client 方法
- **依赖**: Task 1.1

### Task 2.4: 通信与媒体 Client 方法新增（12 个）
- **描述**: 为 VOD、VODMedia、VODProcess、TRTC、TRTCRoom、Meeting、WeLink、TencentConnect、VoiceMsg、MediaAsset、Docs、DocProcess 添加 client 方法
- **依赖**: Task 1.1

### Task 2.5: DNS/域名/安全 Client 方法新增（10 个）
- **描述**: 为 DNSPrivate、DNSSec、PrivDNS、DomainReg、ICPBeian、GTM、EO、CACert、CAPTCHA、ExposedMgr 添加 client 方法
- **依赖**: Task 1.1

### Task 2.6: 数据库/存储 Client 方法新增（8 个）
- **描述**: 为 KeeWiDB、CynosDB（已有）、CSP、CSPGateway、TCHouseC、TCHouseD、TCHouseP、NativeBuild 添加 client 方法
- **依赖**: Task 1.1

### Task 2.7: 企业服务 Client 方法新增（12 个）
- **描述**: 为 BI、WeData、CodingDevops、TAPD、BizProcess、ESign、EngWrite、Org、SmartAdvisor、MallTraffic、TourismBigdata、RTIEdu 添加 client 方法
- **依赖**: Task 1.1

### Task 2.8: 安全与合规 Client 方法新增（8 个）
- **描述**: 为 DataAudit、DeviceSafety、PenTest、RiskIdentify、SecCredential、TokenHub、VulnMgr、MiniSafe 添加 client 方法
- **依赖**: Task 1.1

### Task 2.9: 其他 Niche 服务 Client 方法新增（12 个）
- **描述**: 为 CloudBase、CloudContact、CloudStudio、HealthDash、HealthOmics、HealthReport2、IOA、MathGrade、MicroWeda、RTIIndustrial、SpokenEval、Weda 添加 client 方法
- **依赖**: Task 1.1

### Task 2.10-2.17: 各批次 Service 注册
- **描述**: 将 Task 2.1-2.9 新增的 client 方法在 QCloudService 中注册对应的 sync 方法
- **依赖**: 对应的 Task 2.x

---

## Phase 3: ACloud 13 个 Stub 修复

> 改动文件: `ACloudClient.java`, `pom.xml`
> 依赖: 无
> 验证: 编译通过

### Task 3.1: ACloud Client Stub 全部修复 — DONE
- **描述**: 逐个查阅阿里云 SDK 源码，替换 13 个空实现为真实 API 调用
- **具体清单**:
  1. `listKafka()` — alikafka20190916: GetInstanceList ✅
  2. `listEss()` — ess20220222: DescribeScalingGroups（升级SDK从1.0.1到1.9.13） ✅
  3. `listHss()` — sas20181203: DescribeCloudCenterInstances ✅
  4. `listIoT()` — iot20180120: QueryProductList ✅
  5. `listEmr()` — emr20160408: ListClusters ✅
  6. `listVod()` — vod20170321: GetVideoList ✅
  7. `listAcr()` — cr20181201: ListInstance（升级SDK从cr20160607到cr20181201） ✅
  8. `listDdos()` — ddosbgp20171120: DescribeInstanceList ✅
  9. `listSsl()` — cas20180713: DescribeUserCertificateList ✅
  10. `listCloudFirewall()` — cloudfw20171207: DescribeAssetList ✅
  11. `listDsc()` — dbs20190306: DescribeBackupPlanList ✅
  12. `listPolarDb()` — polardb20170801: DescribeDBClusters ✅
  13. `listSls()` — sls20201230: ListProject（新增SDK依赖） ✅
- **pom.xml 变更**:
  - 新增 `com.aliyun:sls20201230:5.9.0`
  - 升级 `ess20140828:1.0.1` → `ess20220222:1.9.13`
  - 升级 `cr20160607:1.0.0` → `cr20181201:2.0.3`
- **产出文件**: `ACloudClient.java`, `pom.xml`
- **验证**: `mvn compile` BUILD SUCCESS
- **依赖**: 无

---

## Phase 4: HCloud 新增 22 个资源型产品

> 改动文件: entity/mapper/client/service/controller 全新增
> 依赖: 无
> 验证: 编译通过

### Task 4.1: HCloud 新增资源清单确认
- **描述**: 从 `docs/hcloud_api_coverage.md` 提取 22 个待新增资源型产品清单，确认每个资源的 entity 字段、SDK API、唯一键
- **产出**: `.specs/resource-coverage-100/hcloud-new-resources.md`

### Task 4.2: HCloud 新增 Entity + Mapper（第一批：11 个）
- **描述**: 创建 entity 类和 mapper 接口
- **文件**: `dao/entity/hcloud/`, `dao/mapper/hcloud/`
- **依赖**: Task 4.1

### Task 4.3: HCloud 新增 Entity + Mapper（第二批：11 个）
- **描述**: 创建 entity 类和 mapper 接口
- **依赖**: Task 4.1

### Task 4.4: HCloud Client + Service 新增（全部 22 个）
- **描述**: 在 HCloudClient 中新增 list 方法，在 HCloudService 中注册 sync 方法
- **依赖**: Task 4.2, 4.3

### Task 4.5: HCloud Controller 新增
- **描述**: 在 ResourceController 中为新增资源注册查询 endpoint
- **依赖**: Task 4.4

---

## Phase 5: ACloud 新增 21 个资源型产品

> 改动文件: entity/mapper/client/service/controller 全新增
> 依赖: Task 3.1
> 验证: 编译通过

### Task 5.1: ACloud 新增资源清单确认
- **描述**: 从 `docs/aliyun_api_coverage.md` 提取 21 个待新增资源型产品清单，确认 SDK 依赖、entity 字段、唯一键
- **产出**: `.specs/resource-coverage-100/acloud-new-resources.md`
- **可能需新增**: pom.xml 中的阿里云 SDK artifact

### Task 5.2: ACloud 新增 Entity + Mapper（第一批：11 个）
- **描述**: 创建 entity 类和 mapper 接口
- **文件**: `dao/entity/acloud/`, `dao/mapper/acloud/`
- **依赖**: Task 5.1

### Task 5.3: ACloud 新增 Entity + Mapper（第二批：10 个）
- **描述**: 创建 entity 类和 mapper 接口
- **依赖**: Task 5.1

### Task 5.4: ACloud Client + Service 新增（全部 21 个）
- **描述**: 在 ACloudClient 中新增 list 方法，在 ACloudService 中注册 sync 方法
- **依赖**: Task 5.2, 5.3

### Task 5.5: ACloud Controller 新增
- **描述**: 在 ResourceController 中为新增资源注册查询 endpoint
- **依赖**: Task 5.4

---

## Phase 6: 前端全面适配

> 改动文件: Vue 组件、路由、Store
> 依赖: Phase 1-5
> 验证: 前端编译通过 + 页面可访问

### Task 6.1: 前端模板组件生成器
- **描述**: 创建通用资源列表 Vue 组件模板，支持动态列配置（基于 entity 字段自动生成表格列），减少 280+ 个组件的重复代码
- **文件**: `frontend/src/components/ResourceTable.vue`（通用组件）
- **依赖**: 无

### Task 6.2: QCloud 前端视图（180 个）
- **描述**: 为 QCloud 185 个资源中缺少视图的 ~180 个创建 Vue 组件 + 路由
- **文件**: `frontend/src/views/tencent/`, `frontend/src/router/index.js`
- **依赖**: Task 6.1, Phase 1

### Task 6.3: ACloud 前端视图（38 个）
- **描述**: 为 ACloud 39 个资源中缺少视图的 ~38 个创建 Vue 组件 + 路由
- **文件**: `frontend/src/views/aliyun/`, `frontend/src/router/index.js`
- **依赖**: Task 6.1, Phase 5

### Task 6.4: HCloud 前端视图（16 个）
- **描述**: 为 HCloud 新增的 22 个资源创建 Vue 组件 + 路由（已有 6 个视图）
- **文件**: `frontend/src/views/huawei/`, `frontend/src/router/index.js`
- **依赖**: Task 6.1, Phase 4

---

## 依赖关系图

```
Phase 1 (QCloudService) ──→ Phase 2 (QCloud Client) ──→ Task 6.2 (QCloud 前端)
                                                            ↓
Phase 3 (ACloud Stub) ──→ Phase 5 (ACloud 新增) ──→ Task 6.3 (ACloud 前端)
                                                            ↓
Phase 4 (HCloud 新增) ─────────────────────────→ Task 6.4 (HCloud 前端)
                                                            ↓
                                                    Task 6.1 (通用组件) ──→ 全部前端
```

**并行策略**:
- Phase 1 + Phase 3 + Phase 4 可**完全并行**（不同文件、不同云厂商）
- Phase 2 依赖 Phase 1 完成
- Phase 5 依赖 Phase 3 完成
- Phase 6 的通用组件（Task 6.1）可与 Phase 1-5 并行
- Phase 6 的各云视图依赖对应 Phase 完成

---

## 测试策略

由于项目无单元测试框架，验证策略为：

1. **编译验证**: 每个 Phase 完成后执行 `mvn compile` 确保无编译错误
2. **模式一致性**: 代码 review 确认每个 sync 方法遵循 ACloudService 标杆模式
3. **字段映射验证**: 确认 entity 字段名与 SDK model 字段名一致（CGLib BeanCopier 按名匹配）
4. **前端验证**: `npm run build` 确认无编译错误
