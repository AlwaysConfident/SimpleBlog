<template>
<div>
  <Header></Header>
  <el-carousel height="720px" >
    <el-carousel-item v-for="(item, index) in items" :key="index"  class="board">
      <img :src="item.img" alt="图片丢失了" :title="item.title" class="img"/>
      <span>{{item.title}}</span>
    </el-carousel-item>
  </el-carousel>
</div>
</template>

<script>
import Header from './Header.vue'
export default {
  data () {
    return {
      items: [
        {img: require('../../assets/img/welcome/welcome01.png'), title: 'img01'},
        {img: require('../../assets/img/welcome/welcome02.png'), title: 'img02'},
        {img: require('../../assets/img/welcome/welcome03.png'), title: 'img03'},
        {img: require('../../assets/img/welcome/welcome04.png'), title: 'img04'}
      ]
    }
  },
  components: {Header},
  methods: {
    login: function () {
      this.$router.push('/index')
    },
    regist: function () {
      this.$router.push('/regist')
    }
  }
}

</script>

<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .board{
    position: absolute;
    height: 659px;
    width: 100%;
    top: 8.4%;
  }

  .img{
    height: 100%;
    width: 100%;
  }
</style>
