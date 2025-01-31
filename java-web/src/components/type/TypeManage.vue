<template>
  <div>
    <div style="margin-bottom: 5px">
      <!--输入框-->
      <el-input v-model="name"
                placeholder="请输入证券分类名"
                prefix-icon="el-icon-search"
                style="width: 200px"
                size="small"
                @keyup.enter.native="loadPost"></el-input>

      <!--按钮-->
      <el-button type="primary" icon="el-icon-search" style="margin-left: 8px" size="small" @click="loadPost">查询</el-button>
      <el-button type="warning" icon="el-icon-refresh" size="small" @click="reset">重置</el-button>
      <el-row>
        <el-button type="primary" icon="el-icon-plus" style="margin-top: 8px" size="small" @click="add">添加</el-button>
      </el-row>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#868687',color:'#e7e9ea'}">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="证券分类" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>

      <el-table-column prop="op" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="warning" @click="Modify(scope.row)" style="margin-right: 8px">修改</el-button>
          <el-popconfirm title="这是一段内容确定删除吗？"  @confirm="Delete(scope.row.id)">  <!--点击时弹出确认框-->
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
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
    <!--对话框-->
    <el-dialog
        title="信息"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <!--表单-->
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="证券分类" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="18">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TypeManage",
  data() {

    return {
      tableData:[],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      centerDialogVisible: false,
      form:{
        id:'',
        name:'',
        remark:'',
      },
      rules: { //表单验证
        name:[
          {required: true, message: '请输入内容', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
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
    },
    add(){  //打开对话框
      this.centerDialogVisible = true;
      // this.$nextTick(()=>{this.resetForm()})
      this.form = {  //再次点击则重新清空
        id:'',
        name:'',
        remark:''
      };
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/type/save', this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
        }else {
          this.$message({
            message: '操作失败',
            type: 'warning'
          })
        }
      })
    },
    doModify(){
      this.$axios.post(this.$httpUrl+'/type/update', this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
        }else {
          this.$message({
            message: '操作失败',
            type: 'warning'
          })
        }
      })
    },
    save(){  //添加
      this.$refs.form.validate((valid) => { //表单验证
        if (valid) {  //通过的话向后端发送
          if(this.form.id){
            this.doModify();
          }else{
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          alert("请重新输入信息");
          return false;
        }
      });
    },
    // resetForm() {
    //   this.$refs.form.resetFields();
    // },
    Modify(row){
      console.log(row);
      //给表单form赋值
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.remark = row.remark;

      this.centerDialogVisible = true;

    },
    Delete(id){
      this.$axios.get(this.$httpUrl+'/type/delete?id='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost();
        }else {
          this.$message({
            message: '操作失败',
            type: 'warning'
          })
        }
      })
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/type/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
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
    }
  },
  beforeMount(){
    this.loadPost();
  }
}
</script>

<style scoped>

</style>