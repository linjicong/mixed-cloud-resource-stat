<template>
  <el-card shadow="never" class="bill-detail-table">
    <template #header>
      <div class="card-header">
        <span class="card-title">账单明细</span>
        <span class="card-subtitle">
          当前筛选全量合计：实付 <strong>{{ fmtMoney(summary.payAmountSum) }}</strong>
          · 原价 <strong>{{ fmtMoney(summary.officialAmountSum) }}</strong>
          · 优惠 <strong>{{ fmtMoney(summary.discountAmountSum) }}</strong>
          · 共 <strong>{{ detail?.total || 0 }}</strong> 条
        </span>
      </div>
    </template>

    <!-- 明细内筛（与顶部全局筛选解耦，用标签区分） -->
    <div class="inner-filter">
      <el-tag type="info" effect="plain" size="small" class="inner-filter-tag">明细内筛选</el-tag>

      <el-input
        v-model="productMainInput"
        placeholder="产品大类（需精确匹配）"
        clearable
        class="inner-input"
        @clear="handleProductClear"
        @keyup.enter="handleProductSearch"
      >
        <template #append>
          <el-button @click="handleProductSearch">查询</el-button>
        </template>
      </el-input>

      <el-select
        v-model="chargingModeInput"
        placeholder="计费模式"
        clearable
        class="inner-select"
        @change="handleChargingModeChange"
      >
        <el-option
          v-for="mode in CHARGING_MODE_OPTIONS"
          :key="mode"
          :label="mode"
          :value="mode"
        />
      </el-select>

      <el-button v-if="hasInnerFilter" text type="primary" @click="handleClearInner">
        清空内筛
      </el-button>
    </div>

    <el-skeleton :loading="loading" animated :rows="8">
      <template #default>
        <el-alert
          v-if="error"
          type="error"
          :closable="false"
          show-icon
          :title="`明细获取失败：${error}`"
        />
        <el-empty
          v-else-if="!records.length"
          :description="emptyDescription"
          :image-size="90"
        />
        <template v-else>
          <el-table :data="records" stripe border row-key="id" @sort-change="handleSortChange">
            <!-- 展开行：实付构成拆分 -->
            <el-table-column type="expand">
              <template #default="{ row }">
                <div class="expand-panel">
                  <div class="expand-title">实付构成拆分</div>
                  <div class="expand-grid">
                    <div class="expand-item">
                      <span class="expand-label">现金</span>
                      <span class="expand-value">{{ fmtMoney(row.cashPay) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">代金券</span>
                      <span class="expand-value">{{ fmtMoney(row.voucherPay) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">信用/赠送账户</span>
                      <span class="expand-value">{{ fmtMoney(row.creditPay) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">欠费（不计入实付）</span>
                      <span class="expand-value">{{ fmtMoney(row.debtAmount) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">抹零（不计入实付）</span>
                      <span class="expand-value">{{ fmtMoney(row.eraseAmount) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">核销（不计入实付）</span>
                      <span class="expand-value">{{ fmtMoney(row.writeoffAmount) }}</span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">资源 ID / 名称</span>
                      <span class="expand-value">
                        {{ displayOrDash(row.resourceId) }} / {{ displayOrDash(row.resourceName) }}
                      </span>
                    </div>
                    <div class="expand-item">
                      <span class="expand-label">交易时间</span>
                      <span class="expand-value">{{ displayOrDash(row.tradeTime) }}</span>
                    </div>
                  </div>
                </div>
              </template>
            </el-table-column>

            <el-table-column prop="billMonth" label="账期" width="100" sortable="custom" />

            <el-table-column prop="account" label="账号" min-width="140" show-overflow-tooltip />

            <el-table-column label="厂商" width="100">
              <template #default="{ row }">
                <el-tag size="small" :type="PROVIDER_TAG_TYPE[row.provider] || 'info'" effect="light">
                  {{ providerLabelOf(row) }}
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column prop="productMain" label="产品大类" min-width="160" show-overflow-tooltip>
              <template #default="{ row }">{{ displayOrDash(row.productMain) }}</template>
            </el-table-column>

            <el-table-column prop="productSub" label="子产品" min-width="150" show-overflow-tooltip>
              <template #default="{ row }">{{ displayOrDash(row.productSub) }}</template>
            </el-table-column>

            <el-table-column prop="chargingMode" label="计费模式" width="110" />

            <el-table-column
              prop="officialAmount"
              label="原价"
              width="130"
              align="right"
              sortable="custom"
            >
              <template #default="{ row }">{{ fmtAmount(row.officialAmount) }}</template>
            </el-table-column>

            <el-table-column label="优惠" width="120" align="right">
              <template #default="{ row }">{{ fmtAmount(row.discountAmount) }}</template>
            </el-table-column>

            <el-table-column
              prop="payAmount"
              label="实付"
              width="130"
              align="right"
              sortable="custom"
            >
              <template #default="{ row }">
                <span :class="{ 'negative-amount': Number(row.payAmount) < 0 }">
                  {{ fmtAmount(row.payAmount) }}
                </span>
              </template>
            </el-table-column>

            <el-table-column prop="region" label="地域" width="120">
              <template #default="{ row }">{{ displayOrDash(row.region) }}</template>
            </el-table-column>

            <el-table-column label="资源" min-width="150" show-overflow-tooltip>
              <template #default="{ row }">
                {{ displayOrDash(row.resourceId || row.resourceName) }}
              </template>
            </el-table-column>

            <el-table-column label="来源" width="110" align="center">
              <template #default="{ row }">
                <el-tag
                  size="small"
                  :type="AMOUNT_SOURCE_TAG_TYPE[row.amountSource] || 'info'"
                  effect="plain"
                >
                  {{ row.amountSourceLabel || AMOUNT_SOURCE_LABEL[row.amountSource] || row.amountSource }}
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column label="标记" width="100" align="center">
              <template #default="{ row }">
                <el-tag v-if="row.amountAnomaly" size="small" type="warning">数据异常</el-tag>
                <span v-else class="no-mark">—</span>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-wrap">
            <el-pagination
              :current-page="detail?.pageNum || 1"
              :page-size="detail?.pageSize || 20"
              :page-sizes="[20, 50, 100]"
              :total="detail?.total || 0"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
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
import { computed, ref, watch } from 'vue'
import {
  AMOUNT_SOURCE_LABEL,
  AMOUNT_SOURCE_TAG_TYPE,
  CHARGING_MODE_OPTIONS,
  displayOrDash,
  fmtAmount,
  fmtMoney,
  PROVIDER_TAG_TYPE,
  providerLabelOf
} from '../utils/billFormat'

const props = defineProps({
  /** BillDetailPageVO */
  detail: { type: Object, default: null },
  /** 明细内筛 */
  detailFilter: { type: Object, default: () => ({}) },
  /** 是否有账单数据来源（用于区分两种空态） */
  hasBillSource: { type: Boolean, default: true },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

const emit = defineEmits(['change-filter', 'change-page'])

/** 产品大类输入框（回车/按钮才提交，避免每敲一个字都请求） */
const productMainInput = ref(props.detailFilter?.productMain || '')
/** 计费模式选择 */
const chargingModeInput = ref(props.detailFilter?.chargingMode || '')

/** 明细行 */
const records = computed(() => props.detail?.records || [])

/** 当前筛选全量合计 */
const summary = computed(() => ({
  payAmountSum: props.detail?.summary?.payAmountSum ?? 0,
  officialAmountSum: props.detail?.summary?.officialAmountSum ?? 0,
  discountAmountSum: props.detail?.summary?.discountAmountSum ?? 0
}))

/** 是否有内筛条件 */
const hasInnerFilter = computed(
  () => Boolean(props.detailFilter?.productMain) || Boolean(props.detailFilter?.chargingMode)
)

/** 空态文案：区分「无符合条件的账单」与「该厂商账单数据暂未采集」 */
const emptyDescription = computed(() =>
  props.hasBillSource ? '无符合条件的账单' : '该厂商账单数据暂未采集'
)

/** 同步外部筛选变化 */
watch(
  () => props.detailFilter?.productMain,
  (value) => {
    if ((value || '') !== productMainInput.value) {
      productMainInput.value = value || ''
    }
  }
)

watch(
  () => props.detailFilter?.chargingMode,
  (value) => {
    if ((value || '') !== chargingModeInput.value) {
      chargingModeInput.value = value || ''
    }
  }
)

/** 提交产品大类筛选 */
const handleProductSearch = () => {
  emit('change-filter', { productMain: productMainInput.value.trim() })
}

/** 清空产品大类 */
const handleProductClear = () => {
  productMainInput.value = ''
  emit('change-filter', { productMain: '' })
}

/** 计费模式变化 */
const handleChargingModeChange = (value) => {
  emit('change-filter', { chargingMode: value || '' })
}

/** 清空全部内筛 */
const handleClearInner = () => {
  productMainInput.value = ''
  chargingModeInput.value = ''
  emit('change-filter', { productMain: '', chargingMode: '' })
}

/** 服务端排序 */
const handleSortChange = ({ prop, order }) => {
  if (!prop) return
  // el-table 的 order: ascending / descending；后端只认 asc / desc
  const sortField = prop
  const sortOrder = order === 'ascending' ? 'asc' : 'desc'
  emit('change-filter', { sortField, sortOrder })
}

/** 每页条数变化 */
const handleSizeChange = (size) => {
  emit('change-page', { pageSize: size, pageNum: 1 })
}

/** 页码变化 */
const handleCurrentChange = (page) => {
  emit('change-page', { pageNum: page })
}
</script>

<style scoped>
.card-header {
  display: flex;
  align-items: baseline;
  gap: 12px;
  flex-wrap: wrap;
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

.inner-filter {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}

.inner-filter-tag {
  flex: none;
}

.inner-input {
  width: 280px;
}

.inner-select {
  width: 160px;
}

.expand-panel {
  padding: 8px 16px;
}

.expand-title {
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
  font-size: 13px;
}

.expand-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 8px 24px;
}

.expand-item {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  padding: 4px 0;
  border-bottom: 1px dashed #ebeef5;
}

.expand-label {
  color: #909399;
}

.expand-value {
  color: #303133;
  font-weight: 500;
}

.negative-amount {
  color: #f56c6c;
}

.no-mark {
  color: #c0c4cc;
}

.pagination-wrap {
  margin-top: 12px;
  display: flex;
  justify-content: flex-end;
}
</style>
