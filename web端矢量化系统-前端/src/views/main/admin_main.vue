<template>
  <div>
    <h3 style="color: #7095ff">已有项目</h3>
     <div style="margin: 0 auto;border: 5px solid black">
    <table class="table table-bordered table-striped text-center" align="center" style="color: aliceblue;border-collapse:separate; border-spacing:0px 10px;">
      <thead>
      <tr>
        <th>项目名</th>
        <th>项目邀请码</th>
        <th>项目数据源</th>
        <th>项目中心点</th>
        <th>项目发起人</th>
        <th>项目Feature总数</th>

      </tr>
      </thead>
      <tbody>
      <tr v-for ="project in tableData" :key="project.id" >
        <td>{{project.projectid}}</td>
        <td>{{project.requestcode}}</td>
        <td>{{project.source}}</td>
        <td>{{project.center}}</td>
        <td>{{project.user}}</td>
        <td>{{project.Features}}</td>
        <button class="el-admin1" v-on:click="download_file(project.projectid)">下载数据</button>
      </tr>
      </tbody>
    </table>
       <el-row>
     <button class="el-admin2" v-on:click="create_project()">新建项目</button>
       </el-row>
  </div>
  </div>
</template>

<style>
  ul{padding: 0;}
  ul li{list-style: none;}
    .el-admin1{
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
      font-family: Ubuntu,sans-serif;
      font-size: 14px;
      text-decoration: none;
      overflow: hidden;
      -webkit-transition: all .15s ease-in;
      transition: all .15s ease-in;
      border: 6px;
      width:200px;
    }
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  .el-admin2{
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
    font-family: Ubuntu,sans-serif;
    font-size: 14px;
    text-decoration: none;
    overflow: hidden;
    -webkit-transition: all .15s ease-in;
    transition: all .15s ease-in;
    border: 6px;
    width:200px;
  }

</style>

<script>
export default {
  data () {
    return {
      tableData: [{
        'projectid': '001',
        'requestcode': 'ABCDEFG',
        'source': 'XXX map',
        'center': '长沙市岳麓区麓山南路中南大学校本部',
        'user':'lidan',
        'Features':'111'
      }],
      columns: [
        {title: '项目名', key: 'projectid'},
        {title: '项目邀请码', key: 'requestcode'},
        {title: '项目数据源', key: 'source'},
        {title: '项目中心点', key: 'center'},
        {title: '项目发起人', key: 'user'},
        {title: '项目Feature总数', key: 'Features'}
      ],
      tableData1:[{}]
    }
  },
  mounted () {
    var me = this
    /* eslint-disable */
    let data = {'do': 'get_project'}
    $.ajax({
      type: 'POST',
      url: 'http://localhost/vueApp/index1.php',
      data: data,
      success: function (res) {
        console.log(res);
        res = JSON.parse(res);
        console.log(res[0])
        if (res === 'false' || res !== false) {
          me.tableData = res;
          console.log('fuck');
        }
      }
    })
  },
  methods: {
    create_project () {
      console.log("success")
      var me = this
      me.$router.push('create_project')
    },
    download_file (id) {
      var me = this


      var url = 'http://localhost/vueApp/index1.php'
      var form = $("<form>");//定义一个form表单
      form.attr("style", "display:none");
      form.attr("target", "");
      form.attr("method", "post");//请求类型
      form.attr("action", url);//请求地址
      $("body").append(form);//将表单放置在web中

      var input1 = $("<input>");
      input1.attr("type", "hidden");
      input1.attr("name", "do");
      input1.attr("value", "getjson");
      form.append(input1);

      var input2 = $("<input>");
      input2.attr("type", "hidden");
      input2.attr("name", "projectid");
      input2.attr("value", id);
      form.append(input2);
      form.submit();//表单提交
    }

  }
}
</script>
