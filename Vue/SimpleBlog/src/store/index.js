import Vue from 'vue'
import Vuex from 'vuex' // 状态管理模式，管理全局变量

Vue.use(Vuex)

export default new Vuex.Store({ // Vuex存储数据的容器
  state: { // 存储的数据
    userName: window.localStorage.getItem('userName' || '[]') == null ? '' : JSON.parse(localStorage.getItem('userName' || [])),
    adminMenus: []
  },
  mutations: { // 改变数据的方法
    initAdminMenu (state, menus) {
      console.log('init menu')
      state.adminMenus = menus
      console.log(state.adminMenus)
    },
    login (state, userName) {
      state.userName = userName
      window.localStorage.setItem('user', JSON.stringify(userName)) // 将用户信息存储
    },
    logout (state) {
      state.userName = ''
      state.adminMenus = []
      window.localStorage.removeItem('user')
    }
  }
})
