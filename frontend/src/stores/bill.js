/**
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import dayjs from 'dayjs'
import { billApi } from '@/apis/bill'

/** 默认账期：当前月往前 2 个月 ~ 当前月（近 3 个月，含当月） */
const defaultStartMonth = () => dayjs().subtract(2, 'month').format('YYYY-MM')
const defaultEndMonth = () => dayjs().format('YYYY-MM')

/** 明细内筛默认值 */
const defaultDetailFilter = () => ({
  productMain: '',
  chargingMode: '',
  pageNum: 1,
  pageSize: 20,
  sortField: 'payAmount',
  sortOrder: 'desc'
})

/**
 * 统一账单 store。
 *
 * 设计要点：
 * 1. 筛选条件（startMonth/endMonth/providers/accounts/trendMonths）是唯一真源，所有区块共享；
 * 2. 5 份响应数据 + 各自的 loading/error 三件套，保证某一块接口挂了不阻塞其它块；
 * 3. fetchAll 使用 Promise.allSettled（不是 Promise.all），部分失败仍渲染成功部分；
 * 4. 数组参数 join(',')，空数组传 undefined。
 */
export const useBillStore = defineStore('bill', () => {
  // ==================== 筛选条件（唯一真源） ====================
  const startMonth = ref(defaultStartMonth())
  const endMonth = ref(defaultEndMonth())
  const providers = ref([]) // [] = 全部
  const accounts = ref([]) // [] = 全部
  const trendMonths = ref(6)

  // ==================== 各区块独立状态 ====================
  const kpi = ref(null)
  const kpiLoading = ref(false)
  const kpiError = ref(null)

  const trend = ref(null)
  const trendLoading = ref(false)
  const trendError = ref(null)

  const accountsVO = ref(null)
  const accountsLoading = ref(false)
  const accountsError = ref(null)

  const detail = ref(null)
  const detailLoading = ref(false)
  const detailError = ref(null)

  const productTop = ref(null)
  const productTopLoading = ref(false)
  const productTopError = ref(null)

  // ==================== 区块内维度选择 ====================
  /** 占比图维度：provider / account */
  const shareDimension = ref('provider')
  /** 产品 TOP 维度：provider / account */
  const productDimension = ref('provider')
  /** 产品 TOP N */
  const productTopN = ref(10)

  // ==================== 明细内筛（与全局筛选解耦） ====================
  const detailFilter = ref(defaultDetailFilter())

  // ==================== 计算属性 ====================
  /** 公共查询参数（账期 + 厂商 + 账号） */
  const baseParams = computed(() => {
    const params = {
      startMonth: startMonth.value,
      endMonth: endMonth.value
    }
    if (providers.value.length > 0) {
      params.providers = providers.value.join(',')
    }
    if (accounts.value.length > 0) {
      params.accounts = accounts.value.join(',')
    }
    return params
  })

  /** 明细查询参数（公共参数 + 内筛） */
  const detailParams = computed(() => {
    const params = { ...baseParams.value }
    const filter = detailFilter.value
    if (filter.productMain) {
      params.productMain = filter.productMain
    }
    if (filter.chargingMode) {
      params.chargingMode = filter.chargingMode
    }
    params.pageNum = filter.pageNum
    params.pageSize = filter.pageSize
    params.sortField = filter.sortField
    params.sortOrder = filter.sortOrder
    return params
  })

  // ==================== 数据请求 ====================

  /** 拉取 KPI（独立失败） */
  async function fetchKpi() {
    kpiLoading.value = true
    kpiError.value = null
    try {
      const response = await billApi.getKpi(baseParams.value)
      kpi.value = response.data
    } catch (error) {
      kpiError.value = error?.response?.data?.message || error?.message || 'KPI 获取失败'
      kpi.value = null
    } finally {
      kpiLoading.value = false
    }
  }

  /** 拉取趋势（独立失败） */
  async function fetchTrend() {
    trendLoading.value = true
    trendError.value = null
    try {
      const params = { ...baseParams.value, months: trendMonths.value }
      const response = await billApi.getTrend(params)
      trend.value = response.data
    } catch (error) {
      trendError.value = error?.response?.data?.message || error?.message || '趋势获取失败'
      trend.value = null
    } finally {
      trendLoading.value = false
    }
  }

  /** 拉取账号维度（独立失败） */
  async function fetchAccounts() {
    accountsLoading.value = true
    accountsError.value = null
    try {
      const params = { ...baseParams.value, topN: 8 }
      const response = await billApi.getAccounts(params)
      accountsVO.value = response.data
    } catch (error) {
      accountsError.value = error?.response?.data?.message || error?.message || '账号数据获取失败'
      accountsVO.value = null
    } finally {
      accountsLoading.value = false
    }
  }

  /** 拉取明细分页（独立失败） */
  async function fetchDetail() {
    detailLoading.value = true
    detailError.value = null
    try {
      const response = await billApi.getDetails(detailParams.value)
      detail.value = response.data
    } catch (error) {
      detailError.value = error?.response?.data?.message || error?.message || '明细获取失败'
      detail.value = null
    } finally {
      detailLoading.value = false
    }
  }

  /** 拉取产品 TOP（独立失败） */
  async function fetchProductTop() {
    productTopLoading.value = true
    productTopError.value = null
    try {
      const params = {
        ...baseParams.value,
        topN: productTopN.value,
        dimension: productDimension.value
      }
      const response = await billApi.getProductTop(params)
      productTop.value = response.data
    } catch (error) {
      productTopError.value = error?.response?.data?.message || error?.message || '产品 TOP 获取失败'
      productTop.value = null
    } finally {
      productTopLoading.value = false
    }
  }

  /**
   * 并发拉取 5 个接口。
   * 使用 Promise.allSettled：任一接口失败不影响其它区块渲染（PRD 6.9 降级策略）。
   */
  async function fetchAll() {
    await Promise.allSettled([
      fetchKpi(),
      fetchTrend(),
      fetchAccounts(),
      fetchDetail(),
      fetchProductTop()
    ])
  }

  /** 仅刷新明细（排序 / 分页 / 内筛变化时用） */
  async function refreshDetail() {
    await fetchDetail()
  }

  // ==================== 筛选操作 ====================

  /**
   * 更新账期范围。
   * @param {[string,string]} range [startMonth, endMonth]
   */
  function setDateRange(range) {
    if (!Array.isArray(range) || range.length !== 2) return
    const [start, end] = range
    if (!start || !end) return
    startMonth.value = start <= end ? start : end
    endMonth.value = start <= end ? end : start
    resetDetailPage()
  }

  /**
   * 更新厂商筛选。
   * @param {string[]} list 厂商标识列表
   */
  function setProviders(list) {
    providers.value = Array.isArray(list) ? [...list] : []
    resetDetailPage()
  }

  /**
   * 更新账号筛选。
   * @param {string[]} list 账号名列表
   */
  function setAccounts(list) {
    accounts.value = Array.isArray(list) ? [...list] : []
    resetDetailPage()
  }

  /**
   * 更新趋势窗口。
   * @param {number} months 6 或 12
   */
  function setTrendMonths(months) {
    trendMonths.value = months === 12 ? 12 : 6
  }

  /** 明细筛选变化：回到第 1 页（PRD 验收：切换筛选回第 1 页） */
  function resetDetailPage() {
    detailFilter.value = { ...detailFilter.value, pageNum: 1 }
  }

  /**
   * 更新明细内筛。
   * @param {object} patch 需要覆盖的字段
   * @param {boolean} resetPage 是否回到第 1 页，默认 true
   */
  function patchDetailFilter(patch, resetPage = true) {
    detailFilter.value = {
      ...detailFilter.value,
      ...patch,
      pageNum: resetPage ? 1 : detailFilter.value.pageNum
    }
  }

  /** 重置全部筛选为默认值 */
  function resetFilters() {
    startMonth.value = defaultStartMonth()
    endMonth.value = defaultEndMonth()
    providers.value = []
    accounts.value = []
    trendMonths.value = 6
    shareDimension.value = 'provider'
    productDimension.value = 'provider'
    productTopN.value = 10
    detailFilter.value = defaultDetailFilter()
  }

  // ==================== 下钻联动 ====================

  /**
   * 趋势图点击某月 → 账期收窄为该单月。
   * @param {string} month YYYY-MM
   */
  function drillMonth(month) {
    if (!month) return
    startMonth.value = month
    endMonth.value = month
    resetDetailPage()
  }

  /**
   * 占比图 / 账号表点击某账号 → 账号筛选收敛为该账号。
   * @param {string} account 账号名
   */
  function drillAccount(account) {
    if (!account) return
    accounts.value = [account]
    resetDetailPage()
  }

  /**
   * 占比图点击某厂商 → 厂商筛选收敛为该厂商。
   * @param {string} provider 厂商标识或厂商中文名
   */
  function drillProvider(provider) {
    if (!provider) return
    const keyMap = { 华为云: 'huawei', 腾讯云: 'tencent', 阿里云: 'aliyun' }
    const key = keyMap[provider] || provider
    providers.value = [key]
    resetDetailPage()
  }

  /**
   * 产品 TOP 点击某产品 → 明细按该产品筛选。
   * @param {string} productMain 产品大类
   */
  function drillProduct(productMain) {
    if (!productMain) return
    patchDetailFilter({ productMain }, true)
  }

  /**
   * 清空明细内筛。
   */
  function clearDetailFilter() {
    detailFilter.value = defaultDetailFilter()
  }

  return {
    // 筛选
    startMonth,
    endMonth,
    providers,
    accounts,
    trendMonths,
    // 数据与状态
    kpi,
    kpiLoading,
    kpiError,
    trend,
    trendLoading,
    trendError,
    accountsVO,
    accountsLoading,
    accountsError,
    detail,
    detailLoading,
    detailError,
    productTop,
    productTopLoading,
    productTopError,
    // 维度选择
    shareDimension,
    productDimension,
    productTopN,
    // 明细内筛
    detailFilter,
    detailParams,
    baseParams,
    // 请求
    fetchKpi,
    fetchTrend,
    fetchAccounts,
    fetchDetail,
    fetchProductTop,
    fetchAll,
    refreshDetail,
    // 操作
    setDateRange,
    setProviders,
    setAccounts,
    setTrendMonths,
    patchDetailFilter,
    resetDetailPage,
    resetFilters,
    clearDetailFilter,
    // 下钻
    drillMonth,
    drillAccount,
    drillProvider,
    drillProduct
  }
})
