<template>
  <div class="provider-overview">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span>{{ providerLabel }}资源总览</span>
          <el-button type="primary" @click="refresh">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>

      <!-- 资源分类卡片 -->
      <div v-for="category in categories" :key="category.key" class="category-section">
        <h3 class="category-title">
          <el-icon><component :is="category.icon" /></el-icon>
          {{ category.label }}
        </h3>
        <el-row :gutter="16">
          <el-col :span="6" v-for="item in category.items" :key="item.type">
            <el-card
              shadow="hover"
              class="resource-card"
              @click="goToDetail(item.route)"
            >
              <div class="resource-name">{{ item.label }}</div>
              <div class="resource-count">
                <span class="count-value">{{ getCount(item.type) }}</span>
                <span class="count-unit">个实例</span>
              </div>
              <div class="resource-type">{{ item.type }}</div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useCloudStore } from '@/stores/cloud'

const props = defineProps({
  provider: { type: String, required: true },
  providerLabel: { type: String, required: true },
  categories: { type: Array, required: true }
})

const router = useRouter()
const cloudStore = useCloudStore()
const stats = computed(() => cloudStore.providerStats?.[props.provider] || {})

const getCount = (type) => stats.value[type] || 0

const goToDetail = (route) => {
  router.push(route)
}

const refresh = async () => {
  await cloudStore.fetchProviderStats()
}

watch(() => cloudStore.selectedConfName, refresh)
onMounted(refresh)
</script>

<style lang="scss" scoped>
.provider-overview {
  .category-section {
    margin-bottom: 28px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .category-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    gap: 6px;
  }

  .resource-card {
    cursor: pointer;
    margin-bottom: 16px;
    transition: all 0.2s;

    &:hover {
      transform: translateY(-2px);
      border-color: #409EFF;
    }

    .resource-name {
      font-size: 14px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
    }

    .resource-count {
      .count-value {
        font-size: 28px;
        font-weight: bold;
        color: #409EFF;
      }

      .count-unit {
        font-size: 12px;
        color: #909399;
        margin-left: 4px;
      }
    }

    .resource-type {
      font-size: 12px;
      color: #C0C4CC;
      margin-top: 4px;
    }
  }
}
</style>
