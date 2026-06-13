<template>
  <ResourceTable
    provider="aliyun"
    resource-type="apigateway"
    title="API 网关"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ApiGroupId', label: '分组ID' },
    { prop: 'GroupName', label: '分组名称' },
    { prop: 'SubDomain', label: '子域名' },
    { prop: 'Description', label: '描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'CreatedTime', label: '创建时间', type: 'date' },
]
</script>
