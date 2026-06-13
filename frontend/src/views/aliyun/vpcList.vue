<template>
  <ResourceTable
    provider="aliyun"
    resource-type="vpc"
    title="专有网络 VPC"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'VpcId', label: 'VPC ID' },
    { prop: 'VpcName', label: 'VPC名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'CidrBlock', label: 'CIDR' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Description', label: '描述' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
