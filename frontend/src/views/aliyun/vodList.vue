<template>
  <ResourceTable
    provider="aliyun"
    resource-type="vod"
    title="视频点播 VOD"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'VideoId', label: '视频ID' },
    { prop: 'Title', label: '标题' },
    { prop: 'RegionId', label: '地域' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'Duration', label: '时长' },
    { prop: 'Size', label: '大小' },
    { prop: 'CoverURL', label: '封面URL' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
