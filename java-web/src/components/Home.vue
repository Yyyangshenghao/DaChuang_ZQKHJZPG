<template>
  <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
    <el-card>
    <h1 class="welcome-msg" slot="header">{{'欢迎你！'+user.name}}</h1>
      <el-descriptions  title="个人中心" :column="2" size="40" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          账号
        </template>
        {{user.no}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          电话
        </template>
        {{user.phone}}
      </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            姓名
          </template>
          {{user.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-info"></i>
            年龄
          </template>
          {{user.age}}
        </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          性别
        </template>
        <el-tag
            :type="user.sex === 1 ? 'primary' : 'danger'"
            disable-transitions><i :class="user.sex===1?'el-icon-male':'el-icon-female'"></i>{{user.sex===1?"男":"女"}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          角色
        </template>
        <el-tag
            type="success"
            disable-transitions>{{user.roleId===0?"经理":(user.roleId===1?"业务员":"客户")}}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
        <div class="time">
          <DateUtils></DateUtils>
        </div>
    </el-card>


  </div>
</template>

<script>
  import DateUtils from "./DateUtils";
  export default {
    name: "Home",
    components: {DateUtils},
    data() {

      return {
        user:{}
      }
    },
    computed:{

    },
    methods:{
      init(){
        this.user = JSON.parse(sessionStorage.getItem('CurUser'))
      }
    },
    created(){
      this.init()
    }
  }

</script>

<style scoped>
  .el-descriptions{
    width:90%;
    margin: 0 auto;
    text-align: center;
  }
  .welcome-msg {
    font-size: 25px;
    font-family: 钉钉进步体,sans-serif;
    text-align: left;
    height: 30px;
  }
  .time{
    text-align: right;

  }
</style>