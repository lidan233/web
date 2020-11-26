import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login/login.vue'
import Register from '@/views/register/register.vue'
import Main from '@/views/main/main.vue'
import AdminMain from '@/views/main/admin_main.vue'
import Home from '@/views/home/home.vue'
import Map from '@/views/map/map.vue'
import CreateProject from '@/views/admin/create_project'
import Projector from '@/views/projector/projector.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/main',
      name: 'Main',
      component: Main
    },
    {
      path: '/admin_main',
      name: 'admin_main',
      component: AdminMain
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/map',
      name: 'Map',
      component: Map
    },
    {
      path: '/create_project',
      name: 'create_project',
      component: CreateProject
    },
    {
      path: '/projector',
      name: 'projector',
      component: Projector
    }

  ] ,

})
