<template>
  <ResourceTable
    provider="aliyun"
    resource-type="kafka"
    title="消息队列 Kafka"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ServiceStatus', label: '状态', type: 'status' },
    { prop: 'DeployType', label: '部署类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
    { prop: 'ExpireTime', label: '到期时间', type: 'date' },
]
</script>
