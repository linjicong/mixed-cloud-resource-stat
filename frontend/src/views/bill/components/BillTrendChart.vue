<template>
  <el-card shadow="never" class="bill-trend-chart">
    <template #header>
      <div class="card-header">
        <span class="card-title">历史费用趋势</span>
        <el-radio-group :model-value="months" size="small" @change="handleMonthsChange">
          <el-radio-button :label="6">近 6 月</el-radio-button>
          <el-radio-button :label="12">近 12 月</el-radio-button>
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
          :title="`趋势数据获取失败：${error}`"
        />
        <!-- 空态：不画空坐标系 -->
        <el-empty
          v-else-if="!hasData"
          description="所选范围内暂无账单数据"
          :image-size="90"
        />
        <VChart
          v-else
          :option="option"
          autoresize
          class="trend-chart"
          @click="handleChartClick"
        />
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
import { fmtAmount, fmtRatio, providerColor } from '../utils/billFormat'

const props = defineProps({
  /** BillTrendVO */
  trend: { type: Object, default: null },
  /** 窗口月数 */
  months: { type: Number, default: 6 },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

const emit = defineEmits(['change-months', 'drill-month'])

/** 月份骨架 */
const monthList = computed(() => props.trend?.months || [])
/** 合计序列 */
const totalAmounts = computed(() => props.trend?.totalAmounts || [])
/** 环比序列 */
const chainRatios = computed(() => props.trend?.chainRatios || [])
/** 厂商序列 */
const providerSeries = computed(() => props.trend?.series || [])
/** 是否有数据 */
const hasData = computed(() => Boolean(props.trend?.hasData) && monthList.value.length > 0)

/**
 * Y 轴金额简写（过万显示「万」）。
 * @param {number} value 金额
 * @returns {string|number}
 */
const axisFormatter = (value) => {
  const num = Number(value) || 0
  return num >= 10000 ? `${(num / 10000).toFixed(1)}万` : num
}

/** 图表配置：折线(合计) + 堆叠柱(各厂商)，只用已注册组件 */
const option = computed(() => {
  const barSeries = providerSeries.value.map((item) => ({
    name: item.label,
    type: 'bar',
    stack: 'provider',
    barMaxWidth: 28,
    emphasis: { focus: 'series' },
    itemStyle: { color: providerColor(item.provider) },
    data: item.amounts || []
  }))

  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      formatter: (params) => {
        if (!Array.isArray(params) || params.length === 0) return ''
        const dataIndex = params[0].dataIndex
        const lines = [`${params[0].axisValue}`]
        lines.push(`合计：¥ ${fmtAmount(totalAmounts.value[dataIndex])}`)
        params.forEach((p) => {
          if (p.seriesType === 'bar') {
            lines.push(`${p.marker}${p.seriesName}：¥ ${fmtAmount(p.value)}`)
          }
        })
        const ratio = chainRatios.value[dataIndex]
        lines.push(`环比：${fmtRatio(ratio)}`)
        return lines.join('<br/>')
      }
    },
    legend: { bottom: 0, type: 'scroll' },
    grid: { left: 60, right: 24, top: 24, bottom: 56 },
    xAxis: {
      type: 'category',
      data: monthList.value,
      axisLabel: { rotate: 0 },
      axisTick: { alignWithLabel: true }
    },
    yAxis: {
      type: 'value',
      name: '金额（元）',
      nameTextStyle: { color: '#909399', fontSize: 12 },
      axisLabel: { formatter: axisFormatter },
      splitLine: { lineStyle: { type: 'dashed' } }
    },
    series: [
      {
        name: '合计',
        type: 'line',
        smooth: true,
        symbolSize: 6,
        z: 10,
        lineStyle: { width: 2, color: '#303133' },
        itemStyle: { color: '#303133' },
        data: totalAmounts.value
      },
      ...barSeries
    ]
  }
})

/** 切换 6 / 12 月 */
const handleMonthsChange = (value) => {
  emit('change-months', Number(value))
}

/** 点击某月柱状 → 下钻为该单月 */
const handleChartClick = (params) => {
  if (!params || !params.name) return
  emit('drill-month', params.name)
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

.trend-chart {
  height: 320px;
  width: 100%;
}
</style>
