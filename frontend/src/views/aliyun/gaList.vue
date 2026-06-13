<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ga"
    title="全球加速 GA"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'AcceleratorId', label: '加速器ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'State', label: '状态', type: 'status' },
    { prop: 'AcceleratorArea', label: '加速区域' },
    { prop: 'PaymentType', label: '付费类型' },
]
</script>
