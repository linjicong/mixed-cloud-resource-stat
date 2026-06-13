<template>
  <ResourceTable
    provider="aliyun"
    resource-type="securitygroup"
    title="安全组"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'SecurityGroupId', label: '安全组ID' },
    { prop: 'SecurityGroupName', label: '安全组名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'VpcId', label: 'VPC ID' },
    { prop: 'Description', label: '描述' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
