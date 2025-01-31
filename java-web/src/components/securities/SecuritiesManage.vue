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
      <el-row>
        <el-button type="primary" icon="el-icon-plus" style="margin-top: 8px" size="small" @click="add" v-if="user.roleId != 2">添加</el-button>
        <el-button type="primary" icon="el-icon-plus" style="margin-top: 8px" size="small" @click="deal" v-if="user.roleId != 2">交易</el-button>
        <el-button type="primary" icon="el-icon-minus" style="margin-top: 8px" size="small" @click="dealoff" v-if="user.roleId != 2">卖出</el-button>
      </el-row>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#868687',color:'#e7e9ea'}"
              highlight-current-row
              @current-change="selectCurrentChange">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="证券名称" width="180">
      </el-table-column>
      <el-table-column prop="securitiesclass" label="证券级别" width="180" :formatter="formatterClass">
      </el-table-column>
      <el-table-column prop="securitiestype" label="证券分类" width="180" :formatter="formatterType">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>

      <el-table-column prop="op" label="操作" v-if="user.roleId != 2">
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
        <el-form-item label="证券名称" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="证券级别" prop="securitiesclass">
          <el-col :span="18">
            <el-select v-model="form.securitiesclass" placeholder="请选择级别" size="small" style="margin-left: 8px">
              <el-option
                  v-for="item in classData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="证券分类" prop="securitiestype">
          <el-col :span="18">
            <el-select v-model="form.securitiestype" placeholder="请选择类型" size="small" style="margin-left: 8px">
              <el-option
                  v-for="item in typeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="18">
            <el-input v-model="form.count"></el-input>
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
    <!--对话框1-->
    <el-dialog
        title="信息"
        :visible.sync="dealDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="68%"
          title="交易人选择"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>
      <!--表单1-->
      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="证券名称">
          <el-col :span="18">
            <el-input v-model="form1.securityname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="交易人">
          <el-col :span="18">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="18">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="18">
            <el-input type="textarea" v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dealDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doDeal">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import SelectUser from "@/components/user/SelectUser";
export default {
  name: "SecuritiesManage",
  components: {SelectUser},
  data() {
    let checkCount = (rule, value, callback) => {
      if(value>9999){
        callback(new Error('数量输⼊过⼤'));
      }else{
        callback();
      }
    };
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
      dealDialogVisible: false,
      innerVisible: false,
      currentRow:{},
      tempUser:{},
      form:{
        id:'',
        name:'',
        securitiesclass:'',
        securitiestype:'',
        count:'',
        remark:''
      },
      rules: { //表单验证
        name:[
          {required: true, message: '请输入证券名', trigger: 'blur'}
        ],
        securitiesclass:[
          {required: true, message: '请选择级别', trigger: 'blur'}
        ],
        securitiestype:[
          {required: true, message: '请选择分类', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输⼊数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/,message: '数量必须为正整数字',trigger: "blur"},
          {validator:checkCount,trigger: 'blur'}
        ],
      },
      form1:{
        security:'',
        securityname:'',
        count:'',
        username:'',
        userid:'',
        adminId:'',
        remark:'',
        action:'1'
      },
      rules1:{

      }
    }
  },
  methods:{
    doSelectUser(val){
      // console.log(val);
      this.tempUser = val;
    },
    confirmUser(){
      this.form1.username = this.tempUser.name;
      this.form1.userid = this.tempUser.id;
      this.innerVisible = false;
    },
    selectUser(){
      this.innerVisible = true;
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    formatterClass(row){
      let temp = this.classData.find(item1=>{
        return item1.id == row.securitiesclass;
      })
      return temp && temp.name;
    },
    formatterType(row){
      let temp = this.typeData.find(item1=>{
        return item1.id == row.securitiestype;
      })
      return temp && temp.name;
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
    reset(){ //输入框下拉框重置
      this.name = '';
      this.securitiesclass = '';
      this.securitiestype = '';
    },
    resetForm1(){
      this.$refs.form1.resetFields();
    },
    add(){  //打开对话框
      this.centerDialogVisible = true;
      // this.$nextTick(()=>{this.resetForm()})
      this.form = {  //再次点击则重新清空
        id:'',
        name:'',
        securitiesclass:'',
        securitiestype:'',
        count:'',
        remark:''
      };
    },
    deal(){
      if(!this.currentRow.id){
        alert("请先选择");
        return;
      };
      this.dealDialogVisible = true;
      // this.form1 = {  //再次点击则重新清空
      //   // id:'',
      //   // name:'',
      //   // securitiesclass:'',
      //   // securitiestype:'',
      //   // count:'',
      //   // remark:''
      //   security:'',
      //   securityname:'',
      //   count:'',
      //   userid:'7',
      //   adminId:'',
      //   remark:''
      // };
      this.$nextTick(()=>{
        this.resetForm1()
      })
      this.form1.securityname = this.currentRow.name;
      this.form1.security = this.currentRow.id;
      this.form1.adminId = this.user.id;
      this.form1.action = '1';
    },
    dealoff(){
      if(!this.currentRow.id){
        alert("请先选择");
        return;
      };
      this.dealDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm1()
      })
      this.form1.securityname = this.currentRow.name;
      this.form1.security = this.currentRow.id;
      this.form1.adminId =this.user.id;
      this.form1.action = '2';
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/securities/save', this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/securities/update', this.form).then(res=>res.data).then(res=>{
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
    doDeal(){
      this.$axios.post(this.$httpUrl+'/record/save', this.form1).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dealDialogVisible = false;
          this.loadPost();
          this.resetForm1();
        }else {
          this.$message({
            message: '操作失败',
            type: 'warning'
          })
        }
      })
    },
    // resetForm() {
    //   this.$refs.form.resetFields();
    // },
    Modify(row){
      console.log(row);
      //给表单form赋值
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.securitiesclass = row.securitiesclass;
      this.form.securitiestype = row.securitiestype;
      this.form.count = row.count;
      this.form.remark = row.remark;
      this.centerDialogVisible = true;

    },
    Delete(id){
      this.$axios.get(this.$httpUrl+'/securities/delete?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/securities/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          securitiesclass:this.securitiesclass+'',
          securitiestype:this.securitiestype+''
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