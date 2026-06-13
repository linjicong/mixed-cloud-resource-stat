<template>
  <ResourceTable
    provider="aliyun"
    resource-type="maxcompute"
    title="大数据计算 MaxCompute"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'Name', label: '项目名称' },
    { prop: 'DisplayName', label: '显示名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Owner', label: '所有者' },
    { prop: 'CreatedTime', label: '创建时间', type: 'date' },
]
</script>
