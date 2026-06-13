<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ack"
    title="容器服务 ACK"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ClusterId', label: '集群ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'State', label: '状态', type: 'status' },
    { prop: 'ClusterType', label: '集群类型' },
    { prop: 'ClusterSpec', label: '集群规格' },
    { prop: 'CurrentVersion', label: '当前版本' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
