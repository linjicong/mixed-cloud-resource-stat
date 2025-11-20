<template>
  <div class="huawei-elb">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Connection /></el-icon>
          华为云负载均衡器
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
                <el-option label="运行中" value="ONLINE" />
                <el-option label="离线" value="OFFLINE" />
                <el-option label="错误" value="ERROR" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredElbList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="name" label="负载均衡器名称" width="200">
            <template #default="{ row }">
              <div class="elb-name">
                <el-icon><Connection /></el-icon>
                {{ row.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="实例ID" width="200" />
          <el-table-column prop="operatingStatus" label="运行状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.operatingStatus)" class="status-tag">
                {{ getStatusText(row.operatingStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="provisioningStatus" label="配置状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getProvisioningType(row.provisioningStatus)" class="status-tag">
                {{ getProvisioningText(row.provisioningStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="vipAddress" label="虚拟IP" width="120" />
          <el-table-column prop="vpcId" label="VPC ID" width="150" />
          <el-table-column prop="l4FlavorId" label="L4规格" width="120" />
          <el-table-column prop="l7FlavorId" label="L7规格" width="120" />
          <el-table-column prop="createdAt" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createdAt) }}
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
      <div v-if="selectedElb" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="负载均衡器名称">{{ selectedElb.name }}</el-descriptions-item>
          <el-descriptions-item label="实例ID">{{ selectedElb.id }}</el-descriptions-item>
          <el-descriptions-item label="运行状态">
            <el-tag :type="getStatusType(selectedElb.operatingStatus)">
              {{ getStatusText(selectedElb.operatingStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="配置状态">
            <el-tag :type="getProvisioningType(selectedElb.provisioningStatus)">
              {{ getProvisioningText(selectedElb.provisioningStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="虚拟IP">{{ selectedElb.vipAddress }}</el-descriptions-item>
          <el-descriptions-item label="VPC ID">{{ selectedElb.vpcId }}</el-descriptions-item>
          <el-descriptions-item label="L4规格">{{ selectedElb.l4FlavorId }}</el-descriptions-item>
          <el-descriptions-item label="L7规格">{{ selectedElb.l7FlavorId }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedElb.createdAt) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDate(selectedElb.updatedAt) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>监听器</h4>
          <el-table :data="selectedElb.listeners" size="small">
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="protocol" label="协议" />
            <el-table-column prop="port" label="端口" />
            <el-table-column prop="defaultPoolId" label="默认后端组" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>后端组</h4>
          <el-table :data="selectedElb.pools" size="small">
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="protocol" label="协议" />
            <el-table-column prop="lbAlgorithm" label="负载均衡算法" />
            <el-table-column prop="healthmonitorId" label="健康检查" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedElb.tags" 
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
const { huaweiResources, loading } = cloudStore

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
const selectedElb = ref(null)

// 计算属性
const elbList = computed(() => huaweiResources.elb || [])

const filteredElbList = computed(() => {
  let filtered = elbList.value

  if (searchForm.name) {
    filtered = filtered.filter(elb => 
      elb.name.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(elb => elb.operatingStatus === searchForm.status)
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    'ONLINE': 'success',
    'OFFLINE': 'warning',
    'ERROR': 'danger',
    'DEGRADED': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'ONLINE': '运行中',
    'OFFLINE': '离线',
    'ERROR': '错误',
    'DEGRADED': '降级'
  }
  return statusMap[status] || status
}

const getProvisioningType = (status) => {
  const statusMap = {
    'ACTIVE': 'success',
    'PENDING_CREATE': 'info',
    'PENDING_UPDATE': 'warning',
    'PENDING_DELETE': 'danger'
  }
  return statusMap[status] || 'info'
}

const getProvisioningText = (status) => {
  const statusMap = {
    'ACTIVE': '活跃',
    'PENDING_CREATE': '创建中',
    'PENDING_UPDATE': '更新中',
    'PENDING_DELETE': '删除中'
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
  selectedElb.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchHuaweiResources('elb')
}

const syncData = async () => {
  try {
    ElMessage.info('正在同步数据...')
    await cloudStore.syncResources('huawei')
    ElMessage.success('数据同步完成')
    refreshData()
  } catch (error) {
    ElMessage.error('数据同步失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.fetchHuaweiResources('elb')
})
</script>

<style lang="scss" scoped>
.huawei-elb {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .elb-name {
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
