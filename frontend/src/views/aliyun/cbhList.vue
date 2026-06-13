<template>
  <ResourceTable
    provider="aliyun"
    resource-type="cbh"
    title="云堡垒机 CBH"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PayType', label: '付费类型' },
    { prop: 'InstanceSpecification', label: '规格' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
    { prop: 'ExpiredTime', label: '到期时间', type: 'date' },
]
</script>
