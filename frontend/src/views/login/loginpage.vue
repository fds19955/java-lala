<script setup>
import { UserFilled, View, Back } from '@element-plus/icons-vue'
import { userRegisterService, userLoginService } from '@/api/user'
import { useUserStore } from '@/stores/index'
import { ref, reactive } from 'vue'
import { watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 思路：1 每个item的校验，标准校验和自定义校验 2 表单校验，全是空不能登录注册，和表单绑定ref,调用方法

// 控制当前显示的是登录页还是注册页
const isLoginPage = ref(true)
const toChangePage = () => {
  isLoginPage.value = !isLoginPage.value
}
watch(isLoginPage, () => {
  // 重置登录表单
  formlogin.value = {
    username: '',
    password: ''
  }

  // 重置注册表单
  formregister.value = {
    username: '',
    password: '',
    confirmPassword: ''
  }
})

// 登录表单数据
const formlogin = ref({
  username: '',
  password: ''
})

// 注册表单数据
const formregister = ref({
  username: '',
  password: '',
  confirmPassword: ''
})

// 表单引用，用来拿到组件实例，渲染完成后调用组件里面的方法
const loginFormRef = ref()
const registerFormRef = ref()

// 登录表单校验规则
const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: '用户名为3-10位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6-15位的非空字符',
      trigger: 'blur'
    }
  ]
}

// 注册表单校验规
const rules = {
  // 常规校验
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: '用户名为3-10位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6-15位的非空字符',
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6-15位的非空字符',
      trigger: 'blur'
    },
    // 自定义校验
    {
      validator: (rule, value, callback) => {
        if (!value) {
          callback(new Error('请再次输入密码'))
          return
        }
        if (value !== formregister.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const userStore = useUserStore()

// 登录处理函数
const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    await loginFormRef.value.validate()
    const res = await userLoginService(formlogin.value)
    ElMessage.success('登录成功！')
    userStore.setToken(res.data.data)
    console.log('登录成功,token是:', res.data.data)
    router.push('/')
    // console.log(res)
  } catch (error) {
    ElMessage.error('请正确填写表单')
  }
}

// 注册处理函数
const handleRegister = async () => {
  if (!registerFormRef.value) return

  try {
    // 这里拿到了整个el-form的实例了，调用里面的方法，注意ref的绑定实例必须在渲染完成后
    await registerFormRef.value.validate()
    await userRegisterService(formregister.value)
    ElMessage.success('注册成功！')
    // 这里可以添加实际注册逻辑，如调用API
    console.log('注册数据:', formregister.value)
    // 注册成功后切换到登录页
    isLoginPage.value = true
  } catch (error) {
    ElMessage.error('请正确填写表单')
  }
}
</script>
<template>
  <el-row class="login-page">
    <el-col :span="12" class="background"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 登录表单 -->
      <el-form
        v-if="isLoginPage"
        :model="formlogin"
        :rules="loginRules"
        ref="loginFormRef"
      >
        <el-form-item class="el-form-login">
          <h2>登录</h2>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            :prefix-icon="UserFilled"
            v-model="formlogin.username"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :prefix-icon="View"
            type="password"
            placeholder="请输入密码"
            v-model="formlogin.password"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item class="zhuce">
          <!-- 普通按钮的默认行为：普通 <button>元素在表单中默认类型是 type="submit"，会触发表单的默认提交行为，
            这可能与 Element Plus 的验证机制冲突 -->
          <!-- <button class="mybutton" @click="handleLogin">登录</button> -->
          <el-button
            class="mybutton"
            type="primary"
            @click="handleLogin"
            style="width: 100%"
            >登录</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            link
            type="primary"
            class="back-button"
            @click="toChangePage"
          >
            <span>没有账号？立即注册</span>
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 整个注册页表单 -->
      <el-form
        :model="formregister"
        :rules="rules"
        ref="registerFormRef"
        v-else
      >
        <el-form-item class="el-form-login">
          <h2>注册</h2>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            :prefix-icon="UserFilled"
            v-model="formregister.username"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :prefix-icon="View"
            placeholder="请输入密码"
            v-model="formregister.password"
            type="password"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            :prefix-icon="View"
            placeholder="请再次输入密码"
            v-model="formregister.confirmPassword"
            type="password"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item class="zhuce">
          <!-- 普通按钮的默认行为：普通 <button>元素在表单中默认类型是 type="submit"，会触发表单的默认提交行为，
            这可能与 Element Plus 的验证机制冲突 -->
          <!-- <button class="mybutton" @click="handleRegister">注册</button> -->
          <el-button
            class="mybutton"
            type="primary"
            @click="handleRegister"
            style="width: 100%"
            >注册</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            link
            type="primary"
            class="back-button"
            @click="toChangePage"
          >
            <el-icon><Back /></el-icon>
            <span>返回</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style scoped>
.login-page {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.background {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  height: 100vh;
  border-radius: 10px 0 0 10px;
}

.form {
  padding: 40px;
  background: white;
  border-radius: 0 10px 10px 0;
  box-shadow:
    0 15px 35px rgba(50, 50, 93, 0.1),
    0 5px 15px rgba(0, 0, 0, 0.07);
  height: 70vh;
  display: flex;
  align-items: center;
}

.el-form-login {
  text-align: center;
  margin-bottom: 30px;
}

.el-form-login h2 {
  color: #333;
  margin: 0;
}

.zhuce {
  margin-top: 30px;
}

.mybutton {
  width: 100%;
  padding: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.mybutton:hover {
  background-color: #66b1ff;
}

.back-button {
  color: #409eff !important;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

@media (max-width: 768px) {
  .background {
    display: none;
  }

  .form {
    width: 90%;
    margin: 0 auto;
    border-radius: 10px;
  }
}
</style>
