# Design: 资源型产品覆盖率达到 100%

> Feature ID: `resource-coverage-100`
> Trace ID: `sc-20260612-7406a5`
> 创建时间: 2026-06-12

---

## 1. 现状分析

### 1.1 覆盖率差距

| 云厂商 | 当前 | 目标 | 差距 | 主要阻塞点 |
|--------|------|------|------|-----------|
| HCloud | 69/91 | 91 | +22 | 需新增 22 个资源类型 |
| QCloud | 169/214 | 214+170entity | 极大 | QCloudService 是空壳 stub |
| ACloud | 38/59 | 59 | +21+13stub | 13 个 client stub + 21 个新增 |

### 1.2 技术架构（四层模式）

```
Entity (数据模型) → Mapper (数据访问) → Client (API 调用) → Service (同步编排) → Controller (查询接口)
```

- **Entity**: `dao/entity/{provider}/XxxYyy.java` + `@Entity @Table` + 继承 `BasicEntity`
- **Mapper**: `dao/mapper/{provider}/XxxYyyMapper.java` + 继承 `CommonMapper<T>`
- **Client**: `client/{Provider}Client.java` 中的 `listXxx()` 方法，调用 SDK API + BeanUtils 拷贝
- **Service**: `service/{Provider}Service.java` 的 `syncEcs()` 中调用 client + diff 增删
- **Controller**: `controller/ResourceController.java` switch-case 注册查询

### 1.3 同步模式（以 ACloudService 为标杆）

```java
// diff-based 同步：API 全量 vs DB 全量 → insert 新增 + soft-delete 移除
private int syncXxx(ProviderClient client, CloudConf conf) {
    List<Entity> apiList = client.listXxx();
    List<Entity> dbList = mapper.selectByConfName(conf.getName());
    // 按唯一键建 Map
    Map<String, Entity> apiMap = apiList.stream().filter(key非空).collect(toMap);
    Map<String, Entity> dbMap = dbList.stream().filter(key非空).collect(toMap);
    // 新增 = API有 & DB无
    List<Entity> toInsert = apiList.stream().filter(!dbMap.containsKey(key)).collect(toList);
    // 软删除 = DB有 & API无
    Set<String> toDeleteIds = dbMap.keySet().stream().filter(!apiMap.containsKey).collect(toSet);
    // 执行
    if (!toInsert.isEmpty()) mapper.insertBatch(toInsert);
    if (!toDeleteIds.isEmpty()) mapper.update(null, uw.set(deleted, 1));
    return insertCount;
}
```

---

## 2. 架构决策

### 2.1 QCloud 实现策略（最关键决策）

**决策**: 实现完整 QCloudService，分两批：
1. **Batch 1**: 激活现有 66 个 client 方法（只需写 service sync 方法）
2. **Batch 2**: 为 ~119 个 entity-only 类型新增 client 方法 + sync 方法

**理由**: QCloudService 是完全 stub，185 个 entity + mapper 已就位，client 有 66 个方法可用。参照 ACloudService 模式逐个注册是最可靠路径。

**风险**: 119 个新 client 方法需要逐个查找腾讯云 SDK 的 API 类名、请求/响应结构，部分 niche 服务（AI/ML/游戏/医疗）的 API 模式可能不一致。

### 2.2 ACloud Stub 修复策略

**决策**: 逐个查阅阿里云 SDK 源码，替换空实现为真实 API 调用。

**关键风险**:
- SLS 无 SDK 依赖（需新增 pom.xml）
- 部分 SDK 版本较旧（ess20140828 1.0.1、iot20180120 6.0.0），API 类名可能与注释中的不一致
- 需要用已实现的方法（如 `listEcs()`）作为对照模板

### 2.3 HCloud/ACloud 新增资源策略

**决策**: 按 `docs/cloud_api_coverage.md` 中标注的差距逐个补齐。每个新增资源需要完整的四层（Entity + Mapper + Client + Service + Controller）。

**依赖**: 部分阿里云新增资源可能需要在 pom.xml 中添加新的 SDK artifact。

### 2.4 前端策略

**决策**: 为每种新增资源创建 Vue 视图组件 + 路由注册。后端 ResourceController 已有通用查询 endpoint，前端只需调用 `cloudStore.fetch{Provider}Resources(type)`。

**模式**: 复用 `CvmList.vue` 模板（el-table + 搜索 + 详情弹窗 + 分页）。

---

## 3. 实施阶段

### Phase 1: QCloudService 修复（优先级最高，影响面最大）

**目标**: 让 QCloud 的 66 个已有 client 方法全部可用

**改动文件**:
- `QCloudService.java` — 从 stub 改为完整实现

**工作内容**:
1. 注入 185 个 QCloud Mapper（`@Resource`）
2. 为 66 个已有 client 方法各写一个 `syncXxx()` 私有方法
3. 在 `syncEcs()` 中串联所有 sync 方法
4. 每个 sync 方法需确定唯一键字段（需逐个查看 entity 的字段定义）

**预估**: 每个 sync 方法约 30 行代码，66 个 ≈ 2000 行

### Phase 2: QCloud Client 新增（~119 个）

**目标**: 为 119 个 entity-only 类型添加 client 方法

**改动文件**:
- `QCloudClient.java` — 新增 ~119 个 `listXxx()` 方法
- `QCloudService.java` — 新增对应的 sync 方法

**工作内容**:
1. 逐个查找腾讯云 SDK 中对应服务的 Client 类、Request/Response 类
2. 按 Pattern A（单次请求）或 Pattern B（分页循环）实现
3. 确定 entity 的 SDK 字段映射（CGLib BeanCopier 按字段名匹配）
4. 注册到 QCloudService.syncEcs()

**风险**: 部分 niche 服务可能需要开通 API 权限，部分 SDK 类名需要实际编译验证

### Phase 3: ACloud Stub 修复（13 个）

**目标**: 将 13 个空实现替换为真实 API 调用

**改动文件**:
- `ACloudClient.java` — 替换 13 个 stub 方法
- `pom.xml` — 可能需要新增 SLS SDK 依赖

**需修复的 stub**:

| # | 方法 | SDK | 预估难度 |
|---|------|-----|---------|
| 1 | listKafka() | alikafka20190916 | 中（返回对象非 List） |
| 2 | listEss() | ess20140828 | 中（SDK 版本旧） |
| 3 | listHss() | sas20181203 | 中（类名不确定） |
| 4 | listIoT() | iot20180120 | 中（类名不确定） |
| 5 | listEmr() | emr20160408 | 中 |
| 6 | listVod() | vod20170321 | 低（API 结构标准） |
| 7 | listAcr() | cr20160607 | 低 |
| 8 | listDdos() | ddosbgp20171120 | 低 |
| 9 | listSsl() | cas20180713 | 低 |
| 10 | listCloudFirewall() | cloudfw20171207 | 低 |
| 11 | listDsc() | dbs20190306 | 低 |
| 12 | listPolarDb() | polardb20170801 | 低 |
| 13 | listSls() | 需新增 SDK | 高（需加依赖） |

### Phase 4: HCloud 新增 22 个资源型产品

**目标**: HCloud 覆盖率 75.8% → 100%

**改动文件**:
- `dao/entity/hcloud/` — 新增 entity 类
- `dao/mapper/hcloud/` — 新增 mapper 接口
- `HCloudClient.java` — 新增 client 方法
- `HCloudService.java` — 新增 sync 方法
- `ResourceController.java` — 新增查询 endpoint
- `StatisticsController.java` — 如需统计则新增
- 前端 Vue 组件 + 路由

**需从 `docs/hcloud_api_coverage.md` 提取具体清单**

### Phase 5: ACloud 新增 21 个资源型产品

**目标**: ACloud 覆盖率 64.4% → 100%

**改动文件**: 同 Phase 4 的 ACloud 版本

**可能需要**: 新增 pom.xml 中的阿里云 SDK 依赖

### Phase 6: 前端全面适配

**目标**: 所有新增资源在前端可查看

**改动文件**:
- `frontend/src/views/huawei/` — 新增 HCloud 视图
- `frontend/src/views/tencent/` — 新增 QCloud 视图（185 个中缺 ~180 个）
- `frontend/src/views/aliyun/` — 新增 ACloud 视图
- `frontend/src/router/index.js` — 注册所有新路由
- `frontend/src/stores/cloud.js` — 可能需要扩展 store

**预估**: 每个 Vue 视图约 150-200 行，280+ 个新视图

---

## 4. 数据模型设计

### 4.1 Entity 基类（已有，无需修改）

```java
// 继承链
BasicEntityExtend (confName, confProvider, confRegion)
  └── BasicEntity (pk, statTime, statDate, deleted)
       └── QCloudXxx / ACloudXxx / HCloudXxx (具体字段)
```

### 4.2 唯一键设计原则

每个 entity 必须有一个可用于 diff 的唯一键字段：
- 实例类资源：`InstanceId` / `instanceId`
- 域名类资源：`domainName`
- 证书类资源：`certId`
- 无明确 ID 的资源：使用 `name` 或复合键

**sync 方法中必须 `filter(e -> keyField != null)` 以防 NPE**

### 4.3 QCloud 字段映射注意事项

QCloud entity 使用 **PascalCase** 字段名（如 `InstanceId`、`InstanceType`），与腾讯云 SDK model 类字段名一致，CGLib BeanCopier 自动匹配。

---

## 5. 接口契约

### 5.1 查询接口（已有，无需修改）

```
GET /api/huawei/{type}     → HCloud 资源列表
GET /api/tencent/{type}    → QCloud 资源列表
GET /api/aliyun/{type}     → ACloud 资源列表
```

`ResourceController` 中 switch-case 已注册所有已知 type → mapper 映射。新增资源只需添加新的 case。

### 5.2 同步接口（已有，无需修改）

```
POST /api/sync/huawei      → HCloudService.syncEcs()
POST /api/sync/tencent     → QCloudService.syncEcs()
POST /api/sync/aliyun      → ACloudService.syncEcs()
```

---

## 6. 风险与缓解

| 风险 | 影响 | 缓解 |
|------|------|------|
| QCloud 119 个新 client 方法的 SDK 类名不确定 | Phase 2 阻塞 | 先用 `javap` 或 IDE 自动补全验证 SDK 类路径 |
| ACloud 部分 SDK 版本旧，API 结构可能变化 | Phase 3 阻塞 | 先实现确定性高的（PolarDB、Kafka），再处理不确定的 |
| SLS 无 SDK 依赖 | Phase 3 listSls() 阻塞 | 新增 `com.aliyun:sls20201230` 依赖 |
| 前端 280+ 个新 Vue 组件工作量极大 | Phase 6 延期 | 可考虑生成模板化组件，或分批交付 |
| 部分云服务 API 需要开通权限 | 运行时失败 | 在 spec 中标注为外部依赖，sync 方法加 try-catch 容错 |
| 无实际云账号测试 | 无法端到端验证 | 编译通过 + 代码模式一致性审查作为替代验证 |

---

## 7. 关键文件清单

| 文件 | 用途 | 改动量 |
|------|------|--------|
| `QCloudService.java` | QCloud 同步核心 | **从 0 → ~5000 行** |
| `QCloudClient.java` | QCloud API 调用 | **新增 ~119 方法** |
| `ACloudClient.java` | 修复 13 个 stub | 修改 13 个方法 |
| `HCloudClient.java` | 新增 22 个方法 | 新增 |
| `HCloudService.java` | 新增 22 个 sync | 新增 |
| `ACloudService.java` | 无需修改（stub 修复后自动生效） | 0 |
| `ResourceController.java` | 新增查询 case | 修改 |
| `dao/entity/hcloud/` | 新增 22 个 entity | 新增 |
| `dao/mapper/hcloud/` | 新增 22 个 mapper | 新增 |
| `pom.xml` | 可能新增 SLS SDK | 修改 |
| `frontend/src/views/` | 新增 ~280 个 Vue 组件 | 新增 |
| `frontend/src/router/index.js` | 注册新路由 | 修改 |
