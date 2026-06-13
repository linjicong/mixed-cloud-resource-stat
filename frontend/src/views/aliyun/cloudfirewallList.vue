<template>
  <ResourceTable
    provider="aliyun"
    resource-type="cloudfirewall"
    title="云防火墙"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Type', label: '类型' },
    { prop: 'IpAddress', label: 'IP地址' },
]
</script>
