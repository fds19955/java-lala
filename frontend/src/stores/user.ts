import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref('')
    const setToken = (newToken) => {
      token.value = newToken
    }
    const removetoken = () => {
      token.value = ''
    }

    return {
      token,
      setToken,
      removetoken
    }
  },
  {
    persist: true
  }
)
