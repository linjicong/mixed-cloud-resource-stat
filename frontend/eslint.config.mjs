import { readFileSync } from 'node:fs'
import js from '@eslint/js'
import pluginVue from 'eslint-plugin-vue'
import globals from 'globals'

/**
 * unplugin-auto-import 在构建期注入的全局（ref/computed/useRoute/ElMessage ...）。
 * 直接从生成产物 src/types/auto-imports.d.ts 读取，避免手写清单与配置漂移。
 */
function readAutoImportGlobals() {
  try {
    const dts = readFileSync(new URL('./src/types/auto-imports.d.ts', import.meta.url), 'utf8')
    return Object.fromEntries([...dts.matchAll(/^ {2}const (\w+):/gm)].map((m) => [m[1], 'readonly']))
  } catch {
    return {}
  }
}

const autoImportGlobals = readAutoImportGlobals()

export default [
  {
    name: 'app/ignores',
    ignores: [
      'dist/**',
      'node_modules/**',
      'coverage/**',
      '**/*.min.js',
      // 由 unplugin 生成，勿手改
      'src/types/**'
    ]
  },

  js.configs.recommended,

  ...pluginVue.configs['flat/recommended'],

  // vue/recommended 里的排版类规则交给编辑器/格式化工具，lint 只关注正确性
  pluginVue.configs['no-layout-rules'],

  {
    name: 'app/language-options',
    files: ['**/*.{js,mjs,cjs,vue}'],
    languageOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
      globals: {
        ...globals.browser,
        ...globals.node,
        ...autoImportGlobals,
        // <script setup> 编译宏
        defineProps: 'readonly',
        defineEmits: 'readonly',
        defineExpose: 'readonly',
        defineOptions: 'readonly',
        defineSlots: 'readonly',
        withDefaults: 'readonly'
      }
    }
  },

  {
    name: 'app/rules',
    files: ['**/*.{js,mjs,cjs,vue}'],
    rules: {
      'vue/multi-word-component-names': 'off',
      'no-console': 'off',
      'no-debugger': 'warn'
    }
  }
]
