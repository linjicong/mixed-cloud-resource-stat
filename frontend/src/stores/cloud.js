import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { cloudApi } from '@/apis/cloud'

export const useCloudStore = defineStore('cloud', () => {
  // 状态
  const cloudConfigs = ref([])
  const huaweiResources = ref({})
  const tencentResources = ref({})
  const aliyunResources = ref({})
  const loading = ref(false)

  // 账号筛选状态
  const selectedConfName = ref('')

  // 汇总统计数据
  const providerStats = ref(null)
  const crossCloudStats = ref(null)

  // 计算属性
  const totalResources = computed(() => {
    return {
      huawei: Object.values(huaweiResources.value).reduce((sum, arr) => sum + arr.length, 0),
      tencent: Object.values(tencentResources.value).reduce((sum, arr) => sum + arr.length, 0),
      aliyun: Object.values(aliyunResources.value).reduce((sum, arr) => sum + arr.length, 0)
    }
  })

  // 按云厂商分组的配置列表
  const configsByProvider = computed(() => {
    const result = { huawei: [], tencent: [], aliyun: [] }
    cloudConfigs.value.forEach(c => {
      const provider = c.confProvider?.toLowerCase()
      if (provider && result[provider]) {
        result[provider].push(c)
      }
    })
    return result
  })

  // 操作
  const fetchCloudConfigs = async () => {
    try {
      loading.value = true
      const response = await cloudApi.getConfigs()
      cloudConfigs.value = response.data
    } catch (error) {
      console.error('获取云配置失败:', error)
    } finally {
      loading.value = false
    }
  }

  const addCloudConfig = async (config) => {
    try {
      const response = await cloudApi.addConfig(config)
      cloudConfigs.value.push(response.data)
      return response.data
    } catch (error) {
      console.error('添加云配置失败:', error)
      throw error
    }
  }

  const updateCloudConfig = async (id, config) => {
    try {
      const response = await cloudApi.updateConfig(id, config)
      const index = cloudConfigs.value.findIndex(c => c.pk === id)
      if (index !== -1) {
        cloudConfigs.value[index] = response.data
      }
      return response.data
    } catch (error) {
      console.error('更新云配置失败:', error)
      throw error
    }
  }

  const deleteCloudConfig = async (id) => {
    try {
      await cloudApi.deleteConfig(id)
      cloudConfigs.value = cloudConfigs.value.filter(c => c.pk !== id)
    } catch (error) {
      console.error('删除云配置失败:', error)
      throw error
    }
  }

  const fetchHuaweiResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getHuaweiResources(resourceType, selectedConfName.value || undefined)
      huaweiResources.value = { ...huaweiResources.value, [resourceType]: response.data }
    } catch (error) {
      console.error(`获取华为云${resourceType}资源失败:`, error)
    } finally {
      loading.value = false
    }
  }

  const fetchTencentResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getTencentResources(resourceType, selectedConfName.value || undefined)
      tencentResources.value = { ...tencentResources.value, [resourceType]: response.data }
    } catch (error) {
      console.error(`获取腾讯云${resourceType}资源失败:`, error)
    } finally {
      loading.value = false
    }
  }

  const fetchAliyunResources = async (resourceType) => {
    try {
      loading.value = true
      const response = await cloudApi.getAliyunResources(resourceType, selectedConfName.value || undefined)
      aliyunResources.value = { ...aliyunResources.value, [resourceType]: response.data }
    } catch (error) {
      console.error(`获取阿里云${resourceType}资源失败:`, error)
    } finally {
      loading.value = false
    }
  }

  // 获取汇总统计
  const fetchProviderStats = async () => {
    try {
      const response = await cloudApi.getProviderStats(selectedConfName.value || undefined)
      providerStats.value = response.data
    } catch (error) {
      console.error('获取汇总统计失败:', error)
    }
  }

  // 获取跨云对比数据
  const fetchCrossCloudStats = async () => {
    try {
      const response = await cloudApi.getCrossCloudStats(selectedConfName.value || undefined)
      crossCloudStats.value = response.data
    } catch (error) {
      console.error('获取跨云对比数据失败:', error)
    }
  }

  // 设置选中的账号
  const setSelectedConfName = (confName) => {
    selectedConfName.value = confName
  }

  const refreshAllData = async () => {
    await fetchCloudConfigs()
  }

  return {
    // 状态
    cloudConfigs,
    huaweiResources,
    tencentResources,
    aliyunResources,
    loading,
    selectedConfName,
    providerStats,
    crossCloudStats,
    // 计算属性
    totalResources,
    configsByProvider,
    // 操作
    fetchCloudConfigs,
    addCloudConfig,
    updateCloudConfig,
    deleteCloudConfig,
    fetchHuaweiResources,
    fetchTencentResources,
    fetchAliyunResources,
    fetchProviderStats,
    fetchCrossCloudStats,
    setSelectedConfName,
    refreshAllData
  }
})
