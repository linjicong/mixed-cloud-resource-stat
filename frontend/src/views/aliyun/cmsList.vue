<template>
  <ResourceTable
    provider="aliyun"
    resource-type="cms"
    title="云监控 CMS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'GroupId', label: '分组ID' },
    { prop: 'GroupName', label: '分组名称' },
    { prop: 'Type', label: '类型' },
    { prop: 'ServiceId', label: '服务ID' },
]
</script>
