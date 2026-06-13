<template>
  <ResourceTable
    provider="aliyun"
    resource-type="emr"
    title="大数据 EMR"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'Id', label: '集群ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'ClusterType', label: '集群类型' },
    { prop: 'ChargeType', label: '付费类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
