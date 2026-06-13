<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ddos"
    title="DDoS 防护"
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
    { prop: 'InstanceType', label: '实例类型' },
    { prop: 'IpType', label: 'IP类型' },
    { prop: 'ExpireTime', label: '到期时间', type: 'date' },
]
</script>
