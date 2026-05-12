<template>
  <div id="login-container">
    <!--
    页面布局
    span:跨列
    offset:偏移
    -->
    <el-row>
      <el-col :span="8" :offset="8">
        <el-card>
          <h1>{{ isLogin ? '用户登录' : '用户注册'}}</h1>
          <el-form>
            <el-form-item>
              <!-- v-model:变量绑定指令，常用于输入框 -->
              <el-input type="text" placeholder="请输入用户名" v-model="username"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input placeholder="请输入密码" v-model="password" show-password></el-input>
            </el-form-item>
            <el-form-item label="身份" label-width="80px">
              <el-select v-model="role" placeholder="请选择">
                <el-option label="管理员" value="admin"></el-option>
                <el-option label="普通用户" value="user"></el-option>
                <el-option label="游戏商家" value="merchant"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <!-- v-on:事件指令，简写为@：@click -->
              <el-button type="primary" v-on:click="isLogin ? login() : register()">{{ isLogin?'登录':'注册' }}</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="text" @click="toggleForm">
                {{ isLogin ? '没有账号? 注册' : '已有账号? 登录' }}
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default{
    data(){
      return{
        username:'',
        password:'',
        userid:'',
        role:'',
        isLogin:true
      }
    },
    methods:{
      login(){
        //获取输入框中的用户名和密码，封装为参数对象
        const params = {
        username: this.username,
        password: this.password,
        role: this.role
        };
        //向后台服务发送请求，处理收到的结果
        this.$http.post('/user/login',params)
        .then(result=>{
          let data=result.data;
          //判断后台返回的结果的code状态码，200-登录成功，否则失败
          console.log(data);
          if(data.code==200){
            this.$message.success(data.msg);
            //把用户名存入页面缓存
            sessionStorage.setItem("username",this.username);
            sessionStorage.setItem("role",this.role);
            sessionStorage.setItem("userid",data.id);
            console.log(data.id); // 确认是否有值
            //页面跳转
            this.$router.push("/index");
          }else{
            this.$message.warning(data.msg);
          }
        })
      },
      register(){
        const params={
          username:this.username,
          password:this.password,
          role:this.role
        };
        this.$http.post('/user/register',params)
        .then(result=>{
          let data=result.data;
          if(data.code==200){
            this.$message.success(data.msg);
            this.toggleForm();
          }
          else{
            this.$message.warning(data.msg);
          }
        })
      },
      toggleForm(){
        this.isLogin=!this.isLogin;
        this.username='';
        this.password='';
        this.role='';
        this.createdAt='';
      },
    }
  }
</script>

<!--
 scoped:css样式只在当前页面中生效
 -->
<style scoped>
  #login-container {
    position: fixed;
    background-image: url(../assets/m-1.jpg);
    height: 100vh;
    width: 100%;
  }
  .el-row{
    margin-top: 170px;
    text-align: center;
  }
</style>
