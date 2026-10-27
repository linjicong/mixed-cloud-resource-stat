<template>
  <el-card shadow="never" class="bill-product-top">
    <template #header>
      <div class="card-header">
        <span class="card-title">产品费用 TOP</span>
        <div class="card-actions">
          <el-radio-group :model-value="topN" size="small" @change="handleTopNChange">
            <el-radio-button :label="5">TOP 5</el-radio-button>
            <el-radio-button :label="10">TOP 10</el-radio-button>
            <el-radio-button :label="20">TOP 20</el-radio-button>
          </el-radio-group>
          <el-radio-group :model-value="dimension" size="small" @change="handleDimensionChange">
            <el-radio-button label="provider">按厂商</el-radio-button>
            <el-radio-button label="account">按账号</el-radio-button>
          </el-radio-group>
        </div>
      </div>
    </template>

    <el-skeleton :loading="loading" animated :rows="8">
      <template #default>
        <el-alert
          v-if="error"
          type="error"
          :closable="false"
          show-icon
          :title="`产品 TOP 获取失败：${error}`"
        />
        <el-empty v-else-if="rows.length === 0" description="所选范围内暂无产品费用数据" :image-size="90" />
        <el-row v-else :gutter="20">
          <!-- 横向条形图 -->
          <el-col :xs="24" :lg="12">
            <VChart :option="option" autoresize class="top-chart" @click="handleChartClick" />
          </el-col>

          <!-- 表格 -->
          <el-col :xs="24" :lg="12">
            <el-table :data="rows" stripe border size="small" @row-click="handleRowClick">
              <el-table-column prop="rank" label="#" width="50" align="center" />
              <el-table-column label="产品" min-width="160" show-overflow-tooltip>
                <template #default="{ row }">
                  <div class="product-cell">
                    <span>{{ row.productMain }}</span>
                    <el-tag
                      size="small"
                      :type="PROVIDER_TAG_TYPE[row.provider] || 'info'"
                      effect="plain"
                    >
                      {{ providerLabelOf(row) }}
                    </el-tag>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="金额" width="130" align="right">
                <template #default="{ row }">{{ fmtMoney(row.payAmount) }}</template>
              </el-table-column>
              <el-table-column label="占比" width="90" align="right">
                <template #default="{ row }">{{ fmtPercent(row.ratio) }}</template>
              </el-table-column>
              <el-table-column label="账号数" width="80" align="center">
                <template #default="{ row }">{{ row.accountCount ?? '—' }}</template>
              </el-table-column>
              <el-table-column label="资源数" width="80" align="center">
                <template #default="{ row }">
                  {{ row.resourceCount === null || row.resourceCount === undefined ? '—' : row.resourceCount }}
                </template>
              </el-table-column>
            </el-table>

            <div v-if="others" class="others-row">
              <span>其他 {{ others.productCount || 0 }} 个产品合计：</span>
              <strong>{{ fmtMoney(others.payAmount) }}</strong>
              <span class="others-ratio">（{{ fmtPercent(others.ratio) }}）</span>
            </div>
          </el-col>
        </el-row>
      </template>
    </el-skeleton>
  </el-card>
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
import { computed } from 'vue'
import {
  fmtAmount,
  fmtMoney,
  fmtPercent,
  providerColor,
  providerLabelOf,
  PROVIDER_TAG_TYPE
} from '../utils/billFormat'

const props = defineProps({
  /** BillProductTopVO */
  productTop: { type: Object, default: null },
  /** TOP N */
  topN: { type: Number, default: 10 },
  /** 维度 provider / account */
  dimension: { type: String, default: 'provider' },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

const emit = defineEmits(['change-topn', 'change-dimension', 'drill-product'])

/** TOP 行 */
const rows = computed(() => props.productTop?.rows || [])
/** topN 之外的合计 */
const others = computed(() => props.productTop?.others || null)

/** 名称 → 占比映射，供 tooltip 使用 */
const ratioMap = computed(() => {
  const map = {}
  rows.value.forEach((row) => {
    const key = props.dimension === 'account' ? `${row.productMain}·${row.account}` : row.productMain
    map[key] = row.ratio
  })
  return map
})

/** 条形图配置：yAxis.inverse 让第一名在顶部 */
const option = computed(() => {
  const categories = rows.value.map((row) =>
    props.dimension === 'account' ? `${row.productMain}（${row.account}）` : row.productMain
  )

  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      formatter: (params) => {
        if (!Array.isArray(params) || params.length === 0) return ''
        const p = params[0]
        const ratio = ratioMap.value[p.name]
        return `${p.name}<br/>¥ ${fmtAmount(p.value)}`
          + (ratio === undefined ? '' : `（${fmtPercent(ratio)}）`)
      }
    },
    grid: { left: 8, right: 90, top: 8, bottom: 8, containLabel: true },
    xAxis: {
      type: 'value',
      axisLabel: {
        formatter: (value) => (Number(value) >= 10000 ? `${(Number(value) / 10000).toFixed(1)}万` : value)
      },
      splitLine: { lineStyle: { type: 'dashed' } }
    },
    // inverse: true → 第一名在最上方
    yAxis: {
      type: 'category',
      inverse: true,
      data: categories,
      axisLabel: { width: 120, overflow: 'truncate' }
    },
    series: [
      {
        type: 'bar',
        barMaxWidth: 20,
        data: rows.value.map((row) => ({
          value: Number(row.payAmount),
          itemStyle: { color: providerColor(row.provider) }
        })),
        // 条上标金额，不需要 markPoint
        label: {
          show: true,
          position: 'right',
          formatter: (p) => `¥ ${fmtAmount(p.value)}`,
          fontSize: 11
        }
      }
    ]
  }
})

/** 切换 TOP N */
const handleTopNChange = (value) => {
  emit('change-topn', Number(value))
}

/** 切换维度 */
const handleDimensionChange = (value) => {
  emit('change-dimension', value)
}

/** 点击条形下钻到明细（按产品筛选） */
const handleChartClick = (params) => {
  if (!params || !params.name) return
  // 账号维度下名称带「（账号）」，取产品名部分
  const productName = String(params.name).split('（')[0]
  emit('drill-product', productName)
}

/** 表格行点击同样下钻 */
const handleRowClick = (row) => {
  if (!row?.productMain) return
  emit('drill-product', row.productMain)
}
</script>

<style scoped>
.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 8px;
}

.card-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
}

.card-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.top-chart {
  height: 340px;
  width: 100%;
}

.product-cell {
  display: flex;
  align-items: center;
  gap: 6px;
}

.others-row {
  margin-top: 10px;
  font-size: 12px;
  color: #606266;
  text-align: right;
}

.others-ratio {
  color: #909399;
}
</style>
