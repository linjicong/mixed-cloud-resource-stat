import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/views/Dashboard.vue'),
    meta: { title: '仪表盘' }
  },
  {
    path: '/config',
    children: [
      {
        path: 'cloud',
        name: 'CloudConfig',
        component: () => import('@/views/config/CloudConfig.vue'),
        meta: { title: '云厂商配置' }
      }
    ]
  },
  {
    path: '/huawei',
    children: [
      {
        path: 'ecs',
        name: 'HuaweiEcs',
        component: () => import('@/views/huawei/EcsList.vue'),
        meta: { title: '华为云ECS' }
      },
      {
        path: 'rds',
        name: 'HuaweiRds',
        component: () => import('@/views/huawei/RdsList.vue'),
        meta: { title: '华为云RDS' }
      },
      {
        path: 'elb',
        name: 'HuaweiElb',
        component: () => import('@/views/huawei/ElbList.vue'),
        meta: { title: '华为云ELB' }
      },
      {
        path: 'evs',
        name: 'HuaweiEvs',
        component: () => import('@/views/huawei/EvsList.vue'),
        meta: { title: '华为云EVS' }
      },
      {
        path: 'vpc',
        name: 'HuaweiVpc',
        component: () => import('@/views/huawei/VpcList.vue'),
        meta: { title: '华为云VPC' }
      },
      {
        path: 'bills',
        name: 'HuaweiBills',
        component: () => import('@/views/huawei/BillsList.vue'),
        meta: { title: '华为云账单' }
      },
      {
        path: 'eip',
        name: 'HuaweiEip',
        component: () => import('@/views/huawei/EipList.vue'),
        meta: { title: '华为云弹性公网IP EIP' }
      },
      {
        path: 'ims',
        name: 'HuaweiIms',
        component: () => import('@/views/huawei/ImsList.vue'),
        meta: { title: '华为云镜像服务 IMS' }
      },
      {
        path: 'cbr',
        name: 'HuaweiCbr',
        component: () => import('@/views/huawei/CbrList.vue'),
        meta: { title: '华为云云备份 CBR' }
      },
      {
        path: 'dcs',
        name: 'HuaweiDcs',
        component: () => import('@/views/huawei/DcsList.vue'),
        meta: { title: '华为云分布式缓存 DCS' }
      },
      {
        path: 'dds',
        name: 'HuaweiDds',
        component: () => import('@/views/huawei/DdsList.vue'),
        meta: { title: '华为云文档数据库 DDS' }
      },
      {
        path: 'obs',
        name: 'HuaweiObs',
        component: () => import('@/views/huawei/ObsList.vue'),
        meta: { title: '华为云对象存储 OBS' }
      },
      {
        path: 'sfs',
        name: 'HuaweiSfs',
        component: () => import('@/views/huawei/SfsList.vue'),
        meta: { title: '华为云弹性文件服务 SFS' }
      },
      {
        path: 'ces_metric',
        name: 'HuaweiCesMetric',
        component: () => import('@/views/huawei/CesMetricList.vue'),
        meta: { title: '华为云云监控指标 CES' }
      },
      {
        path: 'dns_private',
        name: 'HuaweiDnsPrivate',
        component: () => import('@/views/huawei/DnsPrivateList.vue'),
        meta: { title: '华为云私有DNS' }
      },
      {
        path: 'dns_record_sets',
        name: 'HuaweiDnsRecordSets',
        component: () => import('@/views/huawei/DnsRecordSetsList.vue'),
        meta: { title: '华为云DNS记录集' }
      },
      {
        path: 'cce',
        name: 'HuaweiCce',
        component: () => import('@/views/huawei/CceList.vue'),
        meta: { title: '华为云云容器引擎 CCE' }
      },
      {
        path: 'auth_domain',
        name: 'HuaweiAuthDomain',
        component: () => import('@/views/huawei/AuthDomainList.vue'),
        meta: { title: '华为云认证域名' }
      },
      {
        path: 'user',
        name: 'HuaweiUser',
        component: () => import('@/views/huawei/UserList.vue'),
        meta: { title: '华为云IAM用户' }
      },
      {
        path: 'access_key',
        name: 'HuaweiAccessKey',
        component: () => import('@/views/huawei/AccessKeyList.vue'),
        meta: { title: '华为云访问密钥' }
      },
      {
        path: 'resource',
        name: 'HuaweiResource',
        component: () => import('@/views/huawei/ResourceList.vue'),
        meta: { title: '华为云资源管理 RMS' }
      },
      {
        path: 'bills_fee_records',
        name: 'HuaweiBillsFeeRecords',
        component: () => import('@/views/huawei/BillsFeeRecordsList.vue'),
        meta: { title: '华为云费用记录' }
      },
      {
        path: 'resource_record_detail',
        name: 'HuaweiResourceRecordDetail',
        component: () => import('@/views/huawei/ResourceRecordDetailList.vue'),
        meta: { title: '华为云资源记录详情' }
      },
      {
        path: 'nat',
        name: 'HuaweiNat',
        component: () => import('@/views/huawei/NatList.vue'),
        meta: { title: '华为云NAT网关' }
      },
      {
        path: 'functiongraph',
        name: 'HuaweiFunctiongraph',
        component: () => import('@/views/huawei/FunctiongraphList.vue'),
        meta: { title: '华为云函数工作流 FunctionGraph' }
      },
      {
        path: 'vpn',
        name: 'HuaweiVpn',
        component: () => import('@/views/huawei/VpnList.vue'),
        meta: { title: '华为云虚拟专用网络 VPN' }
      },
      {
        path: 'gaussdb',
        name: 'HuaweiGaussdb',
        component: () => import('@/views/huawei/GaussdbList.vue'),
        meta: { title: '华为云高斯数据库 GaussDB' }
      },
      {
        path: 'kms',
        name: 'HuaweiKms',
        component: () => import('@/views/huawei/KmsList.vue'),
        meta: { title: '华为云密钥管理 KMS' }
      },
      {
        path: 'waf',
        name: 'HuaweiWaf',
        component: () => import('@/views/huawei/WafList.vue'),
        meta: { title: '华为云Web应用防火墙 WAF' }
      },
      {
        path: 'cts',
        name: 'HuaweiCts',
        component: () => import('@/views/huawei/CtsList.vue'),
        meta: { title: '华为云云审计 CTS' }
      },
      {
        path: 'kafka',
        name: 'HuaweiKafka',
        component: () => import('@/views/huawei/KafkaList.vue'),
        meta: { title: '华为云分布式消息 Kafka' }
      },
      {
        path: 'rocketmq',
        name: 'HuaweiRocketmq',
        component: () => import('@/views/huawei/RocketmqList.vue'),
        meta: { title: '华为云消息队列 RocketMQ' }
      },
      {
        path: 'rabbitmq',
        name: 'HuaweiRabbitmq',
        component: () => import('@/views/huawei/RabbitmqList.vue'),
        meta: { title: '华为云消息队列 RabbitMQ' }
      },
      {
        path: 'lts',
        name: 'HuaweiLts',
        component: () => import('@/views/huawei/LtsList.vue'),
        meta: { title: '华为云云日志服务 LTS' }
      },
      {
        path: 'cdn',
        name: 'HuaweiCdn',
        component: () => import('@/views/huawei/CdnList.vue'),
        meta: { title: '华为云内容分发 CDN' }
      },
      {
        path: 'antiddos',
        name: 'HuaweiAntiddos',
        component: () => import('@/views/huawei/AntiddosList.vue'),
        meta: { title: '华为云Anti-DDoS' }
      },
      {
        path: 'hss',
        name: 'HuaweiHss',
        component: () => import('@/views/huawei/HssList.vue'),
        meta: { title: '华为云主机安全 HSS' }
      },
      {
        path: 'swr',
        name: 'HuaweiSwr',
        component: () => import('@/views/huawei/SwrList.vue'),
        meta: { title: '华为云容器镜像 SWR' }
      },
      {
        path: 'smn',
        name: 'HuaweiSmn',
        component: () => import('@/views/huawei/SmnList.vue'),
        meta: { title: '华为云消息通知 SMN' }
      },
      {
        path: 'apig',
        name: 'HuaweiApig',
        component: () => import('@/views/huawei/ApigList.vue'),
        meta: { title: '华为云API网关 APIG' }
      },
      {
        path: 'aom',
        name: 'HuaweiAom',
        component: () => import('@/views/huawei/AomList.vue'),
        meta: { title: '华为云应用运维管理 AOM' }
      },
      {
        path: 'css',
        name: 'HuaweiCss',
        component: () => import('@/views/huawei/CssList.vue'),
        meta: { title: '华为云云搜索服务 CSS' }
      },
      {
        path: 'cfw',
        name: 'HuaweiCfw',
        component: () => import('@/views/huawei/CfwList.vue'),
        meta: { title: '华为云云防火墙 CFW' }
      },
      {
        path: 'ccm',
        name: 'HuaweiCcm',
        component: () => import('@/views/huawei/CcmList.vue'),
        meta: { title: '华为云证书管理 CCM' }
      },
      {
        path: 'drs',
        name: 'HuaweiDrs',
        component: () => import('@/views/huawei/DrsList.vue'),
        meta: { title: '华为云数据复制 DRS' }
      },
      {
        path: 'mrs',
        name: 'HuaweiMrs',
        component: () => import('@/views/huawei/MrsList.vue'),
        meta: { title: '华为云MapReduce MRS' }
      },
      {
        path: 'as',
        name: 'HuaweiAs',
        component: () => import('@/views/huawei/AsList.vue'),
        meta: { title: '华为云弹性伸缩 AS' }
      },
      {
        path: 'bms',
        name: 'HuaweiBms',
        component: () => import('@/views/huawei/BmsList.vue'),
        meta: { title: '华为云裸金属 BMS' }
      },
      {
        path: 'workspace',
        name: 'HuaweiWorkspace',
        component: () => import('@/views/huawei/WorkspaceList.vue'),
        meta: { title: '华为云云桌面 Workspace' }
      },
      {
        path: 'dli',
        name: 'HuaweiDli',
        component: () => import('@/views/huawei/DliList.vue'),
        meta: { title: '华为云数据湖 DLI' }
      },
      {
        path: 'dws',
        name: 'HuaweiDws',
        component: () => import('@/views/huawei/DwsList.vue'),
        meta: { title: '华为云数据仓库 DWS' }
      },
      {
        path: 'gaussdbnosql',
        name: 'HuaweiGaussdbnosql',
        component: () => import('@/views/huawei/GaussdbnosqlList.vue'),
        meta: { title: '华为云GaussDB NoSQL' }
      },
      {
        path: 'gaussdbopengauss',
        name: 'HuaweiGaussdbopengauss',
        component: () => import('@/views/huawei/GaussdbopengaussList.vue'),
        meta: { title: '华为云GaussDB OpenGauss' }
      },
      {
        path: 'ddm',
        name: 'HuaweiDdm',
        component: () => import('@/views/huawei/DdmList.vue'),
        meta: { title: '华为云分布式数据库 DDM' }
      },
      {
        path: 'cse',
        name: 'HuaweiCse',
        component: () => import('@/views/huawei/CseList.vue'),
        meta: { title: '华为云微服务引擎 CSE' }
      },
      {
        path: 'servicestage',
        name: 'HuaweiServicestage',
        component: () => import('@/views/huawei/ServicestageList.vue'),
        meta: { title: '华为云ServiceStage' }
      },
      {
        path: 'cbh',
        name: 'HuaweiCbh',
        component: () => import('@/views/huawei/CbhList.vue'),
        meta: { title: '华为云云堡垒机 CBH' }
      },
      {
        path: 'dbss',
        name: 'HuaweiDbss',
        component: () => import('@/views/huawei/DbssList.vue'),
        meta: { title: '华为云数据库安全 DBSS' }
      },
      {
        path: 'vod',
        name: 'HuaweiVod',
        component: () => import('@/views/huawei/VodList.vue'),
        meta: { title: '华为云视频点播 VOD' }
      },
      {
        path: 'live',
        name: 'HuaweiLive',
        component: () => import('@/views/huawei/LiveList.vue'),
        meta: { title: '华为云视频直播 Live' }
      },
      {
        path: 'oms',
        name: 'HuaweiOms',
        component: () => import('@/views/huawei/OmsList.vue'),
        meta: { title: '华为云对象存储迁移 OMS' }
      },
      {
        path: 'sdrs',
        name: 'HuaweiSdrs',
        component: () => import('@/views/huawei/SdrsList.vue'),
        meta: { title: '华为云容灾 SDRS' }
      },
      {
        path: 'sms',
        name: 'HuaweiSms',
        component: () => import('@/views/huawei/SmsList.vue'),
        meta: { title: '华为云短信服务 SMS' }
      },
      {
        path: 'dsc',
        name: 'HuaweiDsc',
        component: () => import('@/views/huawei/DscList.vue'),
        meta: { title: '华为云数据安全中心 DSC' }
      },
      {
        path: 'roma',
        name: 'HuaweiRoma',
        component: () => import('@/views/huawei/RomaList.vue'),
        meta: { title: '华为云应用集成 ROMA' }
      },
      {
        path: 'cph',
        name: 'HuaweiCph',
        component: () => import('@/views/huawei/CphList.vue'),
        meta: { title: '华为云云手机 CPH' }
      },
      {
        path: 'er',
        name: 'HuaweiEr',
        component: () => import('@/views/huawei/ErList.vue'),
        meta: { title: '华为云企业路由器 ER' }
      },
      {
        path: 'vpcep',
        name: 'HuaweiVpcep',
        component: () => import('@/views/huawei/VpcepList.vue'),
        meta: { title: '华为云VPC终端节点 VPCEP' }
      },
      {
        path: 'ief',
        name: 'HuaweiIef',
        component: () => import('@/views/huawei/IefList.vue'),
        meta: { title: '华为云智能边缘 IEF' }
      },
      {
        path: 'iotda',
        name: 'HuaweiIotda',
        component: () => import('@/views/huawei/IotdaList.vue'),
        meta: { title: '华为云物联网 IoTDA' }
      },
      {
        path: 'deh',
        name: 'HuaweiDeh',
        component: () => import('@/views/huawei/DehList.vue'),
        meta: { title: '华为云专属主机 DEH' }
      },
      {
        path: 'bcs',
        name: 'HuaweiBcs',
        component: () => import('@/views/huawei/BcsList.vue'),
        meta: { title: '华为云区块链 BCS' }
      },
      {
        path: 'dc',
        name: 'HuaweiDc',
        component: () => import('@/views/huawei/DcList.vue'),
        meta: { title: '华为云云专线 DC' }
      },
      {
        path: 'ga',
        name: 'HuaweiGa',
        component: () => import('@/views/huawei/GaList.vue'),
        meta: { title: '华为云全球加速 GA' }
      },
      {
        path: 'eg',
        name: 'HuaweiEg',
        component: () => import('@/views/huawei/EgList.vue'),
        meta: { title: '华为云事件网格 EG' }
      },
      {
        path: 'apm',
        name: 'HuaweiApm',
        component: () => import('@/views/huawei/ApmList.vue'),
        meta: { title: '华为云应用性能 APM' }
      },
      {
        path: 'cloudtable',
        name: 'HuaweiCloudtable',
        component: () => import('@/views/huawei/CloudtableList.vue'),
        meta: { title: '华为云表格存储 CloudTable' }
      },
      {
        path: 'dataartsstudio',
        name: 'HuaweiDataartsstudio',
        component: () => import('@/views/huawei/DataartsstudioList.vue'),
        meta: { title: '华为云数据工坊 DataArts' }
      },
      {
        path: 'dis',
        name: 'HuaweiDis',
        component: () => import('@/views/huawei/DisList.vue'),
        meta: { title: '华为云数据接入 DIS' }
      },
      {
        path: 'mas',
        name: 'HuaweiMas',
        component: () => import('@/views/huawei/MasList.vue'),
        meta: { title: '华为云多活 MAS' }
      },
      {
        path: 'mpc',
        name: 'HuaweiMpc',
        component: () => import('@/views/huawei/MpcList.vue'),
        meta: { title: '华为云媒体处理 MPC' }
      },
      {
        path: 'clouddc',
        name: 'HuaweiClouddc',
        component: () => import('@/views/huawei/ClouddcList.vue'),
        meta: { title: '华为云云化数据中心 CloudDC' }
      },
      {
        path: 'kvs',
        name: 'HuaweiKvs',
        component: () => import('@/views/huawei/KvsList.vue'),
        meta: { title: '华为云键值存储 KVS' }
      },
      {
        path: 'eds',
        name: 'HuaweiEds',
        component: () => import('@/views/huawei/EdsList.vue'),
        meta: { title: '华为云云桌面 EDS' }
      },
      {
        path: 'tics',
        name: 'HuaweiTics',
        component: () => import('@/views/huawei/TicsList.vue'),
        meta: { title: '华为云威胁情报 TICS' }
      },
      {
        path: 'organizations',
        name: 'HuaweiOrganizations',
        component: () => import('@/views/huawei/OrganizationsList.vue'),
        meta: { title: '华为云组织 Organizations' }
      },
      {
        path: 'ram',
        name: 'HuaweiRam',
        component: () => import('@/views/huawei/RamList.vue'),
        meta: { title: '华为云资源访问管理 RAM' }
      },
      {
        path: 'coc',
        name: 'HuaweiCoc',
        component: () => import('@/views/huawei/CocList.vue'),
        meta: { title: '华为云运维编排 COC' }
      }]
  },
  {
    path: '/tencent',
    children: [
      {
        path: 'cvm',
        name: 'TencentCvm',
        component: () => import('@/views/tencent/CvmList.vue'),
        meta: { title: '腾讯云CVM' }
      },
      {
        path: 'cdb',
        name: 'TencentCdb',
        component: () => import('@/views/tencent/CdbList.vue'),
        meta: { title: '腾讯云CDB' }
      },
      {
        path: 'clb',
        name: 'TencentClb',
        component: () => import('@/views/tencent/ClbList.vue'),
        meta: { title: '腾讯云CLB' }
      },
      {
        path: 'cbs',
        name: 'TencentCbs',
        component: () => import('@/views/tencent/CbsList.vue'),
        meta: { title: '腾讯云CBS' }
      },
      {
        path: 'bills',
        name: 'TencentBills',
        component: () => import('@/views/tencent/BillsList.vue'),
        meta: { title: '腾讯云账单' }
      },
      {
        path: 'vpc',
        name: 'TencentVpc',
        component: () => import('@/views/tencent/VpcList.vue'),
        meta: { title: '腾讯云私有网络 VPC' }
      },
      {
        path: 'subnet',
        name: 'TencentSubnet',
        component: () => import('@/views/tencent/SubnetList.vue'),
        meta: { title: '腾讯云子网' }
      },
      {
        path: 'eip',
        name: 'TencentEip',
        component: () => import('@/views/tencent/EipList.vue'),
        meta: { title: '腾讯云弹性公网IP EIP' }
      },
      {
        path: 'sg',
        name: 'TencentSg',
        component: () => import('@/views/tencent/SgList.vue'),
        meta: { title: '腾讯云安全组' }
      },
      {
        path: 'cdn',
        name: 'TencentCdn',
        component: () => import('@/views/tencent/CdnList.vue'),
        meta: { title: '腾讯云CDN' }
      },
      {
        path: 'redis',
        name: 'TencentRedis',
        component: () => import('@/views/tencent/RedisList.vue'),
        meta: { title: '腾讯云云数据库 Redis' }
      },
      {
        path: 'mongodb',
        name: 'TencentMongodb',
        component: () => import('@/views/tencent/MongodbList.vue'),
        meta: { title: '腾讯云云数据库 MongoDB' }
      },
      {
        path: 'cynosdb',
        name: 'TencentCynosdb',
        component: () => import('@/views/tencent/CynosdbList.vue'),
        meta: { title: '腾讯云TDSQL-C CynosDB' }
      },
      {
        path: 'postgresql',
        name: 'TencentPostgresql',
        component: () => import('@/views/tencent/PostgresqlList.vue'),
        meta: { title: '腾讯云云数据库 PostgreSQL' }
      },
      {
        path: 'sqlserver',
        name: 'TencentSqlserver',
        component: () => import('@/views/tencent/SqlserverList.vue'),
        meta: { title: '腾讯云云数据库 SQL Server' }
      },
      {
        path: 'nat',
        name: 'TencentNat',
        component: () => import('@/views/tencent/NatList.vue'),
        meta: { title: '腾讯云NAT网关' }
      },
      {
        path: 'cfs',
        name: 'TencentCfs',
        component: () => import('@/views/tencent/CfsList.vue'),
        meta: { title: '腾讯云文件存储 CFS' }
      },
      {
        path: 'dns',
        name: 'TencentDns',
        component: () => import('@/views/tencent/DnsList.vue'),
        meta: { title: '腾讯云DNS' }
      },
      {
        path: 'cos',
        name: 'TencentCos',
        component: () => import('@/views/tencent/CosList.vue'),
        meta: { title: '腾讯云对象存储 COS' }
      },
      {
        path: 'scf',
        name: 'TencentScf',
        component: () => import('@/views/tencent/ScfList.vue'),
        meta: { title: '腾讯云无服务器函数 SCF' }
      },
      {
        path: 'mariadb',
        name: 'TencentMariadb',
        component: () => import('@/views/tencent/MariadbList.vue'),
        meta: { title: '腾讯云云数据库 MariaDB' }
      },
      {
        path: 'dcdb',
        name: 'TencentDcdb',
        component: () => import('@/views/tencent/DcdbList.vue'),
        meta: { title: '腾讯云分布式数据库 DCDB' }
      },
      {
        path: 'ckafka',
        name: 'TencentCkafka',
        component: () => import('@/views/tencent/CkafkaList.vue'),
        meta: { title: '腾讯云消息队列 CKafka' }
      },
      {
        path: 'rocketmq',
        name: 'TencentRocketmq',
        component: () => import('@/views/tencent/RocketmqList.vue'),
        meta: { title: '腾讯云消息队列 RocketMQ' }
      },
      {
        path: 'ssl',
        name: 'TencentSsl',
        component: () => import('@/views/tencent/SslList.vue'),
        meta: { title: '腾讯云SSL证书' }
      },
      {
        path: 'waf',
        name: 'TencentWaf',
        component: () => import('@/views/tencent/WafList.vue'),
        meta: { title: '腾讯云Web应用防火墙 WAF' }
      },
      {
        path: 'cls',
        name: 'TencentCls',
        component: () => import('@/views/tencent/ClsList.vue'),
        meta: { title: '腾讯云日志服务 CLS' }
      },
      {
        path: 'monitor',
        name: 'TencentMonitor',
        component: () => import('@/views/tencent/MonitorList.vue'),
        meta: { title: '腾讯云云监控' }
      },
      {
        path: 'domain',
        name: 'TencentDomain',
        component: () => import('@/views/tencent/DomainList.vue'),
        meta: { title: '腾讯云域名' }
      },
      {
        path: 'tke',
        name: 'TencentTke',
        component: () => import('@/views/tencent/TkeList.vue'),
        meta: { title: '腾讯云容器服务 TKE' }
      },
      {
        path: 'tcr',
        name: 'TencentTcr',
        component: () => import('@/views/tencent/TcrList.vue'),
        meta: { title: '腾讯云容器镜像 TCR' }
      },
      {
        path: 'es',
        name: 'TencentEs',
        component: () => import('@/views/tencent/EsList.vue'),
        meta: { title: '腾讯云Elasticsearch' }
      },
      {
        path: 'memcached',
        name: 'TencentMemcached',
        component: () => import('@/views/tencent/MemcachedList.vue'),
        meta: { title: '腾讯云云缓存 Memcached' }
      },
      {
        path: 'keewidb',
        name: 'TencentKeewidb',
        component: () => import('@/views/tencent/KeewidbList.vue'),
        meta: { title: '腾讯云轻量数据库 KeeWiDB' }
      },
      {
        path: 'ctsdb',
        name: 'TencentCtsdb',
        component: () => import('@/views/tencent/CtsdbList.vue'),
        meta: { title: '腾讯云时序数据库 CTSDB' }
      },
      {
        path: 'chdfs',
        name: 'TencentChdfs',
        component: () => import('@/views/tencent/ChdfsList.vue'),
        meta: { title: '腾讯云云HDFS' }
      },
      {
        path: 'as',
        name: 'TencentAs',
        component: () => import('@/views/tencent/AsList.vue'),
        meta: { title: '腾讯云弹性伸缩 AS' }
      },
      {
        path: 'lighthouse',
        name: 'TencentLighthouse',
        component: () => import('@/views/tencent/LighthouseList.vue'),
        meta: { title: '腾讯云轻量应用服务器' }
      },
      {
        path: 'dc',
        name: 'TencentDc',
        component: () => import('@/views/tencent/DcList.vue'),
        meta: { title: '腾讯云云专线 DC' }
      },
      {
        path: 'rabbitmq',
        name: 'TencentRabbitmq',
        component: () => import('@/views/tencent/RabbitmqList.vue'),
        meta: { title: '腾讯云消息队列 RabbitMQ' }
      },
      {
        path: 'apigw',
        name: 'TencentApigw',
        component: () => import('@/views/tencent/ApigwList.vue'),
        meta: { title: '腾讯云API网关' }
      },
      {
        path: 'bms',
        name: 'TencentBms',
        component: () => import('@/views/tencent/BmsList.vue'),
        meta: { title: '腾讯云黑石物理服务器' }
      },
      {
        path: 'tdmq',
        name: 'TencentTdmq',
        component: () => import('@/views/tencent/TdmqList.vue'),
        meta: { title: '腾讯云TDMQ' }
      },
      {
        path: 'oceanus',
        name: 'TencentOceanus',
        component: () => import('@/views/tencent/OceanusList.vue'),
        meta: { title: '腾讯云流计算 Oceanus' }
      },
      {
        path: 'emr',
        name: 'TencentEmr',
        component: () => import('@/views/tencent/EmrList.vue'),
        meta: { title: '腾讯云弹性 MapReduce' }
      },
      {
        path: 'gaap',
        name: 'TencentGaap',
        component: () => import('@/views/tencent/GaapList.vue'),
        meta: { title: '腾讯云全球应用加速 GAAP' }
      },
      {
        path: 'agentgw',
        name: 'TencentAgentgw',
        component: () => import('@/views/tencent/AgentgwList.vue'),
        meta: { title: '腾讯云Agent网关' }
      },
      {
        path: 'agentplatform',
        name: 'TencentAgentplatform',
        component: () => import('@/views/tencent/AgentplatformList.vue'),
        meta: { title: '腾讯云Agent平台' }
      },
      {
        path: 'apprender',
        name: 'TencentApprender',
        component: () => import('@/views/tencent/ApprenderList.vue'),
        meta: { title: '腾讯云应用渲染' }
      },
      {
        path: 'asr',
        name: 'TencentAsr',
        component: () => import('@/views/tencent/AsrList.vue'),
        meta: { title: '腾讯云语音识别 ASR' }
      },
      {
        path: 'audit',
        name: 'TencentAudit',
        component: () => import('@/views/tencent/AuditList.vue'),
        meta: { title: '腾讯云云审计' }
      },
      {
        path: 'bi',
        name: 'TencentBi',
        component: () => import('@/views/tencent/BiList.vue'),
        meta: { title: '腾讯云商业智能 BI' }
      },
      {
        path: 'bastion',
        name: 'TencentBastion',
        component: () => import('@/views/tencent/BastionList.vue'),
        meta: { title: '腾讯云堡垒机' }
      },
      {
        path: 'bizprocess',
        name: 'TencentBizprocess',
        component: () => import('@/views/tencent/BizprocessList.vue'),
        meta: { title: '腾讯云流程服务' }
      },
      {
        path: 'cacert',
        name: 'TencentCacert',
        component: () => import('@/views/tencent/CacertList.vue'),
        meta: { title: '腾讯云CA证书' }
      },
      {
        path: 'captcha',
        name: 'TencentCaptcha',
        component: () => import('@/views/tencent/CaptchaList.vue'),
        meta: { title: '腾讯云验证码' }
      },
      {
        path: 'chc',
        name: 'TencentChc',
        component: () => import('@/views/tencent/ChcList.vue'),
        meta: { title: '腾讯云黑石 CHC' }
      },
      {
        path: 'clbgw',
        name: 'TencentClbgw',
        component: () => import('@/views/tencent/ClbgwList.vue'),
        meta: { title: '腾讯云CLB网关' }
      },
      {
        path: 'cloudbase',
        name: 'TencentCloudbase',
        component: () => import('@/views/tencent/CloudbaseList.vue'),
        meta: { title: '腾讯云云开发 CloudBase' }
      },
      {
        path: 'cloudcontact',
        name: 'TencentCloudcontact',
        component: () => import('@/views/tencent/CloudcontactList.vue'),
        meta: { title: '腾讯云云联络中心' }
      },
      {
        path: 'cloudhsm',
        name: 'TencentCloudhsm',
        component: () => import('@/views/tencent/CloudhsmList.vue'),
        meta: { title: '腾讯云云HSM' }
      },
      {
        path: 'cloudphone',
        name: 'TencentCloudphone',
        component: () => import('@/views/tencent/CloudphoneList.vue'),
        meta: { title: '腾讯云云手机' }
      },
      {
        path: 'cloudstudio',
        name: 'TencentCloudstudio',
        component: () => import('@/views/tencent/CloudstudioList.vue'),
        meta: { title: '腾讯云Cloud Studio' }
      },
      {
        path: 'cmq',
        name: 'TencentCmq',
        component: () => import('@/views/tencent/CmqList.vue'),
        meta: { title: '腾讯云消息队列 CMQ' }
      },
      {
        path: 'codingdevops',
        name: 'TencentCodingdevops',
        component: () => import('@/views/tencent/CodingdevopsList.vue'),
        meta: { title: '腾讯云CODING DevOps' }
      },
      {
        path: 'config',
        name: 'TencentConfig',
        component: () => import('@/views/tencent/ConfigList.vue'),
        meta: { title: '腾讯云配置管理' }
      },
      {
        path: 'contentrecognize',
        name: 'TencentContentrecognize',
        component: () => import('@/views/tencent/ContentrecognizeList.vue'),
        meta: { title: '腾讯云内容识别' }
      },
      {
        path: 'contentsafe',
        name: 'TencentContentsafe',
        component: () => import('@/views/tencent/ContentsafeList.vue'),
        meta: { title: '腾讯云内容安全' }
      },
      {
        path: 'controlcenter',
        name: 'TencentControlcenter',
        component: () => import('@/views/tencent/ControlcenterList.vue'),
        meta: { title: '腾讯云控制中心' }
      },
      {
        path: 'csp',
        name: 'TencentCsp',
        component: () => import('@/views/tencent/CspList.vue'),
        meta: { title: '腾讯云云服务代理' }
      },
      {
        path: 'cspgateway',
        name: 'TencentCspgateway',
        component: () => import('@/views/tencent/CspgatewayList.vue'),
        meta: { title: '腾讯云CSP网关' }
      },
      {
        path: 'css',
        name: 'TencentCss',
        component: () => import('@/views/tencent/CssList.vue'),
        meta: { title: '腾讯云云直播 CSS' }
      },
      {
        path: 'cwp',
        name: 'TencentCwp',
        component: () => import('@/views/tencent/CwpList.vue'),
        meta: { title: '腾讯云主机安全' }
      },
      {
        path: 'cwp3',
        name: 'TencentCwp3',
        component: () => import('@/views/tencent/Cwp3List.vue'),
        meta: { title: '腾讯云主机安全V3' }
      },
      {
        path: 'ddos',
        name: 'TencentDdos',
        component: () => import('@/views/tencent/DdosList.vue'),
        meta: { title: '腾讯云DDoS防护' }
      },
      {
        path: 'dlc',
        name: 'TencentDlc',
        component: () => import('@/views/tencent/DlcList.vue'),
        meta: { title: '腾讯云数据湖 DLC' }
      },
      {
        path: 'dnsprivate',
        name: 'TencentDnsprivate',
        component: () => import('@/views/tencent/DnsprivateList.vue'),
        meta: { title: '腾讯云私有DNS' }
      },
      {
        path: 'dnssec',
        name: 'TencentDnssec',
        component: () => import('@/views/tencent/DnssecList.vue'),
        meta: { title: '腾讯云DNSSEC' }
      },
      {
        path: 'dataaudit',
        name: 'TencentDataaudit',
        component: () => import('@/views/tencent/DataauditList.vue'),
        meta: { title: '腾讯云数据审计' }
      },
      {
        path: 'datasafegov',
        name: 'TencentDatasafegov',
        component: () => import('@/views/tencent/DatasafegovList.vue'),
        meta: { title: '腾讯云数据安全治理' }
      },
      {
        path: 'devicesafety',
        name: 'TencentDevicesafety',
        component: () => import('@/views/tencent/DevicesafetyList.vue'),
        meta: { title: '腾讯云设备安全' }
      },
      {
        path: 'distid',
        name: 'TencentDistid',
        component: () => import('@/views/tencent/DistidList.vue'),
        meta: { title: '腾讯云分发ID' }
      },
      {
        path: 'docprocess',
        name: 'TencentDocprocess',
        component: () => import('@/views/tencent/DocprocessList.vue'),
        meta: { title: '腾讯云文档处理' }
      },
      {
        path: 'docs',
        name: 'TencentDocs',
        component: () => import('@/views/tencent/DocsList.vue'),
        meta: { title: '腾讯云腾讯文档' }
      },
      {
        path: 'domainreg',
        name: 'TencentDomainreg',
        component: () => import('@/views/tencent/DomainregList.vue'),
        meta: { title: '腾讯云域名注册' }
      },
      {
        path: 'eo',
        name: 'TencentEo',
        component: () => import('@/views/tencent/EoList.vue'),
        meta: { title: '腾讯云边缘安全 EO' }
      },
      {
        path: 'esign',
        name: 'TencentEsign',
        component: () => import('@/views/tencent/EsignList.vue'),
        meta: { title: '腾讯云电子签名' }
      },
      {
        path: 'engwrite',
        name: 'TencentEngwrite',
        component: () => import('@/views/tencent/EngwriteList.vue'),
        meta: { title: '腾讯云智能写作' }
      },
      {
        path: 'eventbus',
        name: 'TencentEventbus',
        component: () => import('@/views/tencent/EventbusList.vue'),
        meta: { title: '腾讯云事件总线' }
      },
      {
        path: 'exposedmgr',
        name: 'TencentExposedmgr',
        component: () => import('@/views/tencent/ExposedmgrList.vue'),
        meta: { title: '腾讯云暴露面管理' }
      },
      {
        path: 'face',
        name: 'TencentFace',
        component: () => import('@/views/tencent/FaceList.vue'),
        meta: { title: '腾讯云人脸识别' }
      },
      {
        path: 'facefusion',
        name: 'TencentFacefusion',
        component: () => import('@/views/tencent/FacefusionList.vue'),
        meta: { title: '腾讯云人脸融合' }
      },
      {
        path: 'facemakeup',
        name: 'TencentFacemakeup',
        component: () => import('@/views/tencent/FacemakeupList.vue'),
        meta: { title: '腾讯云人脸美妆' }
      },
      {
        path: 'faceswap',
        name: 'TencentFaceswap',
        component: () => import('@/views/tencent/FaceswapList.vue'),
        meta: { title: '腾讯云人脸换脸' }
      },
      {
        path: 'gameantiace',
        name: 'TencentGameantiace',
        component: () => import('@/views/tencent/GameantiaceList.vue'),
        meta: { title: '腾讯云游戏反作弊' }
      },
      {
        path: 'gamedb',
        name: 'TencentGamedb',
        component: () => import('@/views/tencent/GamedbList.vue'),
        meta: { title: '腾讯云游戏数据库' }
      },
      {
        path: 'gameserver',
        name: 'TencentGameserver',
        component: () => import('@/views/tencent/GameserverList.vue'),
        meta: { title: '腾讯云游戏服务器' }
      },
      {
        path: 'gamevoice',
        name: 'TencentGamevoice',
        component: () => import('@/views/tencent/GamevoiceList.vue'),
        meta: { title: '腾讯云游戏语音' }
      },
      {
        path: 'gse',
        name: 'TencentGse',
        component: () => import('@/views/tencent/GseList.vue'),
        meta: { title: '腾讯云游戏服务器引擎' }
      },
      {
        path: 'gtm',
        name: 'TencentGtm',
        component: () => import('@/views/tencent/GtmList.vue'),
        meta: { title: '腾讯云流量管理 GTM' }
      },
      {
        path: 'ghphone',
        name: 'TencentGhphone',
        component: () => import('@/views/tencent/GhphoneList.vue'),
        meta: { title: '腾讯云GHPhone' }
      },
      {
        path: 'hbase',
        name: 'TencentHbase',
        component: () => import('@/views/tencent/HbaseList.vue'),
        meta: { title: '腾讯云HBase' }
      },
      {
        path: 'hsm',
        name: 'TencentHsm',
        component: () => import('@/views/tencent/HsmList.vue'),
        meta: { title: '腾讯云密钥管理 HSM' }
      },
      {
        path: 'healthdash',
        name: 'TencentHealthdash',
        component: () => import('@/views/tencent/HealthdashList.vue'),
        meta: { title: '腾讯云健康看板' }
      },
      {
        path: 'healthomics',
        name: 'TencentHealthomics',
        component: () => import('@/views/tencent/HealthomicsList.vue'),
        meta: { title: '腾讯云健康组学' }
      },
      {
        path: 'healthreport',
        name: 'TencentHealthreport',
        component: () => import('@/views/tencent/HealthreportList.vue'),
        meta: { title: '腾讯云健康报告' }
      },
      {
        path: 'icpbeian',
        name: 'TencentIcpbeian',
        component: () => import('@/views/tencent/IcpbeianList.vue'),
        meta: { title: '腾讯云ICP备案' }
      },
      {
        path: 'ioa',
        name: 'TencentIoa',
        component: () => import('@/views/tencent/IoaList.vue'),
        meta: { title: '腾讯云零信任 IOA' }
      },
      {
        path: 'imageprocess',
        name: 'TencentImageprocess',
        component: () => import('@/views/tencent/ImageprocessList.vue'),
        meta: { title: '腾讯云图像处理' }
      },
      {
        path: 'imagesearch',
        name: 'TencentImagesearch',
        component: () => import('@/views/tencent/ImagesearchList.vue'),
        meta: { title: '腾讯云图片搜索' }
      },
      {
        path: 'iot',
        name: 'TencentIot',
        component: () => import('@/views/tencent/IotList.vue'),
        meta: { title: '腾讯云物联网 IoT' }
      },
      {
        path: 'iotdevice',
        name: 'TencentIotdevice',
        component: () => import('@/views/tencent/IotdeviceList.vue'),
        meta: { title: '腾讯云IoT设备' }
      },
      {
        path: 'iothub',
        name: 'TencentIothub',
        component: () => import('@/views/tencent/IothubList.vue'),
        meta: { title: '腾讯云IoT Hub' }
      },
      {
        path: 'kms',
        name: 'TencentKms',
        component: () => import('@/views/tencent/KmsList.vue'),
        meta: { title: '腾讯云密钥管理 KMS' }
      },
      {
        path: 'knowledgeengine',
        name: 'TencentKnowledgeengine',
        component: () => import('@/views/tencent/KnowledgeengineList.vue'),
        meta: { title: '腾讯云知识引擎' }
      },
      {
        path: 'live',
        name: 'TencentLive',
        component: () => import('@/views/tencent/LiveList.vue'),
        meta: { title: '腾讯云直播' }
      },
      {
        path: 'live2',
        name: 'TencentLive2',
        component: () => import('@/views/tencent/Live2List.vue'),
        meta: { title: '腾讯云直播V2' }
      },
      {
        path: 'mail',
        name: 'TencentMail',
        component: () => import('@/views/tencent/MailList.vue'),
        meta: { title: '腾讯云邮件推送' }
      },
      {
        path: 'malltraffic',
        name: 'TencentMalltraffic',
        component: () => import('@/views/tencent/MalltrafficList.vue'),
        meta: { title: '腾讯云商城流量' }
      },
      {
        path: 'mathgrade',
        name: 'TencentMathgrade',
        component: () => import('@/views/tencent/MathgradeList.vue'),
        meta: { title: '腾讯云数学评分' }
      },
      {
        path: 'mediaasset',
        name: 'TencentMediaasset',
        component: () => import('@/views/tencent/MediaassetList.vue'),
        meta: { title: '腾讯云媒体资产管理' }
      },
      {
        path: 'meeting',
        name: 'TencentMeeting',
        component: () => import('@/views/tencent/MeetingList.vue'),
        meta: { title: '腾讯云腾讯会议' }
      },
      {
        path: 'microweda',
        name: 'TencentMicroweda',
        component: () => import('@/views/tencent/MicrowedaList.vue'),
        meta: { title: '腾讯云微搭低代码' }
      },
      {
        path: 'minisafe',
        name: 'TencentMinisafe',
        component: () => import('@/views/tencent/MinisafeList.vue'),
        meta: { title: '腾讯云小程序安全' }
      },
      {
        path: 'mongodbckafka',
        name: 'TencentMongodbckafka',
        component: () => import('@/views/tencent/MongodbckafkaList.vue'),
        meta: { title: '腾讯云MongoDB-Kafka' }
      },
      {
        path: 'nmt',
        name: 'TencentNmt',
        component: () => import('@/views/tencent/NmtList.vue'),
        meta: { title: '腾讯云机器翻译 NMT' }
      },
      {
        path: 'nativebuild',
        name: 'TencentNativebuild',
        component: () => import('@/views/tencent/NativebuildList.vue'),
        meta: { title: '腾讯云云原生构建' }
      },
      {
        path: 'ocr',
        name: 'TencentOcr',
        component: () => import('@/views/tencent/OcrList.vue'),
        meta: { title: '腾讯云OCR识别' }
      },
      {
        path: 'org',
        name: 'TencentOrg',
        component: () => import('@/views/tencent/OrgList.vue'),
        meta: { title: '腾讯云组织管理' }
      },
      {
        path: 'pentest',
        name: 'TencentPentest',
        component: () => import('@/views/tencent/PentestList.vue'),
        meta: { title: '腾讯云渗透测试' }
      },
      {
        path: 'privdns',
        name: 'TencentPrivdns',
        component: () => import('@/views/tencent/PrivdnsList.vue'),
        meta: { title: '腾讯云私有DNS' }
      },
      {
        path: 'regionmgr',
        name: 'TencentRegionmgr',
        component: () => import('@/views/tencent/RegionmgrList.vue'),
        meta: { title: '腾讯云区域管理' }
      },
      {
        path: 'riskidentify',
        name: 'TencentRiskidentify',
        component: () => import('@/views/tencent/RiskidentifyList.vue'),
        meta: { title: '腾讯云风险识别' }
      },
      {
        path: 'rtiedu',
        name: 'TencentRtiedu',
        component: () => import('@/views/tencent/RtieduList.vue'),
        meta: { title: '腾讯云教育RTI' }
      },
      {
        path: 'rtiindustrial',
        name: 'TencentRtiindustrial',
        component: () => import('@/views/tencent/RtiindustrialList.vue'),
        meta: { title: '腾讯云工业RTI' }
      },
      {
        path: 'safeaudio',
        name: 'TencentSafeaudio',
        component: () => import('@/views/tencent/SafeaudioList.vue'),
        meta: { title: '腾讯云音频安全' }
      },
      {
        path: 'safecenter',
        name: 'TencentSafecenter',
        component: () => import('@/views/tencent/SafecenterList.vue'),
        meta: { title: '腾讯云安全中心' }
      },
      {
        path: 'safedoc',
        name: 'TencentSafedoc',
        component: () => import('@/views/tencent/SafedocList.vue'),
        meta: { title: '腾讯云文档安全' }
      },
      {
        path: 'safeguard',
        name: 'TencentSafeguard',
        component: () => import('@/views/tencent/SafeguardList.vue'),
        meta: { title: '腾讯云安全卫士' }
      },
      {
        path: 'safeimage',
        name: 'TencentSafeimage',
        component: () => import('@/views/tencent/SafeimageList.vue'),
        meta: { title: '腾讯云图片安全' }
      },
      {
        path: 'safemonitor',
        name: 'TencentSafemonitor',
        component: () => import('@/views/tencent/SafemonitorList.vue'),
        meta: { title: '腾讯云安全监控' }
      },
      {
        path: 'safeplatform',
        name: 'TencentSafeplatform',
        component: () => import('@/views/tencent/SafeplatformList.vue'),
        meta: { title: '腾讯云安全平台' }
      },
      {
        path: 'safetext',
        name: 'TencentSafetext',
        component: () => import('@/views/tencent/SafetextList.vue'),
        meta: { title: '腾讯云文本安全' }
      },
      {
        path: 'safevideo',
        name: 'TencentSafevideo',
        component: () => import('@/views/tencent/SafevideoList.vue'),
        meta: { title: '腾讯云视频安全' }
      },
      {
        path: 'seccredential',
        name: 'TencentSeccredential',
        component: () => import('@/views/tencent/SeccredentialList.vue'),
        meta: { title: '腾讯云安全凭证' }
      },
      {
        path: 'secretmgr',
        name: 'TencentSecretmgr',
        component: () => import('@/views/tencent/SecretmgrList.vue'),
        meta: { title: '腾讯云密钥管理' }
      },
      {
        path: 'ses',
        name: 'TencentSes',
        component: () => import('@/views/tencent/SesList.vue'),
        meta: { title: '腾讯云简单邮件服务' }
      },
      {
        path: 'smartadvisor',
        name: 'TencentSmartadvisor',
        component: () => import('@/views/tencent/SmartadvisorList.vue'),
        meta: { title: '腾讯云智能顾问' }
      },
      {
        path: 'smartguide',
        name: 'TencentSmartguide',
        component: () => import('@/views/tencent/SmartguideList.vue'),
        meta: { title: '腾讯云智能导览' }
      },
      {
        path: 'smartview',
        name: 'TencentSmartview',
        component: () => import('@/views/tencent/SmartviewList.vue'),
        meta: { title: '腾讯云智能视图' }
      },
      {
        path: 'sms',
        name: 'TencentSms',
        component: () => import('@/views/tencent/SmsList.vue'),
        meta: { title: '腾讯云短信服务' }
      },
      {
        path: 'smssign',
        name: 'TencentSmssign',
        component: () => import('@/views/tencent/SmssignList.vue'),
        meta: { title: '腾讯云短信签名' }
      },
      {
        path: 'smstemplate',
        name: 'TencentSmstemplate',
        component: () => import('@/views/tencent/SmstemplateList.vue'),
        meta: { title: '腾讯云短信模板' }
      },
      {
        path: 'spokeneval',
        name: 'TencentSpokeneval',
        component: () => import('@/views/tencent/SpokenevalList.vue'),
        meta: { title: '腾讯云口语评测' }
      },
      {
        path: 'sslpod',
        name: 'TencentSslpod',
        component: () => import('@/views/tencent/SslpodList.vue'),
        meta: { title: '腾讯云SSL监测' }
      },
      {
        path: 'tapd',
        name: 'TencentTapd',
        component: () => import('@/views/tencent/TapdList.vue'),
        meta: { title: '腾讯云TAPD' }
      },
      {
        path: 'tbaas',
        name: 'TencentTbaas',
        component: () => import('@/views/tencent/TbaasList.vue'),
        meta: { title: '腾讯云TBaaS区块链' }
      },
      {
        path: 'tcb',
        name: 'TencentTcb',
        component: () => import('@/views/tencent/TcbList.vue'),
        meta: { title: '腾讯云云开发 TCB' }
      },
      {
        path: 'tcaplusdb',
        name: 'TencentTcaplusdb',
        component: () => import('@/views/tencent/TcaplusdbList.vue'),
        meta: { title: '腾讯云TcaplusDB' }
      },
      {
        path: 'tcrent',
        name: 'TencentTcrent',
        component: () => import('@/views/tencent/TcrentList.vue'),
        meta: { title: '腾讯云TCR企业版' }
      },
      {
        path: 'tencentconnect',
        name: 'TencentTencentconnect',
        component: () => import('@/views/tencent/TencentconnectList.vue'),
        meta: { title: '腾讯云企业微信' }
      },
      {
        path: 'tendis',
        name: 'TencentTendis',
        component: () => import('@/views/tencent/TendisList.vue'),
        meta: { title: '腾讯云Tendis' }
      },
      {
        path: 'tchousec',
        name: 'TencentTchousec',
        component: () => import('@/views/tencent/TchousecList.vue'),
        meta: { title: '腾讯云TCHouse-C' }
      },
      {
        path: 'tchoused',
        name: 'TencentTchoused',
        component: () => import('@/views/tencent/TchousedList.vue'),
        meta: { title: '腾讯云TCHouse-D' }
      },
      {
        path: 'tchousep',
        name: 'TencentTchousep',
        component: () => import('@/views/tencent/TchousepList.vue'),
        meta: { title: '腾讯云TCHouse-P' }
      },
      {
        path: 'ti',
        name: 'TencentTi',
        component: () => import('@/views/tencent/TiList.vue'),
        meta: { title: '腾讯云钛' }
      },
      {
        path: 'tihai',
        name: 'TencentTihai',
        component: () => import('@/views/tencent/TihaiList.vue'),
        meta: { title: '腾讯云TI Hai' }
      },
      {
        path: 'tokenhub',
        name: 'TencentTokenhub',
        component: () => import('@/views/tencent/TokenhubList.vue'),
        meta: { title: '腾讯云Token Hub' }
      },
      {
        path: 'tourismbigdata',
        name: 'TencentTourismbigdata',
        component: () => import('@/views/tencent/TourismbigdataList.vue'),
        meta: { title: '腾讯云旅游大数据' }
      },
      {
        path: 'trtc',
        name: 'TencentTrtc',
        component: () => import('@/views/tencent/TrtcList.vue'),
        meta: { title: '腾讯云实时音视频 TRTC' }
      },
      {
        path: 'trtcroom',
        name: 'TencentTrtcroom',
        component: () => import('@/views/tencent/TrtcroomList.vue'),
        meta: { title: '腾讯云TRTC房间' }
      },
      {
        path: 'tse',
        name: 'TencentTse',
        component: () => import('@/views/tencent/TseList.vue'),
        meta: { title: '腾讯云微服务引擎 TSE' }
      },
      {
        path: 'tsf',
        name: 'TencentTsf',
        component: () => import('@/views/tencent/TsfList.vue'),
        meta: { title: '腾讯云微服务平台 TSF' }
      },
      {
        path: 'tts',
        name: 'TencentTts',
        component: () => import('@/views/tencent/TtsList.vue'),
        meta: { title: '腾讯云语音合成 TTS' }
      },
      {
        path: 'vod',
        name: 'TencentVod',
        component: () => import('@/views/tencent/VodList.vue'),
        meta: { title: '腾讯云视频点播 VOD' }
      },
      {
        path: 'vodmedia',
        name: 'TencentVodmedia',
        component: () => import('@/views/tencent/VodmediaList.vue'),
        meta: { title: '腾讯云VOD媒体' }
      },
      {
        path: 'vodprocess',
        name: 'TencentVodprocess',
        component: () => import('@/views/tencent/VodprocessList.vue'),
        meta: { title: '腾讯云VOD处理' }
      },
      {
        path: 'voiceclone',
        name: 'TencentVoiceclone',
        component: () => import('@/views/tencent/VoicecloneList.vue'),
        meta: { title: '腾讯云声音复刻' }
      },
      {
        path: 'voicemsg',
        name: 'TencentVoicemsg',
        component: () => import('@/views/tencent/VoicemsgList.vue'),
        meta: { title: '腾讯云语音消息' }
      },
      {
        path: 'vulnmgr',
        name: 'TencentVulnmgr',
        component: () => import('@/views/tencent/VulnmgrList.vue'),
        meta: { title: '腾讯云漏洞管理' }
      },
      {
        path: 'wedata',
        name: 'TencentWedata',
        component: () => import('@/views/tencent/WedataList.vue'),
        meta: { title: '腾讯云WeData' }
      },
      {
        path: 'welink',
        name: 'TencentWelink',
        component: () => import('@/views/tencent/WelinkList.vue'),
        meta: { title: '腾讯云WeLink' }
      },
      {
        path: 'websearch',
        name: 'TencentWebsearch',
        component: () => import('@/views/tencent/WebsearchList.vue'),
        meta: { title: '腾讯云网络搜索' }
      },
      {
        path: 'weda',
        name: 'TencentWeda',
        component: () => import('@/views/tencent/WedaList.vue'),
        meta: { title: '腾讯云微搭 WeDa' }
      }]
  },
  {
    path: '/aliyun',
    children: [
      {
        path: 'ack',
        name: 'AliyunAck',
        component: () => import('@/views/aliyun/ackList.vue'),
        meta: { title: '阿里云容器服务 ACK' }
      },
      {
        path: 'acr',
        name: 'AliyunAcr',
        component: () => import('@/views/aliyun/acrList.vue'),
        meta: { title: '阿里云容器镜像服务 ACR' }
      },
      {
        path: 'actiontrail',
        name: 'AliyunActiontrail',
        component: () => import('@/views/aliyun/actiontrailList.vue'),
        meta: { title: '阿里云操作审计 ActionTrail' }
      },
      {
        path: 'alb',
        name: 'AliyunAlb',
        component: () => import('@/views/aliyun/albList.vue'),
        meta: { title: '阿里云应用型负载均衡 ALB' }
      },
      {
        path: 'analyticdb',
        name: 'AliyunAnalyticdb',
        component: () => import('@/views/aliyun/analyticdbList.vue'),
        meta: { title: '阿里云分析型数据库 AnalyticDB' }
      },
      {
        path: 'apigateway',
        name: 'AliyunApigateway',
        component: () => import('@/views/aliyun/apigatewayList.vue'),
        meta: { title: '阿里云API网关' }
      },
      {
        path: 'cbh',
        name: 'AliyunCbh',
        component: () => import('@/views/aliyun/cbhList.vue'),
        meta: { title: '阿里云云堡垒机 CBH' }
      },
      {
        path: 'cdn',
        name: 'AliyunCdn',
        component: () => import('@/views/aliyun/cdnList.vue'),
        meta: { title: '阿里云内容分发 CDN' }
      },
      {
        path: 'cen',
        name: 'AliyunCen',
        component: () => import('@/views/aliyun/cenList.vue'),
        meta: { title: '阿里云企业网络 CEN' }
      },
      {
        path: 'clickhouse',
        name: 'AliyunClickhouse',
        component: () => import('@/views/aliyun/clickhouseList.vue'),
        meta: { title: '阿里云云数据库 ClickHouse' }
      },
      {
        path: 'cloudfirewall',
        name: 'AliyunCloudfirewall',
        component: () => import('@/views/aliyun/cloudfirewallList.vue'),
        meta: { title: '阿里云云防火墙' }
      },
      {
        path: 'cms',
        name: 'AliyunCms',
        component: () => import('@/views/aliyun/cmsList.vue'),
        meta: { title: '阿里云云监控 CMS' }
      },
      {
        path: 'config',
        name: 'AliyunConfig',
        component: () => import('@/views/aliyun/configList.vue'),
        meta: { title: '阿里云配置审计' }
      },
      {
        path: 'ddos',
        name: 'AliyunDdos',
        component: () => import('@/views/aliyun/ddosList.vue'),
        meta: { title: '阿里云DDoS防护' }
      },
      {
        path: 'disk',
        name: 'AliyunDisk',
        component: () => import('@/views/aliyun/diskList.vue'),
        meta: { title: '阿里云云盘' }
      },
      {
        path: 'dns',
        name: 'AliyunDns',
        component: () => import('@/views/aliyun/DnsList.vue'),
        meta: { title: '阿里云DNS' }
      },
      {
        path: 'dns_records',
        name: 'AliyunDnsRecords',
        component: () => import('@/views/aliyun/dns_recordsList.vue'),
        meta: { title: '阿里云DNS解析记录' }
      },
      {
        path: 'dsc',
        name: 'AliyunDsc',
        component: () => import('@/views/aliyun/dscList.vue'),
        meta: { title: '阿里云数据安全中心 DSC' }
      },
      {
        path: 'dts',
        name: 'AliyunDts',
        component: () => import('@/views/aliyun/dtsList.vue'),
        meta: { title: '阿里云数据传输服务 DTS' }
      },
      {
        path: 'ecs',
        name: 'AliyunEcs',
        component: () => import('@/views/aliyun/ecsList.vue'),
        meta: { title: '阿里云云服务器 ECS' }
      },
      {
        path: 'eip',
        name: 'AliyunEip',
        component: () => import('@/views/aliyun/eipList.vue'),
        meta: { title: '阿里云弹性公网IP EIP' }
      },
      {
        path: 'elasticsearch',
        name: 'AliyunElasticsearch',
        component: () => import('@/views/aliyun/elasticsearchList.vue'),
        meta: { title: '阿里云Elasticsearch' }
      },
      {
        path: 'emr',
        name: 'AliyunEmr',
        component: () => import('@/views/aliyun/emrList.vue'),
        meta: { title: '阿里云大数据 EMR' }
      },
      {
        path: 'ess',
        name: 'AliyunEss',
        component: () => import('@/views/aliyun/essList.vue'),
        meta: { title: '阿里云弹性伸缩 ESS' }
      },
      {
        path: 'fc',
        name: 'AliyunFc',
        component: () => import('@/views/aliyun/fcList.vue'),
        meta: { title: '阿里云函数计算 FC' }
      },
      {
        path: 'ga',
        name: 'AliyunGa',
        component: () => import('@/views/aliyun/gaList.vue'),
        meta: { title: '阿里云全球加速 GA' }
      },
      {
        path: 'hologres',
        name: 'AliyunHologres',
        component: () => import('@/views/aliyun/hologresList.vue'),
        meta: { title: '阿里云实时数仓 Hologres' }
      },
      {
        path: 'hss',
        name: 'AliyunHss',
        component: () => import('@/views/aliyun/hssList.vue'),
        meta: { title: '阿里云主机安全 HSS' }
      },
      {
        path: 'iot',
        name: 'AliyunIot',
        component: () => import('@/views/aliyun/iotList.vue'),
        meta: { title: '阿里云物联网平台 IoT' }
      },
      {
        path: 'kafka',
        name: 'AliyunKafka',
        component: () => import('@/views/aliyun/kafkaList.vue'),
        meta: { title: '阿里云消息队列 Kafka' }
      },
      {
        path: 'kms',
        name: 'AliyunKms',
        component: () => import('@/views/aliyun/kmsList.vue'),
        meta: { title: '阿里云密钥管理服务 KMS' }
      },
      {
        path: 'lindorm',
        name: 'AliyunLindorm',
        component: () => import('@/views/aliyun/lindormList.vue'),
        meta: { title: '阿里云云数据库 Lindorm' }
      },
      {
        path: 'live',
        name: 'AliyunLive',
        component: () => import('@/views/aliyun/liveList.vue'),
        meta: { title: '阿里云视频直播 Live' }
      },
      {
        path: 'maxcompute',
        name: 'AliyunMaxcompute',
        component: () => import('@/views/aliyun/maxcomputeList.vue'),
        meta: { title: '阿里云大数据计算 MaxCompute' }
      },
      {
        path: 'mongodb',
        name: 'AliyunMongodb',
        component: () => import('@/views/aliyun/mongodbList.vue'),
        meta: { title: '阿里云云数据库 MongoDB' }
      },
      {
        path: 'mse',
        name: 'AliyunMse',
        component: () => import('@/views/aliyun/mseList.vue'),
        meta: { title: '阿里云微服务引擎 MSE' }
      },
      {
        path: 'nas',
        name: 'AliyunNas',
        component: () => import('@/views/aliyun/nasList.vue'),
        meta: { title: '阿里云文件存储 NAS' }
      },
      {
        path: 'nat',
        name: 'AliyunNat',
        component: () => import('@/views/aliyun/natList.vue'),
        meta: { title: '阿里云NAT网关' }
      },
      {
        path: 'nlb',
        name: 'AliyunNlb',
        component: () => import('@/views/aliyun/nlbList.vue'),
        meta: { title: '阿里云网络型负载均衡 NLB' }
      },
      {
        path: 'oos',
        name: 'AliyunOos',
        component: () => import('@/views/aliyun/oosList.vue'),
        meta: { title: '阿里云运维编排 OOS' }
      },
      {
        path: 'oss',
        name: 'AliyunOss',
        component: () => import('@/views/aliyun/ossList.vue'),
        meta: { title: '阿里云对象存储 OSS' }
      },
      {
        path: 'polardb',
        name: 'AliyunPolardb',
        component: () => import('@/views/aliyun/polardbList.vue'),
        meta: { title: '阿里云云数据库 PolarDB' }
      },
      {
        path: 'quota',
        name: 'AliyunQuota',
        component: () => import('@/views/aliyun/quotaList.vue'),
        meta: { title: '阿里云配额中心' }
      },
      {
        path: 'rabbitmq',
        name: 'AliyunRabbitmq',
        component: () => import('@/views/aliyun/rabbitmqList.vue'),
        meta: { title: '阿里云消息队列 RabbitMQ' }
      },
      {
        path: 'rds',
        name: 'AliyunRds',
        component: () => import('@/views/aliyun/rdsList.vue'),
        meta: { title: '阿里云云数据库 RDS' }
      },
      {
        path: 'redis',
        name: 'AliyunRedis',
        component: () => import('@/views/aliyun/redisList.vue'),
        meta: { title: '阿里云云数据库 Redis' }
      },
      {
        path: 'rocketmq',
        name: 'AliyunRocketmq',
        component: () => import('@/views/aliyun/rocketmqList.vue'),
        meta: { title: '阿里云消息队列 RocketMQ' }
      },
      {
        path: 'sae',
        name: 'AliyunSae',
        component: () => import('@/views/aliyun/saeList.vue'),
        meta: { title: '阿里云SAE' }
      },
      {
        path: 'sag',
        name: 'AliyunSag',
        component: () => import('@/views/aliyun/sagList.vue'),
        meta: { title: '阿里云智能接入网关 SAG' }
      },
      {
        path: 'securitygroup',
        name: 'AliyunSecuritygroup',
        component: () => import('@/views/aliyun/securitygroupList.vue'),
        meta: { title: '阿里云安全组' }
      },
      {
        path: 'selectdb',
        name: 'AliyunSelectdb',
        component: () => import('@/views/aliyun/selectdbList.vue'),
        meta: { title: '阿里云云数据库 SelectDB' }
      },
      {
        path: 'slb',
        name: 'AliyunSlb',
        component: () => import('@/views/aliyun/slbList.vue'),
        meta: { title: '阿里云负载均衡 SLB' }
      },
      {
        path: 'sls',
        name: 'AliyunSls',
        component: () => import('@/views/aliyun/slsList.vue'),
        meta: { title: '阿里云日志服务 SLS' }
      },
      {
        path: 'sms',
        name: 'AliyunSms',
        component: () => import('@/views/aliyun/smsList.vue'),
        meta: { title: '阿里云短信服务 SMS' }
      },
      {
        path: 'ssl',
        name: 'AliyunSsl',
        component: () => import('@/views/aliyun/sslList.vue'),
        meta: { title: '阿里云SSL证书' }
      },
      {
        path: 'storagegateway',
        name: 'AliyunStoragegateway',
        component: () => import('@/views/aliyun/storagegatewayList.vue'),
        meta: { title: '阿里云存储网关' }
      },
      {
        path: 'vod',
        name: 'AliyunVod',
        component: () => import('@/views/aliyun/vodList.vue'),
        meta: { title: '阿里云视频点播 VOD' }
      },
      {
        path: 'vpcendpoint',
        name: 'AliyunVpcendpoint',
        component: () => import('@/views/aliyun/vpcendpointList.vue'),
        meta: { title: '阿里云VPC终端节点' }
      },
      {
        path: 'vpc',
        name: 'AliyunVpc',
        component: () => import('@/views/aliyun/vpcList.vue'),
        meta: { title: '阿里云专有网络 VPC' }
      },
      {
        path: 'vpngateway',
        name: 'AliyunVpngateway',
        component: () => import('@/views/aliyun/vpngatewayList.vue'),
        meta: { title: '阿里云VPN网关' }
      },
      {
        path: 'waf',
        name: 'AliyunWaf',
        component: () => import('@/views/aliyun/wafList.vue'),
        meta: { title: '阿里云Web应用防火墙 WAF' }
      }
    ]
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: () => import('@/views/Statistics.vue'),
    meta: { title: '统计分析' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
