import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// 1. 导入自动导入插件
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    // 2. 解析 Element Plus 组件，实现自动导入
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    // 3. 自动注册使用的组件
    Components({
      resolvers: [ElementPlusResolver()]
    })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
  // 注：开发环境：server 配置，解决跨域问题，生产环境注释掉
  // server: {
  //   host: '0.0.0.0', // 可选：允许局域网访问
  //   port: 5173, // 可选：明确指定端口
  //   proxy: {
  //     // 简单键匹配：将所有以 /api 开头的请求进行代理
  //     '/api': {
  //       target: 'http://localhost:8080', // 后端API地址
  //       changeOrigin: true, // 修改请求头中的host为目标URL，用于支持虚拟托管的后端
  //       rewrite: (path) => path.replace(/^\/api/, '') // 可选：重写请求路径，移除 /api 前缀
  //     }
  //   }
  // }
})
