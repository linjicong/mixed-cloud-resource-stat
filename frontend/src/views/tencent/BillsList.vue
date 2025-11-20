<template>
  <div class="tencent-bills">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Money /></el-icon>
          腾讯云账单统计
        </div>
        <div class="actions">
          <el-button @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新数据
          </el-button>
          <el-button type="primary" @click="syncData">
            <el-icon><Upload /></el-icon>
            同步数据
          </el-button>
        </div>
      </div>
      
      <div class="card-content">
        <!-- 时间筛选 -->
        <div class="filter-bar">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-date-picker
                v-model="dateRange"
                type="monthrange"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                format="YYYY-MM"
                value-format="YYYY-MM"
                @change="handleDateChange"
              />
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.productCode"
                placeholder="选择产品"
                clearable
                @change="handleSearch"
              >
                <el-option label="CVM" value="cvm" />
                <el-option label="CDB" value="cdb" />
                <el-option label="CLB" value="clb" />
                <el-option label="CBS" value="cbs" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.payMode"
                placeholder="选择付费方式"
                clearable
                @change="handleSearch"
              >
                <el-option label="包年包月" value="prePay" />
                <el-option label="按量计费" value="postPay" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 统计卡片 -->
        <el-row :gutter="20" class="stats-row">
          <el-col :xs="24" :sm="12" :md="6">
            <div class="stat-card">
              <div class="stat-title">总消费金额</div>
              <div class="stat-value">¥{{ totalAmount.toFixed(2) }}</div>
              <div class="stat-change">本月</div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6">
            <div class="stat-card">
              <div class="stat-title">现金支付</div>
              <div class="stat-value">¥{{ cashPayAmount.toFixed(2) }}</div>
              <div class="stat-change">占比 {{ ((cashPayAmount / totalAmount) * 100).toFixed(1) }}%</div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6">
            <div class="stat-card">
              <div class="stat-title">代金券</div>
              <div class="stat-value">¥{{ voucherPayAmount.toFixed(2) }}</div>
              <div class="stat-change">占比 {{ ((voucherPayAmount / totalAmount) * 100).toFixed(1) }}%</div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6">
            <div class="stat-card">
              <div class="stat-title">赠送金</div>
              <div class="stat-value">¥{{ incentivePayAmount.toFixed(2) }}</div>
              <div class="stat-change">占比 {{ ((incentivePayAmount / totalAmount) * 100).toFixed(1) }}%</div>
            </div>
          </el-col>
        </el-row>

        <!-- 图表 -->
        <el-row :gutter="20" class="charts-row">
          <el-col :xs="24" :lg="12">
            <div class="resource-card">
              <div class="card-header">
                <div class="title">
                  <el-icon><PieChart /></el-icon>
                  产品消费分布
                </div>
              </div>
              <div class="card-content">
                <v-chart 
                  :option="productChartOption" 
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
                  消费趋势
                </div>
              </div>
              <div class="card-content">
                <v-chart 
                  :option="trendChartOption" 
                  style="height: 300px;"
                  autoresize
                />
              </div>
            </div>
          </el-col>
        </el-row>

        <!-- 账单明细表格 -->
        <div class="resource-card">
          <div class="card-header">
            <div class="title">
              <el-icon><List /></el-icon>
              账单明细
            </div>
          </div>
          <div class="card-content">
            <el-table 
              :data="filteredBillsList" 
              v-loading="loading"
              class="resource-table"
              stripe
            >
              <el-table-column prop="feeBeginTime" label="计费开始时间" width="160">
                <template #default="{ row }">
                  {{ formatDate(row.feeBeginTime) }}
                </template>
              </el-table-column>
              <el-table-column prop="productCodeName" label="产品名称" width="120" />
              <el-table-column prop="resourceName" label="资源名称" width="200" />
              <el-table-column prop="regionName" label="地域" width="100" />
              <el-table-column prop="totalCost" label="总费用" width="120">
                <template #default="{ row }">
                  <span class="amount-text">¥{{ parseFloat(row.totalCost || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="cashPayAmount" label="现金支付" width="120">
                <template #default="{ row }">
                  <span class="amount-text">¥{{ parseFloat(row.cashPayAmount || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="voucherPayAmount" label="代金券" width="120">
                <template #default="{ row }">
                  <span class="amount-text">¥{{ parseFloat(row.voucherPayAmount || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="payModeName" label="付费方式" width="100" />
              <el-table-column prop="payTime" label="支付时间" width="160">
                <template #default="{ row }">
                  {{ formatDate(row.payTime) }}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="100" fixed="right">
                <template #default="{ row }">
                  <el-button type="primary" size="small" @click="viewDetail(row)">
                    详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="pagination-container">
              <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :page-sizes="[10, 20, 50, 100]"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 详情对话框 -->
    <el-dialog 
      v-model="detailVisible" 
      title="账单详情"
      width="800px"
    >
      <div v-if="selectedBill" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="计费开始时间">{{ formatDate(selectedBill.feeBeginTime) }}</el-descriptions-item>
          <el-descriptions-item label="计费结束时间">{{ formatDate(selectedBill.feeEndTime) }}</el-descriptions-item>
          <el-descriptions-item label="产品名称">{{ selectedBill.productCodeName }}</el-descriptions-item>
          <el-descriptions-item label="资源名称">{{ selectedBill.resourceName }}</el-descriptions-item>
          <el-descriptions-item label="资源ID">{{ selectedBill.resourceId }}</el-descriptions-item>
          <el-descriptions-item label="地域">{{ selectedBill.regionName }}</el-descriptions-item>
          <el-descriptions-item label="付费方式">{{ selectedBill.payModeName }}</el-descriptions-item>
          <el-descriptions-item label="订单ID">{{ selectedBill.orderId }}</el-descriptions-item>
          <el-descriptions-item label="总费用">¥{{ parseFloat(selectedBill.totalCost || 0).toFixed(2) }}</el-descriptions-item>
          <el-descriptions-item label="现金支付">¥{{ parseFloat(selectedBill.cashPayAmount || 0).toFixed(2) }}</el-descriptions-item>
          <el-descriptions-item label="代金券">¥{{ parseFloat(selectedBill.voucherPayAmount || 0).toFixed(2) }}</el-descriptions-item>
          <el-descriptions-item label="赠送金">¥{{ parseFloat(selectedBill.incentivePayAmount || 0).toFixed(2) }}</el-descriptions-item>
          <el-descriptions-item label="支付时间">{{ formatDate(selectedBill.payTime) }}</el-descriptions-item>
          <el-descriptions-item label="项目名称">{{ selectedBill.projectName }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useCloudStore } from '@/stores/cloud'
import dayjs from 'dayjs'

const cloudStore = useCloudStore()
const { tencentResources, loading } = cloudStore

// 时间范围
const dateRange = ref([])

// 搜索表单
const searchForm = reactive({
  productCode: '',
  payMode: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedBill = ref(null)

// 计算属性
const billsList = computed(() => tencentResources.bills || [])

const filteredBillsList = computed(() => {
  let filtered = billsList.value

  if (searchForm.productCode) {
    filtered = filtered.filter(bill => 
      bill.productCode === searchForm.productCode
    )
  }

  if (searchForm.payMode) {
    filtered = filtered.filter(bill => 
      bill.payModeName?.includes(searchForm.payMode === 'prePay' ? '包年包月' : '按量计费')
    )
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 统计数据
const totalAmount = computed(() => {
  return billsList.value.reduce((sum, bill) => sum + parseFloat(bill.totalCost || 0), 0)
})

const cashPayAmount = computed(() => {
  return billsList.value.reduce((sum, bill) => sum + parseFloat(bill.cashPayAmount || 0), 0)
})

const voucherPayAmount = computed(() => {
  return billsList.value.reduce((sum, bill) => sum + parseFloat(bill.voucherPayAmount || 0), 0)
})

const incentivePayAmount = computed(() => {
  return billsList.value.reduce((sum, bill) => sum + parseFloat(bill.incentivePayAmount || 0), 0)
})

// 产品消费分布图表
const productChartOption = computed(() => {
  const productMap = {}
  billsList.value.forEach(bill => {
    const product = bill.productCodeName || '其他'
    productMap[product] = (productMap[product] || 0) + parseFloat(bill.totalCost || 0)
  })

  const data = Object.entries(productMap).map(([name, value]) => ({
    name,
    value: value.toFixed(2)
  }))

  return {
    title: {
      text: '产品消费分布',
      left: 'center',
      textStyle: { fontSize: 16 }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: ¥{c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: data.map(item => item.name)
    },
    series: [
      {
        name: '消费金额',
        type: 'pie',
        radius: '50%',
        center: ['50%', '60%'],
        data: data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
})

// 消费趋势图表
const trendChartOption = computed(() => {
  const dateMap = {}
  billsList.value.forEach(bill => {
    const date = dayjs(bill.feeBeginTime).format('YYYY-MM-DD')
    dateMap[date] = (dateMap[date] || 0) + parseFloat(bill.totalCost || 0)
  })

  const dates = Object.keys(dateMap).sort()
  const amounts = dates.map(date => dateMap[date].toFixed(2))

  return {
    title: {
      text: '消费趋势',
      left: 'center',
      textStyle: { fontSize: 16 }
    },
    tooltip: {
      trigger: 'axis'
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
      data: dates
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '¥{value}'
      }
    },
    series: [
      {
        name: '消费金额',
        type: 'line',
        data: amounts,
        smooth: true,
        itemStyle: { color: '#00d2ff' },
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: 'rgba(0, 210, 255, 0.3)' },
              { offset: 1, color: 'rgba(0, 210, 255, 0.1)' }
            ]
          }
        }
      }
    ]
  }
})

// 方法
const formatDate = (date) => {
  if (!date) return '-'
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

const handleDateChange = () => {
  // 根据日期范围筛选数据
  handleSearch()
}

const handleSearch = () => {
  currentPage.value = 1
}

const resetSearch = () => {
  Object.assign(searchForm, {
    productCode: '',
    payMode: ''
  })
  dateRange.value = []
  currentPage.value = 1
}

const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
}

const handleCurrentChange = (val) => {
  currentPage.value = val
}

const viewDetail = (row) => {
  selectedBill.value = row
  detailVisible.value = true
}

const refreshData = () => {
  cloudStore.fetchTencentResources('bills')
}

const syncData = async () => {
  try {
    ElMessage.info('正在同步数据...')
    await cloudStore.syncResources('tencent')
    ElMessage.success('数据同步完成')
    refreshData()
  } catch (error) {
    ElMessage.error('数据同步失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.fetchTencentResources('bills')
})
</script>

<style lang="scss" scoped>
.tencent-bills {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .stats-row {
    margin-bottom: 20px;
  }
  
  .charts-row {
    margin-bottom: 20px;
  }
  
  .amount-text {
    color: #f56c6c;
    font-weight: 500;
  }
  
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
}
</style>
