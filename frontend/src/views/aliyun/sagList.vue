<template>
  <ResourceTable
    provider="aliyun"
    resource-type="sag"
    title="智能接入网关 SAG"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'SmartAGId', label: '网关ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'MaxBandwidth', label: '最大带宽' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
