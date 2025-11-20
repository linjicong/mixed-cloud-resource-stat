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
      }
    ]
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
      }
    ]
  },
  {
    path: '/aliyun',
    children: [
      {
        path: 'dns',
        name: 'AliyunDns',
        component: () => import('@/views/aliyun/DnsList.vue'),
        meta: { title: '阿里云DNS' }
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
