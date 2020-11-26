<template>
  <div>
    <h3 style="color: #7095ff">Project Check</h3>
    <div style="margin: 0 auto;border: 5px solid black">
      <table class="table table-bordered table-striped text-center" id="main_table" align="center" style="color: aliceblue;border-collapse:separate; border-spacing:0px 10px;">
        <thead>
        <tr>
          <th>项目ID</th>
          <th>项目参与人数</th>
          <th>现时Feature数</th>
          <th>项目Feature总数</th>

        </tr>
        </thead>
        <tbody>
        <tr v-for ="project in tableData2" :key="project.id" >
          <td>{{project.projectid}}</td>

          <td>{{project.UsrNum}}</td>
          <td>{{project.total}}</td>
          <td>{{project.totalFt}}</td>
          <td><button class="el-proj1" v-on:click="delete_file(project.projectid)">删除项目</button></td>
          <td><button class="el-proj1" v-on:click="delete_file(project.projectid)">切换图表</button></td>
        </tr>
        </tbody>
      </table>
    </div>
    <ul>
     <li>
      <div id="pie1" style="width:48%;float:left;background: transparent;border: 5px solid black">
          <div id="main1" style="width:100%;height: 300px"></div>
      </div></li>
      <li>
    <div id="bar1" style="width: 48%;float:right;background: transparent;border:5px solid black">
       <div id="main2" style="width:100%;height: 300px"></div>
      </div></li>
    </ul>
  </div>

</template>

<script>
    export default {
      data() {
        return {
          tableData2: [{
            'projectid': 1,
            'UsrNum': 30,
            'total': 40,
            'totalFt': 200
          }],


          columns: [
            {title: '项目ID', key: 'projectid'},
            {title: '项目参与人数', key: 'UsrNum}'},
            {title: '现时Feature数', key: 'total'},
            {title: '项目Feature总数', key: 'totalFt'},
          ],
          dataList:[
            {value:1, name:'0-20分'},
            {value:3, name:'21-50分'},
            {value:24, name:'51-70分'},
            {value:7, name:'71-80分'},
            {value:3, name:'81-100分'},
          ],
          pielist:[] ,
          datanamelist:[],
          datalist:[]

        }
      },
      mounted(){
         var me=this;
          // let data = {'tokenid': 'get_ProjINFO'};
          var arr=JSON.parse(localStorage.getItem("temp"))
         let a_code=arr['tokenid']




        $.ajax({
          type: 'POST',
          url: 'http://192.168.8.132:8095/getProjectInfoByProjectId',
          data: {'tokenid':a_code},
          success: function (res) {
            console.log(res);
            var features_num = res['features'] ;
            var source_type = res['source_type'] ;
            var username = res['name'] ;
            var id = res['id'] ;
            me.tableData2[0]['totalFt'] = features_num ;
            me.tableData2[0]['id'] = id ;
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);}
        }) ;


        $.ajax({
          type: 'POST',
          url: 'http://192.168.8.132:8095/getProjectUsers',
          data: {'tokenid':a_code},
          success: function (res) {
            console.log(res);
            var result = 0;
            var temp = res['data'] ;

            var user_name = [] ;
            var user_name_feature = [] ;


            for(var i = 0; i<temp.length;i++)
            {
              var temp_user = temp[i] ;
              result = result+temp_user['features'] ;
              user_name.push(temp_user['name']) ;
              user_name_feature.push(temp_user['features']) ;

            }
            me.tableData2[0]['total'] = result ;
            me.tableData2[0]['UsrNum'] = res['data'].length ;

            me.pielist = [ me.tableData2[0].total ,me.tableData2[0].totalFt-me.tableData2[0].total] ;


            me.datanamelist = user_name ;
            me.datalist = user_name_feature ;
            console.log(me.dataList) ;
            console.log(me.datanamelist) ;
            console.log(me.dataList.length) ;
            console.log(me.datanamelist.length) ;
            me.drawLine() ;
            me.initData();
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);}
        }) ;
        console.log(this.tableData2);






      },
      methods: {
        delete_file(id) {
          var me = this;
          $('#main-table').DataTable({})

        },
        initData(pielist) {
          var myChart = this.$echarts.init(document.getElementById('main1'));
          myChart.setOption({
            title : {
              text: '矢量化进度',//主标题
              x:'center',
              textStyle:{
                color:'white'
              }
            },
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient: 'vertical',
              bottom: 'bottom',
              data: [""]
            },
            series : [
              {
                name:"",
                type: 'pie',
                radius : '80%',
                center: ['50%', '60%'],
                data:this.pielist,
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          });
        },
          drawLine(){
            console.log("fuckfucksasd") ;
            console.log(this.datanamelist) ;
            console.log(this.datalist) ;
            var myChart = this.$echarts.init(document.getElementById('main2'));
            myChart.setOption({
              title: { text: '矢量化信息统计',
                x:'center',
                textStyle:{
                color:'white'
                }
              },
              tooltip: {},
              xAxis: {
                data: this.datanamelist ,

              },
              yAxis: {},
              series: [{
                name: '',
                type: 'bar',
                data:this.datalist//[5, 20, 36, 10, 10, 20]
              }]
            });
          }
      }
    }

</script>

<style scoped>
.el-proj1{
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
  width:150px;
}
li{ display:inline}
</style>
