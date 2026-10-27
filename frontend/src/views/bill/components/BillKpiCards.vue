<template>
  <div class="bill-kpi-cards">
    <el-skeleton :loading="loading" animated :rows="3">
      <template #default>
        <el-row :gutter="16">
          <!-- 卡 1：区间实付 + 环比 -->
          <el-col :xs="24" :sm="12" :lg="6">
            <el-card shadow="hover" class="kpi-card">
              <div class="kpi-title">区间实付总费用</div>
              <div class="kpi-value">
                {{ kpi ? fmtMoney(kpi.totalPayAmount) : '--' }}
              </div>
              <div class="kpi-footer">
                <span class="kpi-footer-label">环比</span>
                <span class="kpi-chip" :class="ratioClass(kpi?.chainRatio?.rate)">
                  {{ ratioArrow(kpi?.chainRatio?.rate) }}
                  {{ fmtRatio(kpi?.chainRatio?.rate) }}
                </span>
                <el-tooltip
                  v-if="previousRangeText"
                  :content="`对比区间：${previousRangeText}，上期实付 ${fmtMoney(kpi?.chainRatio?.previousPayAmount)}`"
                  placement="top"
                >
                  <el-icon class="kpi-help"><InfoFilled /></el-icon>
                </el-tooltip>
              </div>
              <div v-if="kpi?.currentMonthInProgress" class="kpi-hint">
                当月为进行中账期，数据可能不完整
              </div>
            </el-card>
          </el-col>

          <!-- 卡 2：账号数 -->
          <el-col :xs="24" :sm="12" :lg="6">
            <el-card shadow="hover" class="kpi-card">
              <div class="kpi-title">本期账号数</div>
              <div class="kpi-value">
                {{ kpi ? `${kpi.accountWithBillCount || 0} / ${kpi.accountTotalCount || 0}` : '0 / 0' }}
              </div>
              <div class="kpi-footer">
                <el-tooltip
                  :content="`有 ${kpi?.accountWithoutBillCount || 0} 个账号区间内无账单`"
                  placement="top"
                >
                  <span class="kpi-footer-label">
                    产生费用账号 / 账号总数
                    <el-icon><InfoFilled /></el-icon>
                  </span>
                </el-tooltip>
              </div>
            </el-card>
          </el-col>

          <!-- 卡 3：TOP 产品 -->
          <el-col :xs="24" :sm="12" :lg="6">
            <el-card shadow="hover" class="kpi-card">
              <div class="kpi-title">TOP 产品</div>
              <template v-if="kpi?.topProduct">
                <el-tooltip :content="kpi.topProduct.productMain || '--'" placement="top">
                  <div class="kpi-value kpi-value-text">
                    {{ kpi.topProduct.productMain || '--' }}
                  </div>
                </el-tooltip>
                <div class="kpi-footer">
                  <span class="kpi-footer-label">
                    {{ fmtMoney(kpi.topProduct.payAmount) }} ·
                    占比 {{ fmtPercent(kpi.topProduct.ratio) }}
                  </span>
                </div>
              </template>
              <template v-else>
                <div class="kpi-value kpi-value-text">暂无产品数据</div>
                <div class="kpi-footer">
                  <span class="kpi-footer-label">所选范围内无产品费用</span>
                </div>
              </template>
            </el-card>
          </el-col>

          <!-- 卡 4：原价 / 优惠 -->
          <el-col :xs="24" :sm="12" :lg="6">
            <el-card shadow="hover" class="kpi-card">
              <div class="kpi-title">区间原价 / 优惠</div>
              <div class="kpi-value">
                {{ kpi ? fmtMoney(kpi.totalOfficialAmount) : '--' }}
              </div>
              <div class="kpi-footer">
                <span class="kpi-footer-label">
                  优惠 {{ fmtMoney(kpi?.totalDiscountAmount) }}
                  <template v-if="kpi?.discountRate !== null && kpi?.discountRate !== undefined">
                    （折扣率 {{ fmtPercent(kpi.discountRate) }}）
                  </template>
                </span>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 阿里云等厂商占位提示 -->
        <el-alert
          v-for="item in unavailableProviders"
          :key="item.provider"
          type="info"
          :closable="false"
          show-icon
          class="provider-alert"
          :title="`${item.label}：${item.message || '该厂商账单数据暂未采集'}（不影响其他厂商合计）`"
        />
      </template>
    </el-skeleton>

    <el-alert
      v-if="error"
      type="error"
      :closable="false"
      show-icon
      class="provider-alert"
      :title="`KPI 数据获取失败：${error}`"
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
import { computed } from 'vue'
import {
  fmtMoney,
  fmtPercent,
  fmtRatio,
  ratioArrow,
  ratioClass
} from '../utils/billFormat'

const props = defineProps({
  /** BillKpiVO */
  kpi: { type: Object, default: null },
  /** 加载态 */
  loading: { type: Boolean, default: false },
  /** 错误信息 */
  error: { type: String, default: null }
})

/** 不可用的厂商（阿里云等） */
const unavailableProviders = computed(() => {
  const list = props.kpi?.providerAvailability
  if (!Array.isArray(list)) return []
  return list.filter((item) => item.available === false)
})

/** 前置区间文案 */
const previousRangeText = computed(() => {
  const range = props.kpi?.chainRatio?.previousRange
  if (!range || !range.startMonth || !range.endMonth) return ''
  return `${range.startMonth} ~ ${range.endMonth}`
})
</script>

<style scoped>
.bill-kpi-cards {
  margin-bottom: 16px;
}

.kpi-card {
  height: 100%;
  min-height: 116px;
}

.kpi-title {
  font-size: 13px;
  color: #909399;
  margin-bottom: 8px;
}

.kpi-value {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  line-height: 1.3;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.kpi-value-text {
  font-size: 18px;
}

.kpi-footer {
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 4px;
}

.kpi-footer-label {
  display: inline-flex;
  align-items: center;
  gap: 2px;
}

.kpi-chip {
  font-weight: 600;
}

.kpi-chip.up {
  color: #f56c6c;
}

.kpi-chip.down {
  color: #67c23a;
}

.kpi-chip.flat {
  color: #909399;
}

.kpi-help {
  cursor: help;
}

.kpi-hint {
  margin-top: 4px;
  font-size: 12px;
  color: #e6a23c;
}

.provider-alert {
  margin-top: 12px;
}
</style>
