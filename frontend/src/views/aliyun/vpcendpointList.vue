<template>
  <ResourceTable
    provider="aliyun"
    resource-type="vpcendpoint"
    title="VPC 终端节点"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'EndpointId', label: '终端节点ID' },
    { prop: 'EndpointName', label: '终端节点名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'VpcId', label: 'VPC ID' },
    { prop: 'EndpointType', label: '类型' },
    { prop: 'ConnectionStatus', label: '连接状态' },
    { prop: 'EndpointStatus', label: '状态', type: 'status' },
]
</script>
