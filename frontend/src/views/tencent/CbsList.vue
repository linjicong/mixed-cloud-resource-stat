<template>
  <div class="tencent-cbs">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Coin /></el-icon>
          腾讯云云硬盘
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
                <el-option label="待挂载" value="UNATTACHED" />
                <el-option label="已挂载" value="ATTACHED" />
                <el-option label="挂载中" value="ATTACHING" />
                <el-option label="卸载中" value="DETACHING" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.type"
                placeholder="选择类型"
                clearable
                @change="handleSearch"
              >
                <el-option label="高性能云硬盘" value="CLOUD_PREMIUM" />
                <el-option label="SSD云硬盘" value="CLOUD_SSD" />
                <el-option label="增强型SSD云硬盘" value="CLOUD_HSSD" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredCbsList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="diskName" label="云硬盘名称" width="200">
            <template #default="{ row }">
              <div class="cbs-name">
                <el-icon><Coin /></el-icon>
                {{ row.diskName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="diskId" label="云硬盘ID" width="200" />
          <el-table-column prop="diskState" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.diskState)" class="status-tag">
                {{ getStatusText(row.diskState) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="diskSize" label="容量(GB)" width="100" />
          <el-table-column prop="diskType" label="类型" width="150" />
          <el-table-column prop="diskUsage" label="用途" width="100">
            <template #default="{ row }">
              <el-tag :type="row.diskUsage === 'SYSTEM_DISK' ? 'success' : 'info'" size="small">
                {{ row.diskUsage === 'SYSTEM_DISK' ? '系统盘' : '数据盘' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="instanceId" label="挂载实例" width="150" />
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
      title="云硬盘详情"
      width="800px"
    >
      <div v-if="selectedCbs" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="云硬盘名称">{{ selectedCbs.diskName }}</el-descriptions-item>
          <el-descriptions-item label="云硬盘ID">{{ selectedCbs.diskId }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedCbs.diskState)">
              {{ getStatusText(selectedCbs.diskState) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="容量">{{ selectedCbs.diskSize }}GB</el-descriptions-item>
          <el-descriptions-item label="类型">{{ selectedCbs.diskType }}</el-descriptions-item>
          <el-descriptions-item label="用途">
            <el-tag :type="selectedCbs.diskUsage === 'SYSTEM_DISK' ? 'success' : 'info'">
              {{ selectedCbs.diskUsage === 'SYSTEM_DISK' ? '系统盘' : '数据盘' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="挂载实例">{{ selectedCbs.instanceId }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedCbs.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="到期时间">{{ formatDate(selectedCbs.deadlineTime) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedCbs.tags" 
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
  status: '',
  type: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedCbs = ref(null)

// 计算属性
const cbsList = computed(() => tencentResources.cbs || [])

const filteredCbsList = computed(() => {
  let filtered = cbsList.value

  if (searchForm.name) {
    filtered = filtered.filter(cbs => 
      cbs.diskName.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(cbs => cbs.diskState === searchForm.status)
  }

  if (searchForm.type) {
    filtered = filtered.filter(cbs => cbs.diskType === searchForm.type)
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    'UNATTACHED': 'warning',
    'ATTACHED': 'success',
    'ATTACHING': 'info',
    'DETACHING': 'warning',
    'EXPANDING': 'info',
    'ROLLBACKING': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'UNATTACHED': '待挂载',
    'ATTACHED': '已挂载',
    'ATTACHING': '挂载中',
    'DETACHING': '卸载中',
    'EXPANDING': '扩容中',
    'ROLLBACKING': '回滚中'
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
  selectedCbs.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchTencentResources('cbs')
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
  cloudStore.fetchTencentResources('cbs')
})
</script>

<style lang="scss" scoped>
.tencent-cbs {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .cbs-name {
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
