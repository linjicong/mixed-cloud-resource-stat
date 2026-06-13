<template>
  <ProviderOverview provider="huawei" providerLabel="华为云" :categories="categories" />
</template>

<script setup>
import ProviderOverview from '@/components/ProviderOverview.vue'

const categories = [
  {
    key: 'compute', label: '计算', icon: 'Monitor',
    items: [
      { type: 'ecs', label: '弹性云服务器 ECS', route: '/huawei/ecs' },
      { type: 'cce', label: '容器引擎 CCE', route: '/huawei/cce' },
      { type: 'faas', label: '函数工作流', route: '/huawei/functiongraph' },
      { type: 'bms', label: '裸金属 BMS', route: '/huawei/bms' },
      { type: 'as', label: '弹性伸缩 AS', route: '/huawei/as' },
      { type: 'deh', label: '专属主机 DeH', route: '/huawei/deh' },
      { type: 'ims', label: '镜像服务 IMS', route: '/huawei/ims' },
    ]
  },
  {
    key: 'storage', label: '存储', icon: 'Box',
    items: [
      { type: 'obs', label: '对象存储 OBS', route: '/huawei/obs' },
      { type: 'evs', label: '云硬盘 EVS', route: '/huawei/evs' },
      { type: 'sfs', label: '文件存储 SFS', route: '/huawei/sfs' },
      { type: 'cbr', label: '数据备份 CBR', route: '/huawei/cbr' },
    ]
  },
  {
    key: 'database', label: '数据库', icon: 'Coin',
    items: [
      { type: 'rds', label: '关系型数据库 RDS', route: '/huawei/rds' },
      { type: 'dcs', label: '分布式缓存 DCS', route: '/huawei/dcs' },
      { type: 'dds', label: '文档数据库 DDS', route: '/huawei/dds' },
      { type: 'gaussdb', label: 'GaussDB', route: '/huawei/gaussdb' },
      { type: 'ddm', label: '分布式数据库 DDM', route: '/huawei/ddm' },
    ]
  },
  {
    key: 'network', label: '网络', icon: 'Connection',
    items: [
      { type: 'vpc', label: 'VPC 专有网络', route: '/huawei/vpc' },
      { type: 'elb', label: '负载均衡 ELB', route: '/huawei/elb' },
      { type: 'eip', label: '弹性公网 IP', route: '/huawei/eip' },
      { type: 'nat', label: 'NAT 网关', route: '/huawei/nat' },
      { type: 'vpn', label: 'VPN 网关', route: '/huawei/vpn' },
      { type: 'er', label: '企业路由器', route: '/huawei/er' },
      { type: 'dc', label: '云专线 DC', route: '/huawei/dc' },
      { type: 'ga', label: '全球加速 GA', route: '/huawei/ga' },
    ]
  },
  {
    key: 'security', label: '安全', icon: 'Lock',
    items: [
      { type: 'waf', label: 'WAF 防火墙', route: '/huawei/waf' },
      { type: 'antiddos', label: 'DDoS 防护', route: '/huawei/antiddos' },
      { type: 'cfw', label: '云防火墙', route: '/huawei/cfw' },
      { type: 'hss', label: '主机安全 HSS', route: '/huawei/hss' },
      { type: 'kms', label: '密钥管理 KMS', route: '/huawei/kms' },
      { type: 'ccm', label: 'SSL 证书', route: '/huawei/ccm' },
      { type: 'dsc', label: '数据安全', route: '/huawei/dsc' },
      { type: 'cbh', label: '堡垒机', route: '/huawei/cbh' },
    ]
  },
  {
    key: 'middleware', label: '中间件', icon: 'Connection',
    items: [
      { type: 'kafka', label: 'Kafka', route: '/huawei/kafka' },
      { type: 'rocketmq', label: 'RocketMQ', route: '/huawei/rocketmq' },
      { type: 'rabbitmq', label: 'RabbitMQ', route: '/huawei/rabbitmq' },
      { type: 'apig', label: 'API 网关', route: '/huawei/apig' },
      { type: 'cse', label: '微服务引擎', route: '/huawei/cse' },
    ]
  },
  {
    key: 'cdn', label: 'CDN 与媒体', icon: 'VideoCamera',
    items: [
      { type: 'cdn', label: 'CDN', route: '/huawei/cdn' },
      { type: 'live', label: '视频直播', route: '/huawei/live' },
      { type: 'vod', label: '视频点播', route: '/huawei/vod' },
    ]
  },
  {
    key: 'bigdata', label: '大数据', icon: 'DataLine',
    items: [
      { type: 'es', label: '云搜索 CSS', route: '/huawei/css' },
      { type: 'emr', label: '大数据 MRS', route: '/huawei/mrs' },
      { type: 'dli', label: '数据湖 DLI', route: '/huawei/dli' },
      { type: 'dws', label: '数据仓库 DWS', route: '/huawei/dws' },
    ]
  },
  {
    key: 'ops', label: '运维管理', icon: 'Setting',
    items: [
      { type: 'ces', label: '云监控 CES', route: '/huawei/ces_metric' },
      { type: 'cts', label: '云审计 CTS', route: '/huawei/cts' },
      { type: 'lts', label: '日志服务 LTS', route: '/huawei/lts' },
    ]
  },
  {
    key: 'iot', label: '物联网', icon: 'Iphone',
    items: [
      { type: 'iot', label: 'IoT 平台', route: '/huawei/iotda' },
      { type: 'ief', label: '智能边缘', route: '/huawei/ief' },
    ]
  }
]
</script>
