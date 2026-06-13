<template>
  <ResourceTable
    provider="aliyun"
    resource-type="sae"
    title="Serverless 应用引擎 SAE"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'AppId', label: '应用ID' },
    { prop: 'AppName', label: '应用名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'CurrentStatus', label: '状态', type: 'status' },
    { prop: 'Cpu', label: 'CPU核数' },
    { prop: 'Memory', label: '内存(MB)' },
]
</script>
