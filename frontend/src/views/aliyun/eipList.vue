<template>
  <ResourceTable
    provider="aliyun"
    resource-type="eip"
    title="弹性公网IP EIP"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'AllocationId', label: '分配ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'IpAddress', label: 'IP地址' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Bandwidth', label: '带宽(Mbps)' },
]
</script>
