<template>
  <ResourceTable
    provider="aliyun"
    resource-type="cen"
    title="云企业网 CEN"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'CenId', label: '实例ID' },
    { prop: 'Name', label: '名称' },
    { prop: 'Description', label: '描述' },
    { prop: 'Status', label: '状态', type: 'status' },
]
</script>
