import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 直接返回响应数据
    return response
  },
  error => {
    // 处理错误响应
    let errorMessage = '请求失败'
    
    if (error.response) {
      // 服务器返回了错误状态码
      const status = error.response.status
      const data = error.response.data
      
      if (data && data.message) {
        errorMessage = data.message
      } else if (data && typeof data === 'string') {
        errorMessage = data
      } else {
        switch (status) {
          case 400:
            errorMessage = '请求参数错误'
            break
          case 401:
            errorMessage = '未授权，请重新登录'
            break
          case 403:
            errorMessage = '拒绝访问'
            break
          case 404:
            errorMessage = '请求的资源不存在'
            break
          case 500:
            errorMessage = '服务器内部错误'
            break
          default:
            errorMessage = `请求失败 (${status})`
        }
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应
      errorMessage = '网络错误，请检查网络连接'
    } else {
      // 其他错误
      errorMessage = error.message || '请求失败'
    }
    
    ElMessage.error(errorMessage)
    return Promise.reject(error)
  }
)

// 云配置相关API
export const cloudApi = {
  // 获取云配置列表
  getConfigs() {
    return request.get('/cloud-configs')
  },
  
  // 添加云配置
  addConfig(data) {
    return request.post('/cloud-configs', data)
  },
  
  // 更新云配置
  updateConfig(id, data) {
    return request.put(`/cloud-configs/${id}`, data)
  },
  
  // 删除云配置
  deleteConfig(id) {
    return request.delete(`/cloud-configs/${id}`)
  },
  
  // 获取华为云资源
  getHuaweiResources(type) {
    return request.get(`/huawei/${type}`)
  },
  
  // 获取腾讯云资源
  getTencentResources(type) {
    return request.get(`/tencent/${type}`)
  },
  
  // 获取阿里云资源
  getAliyunResources(type) {
    return request.get(`/aliyun/${type}`)
  },
  
  // 同步资源数据
  syncResources(provider) {
    return request.post(`/sync/${provider}`)
  },
  
  // 获取统计数据
  getStatistics() {
    return request.get('/statistics')
  }
}

export default request
