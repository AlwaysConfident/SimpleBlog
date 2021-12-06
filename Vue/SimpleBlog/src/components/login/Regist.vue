<template>
  <body id="poster">
    <el-form :model="registForm" :rules="rules" ref="registForm" class="regist-container" label-position="left"
      label-width="0px">
      <h3 class="regist_title">用户注册</h3>
      <el-form-item prop="name">
        <el-input type="text" v-model="registForm.name" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="pwd">
        <el-input type="password" v-model="registForm.pwd" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item  prop="checkPwd">
        <el-input type="password" v-model="registForm.checkPwd" autocomplete="off" placeholder="再次输入密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none"
          v-on:click="regist('registForm')">注册</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  data () {
    var validatePwd = (rule, value, callback) => {
      if (this.registForm.pwd === '') {
        callback(new Error('请先输入密码'))
      }
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registForm.pwd) {
        callback(new Error('两次密码输入不一致!!!'))
      } else {
        callback()
      }
    }
    return {
      registForm: {
        name: '',
        pwd: '',
        checkPwd: ''
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
        }],
        checkPwd: [{
          validator: validatePwd,
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    regist (registForm) {
      var _this = this
      this.$refs[registForm].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/regist', {
              userName: this.registForm.name,
              password: this.registForm.pwd
            })
            .then(res => {
              if (res.data.responseCode === 200) {
                _this.$router.push('/login')
              } else if (res.data.responseCode === 400) {
                alert('注册出现错误！！！')
              }
            })
            .catch(err => {
              console.error(err)
            })
        } else {
          console.log('注册失败!!')
          return false
        }
      })
    },
    resetForm (registForm) {
      this.$refs[registForm].resetFields()
    }
  }
}

</script>

<style>
  #poster {
    background: url("~assets/img/loginbg.png") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }

  body {
    margin: 0px;
  }

  .regist-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .regist_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>
