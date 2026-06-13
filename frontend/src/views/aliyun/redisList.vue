<template>
  <ResourceTable
    provider="aliyun"
    resource-type="redis"
    title="云数据库 Redis"
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
    { prop: 'Capacity', label: '容量(MB)' },
    { prop: 'InstanceClass', label: '实例规格' },
    { prop: 'EngineVersion', label: '引擎版本' },
]
</script>
