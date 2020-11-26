// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import router from './router'//同级目录下直接用./
import VueResource from 'vue-resource'
import MDialog from 'vue-m-dialog'
import FlashButton from 'flash-button'
import 'element-ui/lib/theme-chalk/index.css'
//import vueClapButton from 'vue-clap-button'
import { Button, Select ,Header,Avatar,Container} from 'element-ui'
import VueParticles from 'vue-particles'
import echarts from 'echarts'
import App from './App.vue';//引入app.vue 用他的内容来替换div id = app,最好和app.VUE放在同一目录下


Vue.use(VueResource)
Vue.use(ElementUI,{ size: 'small', zIndex: 3000 })
Vue.use(Container)
Vue.use(Header)
Vue.use(Button)
Vue.use(Select)
Vue.use(Container)
Vue.use(Avatar)
Vue.use(FlashButton)
Vue.use(VueParticles)
Vue.config.productionTip = false
Vue.component(Button.name, Button);
Vue.component(Select.name, Select);
Vue.use(echarts);
Vue.prototype.$echarts =echarts;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',//<App/>写法表示要选择的组件
  render: h => h(App)
})
Vue.use(MDialog, {//使用dialog组件框
  dialogName: 'm-dialog',
  alertName: 'msg',
  confirmName: 'dialog'
})
