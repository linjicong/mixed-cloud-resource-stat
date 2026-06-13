<template>
  <ResourceTable
    provider="aliyun"
    resource-type="alb"
    title="应用型负载均衡 ALB"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'LoadBalancerId', label: '负载均衡ID' },
    { prop: 'LoadBalancerName', label: '负载均衡名称' },
    { prop: 'DNSName', label: 'DNS名称' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'VpcId', label: 'VPC ID' },
    { prop: 'LoadBalancerType', label: '类型' },
    { prop: 'AddressType', label: '地址类型' },
    { prop: 'LoadBalancerStatus', label: '状态', type: 'status' },
]
</script>
