<template>
  <div class="bill-page">
    <!-- 顶部筛选区 -->
    <BillFilterBar @change="handleFilterChange" />

    <!-- KPI 卡片区 -->
    <div ref="kpiRef">
      <BillKpiCards
        :kpi="billStore.kpi"
        :loading="billStore.kpiLoading"
        :error="billStore.kpiError"
      />
    </div>

    <!-- 趋势 + 占比 -->
    <el-row :gutter="16" class="section-row">
      <el-col :xs="24" :lg="14">
        <BillTrendChart
          :trend="billStore.trend"
          :months="billStore.trendMonths"
          :loading="billStore.trendLoading"
          :error="billStore.trendError"
          @change-months="handleTrendMonthsChange"
          @drill-month="handleDrillMonth"
        />
      </el-col>
      <el-col :xs="24" :lg="10">
        <BillSharePie
          :accounts-v-o="billStore.accountsVO"
          :dimension="billStore.shareDimension"
          :loading="billStore.accountsLoading"
          :error="billStore.accountsError"
          @change-dimension="handleShareDimensionChange"
          @drill-provider="handleDrillProvider"
          @drill-account="handleDrillAccount"
        />
      </el-col>
    </el-row>

    <!-- 账号费用明细 -->
    <div ref="accountRef" class="section-row">
      <BillAccountTable
        :accounts-v-o="billStore.accountsVO"
        :loading="billStore.accountsLoading"
        :error="billStore.accountsError"
        @drill-account="handleDrillAccount"
      />
    </div>

    <!-- 账单明细 -->
    <div ref="detailRef" class="section-row">
      <BillDetailTable
        :detail="billStore.detail"
        :detail-filter="billStore.detailFilter"
        :has-bill-source="hasBillSource"
        :loading="billStore.detailLoading"
        :error="billStore.detailError"
        @change-filter="handleDetailFilterChange"
        @change-page="handleDetailPageChange"
      />
    </div>

    <!-- 产品费用 TOP -->
    <div ref="productRef" class="section-row">
      <BillProductTop
        :product-top="billStore.productTop"
        :top-n="billStore.productTopN"
        :dimension="billStore.productDimension"
        :loading="billStore.productTopLoading"
        :error="billStore.productTopError"
        @change-topn="handleTopNChange"
        @change-dimension="handleProductDimensionChange"
        @drill-product="handleDrillProduct"
      />
    </div>

    <!-- 底部说明条：阿里云账单未采集 -->
    <el-alert
      v-for="item in unavailableProviders"
      :key="`bottom-${item.provider}`"
      type="info"
      :closable="false"
      show-icon
      class="bottom-alert"
      :title="`${item.label}账单数据暂未采集，页面不展示其金额，也不影响其他厂商合计。`"
    />
  </div>
</template>

<script setup>
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
import { computed, onMounted, ref } from 'vue'
import BillFilterBar from './components/BillFilterBar.vue'
import BillKpiCards from './components/BillKpiCards.vue'
import BillTrendChart from './components/BillTrendChart.vue'
import BillSharePie from './components/BillSharePie.vue'
import BillAccountTable from './components/BillAccountTable.vue'
import BillDetailTable from './components/BillDetailTable.vue'
import BillProductTop from './components/BillProductTop.vue'
import { useBillStore } from '@/stores/bill'

const billStore = useBillStore()

/** 区块 DOM 引用，用于下钻滚动定位 */
const kpiRef = ref(null)
const accountRef = ref(null)
const detailRef = ref(null)
const productRef = ref(null)

/** 不可用厂商（阿里云等） */
const unavailableProviders = computed(() => {
  const list = billStore.kpi?.providerAvailability
  if (!Array.isArray(list)) return []
  return list.filter((item) => item.available === false)
})

/** 是否存在任何可用的账单来源（用于区分明细空态文案） */
const hasBillSource = computed(() => {
  const list = billStore.kpi?.providerAvailability
  if (!Array.isArray(list) || list.length === 0) return true
  return list.some((item) => item.available !== false)
})

/**
 * 滚动到指定区块。
 * @param {HTMLElement|null} element 目标元素
 */
const scrollToSection = (element) => {
  if (!element || typeof element.scrollIntoView !== 'function') return
  element.scrollIntoView({ behavior: 'smooth', block: 'start' })
}

/** 筛选变化：整页刷新，明细回到第 1 页 */
const handleFilterChange = async () => {
  billStore.resetDetailPage()
  await billStore.fetchAll()
}

/** 趋势窗口 6/12 切换：只影响趋势接口 */
const handleTrendMonthsChange = async (months) => {
  billStore.setTrendMonths(months)
  await billStore.fetchTrend()
}

/** 趋势图下钻到某月：账期收窄为该单月并刷新 */
const handleDrillMonth = async (month) => {
  billStore.drillMonth(month)
  await billStore.fetchAll()
}

/** 占比图切换维度：只需本地重算，无需请求 */
const handleShareDimensionChange = (dimension) => {
  billStore.shareDimension = dimension === 'account' ? 'account' : 'provider'
}

/** 占比图点击厂商：厂商筛选收敛 */
const handleDrillProvider = async (provider) => {
  billStore.drillProvider(provider)
  await billStore.fetchAll()
}

/** 账号下钻：账号筛选收敛 + 滚动到账号表 */
const handleDrillAccount = async (account) => {
  billStore.drillAccount(account)
  await billStore.fetchAll()
  scrollToSection(accountRef.value)
}

/** 明细内筛变化：回到第 1 页并只刷新明细 */
const handleDetailFilterChange = async (patch) => {
  billStore.patchDetailFilter(patch, true)
  await billStore.refreshDetail()
}

/** 明细分页变化 */
const handleDetailPageChange = async (patch) => {
  billStore.patchDetailFilter(patch, false)
  await billStore.refreshDetail()
}

/** 产品 TOP N 变化 */
const handleTopNChange = async (topN) => {
  billStore.productTopN = topN
  await billStore.fetchProductTop()
}

/** 产品 TOP 维度变化 */
const handleProductDimensionChange = async (dimension) => {
  billStore.productDimension = dimension === 'account' ? 'account' : 'provider'
  await billStore.fetchProductTop()
}

/** 产品下钻：明细按产品筛选 + 滚动到明细区 */
const handleDrillProduct = async (productMain) => {
  billStore.drillProduct(productMain)
  await billStore.refreshDetail()
  scrollToSection(detailRef.value)
}

onMounted(async () => {
  await billStore.fetchAll()
})
</script>

<style scoped>
.bill-page {
  padding-bottom: 24px;
}

.section-row {
  margin-bottom: 16px;
}

.bottom-alert {
  margin-top: 8px;
}
</style>
