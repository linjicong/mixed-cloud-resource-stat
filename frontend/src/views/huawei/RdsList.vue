<template>
  <div class="huawei-rds">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Database /></el-icon>
          华为云RDS数据库
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
                placeholder="搜索数据库名称"
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
                <el-option label="可用" value="available" />
                <el-option label="不可用" value="unavailable" />
                <el-option label="创建中" value="creating" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.engine"
                placeholder="选择引擎"
                clearable
                @change="handleSearch"
              >
                <el-option label="MySQL" value="MySQL" />
                <el-option label="PostgreSQL" value="PostgreSQL" />
                <el-option label="SQL Server" value="SQL Server" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredRdsList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="name" label="数据库名称" width="200">
            <template #default="{ row }">
              <div class="db-name">
                <el-icon><Database /></el-icon>
                {{ row.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="实例ID" width="200" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.status)" class="status-tag">
                {{ getStatusText(row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="datastore" label="数据库引擎" width="120">
            <template #default="{ row }">
              <div v-if="row.datastore">
                {{ row.datastore.type }} {{ row.datastore.version }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="flavorRef" label="规格" width="150" />
          <el-table-column prop="cpu" label="CPU" width="80" />
          <el-table-column prop="mem" label="内存" width="100" />
          <el-table-column prop="volume" label="存储" width="120">
            <template #default="{ row }">
              <div v-if="row.volume">
                {{ row.volume.size }}GB
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="port" label="端口" width="80" />
          <el-table-column prop="created" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.created) }}
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
      title="RDS实例详情"
      width="800px"
    >
      <div v-if="selectedRds" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="数据库名称">{{ selectedRds.name }}</el-descriptions-item>
          <el-descriptions-item label="实例ID">{{ selectedRds.id }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedRds.status)">
              {{ getStatusText(selectedRds.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="数据库引擎">
            {{ selectedRds.datastore?.type }} {{ selectedRds.datastore?.version }}
          </el-descriptions-item>
          <el-descriptions-item label="规格">{{ selectedRds.flavorRef }}</el-descriptions-item>
          <el-descriptions-item label="端口">{{ selectedRds.port }}</el-descriptions-item>
          <el-descriptions-item label="CPU">{{ selectedRds.cpu }}</el-descriptions-item>
          <el-descriptions-item label="内存">{{ selectedRds.mem }}</el-descriptions-item>
          <el-descriptions-item label="存储大小">
            {{ selectedRds.volume?.size }}GB
          </el-descriptions-item>
          <el-descriptions-item label="存储类型">
            {{ selectedRds.volume?.type }}
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedRds.created) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDate(selectedRds.updated) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>网络信息</h4>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="VPC ID">{{ selectedRds.vpcId }}</el-descriptions-item>
            <el-descriptions-item label="子网ID">{{ selectedRds.subnetId }}</el-descriptions-item>
            <el-descriptions-item label="安全组ID">{{ selectedRds.securityGroupId }}</el-descriptions-item>
            <el-descriptions-item label="私网IP">
              <div v-for="ip in selectedRds.privateIps" :key="ip">
                {{ ip }}
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedRds.tags" 
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
  status: '',
  engine: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedRds = ref(null)

// 计算属性
const rdsList = computed(() => huaweiResources.rds || [])

const filteredRdsList = computed(() => {
  let filtered = rdsList.value

  if (searchForm.name) {
    filtered = filtered.filter(rds => 
      rds.name.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(rds => rds.status === searchForm.status)
  }

  if (searchForm.engine) {
    filtered = filtered.filter(rds => 
      rds.datastore?.type === searchForm.engine
    )
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    'available': 'success',
    'unavailable': 'danger',
    'creating': 'info',
    'updating': 'warning',
    'deleting': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'available': '可用',
    'unavailable': '不可用',
    'creating': '创建中',
    'updating': '更新中',
    'deleting': '删除中'
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
    status: '',
    engine: ''
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
  selectedRds.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchHuaweiResources('rds')
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
  cloudStore.fetchHuaweiResources('rds')
})
</script>

<style lang="scss" scoped>
.huawei-rds {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .db-name {
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
