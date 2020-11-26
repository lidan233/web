<template>
  <div>
    <h3>以下均为必填项</h3>
    <form id="staff_info" align="center" style="color: aliceblue;">
      <fieldset style="margin: 0 auto;border: 5px solid black">
        <label for="name">地图</label>
        <el-select size="small" v-model="create_map_kind" id="name" >
          <el-option value="GOOGLE_SATELLITE_CHINA">谷歌影像(中国)</el-option>
          <el-option value="GOOGLE_ROADMAP_CHINA">谷歌地图(中国)</el-option>
          <el-option value="GOOGLE_SATELLITE">谷歌影像</el-option>
          <el-option value="GOOGLE_ROADMAP">谷歌地图</el-option>
          <el-option value="AMAP_ROAD">高德地图</el-option>
          <el-option value="AMAP_SATELLITE">高德影像</el-option>
          <el-option value="TIANDITU_ROAD">天地图</el-option>
          <el-option value="BING_ROAD">Bing地图</el-option>
          <el-option value="BING_SATELLITE">Bing影像</el-option>
          <el-option value="OSM">OpenStreetMap</el-option>
        </el-select><br>

        <label>中心点</label>
        <input type="text" v-model="coordinate_x"  id="x" value="" class="text ui-widget-content ui-corner-all">
        <input type="text" v-model="coordinate_y"  id="y" value="" class="text ui-widget-content ui-corner-all">
        <br>
        <label>项目发起人</label>
        <input type="text" v-model="ProjectUsr"  id="go" value="" class="text ui-widget-content ui-corner-all">
        <br>
        <label>Feature总数</label>
        <input type="text" v-model="FtNum"  id="Feature" value="" class="text ui-widget-content ui-corner-all">
        <br>
        <label>范围</label>
        <input type="text" v-model="max_x" id="extent_max_x" value="" class="text ui-widget-content ui-corner-all">
        <input type="text" v-model="max_y" id="extent_max_y" value="" class="text ui-widget-content ui-corner-all">
        <input type="text" v-model="min_x" id="extent_min_x" value="" class="text ui-widget-content ui-corner-all">
        <input type="text" v-model="min_y" id="extent_min_y" value="" class="text ui-widget-content ui-corner-all">
      </fieldset>
    </form>
    <button class="el-create" v-on:click="create_first_project()">创建项目</button>
  </div>
</template>

<style>
  ul{padding: 0;}
  ul li{list-style: none;}
  .el-create {
    display: inline-block;
    text-indent: 0;
    position: relative;
    color: #eeeeee;
    background: black;
    padding: 7px 15px;
    border-radius: 6px;
    height: 32px;
    opacity: .8;
    text-align: center;
    font-family: Ubuntu, sans-serif;
    font-size: 14px;
    text-decoration: none;
    overflow: hidden;
    -webkit-transition: all .15s ease-in;
    transition: all .15s ease-in;
    border: 6px;
    width: 200px;
  }
  form#staff_info fieldset {
    background: rgba(255,255,255,.3);
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    -khtml-border-radius: 5px;
    border-radius: 5px;
    line-height: 40px;
    list-style: none;
    padding: 5px 10px;
    margin-bottom: 2px;
  }


  form#staff_info fieldset legend {
    color:#302A2A;
    font: bold 16px/2 Verdana, Geneva, sans-serif;
    font-weight: bold;
    text-align: left;
    text-shadow: 2px 2px 2px rgb(88, 126, 156);
  }
</style>

<script>
/* eslint-disable */
  export default{//提供接口给外部，其他文件通过import引入
    data () {
      return {
        show1: false,
        create_map_kind:"BING_SATELLITE" ,
        coordinate_x:120 ,
        coordinate_y:30 ,
        max_x:130 ,
        max_y:40 ,
        min_x:110 ,
        min_y:20,
        ProjectUsr:"",
        FtNum:""

      }
    },
    methods:{
      create_first_project () {
        var me = this ;
        let data = {
          'tokenid': JSON.parse(localStorage.getItem("temp"))['tokenid'],
          'source_kind': this.create_map_kind,
          'center_x': this.coordinate_x,
          'center_y': this.coordinate_y,
          'extent_min_x': this.min_x,
          'extent_min_y': this.min_y,
          'extent_max_x': this.max_x,
          'extent_max_y': this.max_y,
          'project_user_name':this. ProjectUsr,
          'features':this.FtNum
        }

          console.log(data) ;

        $.ajax({
          type: 'POST',
          url: "http://192.168.8.132:8095/addProject",
          data: data,

          success: function(res) {
            console.log("ok")
            console.log(res);


            if (res!= "false"&&res!=false){

                  $("#dialog").dialog({
                  autoOpen:false , //设置对话框打开的方式 不是自动打开
                  show:"blind",  //打开时的动画效果
                  hide:"explode",  //关闭时的动画效果
                  modal:true, //遮罩效果true  false 非遮罩效果
                  buttons:[
                    {
                      text:"OK",
                      click:function(){
                        $(this).dialog("close");//关闭对话框的操作
                      }
                    },
                    {
                      text:"取消",
                      click:function(){

                        $(this).dialog("close");//关闭对话框的操作
                      }
                    }],
                  draggable:true ,//是否可以拖动的效果  true 可以拖动 默认值就是true  false代表不可以拖动
                  closeOnEscape:false ,//是否采用esc键退出对话框 如果为false 则不采用 true 采用 默认值是true
                  title:"添加项目操作界面" ,//对话框的标题
                  position:"top", //对话框弹出的位置 可以使top left right center  bottom 默认值是 center
                  width:600,  //对话框的宽度
                  height:400, //对话框的高度
                  resizable:false ,//是否可以改变的操作   false 则不可以改变尺寸  true可以改变尺寸   默认值是true
                  zIndex:6,
                  drag:function(event,ui){
                    //可以测试出 对话框的当前坐标位置
                  }
                });
                $("#dialog").html(res) ;
                $("#dialog").dialog("open");

            }
          }

        }) ;
      }
    }

  }
</script>
