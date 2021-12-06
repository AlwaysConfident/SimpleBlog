<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <Sidebar @indexSelect="listByCategory" ref="sideBar"></Sidebar>
    </el-aside>
    <el-main>
      <books class="books-area" ref="booksArea"></books>
    </el-main>
  </el-container>
</template>

<script>
import Sidebar from './Sidebar.vue'
import Books from './Books.vue'
export default {
  name: 'AppLibrary',
  components: {
    Sidebar,
    Books
  },
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideBar.cid
      var url = 'categories/' + cid + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.$refs.booksArea.books = resp.data
        }
      })
    }
  }
}

</script>

<style scoped>
  .books-area {
    width: 990px;
    margin-top: 5%;
  }
</style>
