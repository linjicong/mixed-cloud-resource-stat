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

/**
 * 账单展示格式化工具。
 *
 * 关键约定（PRD R10「无数据时不显示 0 值假象」）：
 * - fmtAmount(null) 返回 '--'，不是 '0.00'
 * - fmtRatio(null) 返回 '不可比'，不是 '0.00%'
 */

/**
 * 金额格式化：千分位 + 固定 2 位小数。
 * @param {number|string|null|undefined} value 金额
 * @returns {string} 如 '1,234.56'；null/undefined/NaN 返回 '--'
 */
export const fmtAmount = (value) => {
  if (value === null || value === undefined || value === '') return '--'
  const num = Number(value)
  if (Number.isNaN(num)) return '--'
  return num.toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
}

/**
 * 带 ¥ 前缀的金额。
 * @param {number|string|null|undefined} value 金额
 * @returns {string}
 */
export const fmtMoney = (value) => {
  const text = fmtAmount(value)
  return text === '--' ? '--' : `¥ ${text}`
}

/**
 * 百分比格式化（环比 / 占比）。
 * @param {number|string|null|undefined} value 百分比数值，12.34 表示 +12.34%
 * @returns {string} 如 '+12.34%'；null 返回 '不可比'
 */
export const fmtRatio = (value) => {
  if (value === null || value === undefined || value === '') return '不可比'
  const num = Number(value)
  if (Number.isNaN(num)) return '不可比'
  return `${num > 0 ? '+' : ''}${num.toFixed(2)}%`
}

/**
 * 占比格式化（不显示正号，null 显示 '--'）。
 * @param {number|string|null|undefined} value 百分比数值
 * @returns {string}
 */
export const fmtPercent = (value) => {
  if (value === null || value === undefined || value === '') return '--'
  const num = Number(value)
  if (Number.isNaN(num)) return '--'
  return `${num.toFixed(2)}%`
}

/**
 * 环比颜色 class。成本直觉：涨红跌绿（花更多钱是坏消息）。
 * @param {number|string|null|undefined} value 环比百分比
 * @returns {'up'|'down'|'flat'}
 */
export const ratioClass = (value) => {
  if (value === null || value === undefined || value === '') return 'flat'
  const num = Number(value)
  if (Number.isNaN(num) || num === 0) return 'flat'
  return num > 0 ? 'up' : 'down'
}

/**
 * 环比箭头。
 * @param {number|string|null|undefined} value 环比百分比
 * @returns {string} '↑' | '↓' | '—'
 */
export const ratioArrow = (value) => {
  const cls = ratioClass(value)
  if (cls === 'up') return '↑'
  if (cls === 'down') return '↓'
  return '—'
}

/** 厂商配色（沿用 Summary.vue 既有配色，全站一致） */
export const PROVIDER_COLOR = {
  huawei: '#F56C6C',
  tencent: '#409EFF',
  aliyun: '#E6A23C'
}

/** 厂商中文标签（后端已下发 providerLabel，此 map 仅作兜底） */
export const PROVIDER_LABEL = {
  huawei: '华为云',
  tencent: '腾讯云',
  aliyun: '阿里云'
}

/** 金额来源中文标签（后端已下发 amountSourceLabel，此 map 仅作兜底） */
export const AMOUNT_SOURCE_LABEL = {
  FEE_RECORD: '费用记录',
  BREAK_DOWN: '月度明细',
  RESOURCE_SUMMARY: '资源账单'
}

/** 金额来源角标配色 */
export const AMOUNT_SOURCE_TAG_TYPE = {
  FEE_RECORD: 'success',
  BREAK_DOWN: 'warning',
  RESOURCE_SUMMARY: ''
}

/** el-tag 的厂商配色映射（Element Plus type；缺失时用默认色） */
export const PROVIDER_TAG_TYPE = {
  huawei: 'danger',
  tencent: 'primary',
  aliyun: 'warning'
}

/** 计费模式下拉候选（与后端 ChargingModeEnum 的 6 个统一文本严格一致） */
export const CHARGING_MODE_OPTIONS = ['包年包月', '按需', '预留实例', '资源包', '节省计划', '其他']

/** 支持账单数据的厂商（与后端 SUPPORTED_PROVIDERS 一致） */
export const SUPPORTED_PROVIDERS = ['huawei', 'tencent']

/**
 * 取厂商颜色。
 * @param {string} provider 厂商标识
 * @returns {string} 颜色值
 */
export const providerColor = (provider) => PROVIDER_COLOR[provider] || '#909399'

/**
 * 取厂商中文名，优先使用后端下发的 label。
 * @param {{provider?:string, providerLabel?:string}} row 行数据
 * @returns {string}
 */
export const providerLabelOf = (row) => {
  if (!row) return '--'
  return row.providerLabel || PROVIDER_LABEL[row.provider] || row.provider || '--'
}

/**
 * 空值占位显示。
 * @param {*} value 任意值
 * @param {string} placeholder 占位符
 * @returns {string}
 */
export const displayOrDash = (value, placeholder = '—') => {
  if (value === null || value === undefined) return placeholder
  const text = String(value).trim()
  return text === '' ? placeholder : text
}
