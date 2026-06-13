<template>
  <ResourceTable
    provider="aliyun"
    resource-type="dts"
    title="数据传输服务 DTS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DtsInstanceId', label: '实例ID' },
    { prop: 'DtsJobName', label: '任务名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'DtsJobClass', label: '规格' },
    { prop: 'SourceEndpointEngineName', label: '源引擎' },
    { prop: 'DestEndpointEngineName', label: '目标引擎' },
]
</script>
