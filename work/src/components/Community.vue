<template>
  <div class="community-container">
    <el-card class="post-form">
      <h3>发布新帖子</h3>
      <el-form>
        <el-form-item label="标题" label-width="80px">
          <el-input v-model="newPost.title" placeholder="请输入帖子标题"></el-input>
        </el-form-item>
        <el-form-item label="内容" label-width="80px">
          <el-input type="textarea" v-model="newPost.content" placeholder="请输入帖子内容" rows="4"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="publishPost">发布</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="posts-list">
      <h3>论坛帖子</h3>
      <el-empty v-if="posts.length === 0" description="暂无帖子"></el-empty>
      
      <div v-for="post in posts" :key="post.id" class="post-item">
        <div class="post-header">
          <div class="post-title">
            <h4>{{ post.title }}</h4>
            <span class="post-author">作者: {{ post.author }}</span>
            <span class="post-time">{{ formatDate(post.createdAt) }}</span>
          </div>
          <div class="post-actions">
            <el-button 
              v-if="canDeletePost(post)" 
              type="danger" 
              size="small" 
              @click="deletePost(post.id)">
              删除
            </el-button>
          </div>
        </div>
        <div class="post-content">
          {{ post.content }}
        </div>
        <el-divider></el-divider>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: sessionStorage.getItem('username'),
      userid: sessionStorage.getItem('userid'),
      role: sessionStorage.getItem('role'),
      newPost: {
        title: '',
        content: ''
      },
      posts: []
    }
  },
  methods: {
    publishPost() {
      if (!this.newPost.title.trim()) {
        this.$message.warning('请输入帖子标题');
        return;
      }
      if (!this.newPost.content.trim()) {
        this.$message.warning('请输入帖子内容');
        return;
      }

      const params = {
        title: this.newPost.title,
        content: this.newPost.content,
        author: this.username,
        userid: this.userid,
        role: this.role
      };

      this.$http.post('/community/addpost', params)
        .then(result => {
          let data = result.data;
          if (data.code == 200) {
            this.$message.success('帖子发布成功');
            this.newPost.title = '';
            this.newPost.content = '';
            this.fetchPosts();
          } else {
            this.$message.warning(data.msg);
          }
        })
    },
    fetchPosts() {
      this.$http.get('/community/posts')
        .then(result => {
          let data = result.data;
          if (data.code == 200) {
            this.posts = data.data;
          }
        })
    },
    canDeletePost(post) {
      // 管理员可以删除任意帖子
      if (this.role === 'admin') {
        return true;
      }
      // 普通用户和游戏商家只能删除自己的帖子
      return post.userid == this.userid;
    },
    deletePost(postId) {
      this.$confirm('确定要删除此帖子吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          id: postId
        };
        this.$http.post('/community/deletepost', params)
          .then(result => {
            let data = result.data;
            if (data.code == 200) {
              this.$message.success('帖子删除成功');
              this.fetchPosts();
            } else {
              this.$message.warning(data.msg);
            }
          })
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    formatDate(dateStr) {
      if (!dateStr) return '';
      const date = new Date(dateStr);
      return date.toLocaleString('zh-CN');
    }
  },
  mounted() {
    this.fetchPosts();
  }
}
</script>

<style scoped>
.community-container {
  padding: 20px;
}

.post-form {
  margin-bottom: 20px;
}

.post-form h3 {
  margin-top: 0;
  color: #333;
}

.posts-list h3 {
  margin-top: 0;
  color: #333;
}

.post-item {
  padding: 15px 0;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.post-title h4 {
  margin: 0 0 8px 0;
  color: #303133;
}

.post-author {
  margin-right: 15px;
  color: #909399;
  font-size: 12px;
}

.post-time {
  color: #909399;
  font-size: 12px;
}

.post-actions {
  display: flex;
  gap: 10px;
}

.post-content {
  padding: 10px 0;
  color: #606266;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-word;
}
</style>
