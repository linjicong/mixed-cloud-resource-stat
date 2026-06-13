<template>
  <ResourceTable
    provider="aliyun"
    resource-type="sls"
    title="日志服务 SLS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ProjectName', label: '项目名称' },
    { prop: 'Description', label: '描述' },
    { prop: 'Region', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
