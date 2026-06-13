<template>
  <ResourceTable
    provider="aliyun"
    resource-type="config"
    title="配置审计"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ResourceId', label: '资源ID' },
    { prop: 'ResourceName', label: '资源名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'ResourceType', label: '资源类型' },
    { prop: 'ComplianceType', label: '合规类型' },
]
</script>
