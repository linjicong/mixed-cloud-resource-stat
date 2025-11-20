<template>
  <div class="tencent-cvm">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Platform /></el-icon>
          腾讯云CVM实例
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
                <el-option label="运行中" value="RUNNING" />
                <el-option label="已停止" value="STOPPED" />
                <el-option label="启动中" value="STARTING" />
                <el-option label="停止中" value="STOPPING" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.region"
                placeholder="选择地域"
                clearable
                @change="handleSearch"
              >
                <el-option
                  v-for="region in availableRegions"
                  :key="region"
                  :label="region"
                  :value="region"
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
          :data="filteredCvmList" 
          v-loading="loading"
          class="resource-table"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="instanceName" label="实例名称" width="200">
            <template #default="{ row }">
              <div class="instance-name">
                <el-icon><Monitor /></el-icon>
                {{ row.instanceName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="instanceId" label="实例ID" width="200" />
          <el-table-column prop="instanceState" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.instanceState)" class="status-tag">
                {{ getStatusText(row.instanceState) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="regionName" label="地域" width="120" />
          <el-table-column prop="zoneName" label="可用区" width="120" />
          <el-table-column prop="instanceType" label="实例类型" width="150" />
          <el-table-column prop="cpu" label="CPU" width="80" />
          <el-table-column prop="memory" label="内存(GB)" width="100" />
          <el-table-column prop="osName" label="操作系统" width="150" />
          <el-table-column prop="createdTime" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createdTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="publicIpAddresses" label="公网IP" width="120">
            <template #default="{ row }">
              <div v-if="row.publicIpAddresses && row.publicIpAddresses.length > 0">
                {{ row.publicIpAddresses[0] }}
              </div>
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
      title="CVM实例详情"
      width="800px"
    >
      <div v-if="selectedCvm" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="实例名称">{{ selectedCvm.instanceName }}</el-descriptions-item>
          <el-descriptions-item label="实例ID">{{ selectedCvm.instanceId }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedCvm.instanceState)">
              {{ getStatusText(selectedCvm.instanceState) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="地域">{{ selectedCvm.regionName }}</el-descriptions-item>
          <el-descriptions-item label="可用区">{{ selectedCvm.zoneName }}</el-descriptions-item>
          <el-descriptions-item label="实例类型">{{ selectedCvm.instanceType }}</el-descriptions-item>
          <el-descriptions-item label="CPU">{{ selectedCvm.cpu }}核</el-descriptions-item>
          <el-descriptions-item label="内存">{{ selectedCvm.memory }}GB</el-descriptions-item>
          <el-descriptions-item label="操作系统">{{ selectedCvm.osName }}</el-descriptions-item>
          <el-descriptions-item label="付费方式">{{ selectedCvm.instanceChargeType }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedCvm.createdTime) }}</el-descriptions-item>
          <el-descriptions-item label="过期时间">{{ formatDate(selectedCvm.expiredTime) }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>网络信息</h4>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="VPC ID">
              {{ selectedCvm.virtualPrivateCloud?.vpcId }}
            </el-descriptions-item>
            <el-descriptions-item label="子网ID">
              {{ selectedCvm.virtualPrivateCloud?.subnetId }}
            </el-descriptions-item>
            <el-descriptions-item label="私网IP">
              <div v-for="ip in selectedCvm.privateIpAddresses" :key="ip">
                {{ ip }}
              </div>
            </el-descriptions-item>
            <el-descriptions-item label="公网IP">
              <div v-for="ip in selectedCvm.publicIpAddresses" :key="ip">
                {{ ip }}
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </div>
        
        <div class="detail-section">
          <h4>磁盘信息</h4>
          <el-table :data="selectedCvm.dataDisks" size="small">
            <el-table-column prop="diskType" label="磁盘类型" />
            <el-table-column prop="diskSize" label="磁盘大小(GB)" />
            <el-table-column prop="diskId" label="磁盘ID" />
          </el-table>
        </div>
        
        <div class="detail-section">
          <h4>安全组</h4>
          <el-tag 
            v-for="sg in selectedCvm.securityGroupIds" 
            :key="sg"
            class="security-group-tag"
          >
            {{ sg }}
          </el-tag>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedCvm.tags" 
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
  region: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedCvm = ref(null)

// 选中的行
const selectedRows = ref([])

// 计算属性
const cvmList = computed(() => tencentResources.cvm || [])

const availableRegions = computed(() => {
  const regions = new Set()
  cvmList.value.forEach(cvm => {
    if (cvm.regionName) {
      regions.add(cvm.regionName)
    }
  })
  return Array.from(regions)
})

const filteredCvmList = computed(() => {
  let filtered = cvmList.value

  if (searchForm.name) {
    filtered = filtered.filter(cvm => 
      cvm.instanceName.toLowerCase().includes(searchForm.name.toLowerCase())
    )
  }

  if (searchForm.status) {
    filtered = filtered.filter(cvm => cvm.instanceState === searchForm.status)
  }

  if (searchForm.region) {
    filtered = filtered.filter(cvm => cvm.regionName === searchForm.region)
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getStatusType = (status) => {
  const statusMap = {
    'RUNNING': 'success',
    'STOPPED': 'warning',
    'STARTING': 'info',
    'STOPPING': 'warning',
    'REBOOTING': 'info',
    'TERMINATING': 'danger',
    'TERMINATED': 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'RUNNING': '运行中',
    'STOPPED': '已停止',
    'STARTING': '启动中',
    'STOPPING': '停止中',
    'REBOOTING': '重启中',
    'TERMINATING': '销毁中',
    'TERMINATED': '已销毁'
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
    region: ''
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
  selectedCvm.value = row
  detailVisible.value = true
}

const viewMonitor = (row) => {
  ElMessage.info('监控功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchTencentResources('cvm')
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
  cloudStore.fetchTencentResources('cvm')
})
</script>

<style lang="scss" scoped>
.tencent-cvm {
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
