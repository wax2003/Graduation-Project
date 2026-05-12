
<template>
  <div class="page-container">
    <!-- 筛选卡片 -->
    <div class="filter-container">
      <el-card>
        <div class="game-condition">
          <!-- 游戏区服选择 -->
          <div class="game-condition-item">
            <span>游戏名：</span>
            <el-select
              v-model="selectedGame"
              placeholder="请选择游戏"
              class="w240"
              @change="addFilter('game', selectedGame)"
            >
              <el-option
                v-for="game in games"
                :key="game"
                :label="game"
                :value="game"
              ></el-option>
            </el-select>
            <div class="input-with-button">
    <el-input
      v-model="searchKeyword"
      placeholder="添加游戏关键词"
      class="ml10 w240"
    ></el-input>
    <el-button
      type="primary"
      class="ml10"
      icon="el-icon-search"
      @click="handleSearch"
    >
      搜索
    </el-button>
  </div>


          </div>

          <!-- 价格筛选 -->
          <div class="game-condition-item">
            <span>价格筛选：</span>
            <el-radio-group
              v-model="selectedPriceRange"
              class="price-radio-group"
              @change="onPriceRangeChange"
            
            >
              <el-radio-button label="0-2元">0-2元</el-radio-button>
              <el-radio-button label="2-3元">2-3元</el-radio-button>
              <el-radio-button label="3-5元">3-5元</el-radio-button>
              <el-radio-button label="5-10元">5-10元</el-radio-button>
            </el-radio-group>
          </div>
        </div>

        <!-- 筛选条件标签 -->
        <div class="filter-tags">
          <div
            v-for="(tag, index) in activeFilters"
            :key="index"
            class="filter-tag"
          >
            <span>{{ tag.label }}</span>
            <i class="el-icon-close" @click="removeFilter(tag.key)"></i>
          </div>
        </div>
      </el-card>
    </div>
    <!-- 卡片区域 -->
    <div class="card-container">
      <el-card
        v-for="item in accounts"
        :key="item.id"
        :body-style="{ padding: '10px' }"
        class="account-card"
        shadow="hover"
      >
        <el-row class="account-info" :gutter="8">
          <el-col :span="6">
  <el-image 
    class="account-image" 
    :src="item.accountPicture" 
    :alt="item.description"
    fit="cover"
    :preview-src-list='[item.accountPicture]'
  />
</el-col>
<el-col :span="18">
  <p class="description">{{ item.description }}</p>
</el-col>

        </el-row>
        <el-row class="account-actions">
          <el-col>
            <div class="action-container">
              <p class="price">
                <span class="currency">¥</span>{{ item.priceHour }}<span class="unit">/时</span>
              </p>
              <el-button type="primary"
              @click="openDialog(item)">立即租用</el-button>
            </div>
          </el-col>
        </el-row>
      </el-card>
  
    </div>
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNumber"
            :page-sizes="[6, 12, 24, 48]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            class="pagination"
            >
        </el-pagination>
 
        <!-- 弹出框 -->
        <el-dialog
  title="核对订单信息"
  :visible.sync="dialogVisible"
  width="500px"
  :modal="false"
>
  <div class="dialog-body">
    <p><strong>游戏名：</strong>{{ selectedGame }}</p>
    <p><strong>游戏描述：</strong>{{ selectedAccount.description }}</p>
    <el-form :model="orderForm">
      <el-form-item label="租赁时间" class="bold-label">
        <el-select v-model="orderForm.rentalTime" placeholder="请选择租赁时间">
          <el-option
            v-for="hour in rentalOptions"
            :key="hour"
            :label="`${hour}小时`"
            :value="hour"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
    <span class="total-amount">
      <strong>实付总额：</strong><span class="amount">¥{{ calculateTotal }}</span>
    </span>
    <el-button type="danger" @click="confirmOrder">支付</el-button>
  </div>
  </div>
  <!-- 将实付金额与完成租号放在一行 -->

</el-dialog>

  </div>
</template>



<script>
export default {
  data() {
    return {
      games: ["穿越火线", "英雄联盟", "无畏契约", "CSGO"],
      selectedGame: null,
      searchKeyword: "",
      selectedPriceRange: null,
      price_min:0,
      price_max:0,
      activeFilters: [], // 存储当前的条件标签
      accounts: {},
      dialogVisible: false,
      selectedAccount: {}, // 存储选中账号信息
      orderForm: {
        rentalTime: null, // 选择的租赁时间
      },
      rentalOptions: [1, 2, 3, 4, 5], // 租赁时间选项
      userid: sessionStorage.getItem('userid'),
      total_price: 0,  //总金额
      pageNumber:1,
      pageSize:6,
      total:0, //总记录数

    };
  },
  computed: {
  // 动态计算总金额
  calculateTotal() {
    if (!this.selectedAccount.priceHour || !this.orderForm.rentalTime) {
      return 0; // 如果数据不足，返回 0
    
    }else{
      this.total_price = this.selectedAccount.priceHour * this.orderForm.rentalTime;
       this.total_price = this.total_price.toFixed(2); // 保留两位小数
    }
  return this.total_price;
   
    }
        },
  methods: {

    openDialog(account) {
      this.selectedAccount = account;
      this.orderForm.rentalTime = null; // 重置租赁时间
      this.dialogVisible = true;
    },
    // 确认订单逻辑
    confirmOrder() {
      if (!this.orderForm.rentalTime) {
        this.$message.error("请选择租赁时间！");
        return;
      }
  // 发送订单信息到后端
      /*
     先查询账号状态是否为待租用
    然后将订单信息插入到订单表中
       userid: 1,
       game_account_id: 1,
        rental_time: 2,
        price: 100
        status:租用中
       */
   this.$http.get(`/accounts/status/${this.selectedAccount.id}`)
  .then(response => {
    if(response.data.status == "待租用"){
      this.selectedAccount = response.data;
      const params = {
        userId: this.userid,
        gameAccountId: this.selectedAccount.id,
        time: this.orderForm.rentalTime,
        price: this.total_price,
        status: "租用中"
      };
      console.log(params);
      this.$http.post('/rental/add', params)
      .then(response => {
        console.log(response.data);
        this.$message.success("开始租用!"); // 提示用户  增加账号 密码
        this.dialogVisible = false; // 关闭对话框
      })
      .catch(error => {
        console.log(error);
      });
    }else{
      this.$message.error("账号已被租用！");
    }
  });

    },
    

    handleSearch() {
    if (this.searchKeyword.trim()) {
      console.log("搜索关键词：", this.searchKeyword);
      this.addFilter("search", this.searchKeyword);
    } else {
      alert("请输入搜索关键词！");
    }
  },
    // 添加筛选条件
    addFilter(key, value) {
      if (!value) return;
      const existingFilter = this.activeFilters.find((filter) => filter.key === key);
      if (existingFilter) {
        existingFilter.label = value;
      } else {
        this.activeFilters.push({ key, label: value });
      }
      this.getList();
    },
    // 移除筛选条件
    removeFilter(key) {
      this.activeFilters = this.activeFilters.filter((filter) => filter.key !== key);
      if (key === "game") this.selectedGame = null;
      if (key === "search") this.searchKeyword = "";
      if (key === "price") this.selectedPriceRange = null;
      this.price_min = 0;
      this.price_max = 0;
      this.getList();
    },
    getList(){
      console.log(this.userid);
      const params = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      gameName: this.selectedGame,
      priceMin:this.price_min,
      priceMax:this.price_max,
      status:"待租用",
      description:this.searchKeyword

    };
       this.$http.post('/accounts/pagex', params)
      .then(response => {
        console.log(response.data);
        this.accounts = response.data.rows;
        this.total = response.data.total;
      })
      .catch(error => {
        console.log(error);
      }
      
    )
     
    },
    handleSizeChange(val){
       this.pageSize=val;
      this.getList();
            },
      handleCurrentChange(val){
          this.pageNumber=val;
         this.getList();
            },
      //设置价格上下限
    onPriceRangeChange() {
      console.log("价格筛选：", this.selectedPriceRange);
     
      // 根据 selectedPriceRange 设置 priceMin 和 priceMax
      const rangeMap = {
        '0-2元': { min: 0, max: 2 },
        '2-3元': { min: 2, max: 3 },
        '3-5元': { min: 3, max: 5 },
        '5-10元': { min: 5, max: 10 },
      };
      const range = rangeMap[this.selectedPriceRange];
      if (range) {
        this.price_min = range.min;
        this.price_max = range.max;
      } else {
        this.price_min = 0;
        this.price_max = 0;
      }
      this.addFilter('price',this.selectedPriceRange)
    },
  },
 
};
</script>

  <style scoped>

.pagination{

  display: flex;
  justify-content: flex-end; /* 右对齐 */
  margin-top: 20px;
  margin-right: 200px;
  background: #fff; /* 如果需要背景色 */
}


.page-container {
  display: flex;
  flex-direction: column;
  height: 80vh; /* 整个页面高度 */
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
  overflow-y: auto; /* 卡片滚动条 */
  align-items: center;
  
}

.account-card {
  margin:  10px;
  padding: 15px;
  width: 650px;
  height: 230px;
  border-radius: 6px;
  box-shadow: 0 2px 14px 0 rgba(0, 0, 0, 0.1);
}

.account-image {
  width: 100%;
  height: 155px;
  border-radius: 6px;
  object-fit: cover;
  object-position: center; /* 显示中间部分 */
  overflow: hidden;
}

.description {
  font-size: 18px;
  color: #666;
}

.action-container {
  display: flex;
  align-items: center;
  gap: 10px; /* 按钮与价格之间的间距 */
  justify-content: flex-end; /* 右对齐 */
}

.price {
  font-size: 18px;
  color: #ff0000;
  font-weight: bold;
}


/* 筛选卡片宽度 */
.filter-container {
  margin: 20px auto;
  max-width: 1200px;
}

/*游戏名 游戏价格*/
.game-condition-item {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.w240 {
  width: 240px;
}

.ml10 {
  margin-left: 10px;
}

/* 价格筛选 */
.price-radio-group {
  display: flex;
  gap: 10px;
}

/* 筛选条件标签 */
.filter-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 20px;
}

.filter-tag {
  display: flex;
  align-items: center;
  background: #f5f5f5;
  border: 1px solid #dcdfe6;
  border-radius: 16px;
  padding: 5px 10px;
  font-size: 14px;
  color: #606266;
}

.filter-tag i {
  margin-left: 5px;
  cursor: pointer;
}

.total-amount {
  font-size: 20px;
}

.amount {
  margin-right: 10px;
  color: #ff0000; /* 红色字体 */
  font-weight: bold;
}


 .dialog-footer {
  display: flex;
  justify-content: flex-end; /* 右对齐 */
  align-items: center; /* 垂直居中 */
  gap: 10px; /* 按钮与总额之间的间距 */

 }

 /* 加粗标签 */
 .bold-label {
  font-weight: bold;
}

/* 搜索框右移 */
.input-with-button{
  margin-left: 500px;
  gap: 10px;
}

  </style>