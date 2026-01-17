import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/login', component: () => import('@/views/login/loginpage.vue') },
    {
      path: '/',
      component: () => import('@/views/layout/layoutcontainer.vue'),
      redirect: '/article/manage',
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

export default router
