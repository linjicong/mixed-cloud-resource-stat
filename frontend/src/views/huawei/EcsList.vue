<template>
  <div class="huawei-ecs">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Platform /></el-icon>
          华为云ECS实例
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
                placeholder="搜索实例名称"
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
                <el-option label="运行中" value="ACTIVE" />
                <el-option label="已停止" value="SHUTOFF" />
                <el-option label="错误" value="ERROR" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.zone"
                placeholder="选择可用区"
                clearable
                @change="handleSearch"
              >
                <el-option
                  v-for="zone in availableZones"
                  :key="zone"
                  :label="zone"
                  :value="zone"
                />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredEcsList" 
          v-loading="loading"
          class="resource-table"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="name" label="实例名称" width="200">
            <template #default="{ row }">
              <div class="instance-name">
                <el-icon><Monitor /></el-icon>
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
          <el-table-column prop="osEXTAZAvailabilityZone" label="可用区" width="120" />
          <el-table-column prop="flavor" label="规格" width="150">
            <template #default="{ row }">
              <div v-if="row.flavor">
                {{ row.flavor.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="image" label="镜像" width="150">
            <template #default="{ row }">
              <div v-if="row.image">
                {{ row.image.name }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="created" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.created) }}
            </template>
          </el-table-column>
          <el-table-column prop="accessIPv4" label="公网IP" width="120" />
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
      title="ECS实例详情"
      width="800px"
    >
      <div v-if="selectedEcs" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="实例名称">{{ selectedEcs.name }}</el-descriptions-item>
          <el-descriptions-item label="实例ID">{{ selectedEcs.id }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedEcs.status)">
              {{ getStatusText(selectedEcs.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="可用区">{{ selectedEcs.osEXTAZAvailabilityZone }}</el-descriptions-item>
          <el-descriptions-item label="规格">{{ selectedEcs.flavor?.name }}</el-descriptions-item>
          <el-descriptions-item label="镜像">{{ selectedEcs.image?.name }}</el-descriptions-item>
          <el-descriptions-item label="公网IP">{{ selectedEcs.accessIPv4 }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedEcs.created) }}</el-descriptions-item>
          <el-descriptions-item label="描述" :span="2">{{ selectedEcs.description }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>安全组</h4>
          <el-tag 
            v-for="sg in selectedEcs.securityGroups" 
            :key="sg.id"
            class="security-group-tag"
          >
            {{ sg.name }}
          </el-tag>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedEcs.tags" 
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
  zone: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedEcs = ref(null)

// 选中的行
const selectedRows = ref([])

// 计算属性
const ecsList = computed(() => huaweiResources.ecs || [])

const availableZones = computed(() => {
  const zones = new Set()
  ecsList.value.forEach(ecs => {
    if (ecs.osEXTAZAvailabilityZone) {
      zones.add(ecs.osEXTAZAvailabilityZone)
    }
  })
  return Array.from(zones)
})

const filteredEcsList = computed(() => {
  let filtered = ecsList.value

  if (searchForm.name) {
    filtered = filtered.filter(ecs => 
      ecs.name.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(ecs => ecs.status === searchForm.status)
  }

  if (searchForm.zone) {
    filtered = filtered.filter(ecs => ecs.osEXTAZAvailabilityZone === searchForm.zone)
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    'ACTIVE': 'success',
    'SHUTOFF': 'warning',
    'ERROR': 'danger',
    'BUILD': 'info',
    'REBOOT': 'warning'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'ACTIVE': '运行中',
    'SHUTOFF': '已停止',
    'ERROR': '错误',
    'BUILD': '创建中',
    'REBOOT': '重启中'
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
    zone: ''
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

const handleSelectionChange = (selection) => {
  selectedRows.value = selection
}

const viewDetail = (row) => {
  selectedEcs.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchHuaweiResources('ecs')
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
  cloudStore.fetchHuaweiResources('ecs')
})
</script>

<style lang="scss" scoped>
.huawei-ecs {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .instance-name {
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
      
      .security-group-tag {
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
