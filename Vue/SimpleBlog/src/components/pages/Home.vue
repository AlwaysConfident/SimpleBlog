<template>
  <div>
    <v-head></v-head>
    <router-view/>
  </div>
</template>

<script>
import vHead from './Header.vue'
export default {
  name: 'Home',
  components: {vHead}
}
</script>

<style scoped>

</style>
