<template>
  <ResourceTable
    provider="aliyun"
    resource-type="quota"
    title="配额中心"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'QuotaId', label: '配额ID' },
    { prop: 'QuotaName', label: '配额名称' },
    { prop: 'ProductCode', label: '产品代码' },
    { prop: 'QuotaUnit', label: '配额单位' },
    { prop: 'QuotaRange', label: '配额范围' },
]
</script>
