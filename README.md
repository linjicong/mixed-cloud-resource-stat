# Getting Started
实现三大云厂商资源的统一管理
华为云
腾讯云
阿里云



# 注意事项

# 开发
TypeHandle不能使用泛型,需定义明确类型,这是个问题,还没有找到解决方案
https://gitee.com/free/Mapper/issues/I1XMD2#note_11943350

关于自动生成数据库表:
由于字段太多,采用自动生成方式节约时间,从SDK直接拷贝相应类型后删除多余代码,再做以下处理
1. 引入以下依赖
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
2. application.yaml配置
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```
3. 实体类上使用注解
```shell
类上:
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class) // 使用json类型
字段上:
@Column(name="os_ext_sts_task_state",columnDefinition="json") //非基本类型需要,字段名不规则需要自定义
@Type(type = "json") //非基本类型需要
```
4. 非基本类型需要自定义TypeHandle,继承JsonTypeHandle

华为云:
缺失SFS的SDK,只有SFS Turbo,可以考虑使用OpenStackSDK替代
