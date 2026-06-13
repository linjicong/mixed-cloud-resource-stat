<template>
  <ResourceTable
    provider="aliyun"
    resource-type="mongodb"
    title="云数据库 MongoDB"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DBInstanceId', label: '实例ID' },
    { prop: 'DBInstanceDescription', label: '实例描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'DBInstanceStatus', label: '状态', type: 'status' },
    { prop: 'Engine', label: '引擎' },
    { prop: 'EngineVersion', label: '引擎版本' },
    { prop: 'DBInstanceType', label: '实例类型' },
]
</script>
