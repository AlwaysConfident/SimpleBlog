import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/welcome/Welcome'

Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/login/Login')
    },
    {
      path: '/regist',
      name: 'Regist',
      component: () => import('../components/login/Regist')
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../components/pages/Home'),
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'Index',
          component: () => import('../components/home/Index'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/library',
          name: 'LibraryIndex',
          component: () => import('../components/library/LibraryIndex'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../components/admin/AdminIndex'),
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: () => import('../components/admin/dashboard/admin/index'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/admin/content/editor',
      name: 'Editor',
      component: () => import('../components/admin/content/ArticleEditor'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/blog/article',
      name: 'Article',
      component: () => import('../components/blog/ArticleDetails')
    },
    {
      path: '/blog/article/edit',
      name: 'ArticleEdit',
      component: () => import('../components/admin/content/ArticleEditor')
    },
    {
      path: '*',
      name: '404',
      component: () => import('../components/exception/404')
    }
  ]
})

// 用于创建默认路由
export const createRouter = routes => new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/login/Login')
    },
    {
      path: '/regist',
      name: 'Regist',
      component: () => import('../components/login/Regist')
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../components/pages/Home'),
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'Index',
          component: () => import('../components/home/Index'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/library',
          name: 'LibraryIndex',
          component: () => import('../components/library/LibraryIndex'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('../components/admin/AdminIndex'),
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/dashboard',
          name: 'Dashboard',
          component: () => import('../components/admin/dashboard/admin/index'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/admin/content/editor',
      name: 'Editor',
      component: () => import('../components/admin/content/ArticleEditor'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/blog/article',
      name: 'Article',
      component: () => import('../components/blog/ArticleDetails')
    },
    {
      path: '/blog/article/edit',
      name: 'ArticleEdit',
      component: () => import('../components/admin/content/ArticleEditor')
    },
    {
      path: '*',
      name: '404',
      component: () => import('../components/exception/404')
    }
  ]
})
