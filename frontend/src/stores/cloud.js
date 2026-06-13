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

  // 计算属性
  const totalResources = computed(() => {
    return {
      huawei: Object.values(huaweiResources.value).reduce((sum, arr) => sum + arr.length, 0),
      tencent: Object.values(tencentResources.value).reduce((sum, arr) => sum + arr.length, 0),
      aliyun: Object.values(aliyunResources.value).reduce((sum, arr) => sum + arr.length, 0)
    }
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
      const response = await cloudApi.getHuaweiResources(resourceType)
      // 动态初始化资源类型（使用展开运算符创建新对象触发响应式更新）
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
      const response = await cloudApi.getTencentResources(resourceType)
      // 动态初始化资源类型
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
      const response = await cloudApi.getAliyunResources(resourceType)
      // 动态初始化资源类型
      aliyunResources.value = { ...aliyunResources.value, [resourceType]: response.data }
    } catch (error) {
      console.error(`获取阿里云${resourceType}资源失败:`, error)
    } finally {
      loading.value = false
    }
  }

  const refreshAllData = async () => {
    await fetchCloudConfigs()
    // 资源类型按需加载，由各视图组件自行触发
  }

  return {
    // 状态
    cloudConfigs,
    huaweiResources,
    tencentResources,
    aliyunResources,
    loading,
    // 计算属性
    totalResources,
    // 操作
    fetchCloudConfigs,
    addCloudConfig,
    updateCloudConfig,
    deleteCloudConfig,
    fetchHuaweiResources,
    fetchTencentResources,
    fetchAliyunResources,
    refreshAllData
  }
})
