<template>
    <div>
        <el-form :inline="true">
            <el-form-item>
                <el-input placeholder="请输入游戏名称" v-model="accountsname"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getAccountsList">查询</el-button>
            </el-form-item>
            <el-form-item>
                <!-- 添加按钮:1.点击添加用户，弹出对话框
                 2.填写完信息后，点击确定，向后台发送请求提交数据 
                 3.点击取消，关闭对话框-->
                <el-button type="primary" @click="showAddAccountsDialog">添加游戏账号</el-button>
            </el-form-item>
        </el-form>

        <!-- table表格 -->
        <el-table :data="accountsList" border style="width: 100%">
            <el-table-column fixed prop="id" label="序号"></el-table-column>
            <el-table-column prop="gameName" label="游戏名称"></el-table-column>
            <el-table-column prop="accountName" label="游戏账号"></el-table-column>
            <el-table-column prop="description" label="游戏描述"></el-table-column>
            <el-table-column label="账号截图">
                <template slot-scope="scope">
                <img :src="scope.row.accountPicture" width=100px; height=100px;>
                </template>
            </el-table-column>
            <el-table-column prop="priceHour" label="租用价格(单位：元/小时)"></el-table-column>
            <el-table-column prop="status" label="账号状态"></el-table-column>
            <el-table-column prop="createdAt" label="上传时间"></el-table-column>

            <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
                <el-button @click="showUpdate(scope.row)" type="text" size="small">修改</el-button>
                <el-button type="text" size="small" @click="deleteAccounts(scope.row.id)">删除</el-button>
            </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <!--  
            @size-change:每页显示的数据条数发生变化时触发的事件
            @current-change:当页码发生变化时触发的事件
            current-page:当前页码
            page-sizes:每页显示数据条数的集合
            page-size:每页显示的数据条数
            total:数据总数
        -->
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNumber"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

        <!-- 添加对话框 -->
        <!--
        visible.sync:是否显示对话框的标记对象
        model:模型驱动，绑定一个对象，在form中所有输入框绑定的变量都可以是该对象的属性
        -->
        <el-dialog title="添加游戏账号" :visible.sync="addAccountsVisible" append-to-body>
            <el-form :model="accounts">
                <el-form-item label="游戏名称" label-width="80px">
                    <el-input v-model="accounts.gameName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="游戏描述" label-width="80px">
                    <el-input v-model="accounts.description" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="游戏账号" label-width="80px">
                    <el-input v-model="accounts.accountName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号密码" label-width="80px">
                    <el-input v-model="accounts.accountPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="租用价格" label-width="80px">
                    <el-input v-model="accounts.priceHour" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号状态" label-width="80px">
                    <el-select v-model="accounts.status" placeholder="请选择">
                        <el-option label="审核中" value="审核中"></el-option>
                        <el-option label="待租用" value="待租用"></el-option>
                        <el-option label="租用中" value="租用中"></el-option>
                        <el-option label="已下架" value="已下架"></el-option>
                    </el-select>
                </el-form-item>

                
                <el-container>
      <el-header>
        <h2>选择图片文件并读取路径</h2>
      </el-header>
      <el-main>
        <!-- 上传文件按钮 -->
        <el-upload
          :before-upload="handleBeforeUpload"
          :show-file-list="false"
          accept="image/*" 
          v-model="accountPicture"  
        >
          <el-button type="primary">选择图片</el-button>
        </el-upload>

        <!-- 显示文件路径 -->
        <div v-if="filePath">
          <p><strong>文件路径：</strong>{{ filePath }}</p>
        </div>
      </el-main>
    </el-container>
  
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addAccounts">确 定</el-button>
                <el-button @click="cancelAddAccounts">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 修改对话框 -->
        <el-dialog title="修改用户" :visible.sync="modifyAccountsVisible" append-to-body>
            <el-form :model="editAccounts">
                <el-form-item label="ID" label-width="80px">
                    <el-input v-model="editAccounts.id" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="游戏名称" label-width="80px">
                    <el-input v-model="editAccounts.gameName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="游戏描述" label-width="80px">
                    <el-input v-model="editAccounts.description" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="游戏账号" label-width="80px">
                    <el-input v-model="editAccounts.accountName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号密码" label-width="80px">
                    <el-input v-model="editAccounts.accountPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="租用价格" label-width="80px">
                    <el-input v-model="editAccounts.priceHour" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号状态" label-width="80px">
                    <el-select v-model="editAccounts.status" placeholder="请选择">
                        <el-option label="审核中" value="审核中"></el-option>
                        <el-option label="待租用" value="待租用"></el-option>
                        <el-option label="租用中" value="租用中"></el-option>
                        <el-option label="已下架" value="已下架"></el-option>
                    </el-select>
                </el-form-item>                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateAccounts">确 定</el-button>
                <el-button @click="cancelModifyAccounts">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default{
        data(){
            return{
                accountsname:'',
                accountsList:[],
                pageNumber:1,
                pageSize:5,
                total:0,
                addAccountsVisible:false,
                accounts:{},
                modifyAccountsVisible:false,
                editAccounts:{},
                filePath: '', // 用于存储文件路径
                accountPicture: null, // 用于存储选择的文件
            }
        },
        methods:{
            //获取用户信息列表
            getAccountsList(){
                //获取分页参数，封装对象
                let params={};
                params.pageNumber=this.pageNumber;
                params.pageSize=this.pageSize;
                //判断筛选输入框是否为空，不为空则将筛选条件一起封装
                if(this.accountsname!=''){
                    params.gameName=this.accountsname;
                }
                //向后台服务发送请求，处理返回结果
                //get请求的参数格式：{params:XXX},其中冒号前的params是参数名(固定的)
                this.$http.get('/accounts/page',{params:params})
                .then(result=>{
                    let data=result.data;
                    this.total=data.total;
                 
                    this.accountsList=data.rows;
                    console.log(this.accountsList);
                })
            },
            showUpdate(row){
                this.modifyAccountsVisible=true;
                //数据回显
                this.editAccounts=row;
            },
            cancelModifyAccounts(){
                //关闭对话框
                this.modifyAccountsVisible=false;
                //清空对话框中已修改的内容
                this.editAccounts={};
                this.getAccountsList();
            },
            updateAccounts(){
                this.$http.post('/accounts/update',this.editAccounts)
                .then(result=>{
                    let data=result.data;
                    if(data.code==200){
                        this.$message.success(data.msg);
                        this.modifyAccountsVisible=false;
                        this.getAccountsList();
                    }else{
                        this.$message.warning(data.msg);
                    }
                })
            },
            deleteAccounts(id){
                this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    //发送删除请求,处理返回结果
                    let params={};
                    params.id=id;
                    this.$http.get('/accounts/delete',{params:params})
                    .then(result=>{
                        let data=result.data;
                        if(data.code==200){
                            this.$message.success(data.msg);
                            this.getAccountsList();
                        }else{
                            this.$message.warning(data.msg);
                        }
                    })
                }).catch(() => {
                    this.$message({
                    type: 'info',
                    message: '已取消删除'
                    });          
                });
            },
            handleSizeChange(val){
                this.pageSize=val;
                this.getAccountsList();
            },
            handleCurrentChange(val){
                //val:表示翻页的新页码
                //把新页码赋值给pageNumber
                this.pageNumber=val;
                //重新调用getUserList方法
                this.getAccountsList();
            },
            showAddAccountsDialog(){
                //打开添加用户的对话框
                this.addAccountsVisible=true;
            },
            cancelAddAccounts(){
                //关闭对话框
                this.addAccountsVisible=false;
                //清空添加框中的user对象
                this.accounts={};
                this.filePath = ''; // 清空文件路径
            },
            addAccounts(){
                // 获取添加用户信息
               
                console.log('添加用户信息:', this.accounts);
                // 发送请求，处理返回结果
                this.$http.post('/accounts/add',this.accounts)
                .then(result=>{
                    let data=result.data;
                    if(data.code==200){
                        //消息提示
                        this.$message.success(data.msg);
                        //关闭对话框
                        this.addAccountsVisible=false;
                        //重新查询用户列表
                        this.getAccountsList();
                        //清空用户添加对话框中的user对象
                        this.accounts={};
                        // 清空文件路径
                        this.filePath = ''; 
                    }else{
                        this.$message.warning(data.msg);
                    }
                })
            },
                // 在上传文件前处理
            handleBeforeUpload(file) {
            // 限制文件为图片类型
            const isImage = file.type.startsWith('image/');
            if (!isImage) {
                this.$message.error('只能上传图片文件!');
                return false; // 阻止上传
            }

            const path = file.webkitRelativePath || file.name;

            // 将路径和文件名组合起来
            this.filePath = 'http://localhost:8088/view/' + path;
            this.accounts.accountPicture = this.filePath;
            console.log('文件路径:', this.accounts);

            console.log('文件路径:', this.filePath);

      // 阻止自动上传，处理前端逻辑后交给后端
            return false;
            },
            
        },
        mounted(){
            //mounted:文档就绪事件，当页面加载完成后自动触发的事件
            this.getAccountsList();
        }
    }
</script>

<style>
</style>