import request from '@/utils/request'

// 等同于下面这样写
// export const userRegisterService = ({ username, password, confirmPassword }) => {
//   return request.post('/api/register', { username, password, confirmPassword } )
// }

// 登录页，注册账号
export const userRegisterService = ({ username, password, confirmPassword }) =>
  request.post('/api/register', { username, password, confirmPassword } )

// 登录页，登录账号
export const userLoginService = ({ username, password }) =>
  request.post('/api/login', { username, password } )
