<template>
  <ResourceTable
    provider="aliyun"
    resource-type="cdn"
    title="内容分发 CDN"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DomainName', label: '域名' },
    { prop: 'DomainStatus', label: '状态', type: 'status' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ChargeType', label: '付费类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
