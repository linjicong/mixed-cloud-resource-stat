<template>
  <ResourceTable
    provider="aliyun"
    resource-type="mse"
    title="微服务引擎 MSE"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'ClusterName', label: '集群名称' },
    { prop: 'ClusterAliasName', label: '集群别名' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ClusterType', label: '集群类型' },
    { prop: 'Status', label: '状态', type: 'status' },
]
</script>
