<template>
    <div>
        <el-form :inline="true">
            <el-form-item>
                <el-input placeholder="请输入用户名" v-model="username"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getUserList">查询</el-button>
            </el-form-item>
            <el-form-item>
                <!-- 添加按钮:1.点击添加用户，弹出对话框
                 2.填写完信息后，点击确定，向后台发送请求提交数据 
                 3.点击取消，关闭对话框-->
                <el-button type="primary" @click="showAddUserDialog">添加用户</el-button>
            </el-form-item>
        </el-form>

        <!-- table表格 -->
        <el-table :data="userList" border style="width: 100%">
            <el-table-column fixed prop="id" label="序号">
            </el-table-column>
            <el-table-column prop="username" label="用户名">
            </el-table-column>
            <el-table-column prop="role" label="身份">
            </el-table-column>  
            <el-table-column prop="createdAt" label="注册时间">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
                <el-button @click="showUpdate(scope.row)" type="text" size="small">修改</el-button>
                <el-button type="text" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
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

        <!-- 添加用户对话框 -->
        <!--
        visible.sync:是否显示对话框的标记对象
        model:模型驱动，绑定一个对象，在form中所有输入框绑定的变量都可以是该对象的属性
        -->
        <el-dialog title="添加用户" :visible.sync="addUserVisible" append-to-body>
            <el-form :model="user">
                <el-form-item label="用户名" label-width="80px">
                    <el-input v-model="user.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" label-width="80px">
                    <el-input v-model="user.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item label="身份" label-width="80px">
                    <el-select v-model="user.role" placeholder="请选择">
                        <el-option label="管理员" value="admin"></el-option>
                        <el-option label="普通用户" value="user"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAddUser">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 修改用户对话框 -->
        <el-dialog title="修改用户" :visible.sync="modifyUserVisible" append-to-body>
            <el-form :model="editUser">
                <el-form-item label="ID" label-width="80px">
                    <el-input v-model="editUser.id" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="用户名" label-width="80px">
                    <el-input v-model="editUser.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" label-width="80px">
                    <el-input v-model="editUser.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item label="身份" label-width="80px">
                    <el-select v-model="editUser.role" placeholder="请选择">
                        <el-option label="管理员" value="admin"></el-option>
                        <el-option label="普通用户" value="user"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelModifyUser">取 消</el-button>
                <el-button type="primary" @click="updateUser">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default{
        data(){
            return{
                username:'',
                userList:[],
                pageNumber:1,
                pageSize:5,
                total:0,
                addUserVisible:false,
                user:{},
                modifyUserVisible:false,
                editUser:{},
            }
        },
        methods:{
            //获取用户信息列表
            getUserList(){
                //获取分页参数，封装对象
                let params={};
                params.pageNumber=this.pageNumber;
                params.pageSize=this.pageSize;
                //判断筛选输入框是否为空，不为空则将筛选条件一起封装
                if(this.username!=''){
                    params.username=this.username;
                }
                //向后台服务发送请求，处理返回结果
                //get请求的参数格式：{params:XXX},其中冒号前的params是参数名(固定的)
                this.$http.get('/user/page',{params:params})
                .then(result=>{
                    let data=result.data;
                    this.total=data.total;
                    this.userList=data.rows;
                })
            },
            showUpdate(row){
                this.modifyUserVisible=true;
                //数据回显
                this.editUser=row;
            },
            cancelModifyUser(){
                //关闭对话框
                this.modifyUserVisible=false;
                //清空对话框中已修改的内容
                this.editUser={};
                this.getUserList();
            },
            updateUser(){
                this.$http.post('/user/update',this.editUser)
                .then(result=>{
                    let data=result.data;
                    if(data.code==200){
                        this.$message.success(data.msg);
                        this.modifyUserVisible=false;
                        this.getUserList();
                    }else{
                        this.$message.warning(data.msg);
                    }
                })
            },
            deleteUser(id){
                this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    //发送删除请求,处理返回结果
                    //this.$http.get('/user/delete',{params:{"id":id}})
                    let params={};
                    params.id=id;
                    this.$http.get('/user/delete',{params:params})
                    .then(result=>{
                        let data=result.data;
                        if(data.code==200){
                            this.$message.success(data.msg);
                            this.getUserList();
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
                this.getUserList();
            },
            handleCurrentChange(val){
                //val:表示翻页的新页码
                //把新页码赋值给pageNumber
                this.pageNumber=val;
                //重新调用getUserList方法
                this.getUserList();
            },
            showAddUserDialog(){
                //打开添加用户的对话框
                this.addUserVisible=true;
            },
            cancelAddUser(){
                //关闭对话框
                this.addUserVisible=false;
                //清空添加框中的user对象
                this.user={};
            },
            addUser(){
                //获取添加用户信息
                //发送请求，处理返回结果
                this.$http.post('/user/add',this.user)
                .then(result=>{
                    let data=result.data;
                    if(data.code==200){
                        //消息提示
                        this.$message.success(data.msg);
                        //关闭对话框
                        this.addUserVisible=false;
                        //重新查询用户列表
                        this.getUserList();
                        //清空用户添加对话框中的user对象
                        this.user={};
                    }else{
                        this.$message.warning(data.msg);
                    }
                })
            }
        },
        mounted(){
            //mounted:文档就绪事件，当页面加载完成后自动触发的事件
            this.getUserList();
        }
    }
</script>

<style>

</style>