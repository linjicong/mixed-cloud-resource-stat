<template>
  <ResourceTable
    provider="aliyun"
    resource-type="waf"
    title="Web 应用防火墙 WAF"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PayType', label: '付费类型' },
    { prop: 'ExpireTime', label: '到期时间', type: 'date' },
]
</script>
