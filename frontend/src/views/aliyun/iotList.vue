<template>
  <ResourceTable
    provider="aliyun"
    resource-type="iot"
    title="物联网平台 IoT"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ProductKey', label: '产品Key' },
    { prop: 'ProductName', label: '产品名称' },
    { prop: 'NodeType', label: '节点类型' },
    { prop: 'CategoryName', label: '品类名称' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
