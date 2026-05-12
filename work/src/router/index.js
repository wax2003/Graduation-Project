import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login.vue'
import Index from '@/components/Index.vue'
import UserList from '@/components/UserList.vue'
import AccountList from '@/components/AccountList.vue'
import Community from '@/components/Community.vue'


Vue.use(VueRouter)

const routes = [{
    path:'/',
    name:'Login',
    component:Login
  },
  {
    path: '/index',
    name: 'Index',
    component: Index,
    children:[{
      path:'userlist',
      name:'UserList',
      component:UserList
    },
    {
      path:'accountlist',
      name:'AccountList',
      component:AccountList
    },
    {
      path:'home',
      name:'Home',
      component:()=>import('@/components/Home.vue')
    },
    {
      path:'community',
      name:'Community',
      component:Community
    }
  ]
  }
    
 

  
]





const router = new VueRouter({
  routes
})//别改

export default router