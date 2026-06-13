<template>
  <ResourceTable
    provider="aliyun"
    resource-type="dsc"
    title="数据安全中心 DSC"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'InstanceId', label: '实例ID' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'BackupPlanName', label: '备份计划名称' },
    { prop: 'BackupMethod', label: '备份方式' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'PayType', label: '付费类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
