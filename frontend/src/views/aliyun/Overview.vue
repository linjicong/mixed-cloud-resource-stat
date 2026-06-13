<template>
  <ProviderOverview provider="aliyun" providerLabel="阿里云" :categories="categories" />
</template>

<script setup>
import ProviderOverview from '@/components/ProviderOverview.vue'

const categories = [
  {
    key: 'compute', label: '计算', icon: 'Monitor',
    items: [
      { type: 'ecs', label: '云服务器 ECS', route: '/aliyun/ecs' },
      { type: 'ack', label: '容器服务 ACK', route: '/aliyun/ack' },
      { type: 'fc', label: '函数计算 FC', route: '/aliyun/fc' },
      { type: 'ess', label: '弹性伸缩 ESS', route: '/aliyun/ess' },
      { type: 'sae', label: '应用引擎 SAE', route: '/aliyun/sae' },
    ]
  },
  {
    key: 'storage', label: '存储', icon: 'Box',
    items: [
      { type: 'oss', label: '对象存储 OSS', route: '/aliyun/oss' },
      { type: 'disk', label: '云盘 ESSD', route: '/aliyun/disk' },
      { type: 'nas', label: '文件存储 NAS', route: '/aliyun/nas' },
      { type: 'storagegateway', label: '存储网关', route: '/aliyun/storagegateway' },
    ]
  },
  {
    key: 'database', label: '数据库', icon: 'Coin',
    items: [
      { type: 'rds', label: '云数据库 RDS', route: '/aliyun/rds' },
      { type: 'redis', label: 'Redis', route: '/aliyun/redis' },
      { type: 'mongodb', label: 'MongoDB', route: '/aliyun/mongodb' },
      { type: 'polardb', label: 'PolarDB', route: '/aliyun/polardb' },
      { type: 'elasticsearch', label: 'Elasticsearch', route: '/aliyun/elasticsearch' },
      { type: 'clickhouse', label: 'ClickHouse', route: '/aliyun/clickhouse' },
      { type: 'analyticdb', label: 'AnalyticDB', route: '/aliyun/analyticdb' },
      { type: 'lindorm', label: 'Lindorm', route: '/aliyun/lindorm' },
      { type: 'selectdb', label: 'SelectDB', route: '/aliyun/selectdb' },
      { type: 'hologres', label: 'Hologres', route: '/aliyun/hologres' },
      { type: 'dts', label: '数据传输 DTS', route: '/aliyun/dts' },
    ]
  },
  {
    key: 'network', label: '网络', icon: 'Connection',
    items: [
      { type: 'vpc', label: 'VPC', route: '/aliyun/vpc' },
      { type: 'slb', label: '负载均衡 SLB', route: '/aliyun/slb' },
      { type: 'alb', label: '应用型负载均衡', route: '/aliyun/alb' },
      { type: 'nlb', label: '网络型负载均衡', route: '/aliyun/nlb' },
      { type: 'eip', label: '弹性公网 IP', route: '/aliyun/eip' },
      { type: 'nat', label: 'NAT 网关', route: '/aliyun/nat' },
      { type: 'sg', label: '安全组', route: '/aliyun/securitygroup' },
      { type: 'cen', label: '云企业网 CEN', route: '/aliyun/cen' },
      { type: 'vpngateway', label: 'VPN 网关', route: '/aliyun/vpngateway' },
      { type: 'ga', label: '全球加速 GA', route: '/aliyun/ga' },
      { type: 'vpcendpoint', label: '终端节点', route: '/aliyun/vpcendpoint' },
    ]
  },
  {
    key: 'security', label: '安全', icon: 'Lock',
    items: [
      { type: 'waf', label: 'WAF 防火墙', route: '/aliyun/waf' },
      { type: 'ddos', label: 'DDoS 防护', route: '/aliyun/ddos' },
      { type: 'cloudfirewall', label: '云防火墙', route: '/aliyun/cloudfirewall' },
      { type: 'hss', label: '主机安全', route: '/aliyun/hss' },
      { type: 'kms', label: '密钥管理', route: '/aliyun/kms' },
      { type: 'ssl', label: 'SSL 证书', route: '/aliyun/ssl' },
      { type: 'dsc', label: '数据安全', route: '/aliyun/dsc' },
      { type: 'cbh', label: '堡垒机', route: '/aliyun/cbh' },
    ]
  },
  {
    key: 'middleware', label: '中间件', icon: 'Connection',
    items: [
      { type: 'kafka', label: 'Kafka', route: '/aliyun/kafka' },
      { type: 'rocketmq', label: 'RocketMQ', route: '/aliyun/rocketmq' },
      { type: 'rabbitmq', label: 'RabbitMQ', route: '/aliyun/rabbitmq' },
      { type: 'apigateway', label: 'API 网关', route: '/aliyun/apigateway' },
      { type: 'mse', label: '微服务引擎 MSE', route: '/aliyun/mse' },
    ]
  },
  {
    key: 'cdn', label: 'CDN 与媒体', icon: 'VideoCamera',
    items: [
      { type: 'cdn', label: 'CDN', route: '/aliyun/cdn' },
      { type: 'live', label: '视频直播', route: '/aliyun/live' },
      { type: 'vod', label: '视频点播', route: '/aliyun/vod' },
    ]
  },
  {
    key: 'bigdata', label: '大数据', icon: 'DataLine',
    items: [
      { type: 'es', label: 'Elasticsearch', route: '/aliyun/elasticsearch' },
      { type: 'emr', label: 'EMR', route: '/aliyun/emr' },
      { type: 'maxcompute', label: 'MaxCompute', route: '/aliyun/maxcompute' },
      { type: 'sls', label: '日志服务 SLS', route: '/aliyun/sls' },
    ]
  },
  {
    key: 'ops', label: '运维管理', icon: 'Setting',
    items: [
      { type: 'cms', label: '云监控', route: '/aliyun/cms' },
      { type: 'actiontrail', label: '云审计', route: '/aliyun/actiontrail' },
      { type: 'config', label: '资源配置', route: '/aliyun/config' },
      { type: 'oos', label: '运维编排', route: '/aliyun/oos' },
      { type: 'quota', label: '配额中心', route: '/aliyun/quota' },
    ]
  },
  {
    key: 'iot', label: '物联网', icon: 'Iphone',
    items: [
      { type: 'iot', label: 'IoT 平台', route: '/aliyun/iot' },
    ]
  }
]
</script>
