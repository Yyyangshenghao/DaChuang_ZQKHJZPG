<template>
  <div id="app">
<!--    <index></index>-->
    <router-view/>
  </div>
</template>

<script>


// import Index from "@/components";
export default {
  name: 'App',
  components: {
    // Index
  },
  // 解决刷新后vuex丢失路由数据问题
  data(){
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{
    '$store.state.menu':{
      handler(yal, old){
        if(!old && this.user && this.user.no){
          this.$store.commit("setRouter",yal);
        }
      },
      immediate: true
    }
  }
}
</script>

<style>
#app {
  /*height: 100%;*/
}
</style>
