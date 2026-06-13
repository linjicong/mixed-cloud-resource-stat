<template>
  <ResourceTable
    provider="aliyun"
    resource-type="disk"
    title="云盘"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DiskId', label: '磁盘ID' },
    { prop: 'DiskName', label: '磁盘名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ZoneId', label: '可用区' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Type', label: '类型' },
    { prop: 'Size', label: '容量(GB)' },
    { prop: 'Category', label: '类别' },
]
</script>
