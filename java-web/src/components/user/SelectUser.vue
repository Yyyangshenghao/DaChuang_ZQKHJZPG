<template>
  <div>
    <div style="margin-bottom: 5px">
      <!--输入框-->
      <el-input v-model="name"
                placeholder="请输入姓名"
                prefix-icon="el-icon-search"
                style="width: 200px"
                size="small"
                @keyup.enter.native="loadPost"></el-input>
      <!--下拉框-->
      <el-select v-model="sex" filterable placeholder="请选择性别" size="small" style="margin-left: 8px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <!--按钮-->
      <el-button type="primary" icon="el-icon-search" style="margin-left: 8px" size="small" @click="loadPost">查询</el-button>
      <el-button type="warning" icon="el-icon-refresh" size="small" @click="reset">重置</el-button>
      <el-row>
        <el-button type="primary" icon="el-icon-plus" style="margin-top: 8px" size="small" @click="add">添加</el-button>
      </el-row>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#868687',color:'#e7e9ea'}"
              highlight-current-row
              @current-change="selectCurrentChange">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="150">
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180">
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="140">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="100">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="100">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" >
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.roleId === 0 ? '经理' : (scope.row.roleId === 1 ? '业务员' : '客户')}}</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column prop="op" label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button size="small" type="warning" @click="Modify(scope.row)" style="margin-right: 8px">修改</el-button>-->
<!--          <el-popconfirm title="这是一段内容确定删除吗？"  @confirm="Delete(scope.row.id)">  &lt;!&ndash;点击时弹出确认框&ndash;&gt;-->
<!--            <el-button slot="reference" size="small" type="danger">删除</el-button>-->
<!--          </el-popconfirm>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
        <el-form-item label="姓名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="账号" prop="no">
          <el-col :span="18">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="18">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="18">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="2">女</el-radio>
          </el-radio-group>
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
  name: "SelectUser",
  data() {
    let checkAge = (rule, value, callback) => {  //表单验证中判断输入的age是否过大
      if(value > 150) {
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{  //表单验证中判断输入的no是否重复出现了
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback()
        }else{
          callback(new Error('账号已经存在'));
        }
      })
    };
    return {
      tableData:[],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      sex:'',
      sexs:[
        {
          value:'1',
          label:'男'
        },{
          value:'2',
          label:'女'
        }
      ],
      centerDialogVisible: false,
      form:{
        id:'',
        name:'',
        no:'',
        password:'',
        phone:'',
        age:'',
        sex:'',
        roleId:'2'
      },
      rules: { //表单验证
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name:[
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输⼊年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '⻓度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "⼿机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger: "blur"}
        ]
      }
    }
  },
  methods:{
    selectCurrentChange(val) {
      // this.currentRow = val;
      this.$emit("doSelectUser",val);
    },
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
    // loadGet(){
    //   this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
    //     console.log(res)
    //   })
    // },
    reset(){ //输入框下拉框重置
      this.name = '';
      this.sex = '';
    },
    add(){  //打开对话框
      this.centerDialogVisible = true;
      // this.$nextTick(()=>{this.resetForm()})
      this.form = {  //再次点击则重新清空
        id:'',
        name:'',
        no:'',
        password:'',
        phone:'',
        age:'',
        sex:'',
        roleId:'2'};
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save', this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/update', this.form).then(res=>res.data).then(res=>{
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
      this.form.no = row.no;
      this.form.name = row.name;
      this.form.password = row.password;
      this.form.age = row.age + '';
      this.form.sex = row.sex + '';
      this.form.phone = row.phone;
      this.form.roleId = row.roleId;

      this.centerDialogVisible = true;

    },
    Delete(id){
      this.$axios.get(this.$httpUrl+'/user/delete?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          sex:this.sex,
          roleId:'2'
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
    //this.loadGet();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>