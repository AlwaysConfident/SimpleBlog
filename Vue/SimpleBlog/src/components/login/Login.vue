<template>

  <body id="poster">
    <el-form :model="loginForm" :rules="rules" ref="loginForm" class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系统登录</h3>
      <el-form-item prop="name">
        <el-input type="text" v-model="loginForm.name" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="pwd">
        <el-input type="password" v-model="loginForm.pwd" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  data () {
    return {
      loginForm: {
        name: '',
        pwd: ''
      },
      response: [],
      rules: {
        name: [{
          required: true,
          message: '请输入用户名称',
          trigger: 'blur'
        },
        {
          min: 3,
          max: 5,
          message: '长度在 3 到 5 个字符',
          trigger: 'blur'
        }
        ],
        pwd: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    login (loginForm) {
      var _this = this
      this.$refs[loginForm].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/login', {
              userName: this.loginForm.name,
              password: this.loginForm.pwd
            })
            .then(res => {
              if (res.data.responseCode === 200) {
                _this.$store.commit('login', this.loginForm.name)
                var path = this.$route.query.redirect
                this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
              } else if (res.data.responseCode === 400) {
                alert('账号密码错误！！！')
              }
            })
            .catch(err => {
              console.error(err)
            })
        } else {
          console.log('登录失败!!')
          return false
        }
      })
    },
    resetForm (loginForm) {
      this.$refs[loginForm].resetFields()
    }
  }
}

</script>

<style>
  #poster {
    background: url("~@/assets/img/loginbg.png") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }

  body {
    margin: 0px;
  }

  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>
