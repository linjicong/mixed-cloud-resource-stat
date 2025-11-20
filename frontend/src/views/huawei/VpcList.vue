<template>
  <div class="huawei-vpc">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Connection /></el-icon>
          华为云VPC网络
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
                placeholder="搜索VPC名称"
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
                <el-option label="错误" value="error" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredVpcList" 
          v-loading="loading"
          class="resource-table"
          stripe
        >
          <el-table-column prop="name" label="VPC名称" width="200">
            <template #default="{ row }">
              <div class="vpc-name">
                <el-icon><Connection /></el-icon>
                {{ row.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="VPC ID" width="200" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.status)" class="status-tag">
                {{ getStatusText(row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="cidr" label="CIDR" width="120" />
          <el-table-column prop="projectId" label="项目ID" width="150" />
          <el-table-column prop="enterpriseProjectId" label="企业项目ID" width="150" />
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
      title="VPC详情"
      width="800px"
    >
      <div v-if="selectedVpc" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="VPC名称">{{ selectedVpc.name }}</el-descriptions-item>
          <el-descriptions-item label="VPC ID">{{ selectedVpc.id }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedVpc.status)">
              {{ getStatusText(selectedVpc.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="CIDR">{{ selectedVpc.cidr }}</el-descriptions-item>
          <el-descriptions-item label="项目ID">{{ selectedVpc.projectId }}</el-descriptions-item>
          <el-descriptions-item label="企业项目ID">{{ selectedVpc.enterpriseProjectId }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedVpc.createdAt) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDate(selectedVpc.updatedAt) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>扩展CIDR</h4>
          <el-tag 
            v-for="cidr in selectedVpc.extendCidrs" 
            :key="cidr"
            class="cidr-tag"
          >
            {{ cidr }}
          </el-tag>
        </div>
        
        <div class="detail-section">
          <h4>云资源</h4>
          <el-table :data="selectedVpc.cloudResources" size="small">
            <el-table-column prop="id" label="资源ID" />
            <el-table-column prop="type" label="资源类型" />
            <el-table-column prop="name" label="资源名称" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedVpc.tags" 
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
const selectedVpc = ref(null)

// 计算属性
const vpcList = computed(() => huaweiResources.vpc || [])

const filteredVpcList = computed(() => {
  let filtered = vpcList.value

  if (searchForm.name) {
    filtered = filtered.filter(vpc => 
      vpc.name.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(vpc => vpc.status === searchForm.status)
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
    'error': 'danger',
    'creating': 'info',
    'deleting': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'available': '可用',
    'unavailable': '不可用',
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
  selectedVpc.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchHuaweiResources('vpc')
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
  cloudStore.fetchHuaweiResources('vpc')
})
</script>

<style lang="scss" scoped>
.huawei-vpc {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .vpc-name {
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
      
      .cidr-tag {
        margin-right: 8px;
        margin-bottom: 8px;
      }
      
      .tag-item {
        margin-right: 8px;
        margin-bottom: 8px;
      }
    }
  }
}
</style>
