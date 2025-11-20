<template>
  <div class="statistics">
    <!-- 概览统计 -->
    <el-row :gutter="20" class="overview-stats">
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card huawei">
          <div class="stat-title">华为云资源</div>
          <div class="stat-value">{{ totalResources.huawei }}</div>
          <div class="stat-change">
            ECS: {{ huaweiResources.ecs.length }} | 
            RDS: {{ huaweiResources.rds.length }} | 
            ELB: {{ huaweiResources.elb.length }}
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card tencent">
          <div class="stat-title">腾讯云资源</div>
          <div class="stat-value">{{ totalResources.tencent }}</div>
          <div class="stat-change">
            CVM: {{ tencentResources.cvm.length }} | 
            CDB: {{ tencentResources.cdb.length }} | 
            CLB: {{ tencentResources.clb.length }}
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card aliyun">
          <div class="stat-title">阿里云资源</div>
          <div class="stat-value">{{ totalResources.aliyun }}</div>
          <div class="stat-change">
            DNS: {{ aliyunResources.dns.length }}
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-title">总资源数</div>
          <div class="stat-value">{{ totalAllResources }}</div>
          <div class="stat-change">多云统一管理</div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-section">
      <el-col :xs="24" :lg="12">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><PieChart /></el-icon>
              云厂商资源分布
            </div>
            <div class="actions">
              <el-button size="small" @click="refreshChartData">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="cloudDistributionOption" 
              style="height: 400px;"
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
              资源增长趋势
            </div>
            <div class="actions">
              <el-select v-model="trendTimeRange" size="small" @change="updateTrendChart">
                <el-option label="最近7天" value="7d" />
                <el-option label="最近30天" value="30d" />
                <el-option label="最近90天" value="90d" />
              </el-select>
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="trendChartOption" 
              style="height: 400px;"
              autoresize
            />
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 详细统计表格 -->
    <el-row :gutter="20" class="detailed-stats">
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              华为云资源统计
            </div>
          </div>
          <div class="card-content">
            <el-table :data="huaweiStats" size="small" stripe>
              <el-table-column prop="type" label="资源类型" width="100" />
              <el-table-column prop="count" label="数量" width="80" />
              <el-table-column prop="running" label="运行中" width="80" />
              <el-table-column prop="stopped" label="已停止" width="80" />
              <el-table-column prop="percentage" label="运行率" width="80">
                <template #default="{ row }">
                  <el-progress 
                    :percentage="row.percentage" 
                    :color="getProgressColor(row.percentage)"
                    :show-text="false"
                    :stroke-width="6"
                  />
                  <span style="margin-left: 8px; font-size: 12px;">{{ row.percentage }}%</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
      
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              腾讯云资源统计
            </div>
          </div>
          <div class="card-content">
            <el-table :data="tencentStats" size="small" stripe>
              <el-table-column prop="type" label="资源类型" width="100" />
              <el-table-column prop="count" label="数量" width="80" />
              <el-table-column prop="running" label="运行中" width="80" />
              <el-table-column prop="stopped" label="已停止" width="80" />
              <el-table-column prop="percentage" label="运行率" width="80">
                <template #default="{ row }">
                  <el-progress 
                    :percentage="row.percentage" 
                    :color="getProgressColor(row.percentage)"
                    :show-text="false"
                    :stroke-width="6"
                  />
                  <span style="margin-left: 8px; font-size: 12px;">{{ row.percentage }}%</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
      
      <el-col :xs="24" :lg="8">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Platform /></el-icon>
              阿里云资源统计
            </div>
          </div>
          <div class="card-content">
            <el-table :data="aliyunStats" size="small" stripe>
              <el-table-column prop="type" label="资源类型" width="100" />
              <el-table-column prop="count" label="数量" width="80" />
              <el-table-column prop="running" label="正常" width="80" />
              <el-table-column prop="stopped" label="异常" width="80" />
              <el-table-column prop="percentage" label="正常率" width="80">
                <template #default="{ row }">
                  <el-progress 
                    :percentage="row.percentage" 
                    :color="getProgressColor(row.percentage)"
                    :show-text="false"
                    :stroke-width="6"
                  />
                  <span style="margin-left: 8px; font-size: 12px;">{{ row.percentage }}%</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 成本分析 -->
    <el-row :gutter="20" class="cost-analysis">
      <el-col :xs="24" :lg="12">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><Money /></el-icon>
              成本分析
            </div>
            <div class="actions">
              <el-date-picker
                v-model="costDateRange"
                type="monthrange"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                format="YYYY-MM"
                value-format="YYYY-MM"
                size="small"
                @change="updateCostChart"
              />
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="costChartOption" 
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
              成本趋势
            </div>
          </div>
          <div class="card-content">
            <v-chart 
              :option="costTrendOption" 
              style="height: 300px;"
              autoresize
            />
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 资源利用率 -->
    <el-row :gutter="20" class="utilization-section">
      <el-col :xs="24">
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><DataAnalysis /></el-icon>
              资源利用率分析
            </div>
            <div class="actions">
              <el-button size="small" @click="refreshUtilization">
                <el-icon><Refresh /></el-icon>
                刷新数据
              </el-button>
            </div>
          </div>
          <div class="card-content">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="12" :md="6">
                <div class="utilization-item">
                  <div class="utilization-title">CPU利用率</div>
                  <el-progress 
                    :percentage="cpuUtilization" 
                    :color="getUtilizationColor(cpuUtilization)"
                    :stroke-width="12"
                  />
                  <div class="utilization-value">{{ cpuUtilization }}%</div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="12" :md="6">
                <div class="utilization-item">
                  <div class="utilization-title">内存利用率</div>
                  <el-progress 
                    :percentage="memoryUtilization" 
                    :color="getUtilizationColor(memoryUtilization)"
                    :stroke-width="12"
                  />
                  <div class="utilization-value">{{ memoryUtilization }}%</div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="12" :md="6">
                <div class="utilization-item">
                  <div class="utilization-title">存储利用率</div>
                  <el-progress 
                    :percentage="storageUtilization" 
                    :color="getUtilizationColor(storageUtilization)"
                    :stroke-width="12"
                  />
                  <div class="utilization-value">{{ storageUtilization }}%</div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="12" :md="6">
                <div class="utilization-item">
                  <div class="utilization-title">网络利用率</div>
                  <el-progress 
                    :percentage="networkUtilization" 
                    :color="getUtilizationColor(networkUtilization)"
                    :stroke-width="12"
                  />
                  <div class="utilization-value">{{ networkUtilization }}%</div>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useCloudStore } from '@/stores/cloud'
import dayjs from 'dayjs'

const cloudStore = useCloudStore()
const { huaweiResources, tencentResources, aliyunResources, totalResources } = cloudStore

// 时间范围
const trendTimeRange = ref('30d')
const costDateRange = ref([])

// 利用率数据
const cpuUtilization = ref(75)
const memoryUtilization = ref(68)
const storageUtilization = ref(82)
const networkUtilization = ref(45)

// 计算属性
const totalAllResources = computed(() => {
  return totalResources.value.huawei + totalResources.value.tencent + totalResources.value.aliyun
})

// 华为云统计
const huaweiStats = computed(() => {
  return [
    {
      type: 'ECS',
      count: huaweiResources.ecs.length,
      running: huaweiResources.ecs.filter(e => e.status === 'ACTIVE').length,
      stopped: huaweiResources.ecs.filter(e => e.status === 'SHUTOFF').length,
      percentage: huaweiResources.ecs.length > 0 ? 
        Math.round((huaweiResources.ecs.filter(e => e.status === 'ACTIVE').length / huaweiResources.ecs.length) * 100) : 0
    },
    {
      type: 'RDS',
      count: huaweiResources.rds.length,
      running: huaweiResources.rds.filter(r => r.status === 'available').length,
      stopped: huaweiResources.rds.filter(r => r.status === 'unavailable').length,
      percentage: huaweiResources.rds.length > 0 ? 
        Math.round((huaweiResources.rds.filter(r => r.status === 'available').length / huaweiResources.rds.length) * 100) : 0
    },
    {
      type: 'ELB',
      count: huaweiResources.elb.length,
      running: huaweiResources.elb.filter(e => e.operatingStatus === 'ONLINE').length,
      stopped: huaweiResources.elb.filter(e => e.operatingStatus === 'OFFLINE').length,
      percentage: huaweiResources.elb.length > 0 ? 
        Math.round((huaweiResources.elb.filter(e => e.operatingStatus === 'ONLINE').length / huaweiResources.elb.length) * 100) : 0
    }
  ]
})

// 腾讯云统计
const tencentStats = computed(() => {
  return [
    {
      type: 'CVM',
      count: tencentResources.cvm.length,
      running: tencentResources.cvm.filter(c => c.instanceState === 'RUNNING').length,
      stopped: tencentResources.cvm.filter(c => c.instanceState === 'STOPPED').length,
      percentage: tencentResources.cvm.length > 0 ? 
        Math.round((tencentResources.cvm.filter(c => c.instanceState === 'RUNNING').length / tencentResources.cvm.length) * 100) : 0
    },
    {
      type: 'CDB',
      count: tencentResources.cdb.length,
      running: tencentResources.cdb.filter(c => c.status === 1).length,
      stopped: tencentResources.cdb.filter(c => c.status === 4).length,
      percentage: tencentResources.cdb.length > 0 ? 
        Math.round((tencentResources.cdb.filter(c => c.status === 1).length / tencentResources.cdb.length) * 100) : 0
    },
    {
      type: 'CLB',
      count: tencentResources.clb.length,
      running: tencentResources.clb.filter(c => c.status === 1).length,
      stopped: tencentResources.clb.filter(c => c.status === 0).length,
      percentage: tencentResources.clb.length > 0 ? 
        Math.round((tencentResources.clb.filter(c => c.status === 1).length / tencentResources.clb.length) * 100) : 0
    }
  ]
})

// 阿里云统计
const aliyunStats = computed(() => {
  return [
    {
      type: 'DNS',
      count: aliyunResources.dns.length,
      running: aliyunResources.dns.filter(d => d.domainStatus === 'ENABLE').length,
      stopped: aliyunResources.dns.filter(d => d.domainStatus === 'PAUSE').length,
      percentage: aliyunResources.dns.length > 0 ? 
        Math.round((aliyunResources.dns.filter(d => d.domainStatus === 'ENABLE').length / aliyunResources.dns.length) * 100) : 0
    }
  ]
})

// 云厂商资源分布图表
const cloudDistributionOption = computed(() => ({
  title: {
    text: '云厂商资源分布',
    left: 'center',
    textStyle: { fontSize: 16 }
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
      radius: ['40%', '70%'],
      center: ['50%', '60%'],
      data: [
        { value: totalResources.value.huawei, name: '华为云', itemStyle: { color: '#ff6b6b' } },
        { value: totalResources.value.tencent, name: '腾讯云', itemStyle: { color: '#00d2ff' } },
        { value: totalResources.value.aliyun, name: '阿里云', itemStyle: { color: '#ff9a9e' } }
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

// 趋势图表
const trendChartOption = computed(() => ({
  title: {
    text: '资源增长趋势',
    left: 'center',
    textStyle: { fontSize: 16 }
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
    data: generateTrendDates()
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '华为云',
      type: 'line',
      data: generateTrendData('huawei'),
      itemStyle: { color: '#ff6b6b' },
      smooth: true
    },
    {
      name: '腾讯云',
      type: 'line',
      data: generateTrendData('tencent'),
      itemStyle: { color: '#00d2ff' },
      smooth: true
    },
    {
      name: '阿里云',
      type: 'line',
      data: generateTrendData('aliyun'),
      itemStyle: { color: '#ff9a9e' },
      smooth: true
    }
  ]
}))

// 成本分析图表
const costChartOption = computed(() => ({
  title: {
    text: '成本分析',
    left: 'center',
    textStyle: { fontSize: 16 }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
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
    data: ['ECS/CVM', 'RDS/CDB', 'ELB/CLB', 'EVS/CBS', '其他']
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '¥{value}'
    }
  },
  series: [
    {
      name: '华为云',
      type: 'bar',
      data: [1200, 800, 300, 500, 200],
      itemStyle: { color: '#ff6b6b' }
    },
    {
      name: '腾讯云',
      type: 'bar',
      data: [1500, 900, 400, 600, 300],
      itemStyle: { color: '#00d2ff' }
    },
    {
      name: '阿里云',
      type: 'bar',
      data: [800, 400, 200, 300, 100],
      itemStyle: { color: '#ff9a9e' }
    }
  ]
}))

// 成本趋势图表
const costTrendOption = computed(() => ({
  title: {
    text: '成本趋势',
    left: 'center',
    textStyle: { fontSize: 16 }
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['华为云', '腾讯云', '阿里云', '总计'],
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
    data: generateCostTrendDates()
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '¥{value}'
    }
  },
  series: [
    {
      name: '华为云',
      type: 'line',
      data: generateCostTrendData('huawei'),
      itemStyle: { color: '#ff6b6b' },
      smooth: true
    },
    {
      name: '腾讯云',
      type: 'line',
      data: generateCostTrendData('tencent'),
      itemStyle: { color: '#00d2ff' },
      smooth: true
    },
    {
      name: '阿里云',
      type: 'line',
      data: generateCostTrendData('aliyun'),
      itemStyle: { color: '#ff9a9e' },
      smooth: true
    },
    {
      name: '总计',
      type: 'line',
      data: generateCostTrendData('total'),
      itemStyle: { color: '#67c23a' },
      smooth: true,
      lineStyle: {
        type: 'dashed'
      }
    }
  ]
}))

// 方法
const generateTrendDates = () => {
  const days = trendTimeRange.value === '7d' ? 7 : trendTimeRange.value === '30d' ? 30 : 90
  const dates = []
  for (let i = days - 1; i >= 0; i--) {
    dates.push(dayjs().subtract(i, 'day').format('MM-DD'))
  }
  return dates
}

const generateTrendData = (provider) => {
  const days = trendTimeRange.value === '7d' ? 7 : trendTimeRange.value === '30d' ? 30 : 90
  const data = []
  for (let i = 0; i < days; i++) {
    // 模拟数据
    const base = provider === 'huawei' ? 20 : provider === 'tencent' ? 15 : 10
    data.push(base + Math.floor(Math.random() * 10))
  }
  return data
}

const generateCostTrendDates = () => {
  const dates = []
  for (let i = 11; i >= 0; i--) {
    dates.push(dayjs().subtract(i, 'month').format('YYYY-MM'))
  }
  return dates
}

const generateCostTrendData = (provider) => {
  const data = []
  for (let i = 0; i < 12; i++) {
    // 模拟数据
    const base = provider === 'huawei' ? 3000 : provider === 'tencent' ? 2500 : provider === 'aliyun' ? 1500 : 7000
    data.push(base + Math.floor(Math.random() * 1000))
  }
  return data
}

const getProgressColor = (percentage) => {
  if (percentage >= 80) return '#67c23a'
  if (percentage >= 60) return '#e6a23c'
  return '#f56c6c'
}

const getUtilizationColor = (percentage) => {
  if (percentage >= 80) return '#f56c6c'
  if (percentage >= 60) return '#e6a23c'
  return '#67c23a'
}

const refreshChartData = () => {
  // 刷新图表数据
  ElMessage.success('图表数据已刷新')
}

const updateTrendChart = () => {
  // 更新趋势图表
}

const updateCostChart = () => {
  // 更新成本图表
}

const refreshUtilization = () => {
  // 刷新利用率数据
  cpuUtilization.value = Math.floor(Math.random() * 100)
  memoryUtilization.value = Math.floor(Math.random() * 100)
  storageUtilization.value = Math.floor(Math.random() * 100)
  networkUtilization.value = Math.floor(Math.random() * 100)
  ElMessage.success('利用率数据已刷新')
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.refreshAllData()
})
</script>

<style lang="scss" scoped>
.statistics {
  .overview-stats {
    margin-bottom: 20px;
  }
  
  .charts-section {
    margin-bottom: 20px;
  }
  
  .detailed-stats {
    margin-bottom: 20px;
  }
  
  .cost-analysis {
    margin-bottom: 20px;
  }
  
  .utilization-section {
    .utilization-item {
      text-align: center;
      padding: 20px;
      
      .utilization-title {
        font-size: 14px;
        color: #606266;
        margin-bottom: 16px;
      }
      
      .utilization-value {
        font-size: 18px;
        font-weight: bold;
        color: #303133;
        margin-top: 8px;
      }
    }
  }
}
</style>
