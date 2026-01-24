// eslint config rules
import { globalIgnores } from 'eslint/config'
import { defineConfigWithVueTs, vueTsConfigs } from '@vue/eslint-config-typescript'
import pluginVue from 'eslint-plugin-vue'
import prettierConfig from 'eslint-config-prettier'
import prettierPlugin from 'eslint-plugin-prettier'

// To allow more languages other than 'ts' in '.vue' files, uncomment the following line:
// import { configureVueProject } from '@vue/eslint-config-typescript';
// configureVueProject({ scriptLangs: ['ts', 'tsx'] });
// More info at https://github.com/vuejs/eslint-config-typescript/#advanced-setup

export default defineConfigWithVueTs(
  {
    name: 'app/files-to-lint',
    files: ['**/*.ts', '**/*.mts', '**/*.tsx', '**/*.vue'],
  },
  // ✅ 修正：添加缺失的引号
  globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**']),
  pluginVue.configs['flat/essential'],
  vueTsConfigs.recommended,
  // ✅ 修正：简化 Prettier 配置
  prettierConfig,
  {
    plugins: {
      prettier: prettierPlugin,
    },
    rules: {
      'prettier/prettier': [
        'warn',
        {
          singleQuote: true,
          semi: false,
          printWidth: 80,
          trailingComma: 'none',
          endOfLine: 'auto',
        },
      ],
      'vue/multi-word-component-names': [
        'warn',
        {
          ignores: ['index'],
        },
      ],
      // 允许 props 解构
      'vue/no-setup-props-destructure': ['off'],
      // 自定义规则
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    },
  },
)

// import { globalIgnores } from 'eslint/config'
// import { defineConfigWithVueTs, vueTsConfigs } from '@vue/eslint-config-typescript'
// import pluginVue from 'eslint-plugin-vue'
// import skipFormatting from '@vue/eslint-config-prettier/skip-formatting'

// // To allow more languages other than `ts` in `.vue` files, uncomment the following lines:
// // import { configureVueProject } from '@vue/eslint-config-typescript'
// // configureVueProject({ scriptLangs: ['ts', 'tsx'] })
// // More info at https://github.com/vuejs/eslint-config-typescript/#advanced-setup

// export default defineConfigWithVueTs(
//   {
//     name: 'app/files-to-lint',
//     files: ['**/*.{ts,mts,tsx,vue}'],
//   },

//   globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**']),

//   pluginVue.configs['flat/essential'],
//   vueTsConfigs.recommended,
//   skipFormatting,
// )
