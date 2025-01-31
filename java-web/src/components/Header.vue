<template>
  <div style="display: flex; line-height: 80px">
    <div style="color: white">
      <i :class="icon" style="font-size: 40px; line-height: 80px; cursor: pointer" @click="collapse"></i>
    </div>
    <div style="flex: 1; text-align: center; font-size: 40px; color: #FFFFFF;">
      <span style="font-family: 阿里妈妈数黑体,sans-serif;">客户管理系统</span>
    </div>
    <el-dropdown trigger="click">
      <span style="margin-right: 3px; color: #FFFFFF; cursor: pointer;font-size: large">{{user.name}}</span>
      <i class="el-icon-arrow-down" style="margin-right: 15px; color: #FFFFFF"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props:{
    icon:String
  },
  methods:{
    toUser(){
      console.log('to_user');
      this.$router.push("/Home");
    },
    logout(){
      console.log('logout')
      this.$confirm('您确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '退出登录成功'
        });
        this.$router.push("/");
        sessionStorage.clear();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    collapse(){
      this.$emit('doCollapse')
    }
  },
  created(){  //使登录后自动跳转至个人中心
    this.$router.push("/Home")
  }
}
</script>

<style scoped>

</style>