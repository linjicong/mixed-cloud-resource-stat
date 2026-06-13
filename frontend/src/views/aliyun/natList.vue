<template>
  <ResourceTable
    provider="aliyun"
    resource-type="nat"
    title="NAT 网关"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'NatGatewayId', label: '网关ID' },
    { prop: 'NatGatewayName', label: '网关名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'VpcId', label: 'VPC ID' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Spec', label: '规格' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
