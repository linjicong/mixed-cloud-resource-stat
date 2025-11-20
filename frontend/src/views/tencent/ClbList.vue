<template>
  <div class="tencent-clb">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Connection /></el-icon>
          腾讯云负载均衡器
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
        <!-- 搜索和筛选 -->
        <div class="filter-bar">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-input
                v-model="searchForm.name"
                placeholder="搜索负载均衡器名称"
                clearable
                @input="handleSearch"
              >
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.status"
                placeholder="选择状态"
                clearable
                @change="handleSearch"
              >
                <el-option label="运行中" value="1" />
                <el-option label="创建中" value="0" />
                <el-option label="异常" value="2" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredClbList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="loadBalancerName" label="负载均衡器名称" width="200">
            <template #default="{ row }">
              <div class="clb-name">
                <el-icon><Connection /></el-icon>
                {{ row.loadBalancerName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="loadBalancerId" label="实例ID" width="200" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.status)" class="status-tag">
                {{ getStatusText(row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="loadBalancerType" label="类型" width="120" />
          <el-table-column prop="vpcId" label="VPC ID" width="150" />
          <el-table-column prop="subnetId" label="子网ID" width="150" />
          <el-table-column prop="loadBalancerVips" label="VIP" width="120">
            <template #default="{ row }">
              <div v-if="row.loadBalancerVips && row.loadBalancerVips.length > 0">
                {{ row.loadBalancerVips[0] }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" size="small" @click="viewDetail(row)">
                详情
              </el-button>
              <el-button type="warning" size="small" @click="viewMonitor(row)">
                监控
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

    <!-- 详情对话框 -->
    <el-dialog 
      v-model="detailVisible" 
      title="负载均衡器详情"
      width="800px"
    >
      <div v-if="selectedClb" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="负载均衡器名称">{{ selectedClb.loadBalancerName }}</el-descriptions-item>
          <el-descriptions-item label="实例ID">{{ selectedClb.loadBalancerId }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedClb.status)">
              {{ getStatusText(selectedClb.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="类型">{{ selectedClb.loadBalancerType }}</el-descriptions-item>
          <el-descriptions-item label="VPC ID">{{ selectedClb.vpcId }}</el-descriptions-item>
          <el-descriptions-item label="子网ID">{{ selectedClb.subnetId }}</el-descriptions-item>
          <el-descriptions-item label="VIP">
            <div v-for="vip in selectedClb.loadBalancerVips" :key="vip">
              {{ vip }}
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedClb.createTime) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>监听器</h4>
          <el-table :data="selectedClb.listeners" size="small">
            <el-table-column prop="listenerName" label="监听器名称" />
            <el-table-column prop="protocol" label="协议" />
            <el-table-column prop="port" label="端口" />
            <el-table-column prop="healthCheck" label="健康检查" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedClb.tags" 
            :key="tag"
            type="info"
            class="tag-item"
          >
            {{ tag }}
          </el-tag>
        </div>
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

// 搜索表单
const searchForm = reactive({
  name: '',
  status: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedClb = ref(null)

// 计算属性
const clbList = computed(() => tencentResources.clb || [])

const filteredClbList = computed(() => {
  let filtered = clbList.value

  if (searchForm.name) {
    filtered = filtered.filter(clb => 
      clb.loadBalancerName.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(clb => clb.status === parseInt(searchForm.status))
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    0: 'info',
    1: 'success',
    2: 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    0: '创建中',
    1: '运行中',
    2: '异常'
  }
  return statusMap[status] || status
}

const formatDate = (date) => {
  if (!date) return '-'
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

const handleSearch = () => {
  currentPage.value = 1
}

const resetSearch = () => {
  Object.assign(searchForm, {
    name: '',
    status: ''
  })
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
  selectedClb.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchTencentResources('clb')
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
  cloudStore.fetchTencentResources('clb')
})
</script>

<style lang="scss" scoped>
.tencent-clb {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .clb-name {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
  
  .detail-content {
    .detail-section {
      margin-top: 20px;
      
      h4 {
        margin-bottom: 10px;
        color: #303133;
      }
      
      .tag-item {
        margin-right: 8px;
        margin-bottom: 8px;
      }
    }
  }
}
</style>
