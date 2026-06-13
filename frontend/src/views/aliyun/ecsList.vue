<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ecs"
    title="云服务器 ECS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ZoneId', label: '可用区' },
    { prop: 'InstanceType', label: '规格' },
    { prop: 'CPU', label: 'CPU核数' },
    { prop: 'Memory', label: '内存(GB)' },
    { prop: 'OSName', label: '操作系统' },
]
</script>
