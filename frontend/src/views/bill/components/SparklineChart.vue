<template>
  <VChart v-if="hasData" :option="option" autoresize class="sparkline-chart" />
  <span v-else class="sparkline-empty">—</span>
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
import { fmtAmount } from '../utils/billFormat'

/**
 * 迷你趋势折线图（sparkline）。
 *
 * 只使用 main.js 已注册的 LineChart/GridComponent/TooltipComponent，
 * areaStyle 属于 series 配置而非独立组件，无需额外注册。
 */
const props = defineProps({
  /** 金额数组 */
  values: { type: Array, default: () => [] },
  /** 月份骨架，与 values 等长 */
  months: { type: Array, default: () => [] },
  /** 线条颜色 */
  color: { type: String, default: '#409EFF' }
})

const hasData = computed(() => Array.isArray(props.values) && props.values.length > 0)

const option = computed(() => ({
  grid: { left: 1, right: 1, top: 2, bottom: 2 },
  xAxis: {
    type: 'category',
    show: false,
    boundaryGap: false,
    data: props.months
  },
  yAxis: {
    type: 'value',
    show: false,
    // scale:true 让小幅波动也能看出来（不从 0 起）
    scale: true
  },
  tooltip: {
    trigger: 'axis',
    formatter: (params) => {
      if (!params || params.length === 0) return ''
      const p = params[0]
      return `${p.axisValue}<br/>¥ ${fmtAmount(p.value)}`
    }
  },
  series: [
    {
      type: 'line',
      data: props.values,
      smooth: true,
      symbol: 'none',
      lineStyle: { width: 1.5, color: props.color },
      itemStyle: { color: props.color },
      areaStyle: { opacity: 0.12, color: props.color }
    }
  ]
}))
</script>

<style scoped>
.sparkline-chart {
  width: 100px;
  height: 32px;
}

.sparkline-empty {
  color: #c0c4cc;
  font-size: 12px;
}
</style>
