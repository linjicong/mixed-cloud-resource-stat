import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { cloudApi } from '@/apis/cloud'

export const useCloudStore = defineStore('cloud', () => {
  // 状态
  const cloudConfigs = ref([])
  const huaweiResources = ref({
    ecs: [],
    rds: [],
    elb: [],
    evs: [],
    vpc: [],
    bills: []
  })
  const tencentResources = ref({
    cvm: [],
    cdb: [],
    clb: [],
    cbs: [],
    bills: []
  })
  const aliyunResources = ref({
    dns: []
  })
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
      huaweiResources.value[resourceType] = response.data
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
      tencentResources.value[resourceType] = response.data
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
      aliyunResources.value[resourceType] = response.data
    } catch (error) {
      console.error(`获取阿里云${resourceType}资源失败:`, error)
    } finally {
      loading.value = false
    }
  }

  const refreshAllData = async () => {
    await Promise.all([
      fetchCloudConfigs(),
      fetchHuaweiResources('ecs'),
      fetchHuaweiResources('rds'),
      fetchHuaweiResources('elb'),
      fetchHuaweiResources('evs'),
      fetchHuaweiResources('vpc'),
      fetchTencentResources('cvm'),
      fetchTencentResources('cdb'),
      fetchTencentResources('clb'),
      fetchTencentResources('cbs'),
      fetchAliyunResources('dns')
    ])
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
