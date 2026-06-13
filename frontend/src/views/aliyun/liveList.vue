<template>
  <ResourceTable
    provider="aliyun"
    resource-type="live"
    title="视频直播 Live"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'DomainName', label: '域名' },
    { prop: 'DomainType', label: '域名类型' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Description', label: '描述' },
]
</script>
