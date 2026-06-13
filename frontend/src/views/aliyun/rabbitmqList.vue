<template>
  <ResourceTable
    provider="aliyun"
    resource-type="rabbitmq"
    title="消息队列 RabbitMQ"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'InstanceType', label: '实例类型' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PayType', label: '付费类型' },
]
</script>
