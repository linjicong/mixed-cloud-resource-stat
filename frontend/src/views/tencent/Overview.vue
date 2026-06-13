<template>
  <ProviderOverview provider="tencent" providerLabel="腾讯云" :categories="categories" />
</template>

<script setup>
import ProviderOverview from '@/components/ProviderOverview.vue'

const categories = [
  {
    key: 'compute', label: '计算', icon: 'Monitor',
    items: [
      { type: 'cvm', label: '云服务器 CVM', route: '/tencent/cvm' },
      { type: 'tke', label: '容器服务 TKE', route: '/tencent/tke' },
      { type: 'scf', label: '云函数 SCF', route: '/tencent/scf' },
      { type: 'bms', label: '裸金属 BMS', route: '/tencent/bms' },
      { type: 'as', label: '弹性伸缩 AS', route: '/tencent/as' },
      { type: 'lighthouse', label: '轻量应用服务器', route: '/tencent/lighthouse' },
    ]
  },
  {
    key: 'storage', label: '存储', icon: 'Box',
    items: [
      { type: 'cos', label: '对象存储 COS', route: '/tencent/cos' },
      { type: 'cbs', label: '云硬盘 CBS', route: '/tencent/cbs' },
      { type: 'cfs', label: '文件存储 CFS', route: '/tencent/cfs' },
    ]
  },
  {
    key: 'database', label: '数据库', icon: 'Coin',
    items: [
      { type: 'cdb', label: '云数据库 CDB', route: '/tencent/cdb' },
      { type: 'redis', label: 'Redis', route: '/tencent/redis' },
      { type: 'mongodb', label: 'MongoDB', route: '/tencent/mongodb' },
      { type: 'dcdb', label: '分布式数据库', route: '/tencent/dcdb' },
      { type: 'cynosdb', label: 'TDSQL-C', route: '/tencent/cynosdb' },
      { type: 'postgresql', label: 'PostgreSQL', route: '/tencent/postgresql' },
      { type: 'sqlserver', label: 'SQL Server', route: '/tencent/sqlserver' },
    ]
  },
  {
    key: 'network', label: '网络', icon: 'Connection',
    items: [
      { type: 'vpc', label: 'VPC', route: '/tencent/vpc' },
      { type: 'clb', label: '负载均衡 CLB', route: '/tencent/clb' },
      { type: 'eip', label: '弹性公网 IP', route: '/tencent/eip' },
      { type: 'nat', label: 'NAT 网关', route: '/tencent/nat' },
      { type: 'sg', label: '安全组', route: '/tencent/sg' },
      { type: 'dc', label: '云专线', route: '/tencent/dc' },
      { type: 'gaap', label: '全球加速', route: '/tencent/gaap' },
    ]
  },
  {
    key: 'security', label: '安全', icon: 'Lock',
    items: [
      { type: 'waf', label: 'WAF 防火墙', route: '/tencent/waf' },
      { type: 'ddos', label: 'DDoS 防护', route: '/tencent/ddos' },
      { type: 'cwp', label: '主机安全', route: '/tencent/cwp' },
      { type: 'kms', label: '密钥管理', route: '/tencent/kms' },
      { type: 'ssl', label: 'SSL 证书', route: '/tencent/ssl' },
      { type: 'bastion', label: '堡垒机', route: '/tencent/bastion' },
      { type: 'datasafegov', label: '数据安全', route: '/tencent/datasafegov' },
    ]
  },
  {
    key: 'middleware', label: '中间件', icon: 'Connection',
    items: [
      { type: 'ckafka', label: 'CKafka', route: '/tencent/ckafka' },
      { type: 'rocketmq', label: 'RocketMQ', route: '/tencent/rocketmq' },
      { type: 'rabbitmq', label: 'RabbitMQ', route: '/tencent/rabbitmq' },
      { type: 'apigw', label: 'API 网关', route: '/tencent/apigw' },
      { type: 'tse', label: '微服务引擎', route: '/tencent/tse' },
    ]
  },
  {
    key: 'cdn', label: 'CDN 与媒体', icon: 'VideoCamera',
    items: [
      { type: 'cdn', label: 'CDN', route: '/tencent/cdn' },
      { type: 'live', label: '视频直播', route: '/tencent/live' },
      { type: 'vod', label: '视频点播', route: '/tencent/vod' },
    ]
  },
  {
    key: 'bigdata', label: '大数据', icon: 'DataLine',
    items: [
      { type: 'es', label: 'Elasticsearch', route: '/tencent/es' },
      { type: 'emr', label: '弹性 MapReduce', route: '/tencent/emr' },
      { type: 'cls', label: '日志服务 CLS', route: '/tencent/cls' },
    ]
  },
  {
    key: 'ops', label: '运维管理', icon: 'Setting',
    items: [
      { type: 'monitor', label: '云监控', route: '/tencent/monitor' },
      { type: 'audit', label: '云审计', route: '/tencent/audit' },
    ]
  },
  {
    key: 'iot', label: '物联网', icon: 'Iphone',
    items: [
      { type: 'iot', label: 'IoT 平台', route: '/tencent/iot' },
    ]
  }
]
</script>
