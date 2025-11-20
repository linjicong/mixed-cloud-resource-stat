import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { resolve } from 'path'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
      imports: ['vue', 'vue-router', 'pinia'],
      dts: './src/types/auto-imports.d.ts'
    }),
    Components({
      resolvers: [ElementPlusResolver()],
      dts: './src/types/components.d.ts'
    })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
    extensions: ['.mjs', '.js', '.mts', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
        // 排除静态资源文件，只代理 API 请求
        bypass: (req) => {
          const url = req.url || ''
          // 如果请求的是静态资源文件（图片、字体等），不进行代理
          if (/\.(png|jpg|jpeg|gif|svg|ico|woff|woff2|ttf|eot|webp)$/i.test(url)) {
            return url
          }
          // 如果请求的是源代码模块文件（.js, .vue, .ts 等），不进行代理，让 Vite 处理
          // 这些文件不应该通过 /api 路径访问
          if (/\.(js|vue|ts|jsx|tsx|mjs|cjs)$/i.test(url)) {
            return null
          }
        }
      }
    }
  }
})
