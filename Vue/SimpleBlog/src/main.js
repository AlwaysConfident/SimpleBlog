// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import store from './store'

// 设置反向代理，前端请求默认发送到http://localhost:8080/api
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8080/api'
axios.defaults.withCredentials = true

Vue.use(ElementUI)
Vue.use(mavonEditor)

// 全局注册，之后可以在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false // 阻止Vue运行时输出生产信息

router.beforeEach((to, from, next) => { // 全局前置路由守卫
  console.log('beforeEach')
  console.log(localStorage.getItem('user'))
  if (localStorage.getItem('user') && to.path.startsWith('/admin')) { // 动态初始化后台管理页
    console.log('before init')
    initAdminMenu(router, store)
  }
  if (localStorage.getItem('user') && to.path.startsWith('/login')) { // 已登录情况下访问login页面直接跳转到后台页面
    next({
      path: '/admin/dashboard'
    })
  }
  if (to.meta.requireAuth) {
    console.log('need auth')
    if (localStorage.getItem('user')) {
      console.log('logged')
      axios.get('/auth').then(resp => { // 每个页面都向后端发送请求，验证登录状态，防止伪造参数绕过前端登录拦截
        if (resp.status === 200) {
          console.log('auth')
          next()
        }
      }).catch((error) => {
        if (error.data.message === 'unauth') {
          console.log(localStorage.getItem('user'))
          next({ path: '/login' })
        }
      })
    } else {
      next({
        path: 'login',
        query: { redirect: to.fullPath } // 存储路径，登录成功后跳转
      })
    }
  } else {
    next()
  }
})

const formatRoutes = (routes) => { // routes代表从后端获取的菜单列表
  let fmtRoutes = []
  routes.forEach(route => { // 遍历列表
    if (route.children) { // 判断一条菜单项是否含有子项，有则进行递归处理
      route.children = formatRoutes(route.children)
    }

    let fmtRoute = { // 把路由的属性与菜单项的属性对应起来
      path: route.path,
      component: resolve => { // component属性为对象，需要根据名称做出解析，即获取对象引用，把组件导入进来
        require(['./components/admin/' + route.component + '.vue'], resolve)
      },
      name: route.name,
      nameZH: route.nameZH,
      iconCls: route.iconCls,
      children: route.children
    }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}

const initAdminMenu = (router, store) => {
  if (store.state.adminMenus.length > 0) {
    return
  }
  axios.get('/menu').then(resp => {
    if (resp && resp.status === 200) {
      var fmtRoutes = formatRoutes(resp.data)
      for (let i = 0, length = fmtRoutes.length; i < length; i += 1) {
        const element = fmtRoutes[i]
        router.addRoute(element)
      }
      console.log('after init')
      console.log(fmtRoutes)
      // router.addRoute(fmtRoutes)
      store.commit('initAdminMenu', fmtRoutes)
    }
  })
}

/* eslint-disable no-new */
new Vue({// 创建Vue对象
  el: '#app', // 提供一个Dom上已存在的对象给Vue挂载，与index.html对应
  router, // 对象包含路由
  store, // 对象包含Vuex
  components: { App }, // 对象所含有的组件
  template: '<App/>'// 使用该对象的模板
})
