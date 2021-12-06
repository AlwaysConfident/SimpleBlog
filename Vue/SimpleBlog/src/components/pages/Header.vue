<template>
  <el-menu :default-active="this.$route.path" router mode="horizontal" background-color="black" text-color="#FFFFFF"
    active-text-color="red" class="header">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <i class="el-icon-switch-button" @click="logout"
      style="float:right;font-size: 40px;color: #FFFFFF;padding: 10px"></i>
    <a href="#nowhere" style="color: #FFFFFF;float: right;padding: 20px;">更多功能</a>
    <i class="el-icon-menu" style="float:right;font-size: 45px;color: #FFFFFF;padding-top: 8px"></i>
    <span
      style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold; color: #FFFFFF">Simple
      Blog</span>
  </el-menu>
</template>

<script>
import { createRouter } from '../../router'
export default {
  name: 'Header',
  data () {
    return {
      navList: [{
        name: '/index',
        navItem: '首页'
      },
      {
        name: '/jotter',
        navItem: '笔记本'
      },
      {
        name: '/library',
        navItem: '图书馆'
      },
      {
        name: '/admin',
        navItem: '个人中心'
      }
      ]
    }
  },
  methods: {
    logout () {
      var _this = this
      this.$axios.get('/logout')
        .then(res => {
          if (res.data.responseCode === 200) {
            console.log('logout')
            _this.$store.commit('logout')
            _this.$router.replace('/login')

            const newRouter = createRouter()
            _this.$router.matcher = newRouter.matcher
          }
        })
        .catch(err => {
          console.error(err)
        })
    }
  }
}

</script>

<style scoped>
  a {
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }

  .header {
    position: fixed;
    width: 100%;
    z-index: 10;
  }

  .el-icon-switch-button {
    cursor: pointer;
    outline: 0;
  }

</style>
