<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const userInfo = ref({
  username: '用户',
  avatar: ''
})

const currentPageTitle = computed(() => {
  const titleMap = {
    '/article/manage': '文章管理',
    '/article/channel': '栏目管理',
    '/user/profile': '个人资料',
    '/user/avatar': '头像设置',
    '/user/password': '修改密码'
  }
  return titleMap[route.path] || '首页'
})

const handleUserCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      localStorage.removeItem('token')
      router.push('/login')
      ElMessage.success('退出成功')
    })
  } else if (command === 'profile') {
    router.push('/user/profile')
  }
}

onMounted(() => {
  const savedUser = localStorage.getItem('user')
  if (savedUser) {
    userInfo.value = { ...userInfo.value, ...JSON.parse(savedUser) }
  }
})
</script>

<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside width="220px" class="aside-container">
      <div class="logo-container">
        <h2>文章管理系统</h2>
      </div>

      <!-- 导航菜单 -->
      <el-menu
        :default-active="$route.path"
        router
        class="side-menu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/article/manage">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>

        <el-menu-item index="/article/channel">
          <el-icon><FolderOpened /></el-icon>
          <span>栏目管理</span>
        </el-menu-item>

        <el-sub-menu index="user-center">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户中心</span>
          </template>
          <el-menu-item index="/user/profile">个人资料</el-menu-item>
          <el-menu-item index="/user/avatar">头像设置</el-menu-item>
          <el-menu-item index="/user/password">修改密码</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶部头部 -->
      <el-header class="header-container">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentPageTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-dropdown @command="handleUserCommand">
            <span class="user-info">
              <el-avatar :size="32" :src="userInfo.avatar">{{ userInfo.username?.charAt(0) }}</el-avatar>
              <span>{{ userInfo.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主内容区域 -->
      <el-main class="main-container">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside-container {
  background-color: #304156;
}

.logo-container {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  border-bottom: 1px solid #263445;
}

.side-menu {
  border: none;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.main-container {
  background: #f5f7f9;
  padding: 20px;
}
</style>
