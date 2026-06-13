<template>
  <div class="resource-table-wrapper">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Platform /></el-icon>
          {{ title }}
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
        <div class="filter-bar">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-input
                v-model="searchKeyword"
                placeholder="搜索名称/ID"
                clearable
                @input="handleSearch"
              >
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :xs="24" :sm="12" :md="8" :lg="6">
              <el-button @click="resetSearch">重置</el-button>
            </el-col>
          </el-row>
        </div>

        <el-table
          :data="paginatedData"
          v-loading="loading"
          class="resource-table"
          stripe
          @row-dblclick="viewDetail"
        >
          <el-table-column
            v-for="col in columns"
            :key="col.prop"
            :prop="col.prop"
            :label="col.label"
            :width="col.width || 'auto'"
            show-overflow-tooltip
          >
            <template #default="{ row }">
              <!-- 状态列 -->
              <template v-if="col.type === 'status'">
                <el-tag :type="getStatusType(row[col.prop])" class="status-tag">
                  {{ row[col.prop] || '-' }}
                </el-tag>
              </template>
              <!-- 日期列 -->
              <template v-else-if="col.type === 'date'">
                {{ formatDate(row[col.prop]) }}
              </template>
              <!-- IP列 -->
              <template v-else-if="col.type === 'ip'">
                {{ formatIp(row[col.prop]) }}
              </template>
              <!-- 自定义格式化 -->
              <template v-else-if="col.formatter">
                {{ col.formatter(row[col.prop], row) }}
              </template>
              <!-- 默认显示 -->
              <template v-else>
                {{ row[col.prop] ?? '-' }}
              </template>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" size="small" @click="viewDetail(row)">
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>

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
      :title="`${title} - 详情`"
      width="800px"
    >
      <div v-if="selectedRow" class="detail-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item
            v-for="col in columns"
            :key="col.prop"
            :label="col.label"
          >
            <template v-if="col.type === 'status'">
              <el-tag :type="getStatusType(selectedRow[col.prop])">
                {{ selectedRow[col.prop] || '-' }}
              </el-tag>
            </template>
            <template v-else-if="col.type === 'date'">
              {{ formatDate(selectedRow[col.prop]) }}
            </template>
            <template v-else-if="col.type === 'ip'">
              {{ formatIp(selectedRow[col.prop]) }}
            </template>
            <template v-else-if="col.formatter">
              {{ col.formatter(selectedRow[col.prop], selectedRow) }}
            </template>
            <template v-else>
              {{ selectedRow[col.prop] ?? '-' }}
            </template>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useCloudStore } from '@/stores/cloud'
import dayjs from 'dayjs'

const props = defineProps({
  provider: {
    type: String,
    required: true,
    validator: (v) => ['huawei', 'tencent', 'aliyun'].includes(v)
  },
  resourceType: {
    type: String,
    required: true
  },
  title: {
    type: String,
    required: true
  },
  columns: {
    type: Array,
    required: true
  }
})

const cloudStore = useCloudStore()

const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)
const detailVisible = ref(false)
const selectedRow = ref(null)

const loading = computed(() => cloudStore.loading)

const providerKey = computed(() => {
  const map = { huawei: 'huaweiResources', tencent: 'tencentResources', aliyun: 'aliyunResources' }
  return map[props.provider]
})

const resourceList = computed(() => {
  const storeData = cloudStore[providerKey.value]
  return storeData?.[props.resourceType] || []
})

const searchField = computed(() => {
  if (props.columns.length === 0) return ''
  const nameCol = props.columns.find(c =>
    /name/i.test(c.prop) || /Name/i.test(c.label)
  )
  return nameCol ? nameCol.prop : props.columns[0].prop
})

const filteredData = computed(() => {
  let data = resourceList.value
  if (searchKeyword.value && searchField.value) {
    const keyword = searchKeyword.value.toLowerCase()
    data = data.filter(row => {
      const val = row[searchField.value]
      return val && String(val).toLowerCase().includes(keyword)
    })
  }
  return data
})

const paginatedData = computed(() => {
  total.value = filteredData.value.length
  const start = (currentPage.value - 1) * pageSize.value
  return filteredData.value.slice(start, start + pageSize.value)
})

const getStatusType = (status) => {
  if (!status) return 'info'
  const s = String(status).toUpperCase()
  const map = {
    'RUNNING': 'success', 'ACTIVE': 'success', 'AVAILABLE': 'success', 'INUSE': 'success', 'BIND': 'success', 'ACTIVE_RUNNING': 'success',
    'STOPPED': 'warning', 'SHUTOFF': 'warning', 'DISABLE': 'warning', 'UNAVAILABLE': 'warning',
    'ERROR': 'danger', 'FAILED': 'danger', 'DELETED': 'danger', 'TERMINATED': 'danger',
    'PENDING': 'info', 'BUILD': 'info', 'CREATING': 'info', 'STARTING': 'info',
  }
  return map[s] || 'info'
}

const formatDate = (date) => {
  if (!date) return '-'
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

const formatIp = (ip) => {
  if (!ip) return '-'
  if (Array.isArray(ip)) return ip.join(', ') || '-'
  return String(ip)
}

const handleSearch = () => {
  currentPage.value = 1
}

const resetSearch = () => {
  searchKeyword.value = ''
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
  selectedRow.value = row
  detailVisible.value = true
}

const fetchMethodName = computed(() => {
  const map = { huawei: 'fetchHuaweiResources', tencent: 'fetchTencentResources', aliyun: 'fetchAliyunResources' }
  return map[props.provider]
})

const refreshData = () => {
  cloudStore[fetchMethodName.value](props.resourceType)
}

const syncData = async () => {
  try {
    ElMessage.info('正在同步数据...')
    await cloudStore.syncResources(props.provider)
    ElMessage.success('数据同步完成')
    refreshData()
  } catch (error) {
    ElMessage.error('数据同步失败')
  }
}

onMounted(() => {
  cloudStore[fetchMethodName.value](props.resourceType)
})
</script>

<style lang="scss" scoped>
.resource-table-wrapper {
  .filter-bar {
    margin-bottom: 20px;
    padding: 16px;
    background: #f8f9fa;
    border-radius: 8px;
  }

  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }

  .detail-content {
    max-height: 60vh;
    overflow-y: auto;
  }
}
</style>
