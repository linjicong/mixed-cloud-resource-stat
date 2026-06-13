<template>
  <ResourceTable
    provider="aliyun"
    resource-type="oss"
    title="对象存储 OSS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'Name', label: '存储桶名称' },
    { prop: 'Location', label: '地域' },
    { prop: 'CreationDate', label: '创建时间', type: 'date' },
    { prop: 'StorageClass', label: '存储类型' },
    { prop: 'ExtranetEndpoint', label: '外网Endpoint' },
]
</script>
