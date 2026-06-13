<template>
  <div class="account-selector">
    <el-select
      v-model="selectedValue"
      placeholder="选择账号（默认全部）"
      clearable
      filterable
      size="default"
      style="width: 220px"
      @change="handleChange"
    >
      <el-option label="全部账号" value="" />
      <el-option-group
        v-for="(configs, provider) in configsByProvider"
        :key="provider"
        :label="getProviderLabel(provider)"
      >
        <el-option
          v-for="config in configs"
          :key="config.confName"
          :label="config.confName"
          :value="config.confName"
        >
          <span>{{ config.confName }}</span>
          <span class="option-region">{{ config.confRegion }}</span>
        </el-option>
      </el-option-group>
    </el-select>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useCloudStore } from '@/stores/cloud'

const cloudStore = useCloudStore()

const selectedValue = ref('')

const configsByProvider = computed(() => cloudStore.configsByProvider)

const getProviderLabel = (provider) => {
  const map = { huawei: '华为云', tencent: '腾讯云', aliyun: '阿里云' }
  return map[provider] || provider
}

const handleChange = (value) => {
  cloudStore.setSelectedConfName(value || '')
}

onMounted(() => {
  if (cloudStore.cloudConfigs.length === 0) {
    cloudStore.fetchCloudConfigs()
  }
})
</script>

<style lang="scss" scoped>
.account-selector {
  display: inline-flex;
  align-items: center;
}

.option-region {
  float: right;
  color: #909399;
  font-size: 12px;
}
</style>
