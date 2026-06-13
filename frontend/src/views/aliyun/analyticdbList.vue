<template>
  <ResourceTable
    provider="aliyun"
    resource-type="analyticdb"
    title="分析型数据库 AnalyticDB"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DBClusterId', label: '集群ID' },
    { prop: 'DBClusterDescription', label: '集群描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'DBClusterStatus', label: '状态', type: 'status' },
    { prop: 'Engine', label: '引擎' },
    { prop: 'EngineVersion', label: '引擎版本' },
]
</script>
