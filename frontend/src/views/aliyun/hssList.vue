<template>
  <ResourceTable
    provider="aliyun"
    resource-type="hss"
    title="主机安全 HSS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Ip', label: 'IP地址' },
    { prop: 'OsType', label: '操作系统类型' },
    { prop: 'OsName', label: '操作系统名称' },
    { prop: 'Status', label: '状态', type: 'status' },
]
</script>
