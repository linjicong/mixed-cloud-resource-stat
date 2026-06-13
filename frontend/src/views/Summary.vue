<template>
  <div class="summary-page">
    <!-- 总览卡片 -->
    <el-row :gutter="20" class="overview-cards">
      <el-col :span="8">
        <el-card shadow="hover" class="provider-card huawei" @click="$router.push('/huawei')">
          <div class="card-icon"><el-icon><Platform /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ totals.huawei || 0 }}</div>
            <div class="card-label">华为云</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="provider-card tencent" @click="$router.push('/tencent')">
          <div class="card-icon"><el-icon><Platform /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ totals.tencent || 0 }}</div>
            <div class="card-label">腾讯云</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="provider-card aliyun" @click="$router.push('/aliyun')">
          <div class="card-icon"><el-icon><Platform /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ totals.aliyun || 0 }}</div>
            <div class="card-label">阿里云</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 业务域对比 -->
    <el-card style="margin-top: 20px">
      <template #header>按业务域对比（点击数字查看明细）</template>
      <el-table :data="domainTableData" stripe border style="width: 100%">
        <el-table-column prop="domain" label="业务域" width="120" fixed />
        <el-table-column label="华为云" align="center">
          <template #default="{ row }">
            <el-link type="danger" @click="$router.push('/huawei')">{{ row.huaweiCount }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="腾讯云" align="center">
          <template #default="{ row }">
            <el-link type="primary" @click="$router.push('/tencent')">{{ row.tencentCount }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="阿里云" align="center">
          <template #default="{ row }">
            <el-link type="warning" @click="$router.push('/aliyun')">{{ row.aliyunCount }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="total" label="合计" width="80" sortable />
      </el-table>
    </el-card>

    <!-- 详细资源对比 -->
    <el-card style="margin-top: 20px">
      <template #header>详细资源对比（点击数字查看明细）</template>
      <div v-for="(data, domain) in crossCloudData" :key="domain" class="domain-section">
        <h3 class="domain-title">{{ data.label }}</h3>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="provider-section">
              <h4 class="provider-title huawei-text">华为云</h4>
              <div v-for="(count, type) in data.huawei" :key="type" class="resource-item">
                <span class="resource-type">{{ type }}</span>
                <el-link v-if="count > 0" type="danger" @click="$router.push(`/huawei/${getRoute('huawei', type)}`)">{{ count }}</el-link>
                <el-tag v-else type="info" size="small">0</el-tag>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="provider-section">
              <h4 class="provider-title tencent-text">腾讯云</h4>
              <div v-for="(count, type) in data.tencent" :key="type" class="resource-item">
                <span class="resource-type">{{ type }}</span>
                <el-link v-if="count > 0" type="primary" @click="$router.push(`/tencent/${getRoute('tencent', type)}`)">{{ count }}</el-link>
                <el-tag v-else type="info" size="small">0</el-tag>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="provider-section">
              <h4 class="provider-title aliyun-text">阿里云</h4>
              <div v-for="(count, type) in data.aliyun" :key="type" class="resource-item">
                <span class="resource-type">{{ type }}</span>
                <el-link v-if="count > 0" type="warning" @click="$router.push(`/aliyun/${getRoute('aliyun', type)}`)">{{ count }}</el-link>
                <el-tag v-else type="info" size="small">0</el-tag>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from 'vue'
import { useCloudStore } from '@/stores/cloud'

const cloudStore = useCloudStore()

const totals = computed(() => cloudStore.providerStats?.totals || {})
const crossCloudData = computed(() => cloudStore.crossCloudStats || {})

// 路由映射：API 返回的 type key -> 前端路由路径
const routeMap = {
  huawei: { ecs: 'ecs', rds: 'rds', elb: 'elb', evs: 'evs', vpc: 'vpc', eip: 'eip', nat: 'nat', obs: 'obs', dcs: 'dcs', dds: 'dds', cce: 'cce', kafka: 'kafka', rocketmq: 'rocketmq', waf: 'waf', antiddos: 'antiddos', kms: 'kms', cdn: 'cdn', es: 'css', emr: 'mrs', iot: 'iotda', faas: 'functiongraph', sfs: 'sfs', swr: 'swr', ces: 'ces_metric', cts: 'cts', lts: 'lts' },
  tencent: { cvm: 'cvm', cdb: 'cdb', clb: 'clb', cbs: 'cbs', vpc: 'vpc', eip: 'eip', nat: 'nat', cos: 'cos', redis: 'redis', mongodb: 'mongodb', tke: 'tke', ckafka: 'ckafka', rocketmq: 'rocketmq', waf: 'waf', ddos: 'ddos', kms: 'kms', cdn: 'cdn', es: 'es', emr: 'emr', iot: 'iot', scf: 'scf', cfs: 'cfs', tcr: 'tcr', monitor: 'monitor', audit: 'audit', cls: 'cls' },
  aliyun: { ecs: 'ecs', rds: 'rds', slb: 'slb', disk: 'disk', vpc: 'vpc', eip: 'eip', nat: 'nat', oss: 'oss', redis: 'redis', mongodb: 'mongodb', ack: 'ack', kafka: 'kafka', rocketmq: 'rocketmq', waf: 'waf', ddos: 'ddos', kms: 'kms', cdn: 'cdn', es: 'elasticsearch', emr: 'emr', iot: 'iot', fc: 'fc', nas: 'nas', acr: 'acr', cms: 'cms', actiontrail: 'actiontrail', sls: 'sls' }
}

const getRoute = (provider, type) => {
  return routeMap[provider]?.[type] || type
}

const domainTableData = computed(() => {
  const data = crossCloudData.value
  return Object.entries(data).map(([key, value]) => {
    const h = value.huawei ? Object.values(value.huawei).reduce((s, v) => s + v, 0) : 0
    const t = value.tencent ? Object.values(value.tencent).reduce((s, v) => s + v, 0) : 0
    const a = value.aliyun ? Object.values(value.aliyun).reduce((s, v) => s + v, 0) : 0
    return { domain: value.label, huaweiCount: h, tencentCount: t, aliyunCount: a, total: h + t + a }
  })
})

const refresh = async () => {
  await Promise.all([
    cloudStore.fetchProviderStats(),
    cloudStore.fetchCrossCloudStats()
  ])
}

watch(() => cloudStore.selectedConfName, refresh)
onMounted(refresh)
</script>

<style lang="scss" scoped>
.summary-page {
  .provider-card {
    cursor: pointer;
    display: flex;
    align-items: center;
    padding: 20px;
    transition: all 0.2s;

    &:hover {
      transform: translateY(-2px);
    }

    &.huawei { border-top: 3px solid #F56C6C; }
    &.tencent { border-top: 3px solid #409EFF; }
    &.aliyun { border-top: 3px solid #E6A23C; }

    .card-icon {
      font-size: 48px;
      margin-right: 20px;
      color: #909399;
    }

    .card-value {
      font-size: 36px;
      font-weight: bold;
      color: #303133;
    }

    .card-label {
      font-size: 14px;
      color: #909399;
    }
  }

  .domain-section {
    margin-bottom: 24px;
    padding-bottom: 24px;
    border-bottom: 1px solid #ebeef5;

    &:last-child {
      border-bottom: none;
      margin-bottom: 0;
      padding-bottom: 0;
    }
  }

  .domain-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 12px;
    color: #303133;
  }

  .provider-section {
    padding: 12px;
    background: #f8f9fa;
    border-radius: 8px;
  }

  .provider-title {
    font-size: 14px;
    margin-bottom: 8px;

    &.huawei-text { color: #F56C6C; }
    &.tencent-text { color: #409EFF; }
    &.aliyun-text { color: #E6A23C; }
  }

  .resource-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 4px 0;

    .resource-type {
      font-size: 13px;
      color: #606266;
    }
  }
}
</style>
