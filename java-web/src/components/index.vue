<template>
  <el-container style="height: 100%">
    <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246); height: 100%">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px; height: 100%; border-bottom: #C0C4CC 1px solid">
        <Header @doCollapse="doCollapse" :icon="icon"></Header>
      </el-header>

      <el-main style="height: 100%">
<!--        <Main></Main>-->
        <router-view/>
      </el-main>
      <el-footer>
        <dateutils></dateutils>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
// import Main from "@/components/Main";
export default {
  name: "index",
  components: {Header, Aside},//components: {Header, Aside,Main},
  data(){
    return{
      isCollapse:false,
      aside_width: '200px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse
      if(!this.isCollapse){  //此时为展开
          this.aside_width = '200px'
          this.icon='el-icon-s-fold'
      }
      else{  //此时为收起
          this.aside_width = '64px'
          this.icon='el-icon-s-unfold'
      }
    }
  }
}
</script>

<style scoped>
  .el-header {
    background-color: #606266;
    color: #333;
    line-height: 60px;
  }
  .el-main{
    padding: 5px;
  }
  .el-aside {
    background-color: red;
  }
</style>