# Getting Started

基于云厂商提供的SDK，实现三大云厂商资源的统一数据获取。

## 主要功能：

已实现的功能：

华为云

- [x] 弹性云服务器(ECS)
- [x] 云硬盘(EVS)
- [x] 关系型数据库(RDS)
- [x] 负载均衡器(ELB)
- [x] 对象存储服务(OBS)
- [x] 文件存储服务(SFS)
- [x] 分布式缓存 Redis(DCS)
- [x] 文档数据库 Mongo(DDS)
- [x] 虚拟私有云(VPC)
- [x] 云监控指标(CES)
- [x] 云监控数据(CES)
- [x] 运营能力开放(BSS)
  - [x] 查询指定月份的月度摊销成本
  - [x] 查询指定账期的消费流水账单
- [x] 资源管理服务(RMS)

腾讯云

- [x] 云服务器(CVM)
- [x] 云硬盘(CBS)
- [x] 云数据库(CDB)
- [x] 云硬盘(CLB)
- [X] 账单(Billing)
  - [X] 查询账单汇总

阿里云
- [X] 云解析(DNS)
  - [x] 查询域名列表
  - [x] 查询解析记录列表

# 参考

华为云:

- https://sdkcenter.developer.huaweicloud.com/?language=java
- https://github.com/huaweicloud/huaweicloud-sdk-java-v3
- https://apiexplorer.developer.huaweicloud.com/apiexplorer/overview

地区和终端节点:

- https://developer.huaweicloud.com/endpoint

腾讯云:

- https://cloud.tencent.com/document/sdk
- https://github.com/TencentCloud/tencentcloud-sdk-java
- https://console.cloud.tencent.com/api/explorer
  
阿里云:

- https://next.api.aliyun.com/api-tools/sdk
- https://github.com/aliyun/alibabacloud-sdk
- https://next.api.aliyun.com/api/Alidns/2015-01-09/DescribeGtmInstances
