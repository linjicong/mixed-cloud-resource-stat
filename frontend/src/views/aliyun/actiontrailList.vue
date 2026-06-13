<template>
  <ResourceTable
    provider="aliyun"
    resource-type="actiontrail"
    title="操作审计 ActionTrail"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'TrailName', label: '审计名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'HomeRegion', label: '归属地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'OssBucketName', label: 'OSS存储桶' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
