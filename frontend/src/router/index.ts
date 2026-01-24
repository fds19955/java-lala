import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: () => import('@/views/login/loginpage.vue'),
      meta: { requiresAuth: false } // 明确标记不需要认证
    },
    {
      path: '/',
      component: () => import('@/views/layout/layoutcontainer.vue'),
      redirect: '/article/manage',
      meta: { requiresAuth: true }, // 标记该路由及其所有子路由都需要认证
      children: [
        {
          path: '/article/manage',
          component: () => import('@/views/article/articlemanage.vue')
        },
        {
          path: '/article/channel',
          component: () => import('@/views/article/articlechannel.vue')
        },
        {
          path: '/user/profile',
          component: () => import('@/views/user/userprofile.vue')
        },
        {
          path: '/user/avatar',
          component: () => import('@/views/user/useravatar.vue')
        },
        {
          path: '/user/password',
          component: () => import('@/views/user/userpassword.vue')
        }
      ]
    }
  ]
})

// 全局前置路由守卫
router.beforeEach((to, from, next) => {
  // 检查目标路由是否需要认证
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    // 获取token，可以从localStorage中获取
    const token = localStorage.getItem('user')

    if (!token) {
      // 如果没有token，重定向到登录页，并记录原本要去的页面
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      // 有token，允许访问
      next()
    }
  } else {
    // 不需要认证的路由，直接放行
    next()
  }
})

export default router
