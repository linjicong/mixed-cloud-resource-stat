<template>
  <ResourceTable
    provider="aliyun"
    resource-type="nas"
    title="文件存储 NAS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'FileSystemId', label: '文件系统ID' },
    { prop: 'Description', label: '描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'StorageType', label: '存储类型' },
    { prop: 'Capacity', label: '容量(MB)' },
    { prop: 'EncryptType', label: '加密类型' },
]
</script>
