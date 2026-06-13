<template>
  <ResourceTable
    provider="aliyun"
    resource-type="oos"
    title="运维编排 OOS"
    :columns="columns"
  />
</template>

<script setup>
import ResourceTable from '@/components/ResourceTable.vue'

const columns = [
    { prop: 'TemplateName', label: '模板名称' },
    { prop: 'TemplateVersion', label: '版本' },
    { prop: 'Description', label: '描述' },
    { prop: 'TemplateType', label: '类型' },
    { prop: 'CreatedDate', label: '创建时间', type: 'date' },
    { prop: 'ShareType', label: '共享类型' },
]
</script>
