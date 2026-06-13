<template>
  <ResourceTable
    provider="aliyun"
    resource-type="dns_records"
    title="DNS 解析记录"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'RR', label: '主机记录' },
    { prop: 'Type', label: '记录类型' },
    { prop: 'Value', label: '记录值' },
    { prop: 'TTL', label: 'TTL' },
]
</script>
