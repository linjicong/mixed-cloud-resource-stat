<template>
  <div class="aliyun-dns">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Link /></el-icon>
          阿里云DNS解析
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
                v-model="searchForm.domain"
                placeholder="搜索域名"
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
                v-model="searchForm.versionCode"
                placeholder="选择版本"
                clearable
                @change="handleSearch"
              >
                <el-option label="免费版" value="FREE" />
                <el-option label="企业版" value="ENTERPRISE" />
                <el-option label="旗舰版" value="ULTIMATE" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-select
                v-model="searchForm.domainStatus"
                placeholder="选择状态"
                clearable
                @change="handleSearch"
              >
                <el-option label="正常" value="ENABLE" />
                <el-option label="暂停" value="PAUSE" />
                <el-option label="锁定" value="LOCK" />
              </el-select>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 数据表格 -->
        <el-table 
          :data="filteredDnsList" 
          v-loading="loading"
          class="resource-table"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="domainName" label="域名" width="250">
            <template #default="{ row }">
              <div class="domain-name">
                <el-icon><Link /></el-icon>
                {{ row.domainName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="versionCode" label="版本" width="100">
            <template #default="{ row }">
              <el-tag :type="getVersionType(row.versionCode)" class="version-tag">
                {{ getVersionText(row.versionCode) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="domainStatus" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusType(row.domainStatus)" class="status-tag">
                {{ getStatusText(row.domainStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="recordCount" label="解析记录数" width="120" />
          <el-table-column prop="groupName" label="域名分组" width="150" />
          <el-table-column prop="createTime" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="expireTime" label="到期时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.expireTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" size="small" @click="viewDetail(row)">
                详情
              </el-button>
              <el-button type="warning" size="small" @click="viewRecords(row)">
                解析记录
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
      title="DNS域名详情"
      width="800px"
    >
      <div v-if="selectedDns" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="域名">{{ selectedDns.domainName }}</el-descriptions-item>
          <el-descriptions-item label="版本">
            <el-tag :type="getVersionType(selectedDns.versionCode)">
              {{ getVersionText(selectedDns.versionCode) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedDns.domainStatus)">
              {{ getStatusText(selectedDns.domainStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="解析记录数">{{ selectedDns.recordCount }}</el-descriptions-item>
          <el-descriptions-item label="域名分组">{{ selectedDns.groupName }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDate(selectedDns.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="到期时间">{{ formatDate(selectedDns.expireTime) }}</el-descriptions-item>
          <el-descriptions-item label="备注">{{ selectedDns.remark }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="detail-section">
          <h4>域名服务器</h4>
          <el-tag 
            v-for="ns in selectedDns.dnsServers" 
            :key="ns"
            class="ns-tag"
          >
            {{ ns }}
          </el-tag>
        </div>
        
        <div class="detail-section">
          <h4>标签</h4>
          <el-tag 
            v-for="tag in selectedDns.tags" 
            :key="tag"
            type="info"
            class="tag-item"
          >
            {{ tag }}
          </el-tag>
        </div>
      </div>
    </el-dialog>

    <!-- 解析记录对话框 -->
    <el-dialog 
      v-model="recordsVisible" 
      title="DNS解析记录"
      width="1000px"
    >
      <div v-if="selectedDns" class="records-content">
        <div class="records-header">
          <h4>域名: {{ selectedDns.domainName }}</h4>
          <el-button type="primary" size="small" @click="refreshRecords">
            <el-icon><Refresh /></el-icon>
            刷新记录
          </el-button>
        </div>
        
        <el-table 
          :data="dnsRecords" 
          v-loading="recordsLoading"
          size="small"
          stripe
        >
          <el-table-column prop="rr" label="主机记录" width="120" />
          <el-table-column prop="type" label="记录类型" width="100">
            <template #default="{ row }">
              <el-tag :type="getRecordTypeColor(row.type)" size="small">
                {{ row.type }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="value" label="记录值" width="200" />
          <el-table-column prop="ttl" label="TTL" width="80" />
          <el-table-column prop="line" label="解析线路" width="120" />
          <el-table-column prop="status" label="状态" width="80">
            <template #default="{ row }">
              <el-tag :type="row.status === 'ENABLE' ? 'success' : 'warning'" size="small">
                {{ row.status === 'ENABLE' ? '启用' : '暂停' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" size="small" @click="editRecord(row)">
                编辑
              </el-button>
            </template>
          </el-table-column>
        </el-table>
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
const { aliyunResources, loading } = cloudStore

// 搜索表单
const searchForm = reactive({
  domain: '',
  versionCode: '',
  domainStatus: ''
})

// 分页
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 详情对话框
const detailVisible = ref(false)
const selectedDns = ref(null)

// 解析记录对话框
const recordsVisible = ref(false)
const dnsRecords = ref([])
const recordsLoading = ref(false)

// 选中的行
const selectedRows = ref([])

// 计算属性
const dnsList = computed(() => aliyunResources.dns || [])

const filteredDnsList = computed(() => {
  let filtered = dnsList.value

  if (searchForm.domain) {
    filtered = filtered.filter(dns => 
      dns.domainName.toLowerCase().includes(searchForm.domain.toLowerCase())
    )
  }

  if (searchForm.versionCode) {
    filtered = filtered.filter(dns => dns.versionCode === searchForm.versionCode)
  }

  if (searchForm.domainStatus) {
    filtered = filtered.filter(dns => dns.domainStatus === searchForm.domainStatus)
  }

  total.value = filtered.length
  
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  
  return filtered.slice(start, end)
})

// 方法
const getVersionType = (version) => {
  const versionMap = {
    'FREE': 'info',
    'ENTERPRISE': 'success',
    'ULTIMATE': 'warning'
  }
  return versionMap[version] || 'info'
}

const getVersionText = (version) => {
  const versionMap = {
    'FREE': '免费版',
    'ENTERPRISE': '企业版',
    'ULTIMATE': '旗舰版'
  }
  return versionMap[version] || version
}

const getStatusType = (status) => {
  const statusMap = {
    'ENABLE': 'success',
    'PAUSE': 'warning',
    'LOCK': 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status) => {
  const statusMap = {
    'ENABLE': '正常',
    'PAUSE': '暂停',
    'LOCK': '锁定'
  }
  return statusMap[status] || status
}

const getRecordTypeColor = (type) => {
  const typeMap = {
    'A': 'success',
    'AAAA': 'success',
    'CNAME': 'primary',
    'MX': 'warning',
    'TXT': 'info',
    'NS': 'danger'
  }
  return typeMap[type] || 'info'
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
    domain: '',
    versionCode: '',
    domainStatus: ''
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
  selectedDns.value = row
  detailVisible.value = true
}

const viewRecords = (row) => {
  selectedDns.value = row
  recordsVisible.value = true
  loadDnsRecords(row.domainName)
}

const loadDnsRecords = async (domainName) => {
  try {
    recordsLoading.value = true
    // 这里应该调用API获取解析记录
    // const response = await dnsApi.getRecords(domainName)
    // dnsRecords.value = response.data
    
    // 模拟数据
    dnsRecords.value = [
      {
        rr: '@',
        type: 'A',
        value: '192.168.1.1',
        ttl: 600,
        line: '默认',
        status: 'ENABLE',
        createTime: '2024-01-01 00:00:00'
      },
      {
        rr: 'www',
        type: 'CNAME',
        value: 'example.com',
        ttl: 600,
        line: '默认',
        status: 'ENABLE',
        createTime: '2024-01-01 00:00:00'
      }
    ]
  } catch (error) {
    ElMessage.error('获取解析记录失败')
  } finally {
    recordsLoading.value = false
  }
}

const refreshRecords = () => {
  if (selectedDns.value) {
    loadDnsRecords(selectedDns.value.domainName)
  }
}

const editRecord = (record) => {
  ElMessage.info('编辑解析记录功能开发中...')
}

const refreshData = () => {
  cloudStore.fetchAliyunResources('dns')
}

const syncData = async () => {
  try {
    ElMessage.info('正在同步数据...')
    await cloudStore.syncResources('aliyun')
    ElMessage.success('数据同步完成')
    refreshData()
  } catch (error) {
    ElMessage.error('数据同步失败')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.fetchAliyunResources('dns')
})
</script>

<style lang="scss" scoped>
.aliyun-dns {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }
  
  .domain-name {
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
      
      .ns-tag {
        margin-right: 8px;
        margin-bottom: 8px;
      }
      
      .tag-item {
        margin-right: 8px;
        margin-bottom: 8px;
      }
    }
  }
  
  .records-content {
    .records-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      
      h4 {
        margin: 0;
        color: #303133;
      }
    }
  }
}
</style>
