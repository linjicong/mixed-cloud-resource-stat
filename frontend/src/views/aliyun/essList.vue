<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ess"
    title="弹性伸缩 ESS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'ScalingGroupId', label: '伸缩组ID' },
    { prop: 'ScalingGroupName', label: '伸缩组名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'MinSize', label: '最小实例数' },
    { prop: 'MaxSize', label: '最大实例数' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
