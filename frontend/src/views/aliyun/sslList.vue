<template>
  <ResourceTable
    provider="aliyun"
    resource-type="ssl"
    title="SSL 证书"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'CertId', label: '证书ID' },
    { prop: 'CertName', label: '证书名称' },
    { prop: 'Domain', label: '域名' },
    { prop: 'Issuer', label: '颁发者' },
    { prop: 'Status', label: '状态', type: 'status' },
    { prop: 'StartDate', label: '生效时间', type: 'date' },
    { prop: 'EndDate', label: '到期时间', type: 'date' },
]
</script>
