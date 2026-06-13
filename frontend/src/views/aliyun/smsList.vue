<template>
  <ResourceTable
    provider="aliyun"
    resource-type="sms"
    title="短信服务 SMS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'SignName', label: '签名名称' },
    { prop: 'AuditStatus', label: '审核状态', type: 'status' },
    { prop: 'CreateDate', label: '创建时间', type: 'date' },
]
</script>
