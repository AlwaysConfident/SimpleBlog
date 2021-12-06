<template>
  <div class="searchbar">
    <el-input @keyup.enter.native="searchClick" placeholder="通过书名或作者搜索..." prefix-icon="el-icon-search" size="small"
      style="width: 300px;margin-right: 10px" v-model="keywords">
    </el-input>
    <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      keywords: '',
      books: [],
      cardLoading: []
    }
  },
  methods: {
    searchClick () {
      this.$emit('onSearch')
    }
  }
}

</script>

<style scoped>
    .searchbar{
        position: fixed;
        display: flex;
        justify-content: center;
        align-items: center;
        left: 1000px;
        width: 250px;
        z-index: 11;
        top: 3%;
    }
</style>
