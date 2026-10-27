<template>
  <el-card shadow="never" class="bill-share-pie">
    <template #header>
      <div class="card-header">
        <span class="card-title">费用占比</span>
        <el-radio-group :model-value="dimension" size="small" @change="handleDimensionChange">
          <el-radio-button label="provider">按厂商</el-radio-button>
          <el-radio-button label="account">按账号</el-radio-button>
        </el-radio-group>
      </div>
    </template>

    <el-skeleton :loading="loading" animated :rows="6">
      <template #default>
        <el-alert
          v-if="error"
          type="error"
          :closable="false"
          show-icon
          :title="`占比数据获取失败：${error}`"
        />
        <el-empty v-else-if="pieData.length === 0" description="所选范围内暂无账单数据" :image-size="90" />
        <template v-else>
          <VChart :option="option" autoresize class="pie-chart" @click="handleChartClick" />
          <el-alert
            v-if="hasNegative"
            type="warning"
            :closable="false"
            show-icon
            title="存在退款等负金额，环形图仅展示正向占比"
          />
        </template>
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
import { fmtAmount, PROVIDER_COLOR, providerColor } from '../utils/billFormat'

/** 账号维度超过该数量后合并「其他」 */
const MAX_ACCOUNT_SLICES = 8

const props = defineProps({
  /** BillAccountVO */
  accountsVO: { type: Object, default: null },
  /** 维度 provider / account */
  dimension: { type: String, default: 'provider' },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

const emit = defineEmits(['change-dimension', 'drill-provider', 'drill-account'])

/** 环形图数据项 */
const pieData = computed(() => {
  const vo = props.accountsVO
  if (!vo) return []

  if (props.dimension === 'provider') {
    // 后端已提供 byProvider，前端不再二次聚合
    const shares = Array.isArray(vo.byProvider) ? vo.byProvider : []
    return shares
      .filter((item) => Number(item.payAmount) > 0)
      .map((item) => ({
        name: item.label || item.provider,
        provider: item.provider,
        value: Number(item.payAmount)
      }))
  }

  // 账号维度：rows 为全量，超过 8 个合并「其他」
  const rows = Array.isArray(vo.rows) ? vo.rows : []
  const billRows = rows.filter((row) => row.hasBillData !== false && Number(row.payAmount) > 0)
  const total = billRows.reduce((sum, row) => sum + Number(row.payAmount), 0)

  if (billRows.length <= MAX_ACCOUNT_SLICES) {
    return billRows.map((row) => ({
      name: row.account,
      provider: row.provider,
      value: Number(row.payAmount)
    }))
  }

  const top = billRows.slice(0, MAX_ACCOUNT_SLICES).map((row) => ({
    name: row.account,
    provider: row.provider,
    value: Number(row.payAmount)
  }))
  const topSum = top.reduce((sum, item) => sum + item.value, 0)
  const othersValue = total - topSum
  if (othersValue > 0) {
    top.push({ name: '其他', provider: null, value: othersValue })
  }
  return top
})

/** 是否存在负金额（环形图失真提示） */
const hasNegative = computed(() => {
  const rows = props.accountsVO?.rows
  if (!Array.isArray(rows)) return false
  return rows.some((row) => Number(row.payAmount) < 0)
})

/** 环形图配置 */
const option = computed(() => ({
  tooltip: {
    trigger: 'item',
    formatter: (p) => `${p.name}<br/>¥ ${fmtAmount(p.value)}（${p.percent}%）`
  },
  legend: {
    orient: 'vertical',
    right: 8,
    top: 'center',
    type: 'scroll'
  },
  series: [
    {
      type: 'pie',
      radius: ['48%', '72%'],
      center: ['38%', '50%'],
      avoidLabelOverlap: true,
      label: { show: false },
      emphasis: {
        label: { show: true, fontSize: 13, formatter: '{b}\n{d}%' },
        scale: true
      },
      data: pieData.value.map((item, index) => ({
        ...item,
        itemStyle: {
          color:
            props.dimension === 'provider'
              ? providerColor(item.provider)
              : PROVIDER_COLOR[item.provider] || undefined
        }
      }))
    }
  ]
}))

/** 切换维度 */
const handleDimensionChange = (value) => {
  emit('change-dimension', value)
}

/** 点击扇区下钻 */
const handleChartClick = (params) => {
  if (!params || !params.name) return
  if (props.dimension === 'provider') {
    const target = pieData.value.find((item) => item.name === params.name)
    emit('drill-provider', target?.provider || params.name)
  } else {
    emit('drill-account', params.name)
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.card-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
}

.pie-chart {
  height: 320px;
  width: 100%;
}
</style>
