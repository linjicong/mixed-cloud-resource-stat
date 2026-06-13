<template>
  <ResourceTable
    provider="aliyun"
    resource-type="kms"
    title="密钥管理服务 KMS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'KeyId', label: '密钥ID' },
    { prop: 'KeyState', label: '状态', type: 'status' },
    { prop: 'KeySpec', label: '密钥规格' },
    { prop: 'Creator', label: '创建者' },
    { prop: 'Description', label: '描述' },
    { prop: 'CreationTime', label: '创建时间', type: 'date' },
]
</script>
