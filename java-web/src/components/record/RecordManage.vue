<template>
  <div>
    <div style="margin-bottom: 5px">
      <!--输入框-->
      <el-input v-model="name"
                placeholder="请输入证券名称"
                prefix-icon="el-icon-search"
                style="width: 200px"
                size="small"
                @keyup.enter.native="loadPost"></el-input>
      <!--下拉框-->
      <el-select v-model="securitiesclass" placeholder="请选择级别" size="small" style="margin-left: 8px">
        <el-option
            v-for="item in classData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="securitiestype" placeholder="请选择类型" size="small" style="margin-left: 8px">
        <el-option
            v-for="item in typeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <!--按钮-->
      <el-button type="primary" icon="el-icon-search" style="margin-left: 8px" size="small" @click="loadPost">查询</el-button>
      <el-button type="warning" icon="el-icon-refresh" size="small" @click="reset">重置</el-button>
<!--      <el-row>-->
<!--        <el-button type="primary" icon="el-icon-plus" style="margin-top: 8px" size="small" @click="add">添加</el-button>-->
<!--      </el-row>-->
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#868687',color:'#e7e9ea'}">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="securityname" label="证券名称" width="180">
      </el-table-column>
      <el-table-column prop="securityclassname" label="证券级别" width="180">
      </el-table-column>
      <el-table-column prop="securitytypename" label="证券分类" width="180">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180">
      </el-table-column>
      <el-table-column prop="username" label="交易人" width="180">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="180">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>

    </el-table>
    <!--Pagination 分页-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "RecordManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      classData:[],
      typeData:[],
      tableData:[],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      securitiesclass:'',
      securitiestype:'',
      centerDialogVisible: false,
      form:{
        id:'',
        name:'',
        securitiesclass:'',
        securitiestype:'',
        count:'',
        remark:''
      },
    }
  },
  methods:{
    // formatterClass(row){
    //   let temp = this.classData.find(item1=>{
    //     return item1.id == row.securitiesclass;
    //   })
    //   return temp && temp.name;
    // },
    // formatterType(row){
    //   let temp = this.typeData.find(item1=>{
    //     return item1.id == row.securitiestype;
    //   })
    //   return temp && temp.name;
    // },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      // this.pageNum=1;
      this.pageSize=val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val;
      this.loadPost();
    },
    reset(){ //输入框下拉框重置
      this.name = '';
      this.securitiesclass = '';
      this.securitiestype = '';
    },
    // add(){  //打开对话框
    //   this.centerDialogVisible = true;
    //   // this.$nextTick(()=>{this.resetForm()})
    //   this.form = {  //再次点击则重新清空
    //     id:'',
    //     name:'',
    //     securitiesclass:'',
    //     securitiestype:'',
    //     count:'',
    //     remark:''
    //   };
    // },
    // doSave(){
    //   this.$axios.post(this.$httpUrl+'/securities/save', this.form).then(res=>res.data).then(res=>{
    //     console.log(res)
    //     if(res.code == 200){
    //       this.$message({
    //         message: '操作成功',
    //         type: 'success'
    //       });
    //       this.centerDialogVisible = false;
    //       this.loadPost();
    //     }else {
    //       this.$message({
    //         message: '操作失败',
    //         type: 'warning'
    //       })
    //     }
    //   })
    // },
    // doModify(){
    //   this.$axios.post(this.$httpUrl+'/securities/update', this.form).then(res=>res.data).then(res=>{
    //     console.log(res)
    //     if(res.code == 200){
    //       this.$message({
    //         message: '操作成功',
    //         type: 'success'
    //       });
    //       this.centerDialogVisible = false;
    //       this.loadPost();
    //     }else {
    //       this.$message({
    //         message: '操作失败',
    //         type: 'warning'
    //       })
    //     }
    //   })
    // },
    // save(){  //添加
    //   this.$refs.form.validate((valid) => { //表单验证
    //     if (valid) {  //通过的话向后端发送
    //       if(this.form.id){
    //         this.doModify();
    //       }else{
    //         this.doSave();
    //       }
    //     } else {
    //       console.log('error submit!!');
    //       alert("请重新输入信息");
    //       return false;
    //     }
    //   });
    // },
    // Modify(row){
    //   console.log(row);
    //   //给表单form赋值
    //   this.form.id = row.id;
    //   this.form.name = row.name;
    //   this.form.securitiesclass = row.securitiesclass;
    //   this.form.securitiestype = row.securitiestype;
    //   this.form.count = row.count;
    //   this.form.remark = row.remark;
    //   this.centerDialogVisible = true;
    //
    // },
    // Delete(id){
    //   this.$axios.get(this.$httpUrl+'/securities/delete?id='+id).then(res=>res.data).then(res=>{
    //     console.log(res)
    //     if(res.code == 200){
    //       this.$message({
    //         message: '操作成功',
    //         type: 'success'
    //       });
    //       this.loadPost();
    //     }else {
    //       this.$message({
    //         message: '操作失败',
    //         type: 'warning'
    //       })
    //     }
    //   })
    // },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          securitiesclass:this.securitiesclass+'',
          securitiestype:this.securitiestype+'',
          roleId:this.user.roleId+'',
          userId:this.user.id+''
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.tableData = res.data;
          this.total=res.total;
        }else{
          alert('获取数据失败');
        }
        // this.tableData = res;
      })
    },
    loadClass(){
      this.$axios.get(this.$httpUrl+'/class1/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.classData = res.data;
        }else{
          alert('获取数据失败');
        }
      })
    },
    loadType(){
      this.$axios.get(this.$httpUrl+'/type/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.typeData = res.data;
        }else{
          alert('获取数据失败');
        }
      })
    }
  },
  beforeMount(){
    this.loadPost();
    this.loadType();
    this.loadClass();
  }
}
</script>

<style scoped>

</style>