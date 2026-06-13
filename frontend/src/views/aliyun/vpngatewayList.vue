<template>
  <ResourceTable
    provider="aliyun"
    resource-type="vpngateway"
    title="VPN 网关"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'VpnGatewayId', label: '网关ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Spec', label: '规格' },
    { prop: 'MaxConnections', label: '最大连接数' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
    { prop: 'EndTime', label: '到期时间', type: 'date' },
]
</script>
