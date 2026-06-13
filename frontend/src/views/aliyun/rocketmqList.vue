<template>
  <ResourceTable
    provider="aliyun"
    resource-type="rocketmq"
    title="消息队列 RocketMQ"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
