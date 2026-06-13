<template>
  <ResourceTable
    provider="aliyun"
    resource-type="acr"
    title="容器镜像服务 ACR"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'InstanceName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'InstanceSpecification', label: '规格' },
    { prop: 'InstanceType', label: '实例类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
    { prop: 'ModifiedTime', label: '修改时间', type: 'date' },
]
</script>
