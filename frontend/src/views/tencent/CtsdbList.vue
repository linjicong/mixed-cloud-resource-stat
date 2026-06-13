<template>
  <ResourceTable
    provider="tencent"
    resource-type="ctsdb"
    title="时序数据库 CTSDB"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'id', label: 'ID' },
    { prop: 'name', label: '名称' },
    { prop: 'status', label: '状态', type: 'status' },
    { prop: 'createTime', label: '创建时间', type: 'date' }
]
</script>
