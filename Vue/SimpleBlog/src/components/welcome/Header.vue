<template>
  <el-menu :default-active="this.$route.path" router mode="horizontal" background-color="black" text-color="#FFFFFF"
    active-text-color="red" class="header">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name" style="float:right;font-weight: bold;font-size: 20px;color: #FFFFFF;padding-top: 20px;padding: 10px">
      {{ item.navItem }}
    </el-menu-item>
    <span
      style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold; color: #FFFFFF">Simple
      Blog</span>
  </el-menu>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      navList: [{
        name: '/index',
        navItem: '登录',
        icon: 'el-icon-user'
      },
      {
        name: '/regist',
        navItem: '注册',
        icon: ''
      }
      ]
    }
  },
  methods: {
    login () {
      this.$router.push('/home')
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
    min-width: 1537px;
    z-index: 10;
  }

  .el-icon-switch-button {
    cursor: pointer;
    outline: 0;
  }

</style>
