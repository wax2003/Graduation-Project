<template>
  <div>
    <el-container class="index-container">
      <el-header>
        <div class="header-container">
          <div><span>账号租赁系统</span></div>
          <div>
            <!-- 插值表达式：{{}}，用在标签的文本区域 -->
            <span>{{username}}</span>
            <el-button type="success" size="small" @click="logout">退出登录</el-button>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <!-- router:是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转 -->
          <el-menu default-active="2" class="el-menu-vertical-demo" router>
            <!-- 管理员菜单 -->
            <el-submenu index="1" v-show="role=='admin'">
              <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item index="/index/userlist">用户列表</el-menu-item>
            </el-submenu>

            <el-submenu index="2" v-show="role=='admin'">
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span>账号管理</span>
              </template>
              <el-menu-item index="/index/accountlist">账号列表</el-menu-item>
            </el-submenu>

            <!-- 游戏商家菜单 -->
            <el-submenu index="3" v-show="role=='merchant'">
              <template slot="title">
                <i class="el-icon-s-goods"></i>
                <span>游戏管理</span>
              </template>
              <el-menu-item index="/index/gamelist">游戏列表</el-menu-item>
              <el-menu-item index="/index/gameadd">发布游戏</el-menu-item>
            </el-submenu>

            <el-submenu index="4" v-show="role=='merchant'">
              <template slot="title">
                <i class="el-icon-data-analysis"></i>
                <span>收益统计</span>
              </template>
              <el-menu-item index="/index/earnings">收益详情</el-menu-item>
            </el-submenu>

            <!-- 平台用户菜单 -->
            <el-submenu index="5">
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span>账号租赁</span>
              </template>
              <el-menu-item index="/index/home">租赁列表</el-menu-item>
            </el-submenu>

            <el-submenu index="6" v-show="role=='user'">
              <template slot="title">
                <i class="el-icon-s-order"></i>
                <span>我的订单</span>
              </template>
              <el-menu-item index="/index/myorders">订单列表</el-menu-item>
            </el-submenu>

            <!-- 社区交流菜单（所有用户可见） -->
            <el-submenu index="7">
              <template slot="title">
                <i class="el-icon-chat-line-round"></i>
                <span>社区交流</span>
              </template>
              <el-menu-item index="/index/community">交流论坛</el-menu-item>
            </el-submenu>

          </el-menu>

        </el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        username: sessionStorage.getItem('username'),
        role:sessionStorage.getItem('role'),
      }
    },
    methods: {
      logout() {
        // 清除缓存
        sessionStorage.clear();
        // 跳转到登录页面
        this.$router.push("/");
      }
    }
  }
</script>

<style scoped>
  .index-container {
    position: fixed;
    height: 100%;
    width: 100%;
  }
  .el-header {
    padding: 0px;
  }
  .header-container {
    display: flex;
    justify-content: space-between;
    align-items: stretch;
    background-color: lightskyblue;
    padding: 0px 10px 0px 10px;
    color: white;
  }
  span {
    line-height: 60px;
    font-weight: bolder;
  }
  .el-aside {
    background-color: whitesmoke;
  }
  .el-menu {
    background-color: whitesmoke;
    border-right: 0px;
  }
</style>
