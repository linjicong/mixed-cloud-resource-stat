<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="8" :lg="6">
        <div class="stat-card huawei">
          <div class="stat-title">华为云资源</div>
          <div class="stat-value">{{ totalResources?.huawei || 0 }}</div>
          <div class="stat-change">ECS: {{ huaweiResources.ecs.length }} | RDS: {{ huaweiResources.rds.length }}</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8" :lg="6">
        <div class="stat-card tencent">
          <div class="stat-title">腾讯云资源</div>
          <div class="stat-value">{{ totalResources?.tencent || 0 }}</div>
          <div class="stat-change">CVM: {{ tencentResources.cvm.length }} | CDB: {{ tencentResources.cdb.length }}</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8" :lg="6">
        <div class="stat-card aliyun">
          <div class="stat-title">阿里云资源</div>
          <div class="stat-value">{{ totalResources?.aliyun || 0 }}</div>
          <div class="stat-change">DNS: {{ aliyunResources.dns.length }}</div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="8" :lg="6">
        <div class="stat-card">
          <div class="stat-title">总资源数</div>
          <div class="stat-value">{{ (totalResources?.huawei || 0) + (totalResources?.tencent || 0) + (totalResources?.aliyun || 0) }}</div>
          <div class="stat-change">多云统一管理</div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :xs="24" :lg="12">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><PieChart /></el-icon>
              资源分布
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="pieChartOption" 
              style="height: 300px;"
              autoresize
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><TrendCharts /></el-icon>
              资源趋势
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="lineChartOption" 
              style="height: 300px;"
              autoresize
            />
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 最近资源 -->
    <el-row :gutter="20">
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              华为云ECS
            </div>
            <div class="actions">
              <el-button type="text" @click="$router.push('/huawei/ecs')">查看全部</el-button>
            </div>
          </div>
          <div class="card-content">
            <div v-if="huaweiResources.ecs.length === 0" class="empty-state">
              <div class="empty-icon">📊</div>
              <div class="empty-text">暂无数据</div>
            </div>
            <div v-else>
              <div 
                v-for="ecs in huaweiResources.ecs.slice(0, 5)" 
                :key="ecs.id"
                class="resource-item"
              >
                <div class="resource-info">
                  <div class="resource-name">{{ ecs.name }}</div>
                  <div class="resource-detail">{{ ecs.status }} | {{ ecs.osEXTAZAvailabilityZone }}</div>
                </div>
                <el-tag :type="getStatusType(ecs.status)" size="small">
                  {{ ecs.status }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              腾讯云CVM
            </div>
            <div class="actions">
              <el-button type="text" @click="$router.push('/tencent/cvm')">查看全部</el-button>
            </div>
          </div>
          <div class="card-content">
            <div v-if="tencentResources.cvm.length === 0" class="empty-state">
              <div class="empty-icon">📊</div>
              <div class="empty-text">暂无数据</div>
            </div>
            <div v-else>
              <div 
                v-for="cvm in tencentResources.cvm.slice(0, 5)" 
                :key="cvm.instanceId"
                class="resource-item"
              >
                <div class="resource-info">
                  <div class="resource-name">{{ cvm.instanceName }}</div>
                  <div class="resource-detail">{{ cvm.instanceState }} | {{ cvm.regionName }}</div>
                </div>
                <el-tag :type="getStatusType(cvm.instanceState)" size="small">
                  {{ cvm.instanceState }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              阿里云DNS
            </div>
            <div class="actions">
              <el-button type="text" @click="$router.push('/aliyun/dns')">查看全部</el-button>
            </div>
          </div>
          <div class="card-content">
            <div v-if="aliyunResources.dns.length === 0" class="empty-state">
              <div class="empty-icon">📊</div>
              <div class="empty-text">暂无数据</div>
            </div>
            <div v-else>
              <div 
                v-for="dns in aliyunResources.dns.slice(0, 5)" 
                :key="dns.domainName"
                class="resource-item"
              >
                <div class="resource-info">
                  <div class="resource-name">{{ dns.domainName }}</div>
                  <div class="resource-detail">{{ dns.versionCode }} | {{ dns.recordCount }}条记录</div>
                </div>
                <el-tag type="success" size="small">
                  正常
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useCloudStore } from '@/stores/cloud'

const cloudStore = useCloudStore()
// 使用 storeToRefs 保持响应性
const { huaweiResources, tencentResources, aliyunResources, totalResources } = storeToRefs(cloudStore)

// 饼图配置
const pieChartOption = computed(() => ({
  title: {
    text: '云资源分布',
    left: 'center',
    textStyle: {
      fontSize: 16
    }
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['华为云', '腾讯云', '阿里云']
  },
  series: [
    {
      name: '资源分布',
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        { value: totalResources.value?.huawei || 0, name: '华为云', itemStyle: { color: '#ff6b6b' } },
        { value: totalResources.value?.tencent || 0, name: '腾讯云', itemStyle: { color: '#00d2ff' } },
        { value: totalResources.value?.aliyun || 0, name: '阿里云', itemStyle: { color: '#ff9a9e' } }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}))

// 折线图配置
const lineChartOption = computed(() => ({
  title: {
    text: '资源增长趋势',
    left: 'center',
    textStyle: {
      fontSize: 16
    }
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['华为云', '腾讯云', '阿里云'],
    top: 30
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    top: '15%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '华为云',
      type: 'line',
      stack: 'Total',
      data: [120, 132, 101, 134, 90, 230, 210],
      itemStyle: { color: '#ff6b6b' }
    },
    {
      name: '腾讯云',
      type: 'line',
      stack: 'Total',
      data: [220, 182, 191, 234, 290, 330, 310],
      itemStyle: { color: '#00d2ff' }
    },
    {
      name: '阿里云',
      type: 'line',
      stack: 'Total',
      data: [150, 232, 201, 154, 190, 330, 410],
      itemStyle: { color: '#ff9a9e' }
    }
  ]
}))

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    'ACTIVE': 'success',
    'RUNNING': 'success',
    'STOPPED': 'warning',
    'ERROR': 'danger',
    'PENDING': 'info'
  }
  return statusMap[status] || 'info'
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.refreshAllData()
})
</script>

<style lang="scss" scoped>
.dashboard {
  .stats-row {
    margin-bottom: 20px;
  }
  
  .charts-row {
    margin-bottom: 20px;
  }
  
  .resource-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #f0f0f0;
    
    &:last-child {
      border-bottom: none;
    }
    
    .resource-info {
      flex: 1;
      
      .resource-name {
        font-weight: 500;
        color: #303133;
        margin-bottom: 4px;
      }
      
      .resource-detail {
        font-size: 12px;
        color: #909399;
      }
    }
  }
}
</style>
