import axios from 'axios'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建axios实例
const instance = axios.create({
  baseURL: 'http://49.233.185.174:8081', // 后端接口基础路径，直接请求后端
  timeout: 10000 // 超时时间10秒
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 从用户store中获取token
    const userStore = useUserStore()
    if (userStore.token) {
      // 如果有token，添加到请求头
      config.headers.Authorization = userStore.token
    }
    return config
  },
  (err) => {
    return Promise.reject(err)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  (res) => {
    // 处理业务成功 - 根据你的图片，后端返回的code为0表示成功
    if (res.data.code === 200) {
      // 直接返回响应数据
      return res
    } else {
      // 处理业务失败，给错误提示
      // 这里可以根据实际需求使用Element Plus、Ant Design Vue等UI库的提示组件
      // 例如: ElMessage.error(res.data.message || '请求失败')
      ElMessage.error(res.data.message || '请求失败')
      console.error('业务错误:', res.data.message || '请求失败')

      // 抛出一个错误，让后续的catch可以捕获到
      return Promise.reject(res.data)
    }
  },
  (err) => {
    // 处理HTTP错误（如网络错误、401、500等）
    if (err.response) {
      // 服务器响应了错误状态码
      switch (err.response.status) {
        case 401:
          // 未授权，token过期或无效
          const userStore = useUserStore()
          userStore.removetoken() // 清除token
          // 跳转到登录页
          router.push('/login')
          ElMessage.error('登录已过期，请重新登录')
          break
        case 403:
          ElMessage.error('登录已过期，请重新登录')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error('请求失败:', err.message)
      }
    } else if (err.request) {
      // 请求发送了但没有收到响应
      ElMessage.error('网络错误，请检查网络连接')
    } else {
      // 请求配置出错
      ElMessage.error('请求配置错误:', err.message)
    }

    return Promise.reject(err)
  }
)

// 导出实例
export default instance
