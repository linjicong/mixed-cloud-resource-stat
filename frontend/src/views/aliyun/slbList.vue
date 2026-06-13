<template>
  <ResourceTable
    provider="aliyun"
    resource-type="slb"
    title="负载均衡 SLB"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'LoadBalancerId', label: '实例ID' },
    { prop: 'LoadBalancerName', label: '实例名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Address', label: 'IP地址' },
    { prop: 'LoadBalancerStatus', label: '状态', type: 'status' },
    { prop: 'LoadBalancerType', label: '实例类型' },
    { prop: 'AddressType', label: '地址类型' },
    { prop: 'CreateTime', label: '创建时间', type: 'date' },
]
</script>
