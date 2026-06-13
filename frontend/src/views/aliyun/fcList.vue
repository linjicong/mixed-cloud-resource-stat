<template>
  <ResourceTable
    provider="aliyun"
    resource-type="fc"
    title="函数计算 FC"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'FunctionName', label: '函数名称' },
    { prop: 'Description', label: '描述' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Runtime', label: '运行时' },
    { prop: 'Handler', label: '处理器' },
    { prop: 'Timeout', label: '超时(秒)' },
    { prop: 'MemorySize', label: '内存(MB)' },
]
</script>
