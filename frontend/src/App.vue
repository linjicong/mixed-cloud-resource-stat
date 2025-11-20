<template>
  <div id="app">
    <el-container class="layout-container">
      <el-aside width="200px" class="sidebar">
        <div class="logo">
          <el-icon><Cloudy /></el-icon>
          <span>多云管理</span>
        </div>
        <el-menu
          :default-active="$route.path"
          class="sidebar-menu"
          router
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
        >
          <el-menu-item index="/dashboard">
            <el-icon><Odometer /></el-icon>
            <span>仪表盘</span>
          </el-menu-item>
          <el-sub-menu index="config">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>配置管理</span>
            </template>
            <el-menu-item index="/config/cloud">云厂商配置</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="huawei">
            <template #title>
              <el-icon><Platform /></el-icon>
              <span>华为云</span>
            </template>
            <el-menu-item index="/huawei/ecs">ECS实例</el-menu-item>
            <el-menu-item index="/huawei/rds">RDS数据库</el-menu-item>
            <el-menu-item index="/huawei/elb">负载均衡</el-menu-item>
            <el-menu-item index="/huawei/evs">云硬盘</el-menu-item>
            <el-menu-item index="/huawei/vpc">VPC网络</el-menu-item>
            <el-menu-item index="/huawei/bills">账单统计</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="tencent">
            <template #title>
              <el-icon><Platform /></el-icon>
              <span>腾讯云</span>
            </template>
            <el-menu-item index="/tencent/cvm">CVM实例</el-menu-item>
            <el-menu-item index="/tencent/cdb">CDB数据库</el-menu-item>
            <el-menu-item index="/tencent/clb">负载均衡</el-menu-item>
            <el-menu-item index="/tencent/cbs">云硬盘</el-menu-item>
            <el-menu-item index="/tencent/bills">账单统计</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="aliyun">
            <template #title>
              <el-icon><Platform /></el-icon>
              <span>阿里云</span>
            </template>
            <el-menu-item index="/aliyun/dns">DNS解析</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/statistics">
            <el-icon><TrendCharts /></el-icon>
            <span>统计分析</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-container>
        <el-header class="header">
          <div class="header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item 
                v-for="item in breadcrumbs" 
                :key="item.path"
                :to="item.path"
              >
                {{ item.name }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="header-right">
            <el-button type="primary" @click="refreshData">
              <el-icon><Refresh /></el-icon>
              刷新数据
            </el-button>
          </div>
        </el-header>
        
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useCloudStore } from '@/stores/cloud'

const route = useRoute()
const cloudStore = useCloudStore()

const breadcrumbs = computed(() => {
  const pathArray = route.path.split('/').filter(Boolean)
  const breadcrumbItems = []
  
  let currentPath = ''
  pathArray.forEach((segment, index) => {
    currentPath += `/${segment}`
    breadcrumbItems.push({
      path: currentPath,
      name: getBreadcrumbName(segment, index)
    })
  })
  
  return breadcrumbItems
})

const getBreadcrumbName = (segment, index) => {
  const nameMap = {
    dashboard: '仪表盘',
    config: '配置管理',
    cloud: '云厂商配置',
    huawei: '华为云',
    tencent: '腾讯云',
    aliyun: '阿里云',
    ecs: 'ECS实例',
    rds: 'RDS数据库',
    elb: '负载均衡',
    evs: '云硬盘',
    vpc: 'VPC网络',
    bills: '账单统计',
    cvm: 'CVM实例',
    cdb: 'CDB数据库',
    clb: '负载均衡',
    cbs: '云硬盘',
    dns: 'DNS解析',
    statistics: '统计分析'
  }
  
  return nameMap[segment] || segment
}

const refreshData = () => {
  cloudStore.refreshAllData()
}
</script>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
}

.sidebar {
  background-color: #304156;
  
  .logo {
    display: flex;
    align-items: center;
    padding: 20px;
    color: #fff;
    font-size: 18px;
    font-weight: bold;
    
    .el-icon {
      margin-right: 8px;
      font-size: 24px;
    }
  }
  
  .sidebar-menu {
    border-right: none;
  }
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  
  .header-left {
    flex: 1;
  }
  
  .header-right {
    display: flex;
    align-items: center;
  }
}

.main-content {
  background-color: #f5f5f5;
  padding: 20px;
}
</style>
