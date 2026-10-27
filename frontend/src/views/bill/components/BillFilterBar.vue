<template>
  <el-card shadow="never" class="bill-filter-bar">
    <div class="filter-row">
      <!-- 账期范围 -->
      <div class="filter-item">
        <span class="filter-label">账期</span>
        <el-date-picker
          v-model="dateRange"
          type="monthrange"
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          format="YYYY-MM"
          value-format="YYYY-MM"
          :clearable="false"
          @change="handleDateChange"
        />
      </div>

      <!-- 厂商多选 -->
      <div class="filter-item">
        <span class="filter-label">厂商</span>
        <el-select
          v-model="selectedProviders"
          multiple
          collapse-tags
          collapse-tags-tooltip
          placeholder="全部厂商"
          class="filter-select"
          @change="handleProviderChange"
        >
          <el-option
            v-for="item in providerOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>

      <!-- 账号多选（与厂商级联） -->
      <div class="filter-item">
        <span class="filter-label">账号</span>
        <el-select
          v-model="selectedAccounts"
          multiple
          filterable
          collapse-tags
          collapse-tags-tooltip
          placeholder="全部账号"
          class="filter-select account-select"
          @change="handleAccountChange"
        >
          <el-option
            v-for="item in accountOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>

      <div class="filter-item filter-actions">
        <el-button @click="handleReset">重置</el-button>
        <el-popover placement="bottom-end" :width="360" trigger="click">
          <template #reference>
            <el-button text type="primary" class="help-button">
              <el-icon><QuestionFilled /></el-icon>
              <span>口径说明</span>
            </el-button>
          </template>
          <div class="caliber-panel">
            <div class="caliber-title">金额口径说明</div>
            <ul class="caliber-list">
              <li>
                <strong>华为云实付</strong> = 现金 + 代金券 + 信用额度（取自费用记录表）
              </li>
              <li>
                <strong>腾讯云实付</strong> = 现金账户 + 代金券 + 赠送账户（取自账单资源摘要表）
              </li>
              <li>
                <strong>华为云仅统计费用记录表</strong>，月度明细表（摊销口径）数据不计入合计，
                仅在账单明细中作为补充来源并标记「月度明细」角标。
              </li>
              <li>欠费、抹零、核销<strong>不计入实付</strong>，仅在明细展开行展示供核对。</li>
              <li>环比对比「紧邻的等长前置区间」；上期为 0 时显示「不可比」。</li>
              <li>退款等负金额保留参与求和，不做静默修正。</li>
            </ul>
          </div>
        </el-popover>
      </div>
    </div>
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
import { computed, onMounted, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useBillStore } from '@/stores/bill'
import { useCloudStore } from '@/stores/cloud'
import { PROVIDER_LABEL } from '../utils/billFormat'

/** 账期跨度上限（月） */
const MAX_MONTH_SPAN = 24

const billStore = useBillStore()
const cloudStore = useCloudStore()

/** 账期范围 [start, end] */
const dateRange = ref([billStore.startMonth, billStore.endMonth])
/** 已选厂商 */
const selectedProviders = ref([...billStore.providers])
/** 已选账号 */
const selectedAccounts = ref([...billStore.accounts])

const emit = defineEmits(['change'])

/** 厂商选项 */
const providerOptions = computed(() =>
  ['huawei', 'tencent', 'aliyun'].map((value) => ({
    value,
    label: PROVIDER_LABEL[value]
  }))
)

/** 云配置原始列表（兼容 confProvider / provider 两种字段） */
const rawConfigs = computed(() => cloudStore.cloudConfigs || [])

/**
 * 取配置的厂商标识（小写）。
 * @param {object} conf 云配置
 * @returns {string}
 */
const providerOf = (conf) => {
  const raw = conf?.confProvider ?? conf?.provider ?? ''
  return String(raw).toLowerCase()
}

/**
 * 取配置的账号名（兼容 confName / name 两种字段）。
 * @param {object} conf 云配置
 * @returns {string}
 */
const accountOf = (conf) => conf?.confName ?? conf?.name ?? ''

/**
 * 取配置是否启用。
 * @param {object} conf 云配置
 * @returns {boolean}
 */
const enabledOf = (conf) => {
  const value = conf?.enable
  if (value === undefined || value === null) return true
  return value === true || value === 1 || value === '1' || value === 'true'
}

/**
 * 账号下拉候选。
 *
 * 规则（PRD 6.2）：
 * 1. 只列启用账号；
 * 2. 选中厂商后只显示该厂商账号；
 * 3. 已选中但被过滤掉的账号要保留，不能静默清空。
 */
const accountOptions = computed(() => {
  const providerFilter = selectedProviders.value
  const options = []
  const seen = new Set()

  rawConfigs.value.forEach((conf) => {
    const account = accountOf(conf)
    const provider = providerOf(conf)
    if (!account) return
    const isEnabled = enabledOf(conf)
    const alreadySelected = selectedAccounts.value.includes(account)
    // 停用账号：仅当已选中时保留，避免用户看不到自己选中的项
    if (!isEnabled && !alreadySelected) return
    // 厂商级联过滤
    if (providerFilter.length > 0 && provider && !providerFilter.includes(provider) && !alreadySelected) {
      return
    }
    if (seen.has(account)) return
    seen.add(account)
    options.push({
      value: account,
      label: `${account}（${PROVIDER_LABEL[provider] || provider || '未知'}）`
    })
  })

  // 把已选中但配置列表中已不存在的账号也保留成可选项，避免下拉显示原始值
  selectedAccounts.value.forEach((account) => {
    if (!seen.has(account)) {
      seen.add(account)
      options.push({ value: account, label: account })
    }
  })

  return options
})

/**
 * 计算两个月份之间的跨度（含首尾）。
 * @param {string} start 起始月 YYYY-MM
 * @param {string} end 结束月 YYYY-MM
 * @returns {number} 月份数
 */
const monthSpan = (start, end) => {
  const [sy, sm] = start.split('-').map(Number)
  const [ey, em] = end.split('-').map(Number)
  return (ey - sy) * 12 + (em - sm) + 1
}

/** 账期变化 */
const handleDateChange = (value) => {
  if (!Array.isArray(value) || value.length !== 2 || !value[0] || !value[1]) {
    return
  }
  let [start, end] = value
  if (start > end) {
    ;[start, end] = [end, start]
  }
  // 跨度超限：提示并截断为最近 24 个月
  if (monthSpan(start, end) > MAX_MONTH_SPAN) {
    ElMessage.warning(`账期跨度不能超过 ${MAX_MONTH_SPAN} 个月，已自动截断`)
    const endDate = new Date(`${end}-01T00:00:00`)
    endDate.setMonth(endDate.getMonth() - (MAX_MONTH_SPAN - 1))
    const y = endDate.getFullYear()
    const m = String(endDate.getMonth() + 1).padStart(2, '0')
    start = `${y}-${m}`
  }
  dateRange.value = [start, end]
  billStore.setDateRange([start, end])
  emit('change')
}

/** 厂商变化：先把不属于新厂商的账号清掉（已选账号如果厂商未知则保留） */
const handleProviderChange = (value) => {
  selectedProviders.value = Array.isArray(value) ? value : []
  if (selectedProviders.value.length > 0) {
    const allowed = new Set(
      rawConfigs.value
        .filter((conf) => selectedProviders.value.includes(providerOf(conf)))
        .map((conf) => accountOf(conf))
    )
    // 只清理「已知厂商且不在新厂商范围内」的账号；配置里查不到的账号保留（可能是历史停用账号）
    const filtered = selectedAccounts.value.filter((account) => {
      const conf = rawConfigs.value.find((item) => accountOf(item) === account)
      if (!conf) return true
      return allowed.has(account)
    })
    if (filtered.length !== selectedAccounts.value.length) {
      selectedAccounts.value = filtered
      billStore.setAccounts(filtered)
    }
  }
  billStore.setProviders(selectedProviders.value)
  emit('change')
}

/** 账号变化：若选了账号但没选厂商，自动把厂商补上以便级联一致 */
const handleAccountChange = (value) => {
  selectedAccounts.value = Array.isArray(value) ? value : []
  if (selectedAccounts.value.length > 0 && selectedProviders.value.length === 0) {
    const inferred = new Set()
    selectedAccounts.value.forEach((account) => {
      const conf = rawConfigs.value.find((item) => accountOf(item) === account)
      const provider = providerOf(conf)
      if (provider) inferred.add(provider)
    })
    if (inferred.size > 0) {
      selectedProviders.value = Array.from(inferred)
      billStore.setProviders(selectedProviders.value)
    }
  }
  billStore.setAccounts(selectedAccounts.value)
  emit('change')
}

/** 重置 */
const handleReset = () => {
  dateRange.value = []
  selectedProviders.value = []
  selectedAccounts.value = []
  billStore.resetFilters()
  dateRange.value = [billStore.startMonth, billStore.endMonth]
  emit('change')
}

// store 状态被外部下钻修改时，同步回本地控件
watch(
  () => [billStore.startMonth, billStore.endMonth],
  ([start, end]) => {
    if (start && end && (dateRange.value[0] !== start || dateRange.value[1] !== end)) {
      dateRange.value = [start, end]
    }
  }
)

watch(
  () => billStore.providers,
  (list) => {
    if (JSON.stringify(selectedProviders.value) !== JSON.stringify(list)) {
      selectedProviders.value = [...list]
    }
  },
  { deep: true }
)

watch(
  () => billStore.accounts,
  (list) => {
    if (JSON.stringify(selectedAccounts.value) !== JSON.stringify(list)) {
      selectedAccounts.value = [...list]
    }
  },
  { deep: true }
)

onMounted(async () => {
  // 账号列表复用已有 /cloud-configs 接口
  if (rawConfigs.value.length === 0) {
    await cloudStore.fetchCloudConfigs()
  }
  dateRange.value = [billStore.startMonth, billStore.endMonth]
})
</script>

<style scoped>
.bill-filter-bar {
  margin-bottom: 16px;
}

.filter-row {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 16px;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-label {
  color: #606266;
  font-size: 13px;
  white-space: nowrap;
}

.filter-select {
  width: 200px;
}

.account-select {
  width: 240px;
}

.filter-actions {
  margin-left: auto;
  gap: 4px;
}

.help-button {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.caliber-panel {
  font-size: 13px;
  line-height: 1.7;
  color: #606266;
}

.caliber-title {
  font-weight: 600;
  color: #303133;
  margin-bottom: 6px;
}

.caliber-list {
  margin: 0;
  padding-left: 18px;
}

.caliber-list li {
  margin-bottom: 4px;
}
</style>
