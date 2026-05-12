import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Particles from 'vue-particles'//粒子特效
import axios from 'axios'

Vue.use(ElementUI)
Vue.use(Particles)

//设置后台服务域名的全局配置
axios.defaults.baseURL='http://localhost:8088'
//axios设置为全局变量
Vue.prototype.$http=axios

Vue.config.productionTip = false






new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
//别改
