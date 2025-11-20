<template>
  <div class="huawei-evs">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Coin /></el-icon>
          华为云云硬盘
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
                placeholder="搜索云硬盘名称"
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
                <el-option label="正在使用" value="in-use" />
                <el-option label="错误" value="error" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.type"
                placeholder="选择类型"
                clearable
                @change="handleSearch"
              >
                <el-option label="SSD" value="SSD" />
                <el-option label="SAS" value="SAS" />
                <el-option label="SATA" value="SATA" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredEvsList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="name" label="云硬盘名称" width="200">
            <template #default="{ row }">
              <div class="evs-name">
                <el-icon><Coin /></el-icon>
                {{ row.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="云硬盘ID" width="200" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.status)" class="status-tag">
                {{ getStatusText(row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="size" label="容量(GB)" width="100" />
          <el-table-column prop="volumeType" label="类型" width="100" />
          <el-table-column prop="availabilityZone" label="可用区" width="120" />
          <el-table-column prop="bootable" label="可启动" width="80">
            <template #default="{ row }">
              <el-tag :type="row.bootable === 'true' ? 'success' : 'info'" size="small">
                {{ row.bootable === 'true' ? '是' : '否' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="encrypted" label="加密" width="80">
            <template #default="{ row }">
              <el-tag :type="row.encrypted ? 'success' : 'info'" size="small">
                {{ row.encrypted ? '是' : '否' }}
              </el-tag>
            </template>
          </el-table-column>
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
      title="云硬盘详情"
      width="800px"
    >
      <div v-if="selectedEvs" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="云硬盘名称">{{ selectedEvs.name }}</el-descriptions-item>
          <el-descriptions-item label="云硬盘ID">{{ selectedEvs.id }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedEvs.status)">
              {{ getStatusText(selectedEvs.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="容量">{{ selectedEvs.size }}GB</el-descriptions-item>
          <el-descriptions-item label="类型">{{ selectedEvs.volumeType }}</el-descriptions-item>
          <el-descriptions-item label="可用区">{{ selectedEvs.availabilityZone }}</el-descriptions-item>
          <el-descriptions-item label="可启动">
            <el-tag :type="selectedEvs.bootable === 'true' ? 'success' : 'info'">
              {{ selectedEvs.bootable === 'true' ? '是' : '否' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="加密">
            <el-tag :type="selectedEvs.encrypted ? 'success' : 'info'">
              {{ selectedEvs.encrypted ? '是' : '否' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedEvs.createdAt) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDate(selectedEvs.updatedAt) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>挂载信息</h4>
          <el-table :data="selectedEvs.attachments" size="small">
            <el-table-column prop="serverId" label="服务器ID" />
            <el-table-column prop="device" label="设备" />
            <el-table-column prop="hostName" label="主机名" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedEvs.tags" 
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
  type: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedEvs = ref(null)

// 计算属性
const evsList = computed(() => huaweiResources.evs || [])

const filteredEvsList = computed(() => {
  let filtered = evsList.value

  if (searchForm.name) {
    filtered = filtered.filter(evs => 
      evs.name.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(evs => evs.status === searchForm.status)
  }

  if (searchForm.type) {
    filtered = filtered.filter(evs => evs.volumeType === searchForm.type)
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
    'in-use': 'warning',
    'error': 'danger',
    'creating': 'info',
    'deleting': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'available': '可用',
    'in-use': '正在使用',
    'error': '错误',
    'creating': '创建中',
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
    type: ''
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
  selectedEvs.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchHuaweiResources('evs')
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
  cloudStore.fetchHuaweiResources('evs')
})
</script>

<style lang="scss" scoped>
.huawei-evs {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .evs-name {
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
