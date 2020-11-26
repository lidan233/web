<template xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<div>
  <div class="login"  id='shabi' v-show="showLogin">
      <el-container style="height:400px;width:80%;background:rgba(63, 140, 144, 0);margin: auto"
                    direction="vertical">

      <el-header  class="el-header" style="text-align:center; background: transparent" >
        <span style="height: 48px;width: 100px;font-family:Helvetica;font-size: 26px;color:#eeeeee ">VectorEasy Login</span>

      </el-header>
        <p v-show="showHint">{{hint}}</p>
        <div class="grid-content">

        <el-row >

              <span class="svg_container" style="background: #eeeeee">
                 <el-image src="http://localhost/vueApp/src/assets/user1.png" fit="fit"  v-show="Icon"></el-image>
                 <el-image src="http://localhost/vueApp/src/assets/user2.png" fit="fit"  v-show="Icon2"></el-image>
                 <el-image src="http://localhost/vueApp/src/assets/user3.png" fit="fit"  v-show="Icon3"></el-image>
                 </span>
              <el-input  id="fuck" type= "text" class="e_inner"placeholder="请输入用户名"  v-model="username" clearable></el-input>

        <p style="height: 2px"></p>
          </el-row>
        <el-row >
           <span class="svg_container" style="background: #eeeeee">
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" fit="fit"></el-avatar>
               </span>
           <el-input id="fuck1" type="password" placeholder="请输入密码" class="e_inner" v-model="password" show-password></el-input>

        </el-row>
           </div>

        <p style="height: 5px"></p>
        <el-button type="primary"class='get' style="height:55px;width:300px;border: 4px solid;background:#454545;font-size: 25px" @click="login">Login</el-button>
        <p style="height: 1px"></p>
        <el-radio-group v-model="radio2">
          <el-radio label="Project" @change="tab1" style="color: #7b98bc" v-model="role1"></el-radio>
          <el-radio label="Admin" @change="tab2" style="color: #7b98bc"v-model="role2"></el-radio>
          <el-radio label="User" @change="tab3" style="color: #7b98bc" v-model="role3"></el-radio>
        </el-radio-group>
        <span style="height:30px;font-size:20px;font-family: 'Helvetica';text-align: center;color: #eeeeee" @click="ToRegister">isn't user?go to register</span>

      </el-container>



  </div>

    <div class="register" v-show="showRegister">
      <el-container style="height:500px;width:80% ;background:rgba(63, 140, 144, 0);margin: auto"
                    direction="vertical">
      <el-header  class="el-header" style="text-align:center; background: transparent" >
        <span style="height: 48px;width: 100px;font-family:Helvetica;font-size: 26px;color:#eeeeee ">VectorEasy Register</span>
      </el-header>
      <p v-show="showHint">{{hint}}</p>
        <div>
        <el-row class="grid-content1">

         <el-input  id=fuck3 type= "text" placeholder="请输入账号"  class="e_inner"style="padding:0px;margin: auto;line-height:60px;height: 60px" v-model="newAccount" clearable></el-input>
          <el-input  id=fuck5 type= "text" placeholder="请输入用户名"  class="e_inner"style="padding:0px;margin: auto;line-height:50px" v-model="newUsername" clearable></el-input>
         <el-input id=fuck4 type="password" placeholder="请输入密码"  class="e_inner"style="padding:0px;margin: auto;line-height:60px;height: 60px" v-model="newPassword" show-password></el-input>

        </el-row>
      </div>
        <p style="height: 3px"></p>
      <input type="text" placeholder="please input revice code" style="width: 60% ;height: 40px;margin:auto" v-model="newCode">

        <el-checkbox-group v-model="checkList">
          <el-checkbox label="Project" @change="tab4()" style="color: #7b98bc" v-model="role4"></el-checkbox>
          <el-checkbox label="User" @change="tab5()" style="color: #7b98bc"v-model="role5"></el-checkbox>
        </el-checkbox-group>
      <el-button type="Primary" class='get' style="height:55px;width:300px;border: 4px solid;background:#454545;font-size: 25px"v-on:click="register" >Register</el-button>
      <span v-on:click="ToLogin" style="color:#eeeeee">is user？go to login in</span>
      </el-container>
    </div>
</div>


</template>

<style>
  .login-wrap{text-align:center;}
  p{color:red;}
  .get{display:block; width:250px; height:40px; line-height: 40px; margin:0 auto; border:none; background-color:#eeeeee; color:#fff; font-size:16px; margin-bottom:5px;}
  span{cursor:pointer;}
  span:hover{color:#41b883;}

  .grid-content {
    border-radius: 4px;
    height: 220px;
    width:100%;
    margin:auto;

  }
  .grid-content1 {
    border-radius: 4px;

    height: 170px;
    width:100%;
    margin:auto;
    border-radius: 6px;

  }
  .el-header{
    background-color: brown;
    line-height: 60px;
  }
  .el-container{
    position: relative;
    overflow: hidden;
    background:rgba(63, 140, 144, 0);
  }
  #shabi{
    background:rgba(63, 140, 144, 0);
  }
  #fuck.el-input__inner{
        height :47px ;
        background: #454545;
        margin: auto;

      }
  #fuck1.el-input__inner{
    height :47px ;
    background: #454545;
    margin: auto;
  }
  #fuck3.el-input__inner{
    height :47px ;
    width: 98%;
    background: #454545;
    margin: auto;
    border-radius: 0;
  }
  #fuck4.el-input__inner{
    height :47px ;
    background: #454545;
    width: 98%;
    margin: auto;
    border-radius: 0;
  }
  #fuck5.el-input__inner{
    height :47px ;
    background: #454545;
    width: 98%;
    margin: auto;
    border-radius: 0;
  }

    .e_inner {
      background:#eeeeee;
      border: 2px solid;
      -webkit-appearance: none;
      border-radius: 3px;
      padding: 3px 3px 3px 3px;
      vertical-align: middle;
      height: 50px;
      width: 60%;
    }

   .svg_container{
     padding:5px 5px 5px 5px;
     background: transparent ;
     border-radius: 5px;
     border: 4px #eeeeee;
     vertical-align: middle;
     width: 40px;
     display: inline-block;
     height:50px}



</style>

<script>
import _global from '../global/global'
/* eslint-disable */
import { setCookie, getCookie } from '../../assets/js/cookie.js'
export default {
  data() {
    return {
      showLogin: true,
      showRegister: false,
      showHint: false,
      hint: '',
      username: '',
      password: '',
      newUsername: '',
      newPassword: '',
      newCode: '',
      newAccount: '',
      checkList: [],
      Icon: true,
      Icon2: false,
      Icon3: false,
      role1: 'project',
      role2: 'root',
      role3: 'auth',
      role4: 'project',
      role5: 'auth',
      role: '',
      radio2: "Project"

    }
  },
  mounted() {
    if (getCookie('username')) {
      this.$router.push('/home')
    }
  },
  methods: {
    login() {
      if(this.role=="")
      {
        this.role = "project" ;
      }
      if (this.username === '' || this.password === '') {
        alert('请输入用户名或密码')
      } else {
        let data = {'name': this.username, 'passwd': this.password, 'role': this.role}
        // ???
        console.log(_global)
        console.log(data);
        var me = this;
        $.ajax({
          type: 'POST',
          url: 'http://127.0.0.1:8095/login',
          data: data,
          dataType: "text",
          success: function (res) {
            console.log(res)
            localStorage.setItem("temp", res)
            console.log(localStorage.getItem("temp"))
            res = JSON.parse(res)


            if (res["status"] == '200' && data["role"] === 'root') {

              console.log("hello!")
              me.$router.push('admin_main')

            } else if (res === 'passwd') {
              me.tishi = '密码输入错误'
              me.showTishi = true
            }
            else if (res["status"] == '200' && data["role"] === 'project') {
              console.log("projector")
              me.$router.push('projector')

            }
            else if (res["status"] == '200') {
              _global.username = me.username
              _global.userid = res['id']
              _global.projectid = res['projectid']
              console.log(_global.username)
              console.log(_global.userid)
              console.log(_global.projectid)
              me.tishi = '登录成功'
              me.showTishi = true

              me.$router.push('/map');
              setCookie('username', me.username, (1000 * 60))

            }
            else {
              me.tishi = '该用户不存在'
              me.showTishi = true

            }

          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
            me.$router.push('/map');
          }


        });

      }
    },
    tab1:function (val)
    {
      let  me =this
      me.Icon=(val==="Project")?true:false
      me.Icon2 = (val==="Admin")?true:false
      me.Icon3 =(val==="User")?true:false
      me.role = me.role1

    },
    tab2:function(val) {
      let me =this
      me.Icon=(val==="Project")?true:false
      me.Icon2 = (val==="Admin")?true:false
      me.Icon3 =(val==="User")?true:false

      me.role = me.role2

    }
    ,
    tab3:function (val) {
      let me =this
      me.Icon=(val==="Project")?true:false
      me.Icon3 =(val==="User")?true:false
      me.Icon2 = (val==="Admin")?true:false
      me.role = me.role3
    },
    tab4() {

      this.role = this.role4
    },
    tab5() {

      this.role = this.role5
    },
    ToRegister() {
      this.showRegister = true
      this.showLogin = false
    },
    ToLogin() {
      this.showRegister = false
      this.showLogin = true
    },
    register() {
      if (this.newUsername === '' || this.newPassword === '') {
        alert('请输入用户名或密码')
      } else {
        let data = {
          'account': this.newAccount,
          'passwd': this.newPassword,
          'requestcode': this.newCode,
          'name': this.newUsername,
          'role': this.role
        }
        // ???
        $.ajax({
          type: 'POST',
          url: 'http://192.168.8.132:8095/register',
          data: data,
          success: function (res) {
            console.log(res)
            if (res === 'ok') {
              this.tishi = '注册成功'
              this.showTishi = true
              this.username = ''
              this.password = ''


              $("#dialog").dialog({
                autoOpen: false, //设置对话框打开的方式 不是自动打开
                show: "blind",  //打开时的动画效果
                hide: "explode",  //关闭时的动画效果
                modal: true, //遮罩效果true  false 非遮罩效果
                /*				 buttons:{  //添加按钮的操作
                              "确定":function(){
                                    $(this).dialog("close");//关闭对话框的操作
                                  },
                               "取消":function(){
                                     alert("取消的操作");
                                   }
                            },
                */				  //buttons的写法的第二种方式
                buttons: [
                  {
                    text: "ok",
                    click: function () {
                      $(this).dialog("close");//关闭对话框的操作
                    }
                  },
                  {
                    text: "取消",
                    click: function () {

                      $(this).dialog("close");//关闭对话框的操作
                    }
                  }],
                draggable: true,//是否可以拖动的效果  true 可以拖动 默认值就是true  false代表不可以拖动
                closeOnEscape: false,//是否采用esc键退出对话框 如果为false 则不采用 true 采用 默认值是true
                title: "添加用户操作界面",//对话框的标题
                position: "top", //对话框弹出的位置 可以使top left right center  bottom 默认值是 center
                width: 800,  //对话框的宽度
                height: 600, //对话框的高度
                resizable: false,//是否可以改变的操作   false 则不可以改变尺寸  true可以改变尺寸   默认值是true
                zIndex: 6,
                drag: function (event, ui) {
                  //可以测试出 对话框的当前坐标位置
                }
              });
              $("#dialog").html("注册成功");
              $("#dialog").dialog("open");
            }
          },

          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
          },
        });
      }
    }
  }
}




</script>
