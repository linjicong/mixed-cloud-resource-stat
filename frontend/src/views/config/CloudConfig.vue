<template>
  <div class="cloud-config">
    <div class="resource-card">
      <div class="card-header">
        <div class="title">
          <el-icon><Setting /></el-icon>
          云厂商配置管理
        </div>
        <div class="actions">
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon>
            添加配置
          </el-button>
          <el-button @click="refreshConfigs">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </div>
      
      <div class="card-content">
        <el-table 
          :data="cloudConfigs" 
          v-loading="loading"
          class="config-table"
          stripe
        >
          <el-table-column prop="name" label="配置名称" width="150" />
          <el-table-column prop="provider" label="云厂商" width="120">
            <template #default="{ row }">
              <el-tag :type="getProviderType(row.provider)">
                {{ getProviderName(row.provider) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="region" label="区域" width="120" />
          <el-table-column prop="accessKey" label="Access Key" width="200">
            <template #default="{ row }">
              {{ maskAccessKey(row.accessKey) }}
            </template>
          </el-table-column>
          <el-table-column prop="enable" label="状态" width="80">
            <template #default="{ row }">
              <el-switch 
                v-model="row.enable" 
                @change="toggleEnable(row)"
                :loading="row.updating"
              />
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" />
          <el-table-column prop="createTime" label="创建时间" width="160">
            <template #default="{ row }">
              {{ formatDate(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template #default="{ row }">
              <el-button 
                type="primary" 
                size="small" 
                @click="editConfig(row)"
                :icon="Edit"
              >
                编辑
              </el-button>
              <el-button 
                type="danger" 
                size="small" 
                @click="deleteConfig(row)"
                :icon="Delete"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 添加/编辑配置对话框 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="isEdit ? '编辑配置' : '添加配置'"
      width="600px"
      @close="resetForm"
    >
      <el-form 
        ref="formRef"
        :model="form" 
        :rules="rules" 
        label-width="100px"
      >
        <el-form-item label="配置名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入配置名称" />
        </el-form-item>
        
        <el-form-item label="云厂商" prop="provider">
          <el-select v-model="form.provider" placeholder="请选择云厂商" style="width: 100%">
            <el-option label="华为云" value="HCloud" />
            <el-option label="腾讯云" value="QCloud" />
            <el-option label="阿里云" value="ACloud" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="区域" prop="region">
          <el-input v-model="form.region" placeholder="请输入区域" />
        </el-form-item>
        
        <el-form-item label="Access Key" prop="accessKey">
          <el-input 
            v-model="form.accessKey" 
            placeholder="请输入Access Key"
            show-password
          />
        </el-form-item>
        
        <el-form-item label="Secret Key" prop="secretKey">
          <el-input 
            v-model="form.secretKey" 
            placeholder="请输入Secret Key"
            show-password
          />
        </el-form-item>
        
        <el-form-item label="是否启用" prop="enable">
          <el-switch v-model="form.enable" />
        </el-form-item>
        
        <el-form-item label="备注" prop="remark">
          <el-input 
            v-model="form.remark" 
            type="textarea" 
            :rows="3"
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitting">
          {{ isEdit ? '更新' : '添加' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useCloudStore } from '@/stores/cloud'
import dayjs from 'dayjs'

const cloudStore = useCloudStore()
const { cloudConfigs, loading } = cloudStore

// 对话框状态
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref()

// 表单数据
const form = reactive({
  name: '',
  provider: '',
  region: '',
  accessKey: '',
  secretKey: '',
  enable: true,
  remark: ''
})

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入配置名称', trigger: 'blur' }
  ],
  provider: [
    { required: true, message: '请选择云厂商', trigger: 'change' }
  ],
  region: [
    { required: true, message: '请输入区域', trigger: 'blur' }
  ],
  accessKey: [
    { required: true, message: '请输入Access Key', trigger: 'blur' }
  ],
  secretKey: [
    { required: true, message: '请输入Secret Key', trigger: 'blur' }
  ]
}

// 获取云厂商类型
const getProviderType = (provider) => {
  const typeMap = {
    'HCloud': 'danger',
    'QCloud': 'primary',
    'ACloud': 'success'
  }
  return typeMap[provider] || 'info'
}

// 获取云厂商名称
const getProviderName = (provider) => {
  const nameMap = {
    'HCloud': '华为云',
    'QCloud': '腾讯云',
    'ACloud': '阿里云'
  }
  return nameMap[provider] || provider
}

// 掩码Access Key
const maskAccessKey = (accessKey) => {
  if (!accessKey) return '-'
  if (typeof accessKey !== 'string') return '-'
  if (accessKey.length <= 8) return accessKey
  return accessKey.substring(0, 4) + '****' + accessKey.substring(accessKey.length - 4)
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return '-'
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

// 显示添加对话框
const showAddDialog = () => {
  isEdit.value = false
  dialogVisible.value = true
}

// 编辑配置
const editConfig = (row) => {
  isEdit.value = true
  Object.assign(form, {
    pk: row.pk,
    name: row.name,
    provider: row.provider,
    region: row.region,
    accessKey: row.accessKey,
    secretKey: row.secretKey,
    enable: row.enable,
    remark: row.remark
  })
  dialogVisible.value = true
}

// 删除配置
const deleteConfig = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除配置 "${row.name}" 吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    await cloudStore.deleteCloudConfig(row.pk)
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 切换启用状态
const toggleEnable = async (row) => {
  try {
    row.updating = true
    await cloudStore.updateCloudConfig(row.pk, { enable: row.enable })
    ElMessage.success('状态更新成功')
  } catch (error) {
    row.enable = !row.enable // 回滚状态
    ElMessage.error('状态更新失败')
  } finally {
    row.updating = false
  }
}

// 提交表单
const submitForm = async () => {
  try {
    await formRef.value.validate()
    submitting.value = true
    
    if (isEdit.value) {
      await cloudStore.updateCloudConfig(form.pk, form)
      ElMessage.success('更新成功')
    } else {
      await cloudStore.addCloudConfig(form)
      ElMessage.success('添加成功')
    }
    
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
  } finally {
    submitting.value = false
  }
}

// 重置表单
const resetForm = () => {
  Object.assign(form, {
    name: '',
    provider: '',
    region: '',
    accessKey: '',
    secretKey: '',
    enable: true,
    remark: ''
  })
  formRef.value?.resetFields()
}

// 刷新配置列表
const refreshConfigs = () => {
  cloudStore.fetchCloudConfigs()
}

// 组件挂载时获取数据
onMounted(() => {
  cloudStore.fetchCloudConfigs()
})
</script>

<style lang="scss" scoped>
.cloud-config {
  .config-table {
    .el-table {
      border-radius: 8px;
    }
  }
}
</style>
