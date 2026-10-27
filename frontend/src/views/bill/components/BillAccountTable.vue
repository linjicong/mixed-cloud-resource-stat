<template>
  <el-card shadow="never" class="bill-account-table">
    <template #header>
      <div class="card-header">
        <span class="card-title">账号费用明细</span>
        <span class="card-subtitle">点击行可下钻该账号的账单明细</span>
      </div>
    </template>

    <el-skeleton :loading="loading" animated :rows="6">
      <template #default>
        <el-alert
          v-if="error"
          type="error"
          :closable="false"
          show-icon
          :title="`账号数据获取失败：${error}`"
        />
        <el-empty
          v-else-if="sortedRows.length === 0"
          description="所选范围内暂无账号账单数据"
          :image-size="90"
        />
        <template v-else>
          <div class="table-summary">
            区间实付合计：<strong>{{ fmtMoney(totalPayAmount) }}</strong>
            <span class="summary-hint">（占比分母仅含产生费用的账号，无账单账号不参与）</span>
          </div>
          <el-table
            ref="tableRef"
            :data="sortedRows"
            stripe
            border
            highlight-current-row
            :row-class-name="rowClassName"
            @sort-change="handleSortChange"
            @row-click="handleRowClick"
          >
            <el-table-column label="账号" min-width="180" show-overflow-tooltip>
              <template #default="{ row }">
                <div class="account-cell">
                  <span class="account-name">{{ row.account }}</span>
                  <el-tag
                    v-if="row.enabled === false"
                    size="small"
                    type="info"
                    effect="plain"
                  >
                    已停用
                  </el-tag>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="厂商" width="110">
              <template #default="{ row }">
                <el-tag size="small" :type="PROVIDER_TAG_TYPE[row.provider] || 'info'" effect="light">
                  {{ providerLabelOf(row) }}
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column
              prop="payAmount"
              label="区间实付"
              width="150"
              align="right"
              sortable="custom"
            >
              <template #default="{ row }">
                <span v-if="row.hasBillData === false" class="amount-placeholder">--</span>
                <span v-else class="amount-value">{{ fmtMoney(row.payAmount) }}</span>
              </template>
            </el-table-column>

            <el-table-column prop="ratio" label="占比" width="110" align="right" sortable="custom">
              <template #default="{ row }">
                <span v-if="row.hasBillData === false" class="amount-placeholder">--</span>
                <span v-else>{{ fmtPercent(row.ratio) }}</span>
              </template>
            </el-table-column>

            <el-table-column prop="chainRatio" label="环比" width="120" align="right" sortable="custom">
              <template #default="{ row }">
                <span class="ratio-text" :class="ratioClass(row.chainRatio?.rate)">
                  {{ fmtRatio(row.chainRatio?.rate) }}
                </span>
              </template>
            </el-table-column>

            <el-table-column label="近 6 月趋势" width="150" align="center">
              <template #default="{ row }">
                <SparklineChart
                  v-if="row.hasBillData !== false && row.sparkline && row.sparkline.length"
                  :values="row.sparkline"
                  :months="row.sparklineMonths"
                  :color="providerColor(row.provider)"
                />
                <span v-else class="amount-placeholder">—</span>
              </template>
            </el-table-column>

            <el-table-column label="状态" width="130">
              <template #default="{ row }">
                <el-tag v-if="row.hasBillData === false" size="small" type="info" effect="plain">
                  暂无账单数据
                </el-tag>
                <span v-else class="status-ok">正常</span>
              </template>
            </el-table-column>
          </el-table>
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
import { computed, ref } from 'vue'
import SparklineChart from './SparklineChart.vue'
import {
  fmtMoney,
  fmtPercent,
  fmtRatio,
  PROVIDER_TAG_TYPE,
  providerColor,
  providerLabelOf,
  ratioClass
} from '../utils/billFormat'

const props = defineProps({
  /** BillAccountVO */
  accountsVO: { type: Object, default: null },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

const emit = defineEmits(['drill-account'])

const tableRef = ref(null)
/** 前端排序状态（行数只有十几行，内存重排安全） */
const sortState = ref({ prop: 'payAmount', order: 'descending' })

/** 账号行（后端已排序，此处仅做兜底） */
const rows = computed(() => props.accountsVO?.rows || [])

/** 区间实付合计（后端给的占比分母） */
const totalPayAmount = computed(() => props.accountsVO?.totalPayAmount ?? 0)

/** 前端排序后的行 */
const sortedRows = computed(() => {
  const list = [...rows.value]
  const { prop, order } = sortState.value
  if (!prop || !order) return list

  const factor = order === 'ascending' ? 1 : -1

  return list.sort((a, b) => {
    // 无账单账号永远排最后
    const aNo = a.hasBillData === false ? 1 : 0
    const bNo = b.hasBillData === false ? 1 : 0
    if (aNo !== bNo) return aNo - bNo

    let valueA = 0
    let valueB = 0
    if (prop === 'chainRatio') {
      // null 视为最小，排序时排在有值之后
      const ra = a.chainRatio?.rate
      const rb = b.chainRatio?.rate
      if (ra === null || ra === undefined) return 1
      if (rb === null || rb === undefined) return -1
      valueA = Number(ra)
      valueB = Number(rb)
    } else {
      valueA = Number(a[prop] ?? 0)
      valueB = Number(b[prop] ?? 0)
    }
    return (valueA - valueB) * factor
  })
})

/** 行样式：无数据行置灰 */
const rowClassName = ({ row }) => (row.hasBillData === false ? 'row-no-data' : '')

/** 表头排序 */
const handleSortChange = ({ prop, order }) => {
  sortState.value = { prop: prop || 'payAmount', order: order || 'descending' }
}

/** 行点击下钻 */
const handleRowClick = (row) => {
  if (!row) return
  emit('drill-account', row.account)
}
</script>

<style scoped>
.card-header {
  display: flex;
  align-items: baseline;
  gap: 12px;
}

.card-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
}

.card-subtitle {
  font-size: 12px;
  color: #909399;
}

.table-summary {
  margin-bottom: 10px;
  font-size: 13px;
  color: #606266;
}

.summary-hint {
  color: #909399;
  font-size: 12px;
}

.account-cell {
  display: flex;
  align-items: center;
  gap: 6px;
}

.account-name {
  font-weight: 500;
}

.amount-value {
  font-weight: 500;
}

.amount-placeholder {
  color: #c0c4cc;
}

.ratio-text.up {
  color: #f56c6c;
}

.ratio-text.down {
  color: #67c23a;
}

.ratio-text.flat {
  color: #909399;
}

.status-ok {
  color: #67c23a;
  font-size: 12px;
}

:deep(.row-no-data) {
  background-color: #fafafa;
  color: #909399;
}
</style>
