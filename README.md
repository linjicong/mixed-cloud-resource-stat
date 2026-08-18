# 混合云资源统计系统

实现三大云厂商资源的统一管理、监控和统计分析。
- 华为云
- 腾讯云
- 阿里云

## 技术栈

| 组件 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 3.5.7 |
| 数据访问 | MyBatis-Plus + JPA/Hibernate | 3.5.14 / 6.x |
| 数据库 | MySQL | 8.x |
| 前端框架 | Vue 3 + Element Plus + Pinia | - |
| 腾讯云SDK | tencentcloud-sdk-java | 3.1.1355 |
| 腾讯云COS SDK | cos_api | 5.6.137 |
| 华为云SDK | esdk-obs-java-bundle | 3.25.10 |

## 支持的资源类型

### 腾讯云（50个API，45个资源同步）

| 资源类型 | 查询路由 | API方法 | 说明 |
|---------|---------|---------|------|
| CVM 云服务器 | /tencent/cvm | listCvm() | 弹性云服务器 |
| CBS 云硬盘 | /tencent/cbs | listCbs() | 云硬盘 |
| CDB 云数据库MySQL | /tencent/cdb | listCdb() | 云数据库MySQL |
| CLB 负载均衡 | /tencent/clb | listClb() | 负载均衡 |
| CFS 文件存储 | /tencent/cfs | listCfs() | 文件存储 |
| VPC 私有网络 | /tencent/vpc | listVpc() | 私有网络 |
| 子网 | /tencent/subnet | listSubnet() | VPC子网 |
| EIP 弹性公网IP | /tencent/eip | listEip() | 弹性公网IP |
| 安全组 | /tencent/sg | listSecurityGroup() | 安全组 |
| CDN 域名 | /tencent/cdn | listCdnDomain() | CDN加速域名 |
| COS 对象存储 | /tencent/cos | listCos() | 对象存储Bucket |
| Redis 云数据库 | /tencent/redis | listRedis() | 云数据库Redis |
| MongoDB 云数据库 | /tencent/mongodb | listMongoDb() | 云数据库MongoDB |
| CynosDB TDSQL-C | /tencent/cynosdb | listCynosDB() | 云原生数据库 |
| PostgreSQL | /tencent/postgresql | listPostgresql() | 云数据库PostgreSQL |
| SQL Server | /tencent/sqlserver | listSqlserver() | 云数据库SQL Server |
| NAT 网关 | /tencent/nat | listNatGateway() | NAT网关 |
| DNS 域名 | /tencent/dns | listDnsDomain() | DNSPod域名解析 |
| Billing 账单 | /tencent/bills | listBillResourceSummary() | 账单资源摘要 |
| CAM 用户 | - | listUsers() | 子用户管理 |
| CAM 密钥 | - | listAccessKeys() | 访问密钥 |
| 资源标签 | - | listResourceTags() | 资源标签管理 |
| SCF 云函数 | /tencent/scf | listScf() | Serverless云函数 |
| MariaDB | /tencent/mariadb | listMariaDb() | 云数据库MariaDB |
| TDSQL | /tencent/dcdb | listDCDB() | 分布式数据库TDSQL |
| CKafka | /tencent/ckafka | listCkafka() | 消息队列CKafka |
| RocketMQ | /tencent/rocketmq | listRocketMQ() | 消息队列RocketMQ |
| SSL 证书 | /tencent/ssl | listSSL() | SSL证书 |
| WAF 防火墙 | /tencent/waf | listWAF() | Web应用防火墙 |
| CLS 日志服务 | /tencent/cls | listCLS() | 日志服务 |
| Monitor 云监控 | /tencent/monitor | listMonitor() | 云监控 |
| Domain 域名 | /tencent/domain | listDomain() | 域名注册 |
| TKE 容器服务 | /tencent/tke | listTKE() | 容器服务 |
| TCR 镜像仓库 | /tencent/tcr | listTCR() | 容器镜像服务 |
| ES 搜索 | /tencent/es | listES() | Elasticsearch |
| SCF 云函数 | /tencent/scf | listScf() | Serverless云函数 |
| MariaDB | /tencent/mariadb | listMariaDb() | 云数据库MariaDB |
| TDSQL | /tencent/dcdb | listDCDB() | 分布式数据库TDSQL |
| CKafka | /tencent/ckafka | listCkafka() | 消息队列CKafka |
| RocketMQ | /tencent/rocketmq | listRocketMQ() | 消息队列RocketMQ |
| SSL 证书 | /tencent/ssl | listSSL() | SSL证书 |
| WAF 防火墙 | /tencent/waf | listWAF() | Web应用防火墙 |
| CLS 日志服务 | /tencent/cls | listCLS() | 日志服务 |
| Monitor 云监控 | /tencent/monitor | listMonitor() | 云监控 |
| Domain 域名 | /tencent/domain | listDomain() | 域名注册 |
| TKE 容器服务 | /tencent/tke | listTKE() | 容器服务 |
| TCR 镜像仓库 | /tencent/tcr | listTCR() | 容器镜像服务 |
| ES 搜索 | /tencent/es | listES() | Elasticsearch |
| Memcached | /tencent/memcached | listMemcached() | 缓存Memcached |
| KeeWiDB | /tencent/keewidb | listKeeWiDB() | 缓存KeeWiDB |
| CTSDB 时序库 | /tencent/ctsdb | listCTSDB() | 时序数据库 |
| CHDFS 云HDFS | /tencent/chdfs | listCHDFS() | 云HDFS |
| AS 弹性伸缩 | /tencent/as | listAS() | 弹性伸缩 |
| Lighthouse 轻量 | /tencent/lighthouse | listLighthouse() | 轻量应用服务器 |
| DC 专线接入 | /tencent/dc | listDC() | 专线接入 |
| RabbitMQ | /tencent/rabbitmq | listRabbitMQ() | 消息队列RabbitMQ |
| API网关 | /tencent/apigw | listAPIGW() | API网关 |
| BMS 裸金属 | /tencent/bms | listBMS() | 裸金属云服务器 |
| TDMQ | /tencent/tdmq | listTDMQ() | 消息队列TDMQ |
| Oceanus 流计算 | /tencent/oceanus | listOceanus() | 流计算Oceanus |
| EMR 弹性MapReduce | /tencent/emr | listEMR() | 弹性MapReduce |
| GAAP 全球加速 | /tencent/gaap | listGaap() | 全球应用加速 |

### 华为云（9种）

ECS、RDS、ELB、EVS、VPC、EIP、IMS、CBR、Bills

查询路由：`/huawei/{type}`

### 阿里云

DNS 域名解析，查询路由：`/aliyun/dns`

## API接口

| 接口 | 方法 | 说明 |
|------|------|------|
| /{provider}/{type} | GET | 查询指定云厂商资源 |
| /sync/{provider} | POST | 同步指定云厂商全部资源 |
| /statistics | GET | 资源数量统计概览 |
| /cloud-configs | CRUD | 云厂商配置管理 |

## 同步机制

采用增量对比策略：
1. 从云厂商API获取最新资源列表
2. 从数据库查询当前未删除的资源记录
3. 对比唯一标识（如instanceId），找出新增和已下线资源
4. 批量插入新增资源，逻辑删除已下线资源

支持定时自动同步（每天凌晨1点）和手动触发同步。

## 快速开始

```bash
# 数据库初始化
mysql -u root -p cloud_resource < database/schema.sql
mysql -u root -p cloud_resource < database/schema_new_qcloud.sql

# 启动后端
cd backend && mvn spring-boot:run

# 启动前端
cd frontend && npm install && npm run dev
```

## 项目结构

```
backend/src/main/java/com/linjicong/cloud/stat/
  client/              云厂商SDK客户端
    HCloudClient.java  华为云客户端
    QCloudClient.java  腾讯云客户端（23个API方法）
    ACloudClient.java  阿里云客户端
  controller/          REST控制器
  dao/entity/qcloud/   腾讯云实体类（24个）
  dao/mapper/qcloud/   腾讯云Mapper（24个）
  service/
    CloudService.java  云服务接口
    QCloudService.java 腾讯云同步（18个资源）
    HCloudService.java 华为云同步
    CloudFactory.java  云服务工厂
  task/Task.java       定时同步任务
database/
  schema.sql           主表DDL
  schema_new_qcloud.sql 腾讯云新增表DDL（12张）
frontend/              Vue 3 + Element Plus + Pinia
```

## 注意事项

### TypeHandle

TypeHandle不能使用泛型，需定义明确类型，这是个问题，还没有找到解决方案。
https://gitee.com/free/Mapper/issues/I1XMD2#note_11943350

### 数据库表自动生成

由于字段太多，采用自动生成方式节约时间。从SDK直接拷贝相应类型后删除多余代码，再做以下处理：

1. 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>hibernate-types-52</artifactId>
    <version>2.16.2</version>
</dependency>
```

注意需排除javax.persistence 1.0依赖：
```xml
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>4.2.1</version>
    <exclusions>
        <exclusion>
            <artifactId>persistence-api</artifactId>
            <groupId>javax.persistence</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

2. application.yaml配置
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

3. 实体类注解
```java
// 类上
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)

// 字段上
@Column(columnDefinition = "json")
@Type(type = "json")
```

4. 非基本类型需要自定义TypeHandle，继承JsonTypeHandle

### 华为云

缺失SFS的SDK，只有SFS Turbo，可以考虑使用OpenStack SDK替代。

### 参考文档

华为云:

- https://sdkcenter.developer.huaweicloud.com/?language=java
- https://github.com/huaweicloud/huaweicloud-sdk-java-v3

- 弹性云服务器(ECS)
    - https://console.huaweicloud.com/apiexplorer/#/openapi/ECS/doc
      地区和终端节点:

- https://developer.huaweicloud.com/endpoint
- https://apiexplorer.developer.huaweicloud.com/apiexplorer/overview 产品列表
腾讯云:

- https://cloud.tencent.com/document/sdk
- https://github.com/TencentCloud/tencentcloud-sdk-java
- https://console.cloud.tencent.com/api/explorer
- https://cloud.tencent.com/api/list 产品列表

阿里云:

- https://next.api.aliyun.com/api-tools/sdk
- https://github.com/aliyun/alibabacloud-sdk
- https://next.api.aliyun.com/api/Alidns/2015-01-09/DescribeGtmInstances
- https://api.aliyun.com/document?spm=api-workbench.home.0.0.67e2e85crfeQ0V 产品列表

## License

MIT

