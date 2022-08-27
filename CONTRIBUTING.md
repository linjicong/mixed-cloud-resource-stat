完整流程
fork本项目；
克隆(clone)你 fork 的项目到本地；
新建分支(branch)并检出(checkout)新分支；
添加本项目到你的本地 git 仓库作为上游(upstream)；
进行修改，若你的修改包含方法或函数的增减，请记得修改单元测试文件；
变基（衍合 rebase）你的分支到上游 master 分支；
push 你的本地仓库到 GitHub；
提交 pull request；
等待 CI 验证（若不通过则重复 5~7，GitHub 会自动更新你的 pull request）；
等待管理员处理，并及时 rebase 你的分支到上游 master 分支（若上游 master 分支有修改）。
若有必要，可以 git push -f 强行推送 rebase 后的分支到自己的 fork

绝对不可以使用 git push -f 强行推送修改到上游

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

# 问题

开发问题:
TypeHandle不能使用泛型,需定义明确类型,这是个问题,还没有找到解决方案
https://gitee.com/free/Mapper/issues/I1XMD2#note_11943350

华为云:
缺失SFS的SDK,只有SFS Turbo,可以考虑使用OpenStackSDK替代

腾讯云:
没有链式调用,组装比较麻烦
无资源管理服务接口

TODO:
- [ ] 表增加3个字段,账号名称,云厂商类型,区域
- [ ] CloudConf加密
- [ ] 完善分页获取数据
