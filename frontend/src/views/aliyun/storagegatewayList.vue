<template>
  <ResourceTable
    provider="aliyun"
    resource-type="storagegateway"
    title="存储网关"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'GatewayId', label: '网关ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Type', label: '类型' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PaymentType', label: '付费类型' },
]
</script>
