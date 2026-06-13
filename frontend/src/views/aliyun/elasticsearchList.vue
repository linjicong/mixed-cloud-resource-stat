<template>
  <ResourceTable
    provider="aliyun"
    resource-type="elasticsearch"
    title="Elasticsearch"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'Description', label: '描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PaymentType', label: '付费类型' },
    { prop: 'CreatedAt', label: '创建时间', type: 'date' },
    { prop: 'EndTime', label: '到期时间', type: 'date' },
]
</script>
