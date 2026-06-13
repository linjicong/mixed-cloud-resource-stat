# Spec: 资源型产品覆盖率达到 100%

> Feature ID: `resource-coverage-100`
> Flow type: `standard`
> Trace ID: `sc-20260612-7406a5`
> 创建时间: 2026-06-12

---

## 1. 问题陈述

当前混合云资源统计系统对华为云、腾讯云、阿里云三个云厂商的**资源型产品覆盖率**未达到 100%。
根据 `docs/cloud_api_comparison.md` 的定义，资源型产品 = 已实现 + 子产品（有实例可跟踪的产品）。

**现状：**

| 云厂商 | 已实现 | 可采集产品总数 | 覆盖率 | 差距 |
|--------|--------|---------------|--------|------|
| 华为云 | 69 | 91 | 75.8% | **+22** |
| 腾讯云 | 169 | 214 | 79.0% | **+45** |
| 阿里云 | 38 | 59 | 64.4% | **+21** |

此外还存在**已注册但未可用**的严重问题：
- **阿里云**：13 个 client 方法是 stub（返回空列表或抛异常），需补真实实现
- **腾讯云**：`QCloudService.syncEcs()` 是空壳 stub，~170+ 已注册 entity 的 sync 全部失效

---

## 2. 目标

### 成功标准

1. **三云资源型产品覆盖率均达到 100%**（按 `docs/cloud_api_comparison.md` 定义）
2. **QCloud 全量 170+ 已注册 entity** 均有完整 sync pipeline（entity → client → service → controller）
3. **ACloud 13 个 stub** 全部补真实实现
4. **HCloud 22 个待新增**资源型产品全部实现
5. **ACloud 21 个待新增**资源型产品全部实现
6. 前端页面同步适配新增资源的展示

### 最终覆盖率目标

| 云厂商 | 目标已实现 | 可采集产品总数 | 目标覆盖率 |
|--------|-----------|---------------|-----------|
| 华为云 | 91 | 91 | **100%** |
| 腾讯云 | 214 | 214 | **100%** |
| 阿里云 | 59 | 59 | **100%** |

---

## 3. 范围

### 3.1 每个资源型产品的完整实现标准（四层架构）

每个资源型产品必须完成以下四层：

| 层 | 说明 | 产出 |
|----|------|------|
| **Entity** | 数据模型 | `dao/entity/{provider}/XxxYyy.java` + 对应 Mapper |
| **Client** | API 调用封装 | `client/{Provider}Client.java` 中的 `listXxx()` 方法 |
| **Service** | 同步编排 | `service/{Provider}Service.java` 的 `syncEcs()` 中调用 client 方法并写入 DB |
| **Controller** | 查询接口 | `controller/ResourceController.java` 中注册查询 endpoint |

### 3.2 华为云（HCloud）— 新增 22 个

从 `docs/cloud_api_coverage.md` 提取待新增资源型产品清单（已排除无状态 API、SaaS、工具/平台类）。
文档中标注为"子产品"或"待实现"的资源型产品需逐一补齐。

**关键参考文件：**
- Entity: `backend/src/main/java/com/linjicong/cloud/stat/dao/entity/hcloud/`
- Client: `backend/src/main/java/com/linjicong/cloud/stat/client/HCloudClient.java`
- Service: `backend/src/main/java/com/linjicong/cloud/stat/service/HCloudService.java`
- Mapper: `backend/src/main/java/com/linjicong/cloud/stat/dao/mapper/hcloud/`

### 3.3 腾讯云（QCloud）— 新增 45 个 + 修复全部 170+ sync

**Phase 1: 修复 QCloudService stub（激活 ~35 个已有 client 方法）**
- `QCloudService.syncEcs()` 从返回 0 改为逐个调用已有 client 方法
- 参照 `HCloudService.syncEcs()` 的模式

**Phase 2: 补齐 ~135+ entity-only 类型的 client 方法 + service 注册**
- 为每个缺少 client 方法的 entity 添加对应的 `listXxx()` API 调用
- 在 `QCloudService.syncEcs()` 中注册同步调用

**Phase 3: 新增 45 个文档标注的差距资源**

**关键参考文件：**
- Entity: `backend/src/main/java/com/linjicong/cloud/stat/dao/entity/qcloud/`
- Client: `backend/src/main/java/com/linjicong/cloud/stat/client/QCloudClient.java`
- Service: `backend/src/main/java/com/linjicong/cloud/stat/service/QCloudService.java`
- Mapper: `backend/src/main/java/com/linjicong/cloud/stat/dao/mapper/qcloud/`

### 3.4 阿里云（ACloud）— 修复 13 个 stub + 新增 21 个

**Phase 1: 修复 13 个 stub 方法（替换空实现为真实 API 调用）**

| 方法 | 当前状态 | 需要实现 |
|------|---------|---------|
| SLS (日志服务) | throws UnsupportedOperationException | 真实 API 调用 |
| Kafka | 返回空列表 | 真实 API 调用 |
| ESS (弹性伸缩) | 返回空列表 TODO | 真实 API 调用 |
| HSS (主机安全) | 返回空列表 TODO | 真实 API 调用 |
| IoT (物联网) | 返回空列表 TODO | 真实 API 调用 |
| EMR (大数据) | 返回空列表 TODO | 真实 API 调用 |
| VOD (视频点播) | 返回空列表 TODO | 真实 API 调用 |
| ACR (容器镜像) | 返回空列表 TODO | 真实 API 调用 |
| DDoS (防护) | 返回空列表 TODO | 真实 API 调用 |
| SSL (证书) | 返回空列表 TODO | 真实 API 调用 |
| CloudFirewall (防火墙) | 返回空列表 TODO | 真实 API 调用 |
| DSC (数据安全) | 返回空列表 TODO | 真实 API 调用 |
| PolarDB (数据库) | 返回空列表 TODO | 真实 API 调用 |

**Phase 2: 新增 21 个待补齐资源型产品**

**关键参考文件：**
- Entity: `backend/src/main/java/com/linjicong/cloud/stat/dao/entity/acloud/`
- Client: `backend/src/main/java/com/linjicong/cloud/stat/client/ACloudClient.java`
- Service: `backend/src/main/java/com/linjicong/cloud/stat/service/ACloudService.java`
- Mapper: `backend/src/main/java/com/linjicong/cloud/stat/dao/mapper/acloud/`

### 3.5 前端适配

- 新增资源的查询接口在前端页面中注册展示
- 参照现有资源的前端展示模式

---

## 4. 范围外

- 非资源型产品（SaaS、工具/平台类、无状态 API）的补齐
- 云厂商 API 版本升级或迁移
- 资源关联关系图谱（本次只做实例清单）
- 性能优化（大批量 API 分页并发等）
- 除 `syncEcs` 外的独立同步流程（如计费、监控指标）

---

## 5. 验收标准（Given/When/Then）

### AC-1: HCloud 覆盖率 100%
- **Given** HCloud 有 91 个可采集资源型产品
- **When** 执行 `syncEcs` 同步华为云
- **Then** 所有 91 种资源均有实例数据可查

### AC-2: QCloud 覆盖率 100%
- **Given** QCloud 有 214 个可采集资源型产品 + 170+ 已注册 entity
- **When** 执行 `syncEcs` 同步腾讯云
- **Then** 所有已注册 entity 均有完整 sync pipeline 且可查询

### AC-3: ACloud 覆盖率 100%
- **Given** ACloud 有 59 个可采集资源型产品
- **When** 执行 `syncEcs` 同步阿里云
- **Then** 所有 59 种资源均有实例数据可查，无 stub/空实现

### AC-4: 三云对齐一致性
- **Given** 三云均有对应能力的资源（如云服务器、数据库、网络等）
- **When** 查询资源列表
- **Then** 三云数据格式一致，查询接口模式统一

### AC-5: 前端展示完整
- **Given** 新增了资源型产品的后端接口
- **When** 访问前端页面
- **Then** 所有新增资源均可在前端查看和操作

---

## 6. 关键约束

1. **SDK 依赖**：阿里云每个服务有独立 artifact，新增资源时需确认 pom.xml 中已有对应依赖
2. **API 权限**：部分云服务 API 需要开通对应服务或申请权限，可能影响实际可用性
3. **测试环境**：需有三云实际账号和资源实例用于验证
4. **代码规范**：遵循项目既有模式（CGLib BeanCopier、MyBatis-Plus Mapper、统一异常处理）

---

## 7. 关联文档

- 三云接口覆盖对比: `docs/cloud_api_coverage.md`
- 华为云覆盖详情: `docs/hcloud_api_coverage.md`
- 腾讯云覆盖详情: `docs/tencent_api_coverage.md`
- 阿里云覆盖详情: `docs/aliyun_api_coverage.md`
