<template>
  <div>
    <h3>welcome you {{name}}</h3>
    <a href="javascript:void(0);" @click="quit">注销登录</a><!--“href=...”点击链接后，页面不动，只对链接进行操作-->
  </div>
</template>

<script>
import { getCookie, delCookie } from '../../assets/js/cookie.js'
export default{
  data () {
    return {
      name: ''
    }
  },
  mounted () {
    let uname = getCookie('username')
    this.name = uname
    if (uname === '') {
      this.$router.push('/')
    }
  },
  methods: {
    quit () {
      delCookie('username')
      this.$router.push('/')
    }
  }
}
</script>
