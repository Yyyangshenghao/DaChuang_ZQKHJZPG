<template>
<el-container>
  <div class="nav">
    <div class="headWrapper">
    <el-header class="header">
      <el-row :gutter="30" type="flex">
        <el-col :span="3">
          <div class="logoWrapper">
          <img src="../assets/navlogo.jpg" alt="your-logo" height="40px" style="margin-top: 10px"/>
          <span class="logo-name" style="line-height: 45px">花梨证券</span>
          </div>
        </el-col>
        <el-col :span="19" type="flex">
          <el-menu class="menu" mode="horizontal">
            <el-menu-item index="1" class="menu-item">首页</el-menu-item>
            <el-submenu index="2" class="submenu">
              <template slot="title">菜单2</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
              <el-menu-item index="2-3">选项3</el-menu-item>
<!--              <el-submenu index="2-4">-->
<!--                <template slot="title">选项4</template>-->
<!--                <el-menu-item index="2-4-1">小选项1</el-menu-item>-->
<!--                <el-menu-item index="2-4-2">小选项2</el-menu-item>-->
<!--                <el-menu-item index="2-4-3">小选项3</el-menu-item>-->
<!--                <el-menu-item index="2-4-4">小选项4</el-menu-item>-->
<!--              </el-submenu>-->

            </el-submenu>
            <el-menu-item index="3" class="menu-item">菜单3</el-menu-item>
            <el-menu-item index="4" class="menu-item">菜单4</el-menu-item>
            <el-menu-item index="5" class="menu-item">菜单5</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="2">
          <el-button round class='LoginButton' @click="goToLogin" type="primary" size="medium">登录</el-button>
        </el-col>
      </el-row>
    </el-header>
    </div>
    <div class="main">
    <el-main>
      <el-carousel>
        <el-carousel-item v-for="item in 4" :key="item">
          <h3>{{item}}</h3>
        </el-carousel-item>
      </el-carousel>
    </el-main>
    </div>
  </div>
</el-container>
</template>

<script>
export default {
  name: "Nav",
  methods: {
    goToLogin() {
      this.$router.push('/login');
    },
  },

}
window.addEventListener('resize', function() {
  if (window.innerWidth < 1160) {
    document.querySelector('.logo-name').style.display = 'none';
  } else {
    document.querySelector('.logo-name').style.display = 'inline-block';
  }
});

</script>

<style scoped>
.nav{
  flex: auto;
}

.headWrapper{
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header {
  text-align: center;
  width: 100%;
  height: 60px;
  background-color: #ffffff;
  padding: 0 20px;
  box-sizing: border-box;
}

.menu {
  line-height: 60px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;

}

.logo-name{
  font-family: 阿里妈妈数黑体,sans-serif;
  font-size: large;
  margin-left:0;
  margin-top: 10px;
  display: flex;
}


.logoWrapper {
  display: flex;
  align-items: center;
  justify-content: center;
}

.menu-item{
  padding: 0px 70px;

}
.submenu{
  padding:0 45px;
}

.LoginButton{
  /*margin-left: 30px ;*/
  padding:8px 20px;
  display: flex;
  margin-top: 15px;
  background: dimgrey;
  border-color: dimgrey;
}
</style>