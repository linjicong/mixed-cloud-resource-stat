# Getting Started

基于云厂商提供的SDK，实现三大云厂商资源的统一管理。

## 主要功能：

已实现的功能：

华为云

- [x] 弹性云服务器(ECS)
- [x] 云硬盘(EVS)
- [x] 关系型数据库(RDS)
- [x] 云监控指标(CES)
- [x] 云监控数据(CES)
- [x] 负载均衡器(ELB)
- [x] 对象存储服务(OBS)
- [x] 文件存储服务(SFS)
- [x] 分布式缓存 Redis(DCS)
- [x] 文档数据库 Mongo(DDS)
- [x] 虚拟私有云(VPC)
- [x] 运营能力开放(BSS)
  - [x] 查询指定月份的月度摊销成本
  - [x] 查询指定账期的消费流水账单
- [x] 资源管理服务(RMS)

腾讯云

- [x] 云服务器(CVM)
- [x] 云数据库(CDB)
- [X] 账单(Billing)
  - [X] 查询账单汇总

阿里云

# 注意事项

# 开发

1. 组件关系图

2. 准备工作

2.1 配置数据库，修改application.yaml相关数据库配置

2.2 编译和启动应用

3. 开发细节

3.1 开发流程

3.1.1 使用JPA+hibernate实现启动程序自动生成数据库表,步骤如下:

3.1.2 首先从SDK直接拷贝相应Response中返回的具体类型后删除多余代码,例如@JsonInclude,@JsonProperty

3.1.3 继承BasicEntity,在类和字段上添加相关注解

```shell
类上:
@Data
@Entity
@Table(name = "h_cloud_ecs")
@TypeDef(name = "json",typeClass = JsonStringType.class) // 使用json类型
字段上:
@Column(name="os_ext_sts_task_state",columnDefinition="json") //非基本类型需要,字段名不规则(非驼峰,例如双大写,字母大写)需要自定义
@Type(type = "json") //非基本类型需要
```

3.1.4 检查字段名是否符合要求,不符合要求的要使用@Column自定义名称.给非基本类型的字段添加相应TypeHandle,给实体类添加Mapper接口

3.1.5 在Client中创建相关方法,并创建相关测试类进行测试

3.2 前提条件:

3.2.1 引入以下依赖

```shell
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>hibernate-types-52</artifactId>
    <version>2.16.2</version>
</dependency>
需注意需排除javax.persistence1.0依赖
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

3.2.2 application.yaml配置

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

# 参考

华为云:

- https://sdkcenter.developer.huaweicloud.com/?language=java
- https://github.com/huaweicloud/huaweicloud-sdk-java-v3
- https://apiexplorer.developer.huaweicloud.com/apiexplorer/overview

腾讯云:

- https://cloud.tencent.com/document/sdk
- https://github.com/TencentCloud/tencentcloud-sdk-java
- https://console.cloud.tencent.com/api/explorer
  
  # 问题
  
  开发问题:
  TypeHandle不能使用泛型,需定义明确类型,这是个问题,还没有找到解决方案
  https://gitee.com/free/Mapper/issues/I1XMD2#note_11943350

华为云:
缺失SFS的SDK,只有SFS Turbo,可以考虑使用OpenStackSDK替代

腾讯云:
没有链式调用,组装比较麻烦
无资源管理服务接口
