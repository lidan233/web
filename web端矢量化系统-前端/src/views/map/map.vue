<template>
  <div style="text-align: center; margin: 0 auto ">
    <el-card class="box-card" shadow="always" style="margin: 0 auto;height:8%;background:transparent;border: 6px solid black">

    <div>
      <ul>
      <li>
      <div>
      <flash-button type="Primary" class="butt" onclick="mapcontrol.setDrawOperation('SELECT')" size="default">选择</flash-button>
      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('POINT')"size="default">点</flash-button>
      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('LINESTRING')"size="default">线</flash-button>
      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('POLYGON')"size="default">多边形</flash-button>

      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('POINTMODIFYFEATURE')"size="default">操纵点要素</flash-button>
      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('LINESTRINGMODIFYFEATURE')"size="default">操纵线要素</flash-button>
      <flash-button class="butt" onclick="mapcontrol.setDrawOperation('POLYGONMODIFYFEATURE')"size="default">操纵面要素</flash-button>

      <flash-button class="butt" onclick="mapcontrol.cancel()"size="default">撤销</flash-button>
      <flash-button class="butt" onclick="mapcontrol.savebyuser()"size="default">保存</flash-button>
      <flash-button class="butt" onclick="mapcontrol.show()"size="default">弹窗</flash-button>
      <select id="bkmap" onchange="changeBkMap();">
        <option value="GOOGLE_SATELLITE_CHINA">谷歌影像(中国)</option>
        <option value="GOOGLE_ROADMAP_CHINA">谷歌地图(中国)</option>
        <option value="GOOGLE_SATELLITE">谷歌影像</option>
        <option value="GOOGLE_ROADMAP">谷歌地图</option>
        <option value="AMAP_ROAD">高德地图</option>
        <option value="AMAP_SATELLITE">高德影像</option>
        <option value="TIANDITU_ROAD">天地图</option>
        <option value="BING_ROAD">Bing地图</option>
        <option value="BING_SATELLITE">Bing影像</option>
        <option value="OSM">OpenStreetMap</option>
      </select>

      <select id="layer" onchange="changeLayer();">
        <option value="POINT">操作图层(点)</option>
        <option value="LINESTRING">操作图层(线)</option>
        <option value="POLYGON">操作图层(面）</option>
      </select></div></li>
       <li>
      <div class="options" >
        <ul>
          <li>
            <input id="cspline" type="checkbox" onchange="vector.changed()" checked="checked" />
            <label for="cspline" style="color: #eeeeee;"> smooth support</label>
          </li>
          <li style="color: #eeeeee">
              tension:<input id="tension" type="range" min="0" max="1" step="0.1" value="0.5" onchange="vector.changed()" />
          </li>
          <li style="color: #eeeeee">
            pointsPerSeg:<input id="pps" type="range" min="0" max="100" step="1" value="10" onchange="vector.changed()" />
          </li>
          <li>
            <input id="normalize" type="checkbox" onchange="vector.changed()" />
            <label for="normalize" style="color: #eeeeee"> normalize</label>
          </li>
          <li>
            <input id="dpt" type="checkbox" onchange="vector.changed()" />
            <label for="dpt" style="color: #eeeeee"> draw spline points</label>
        </li>
        </ul>
      </div>
       </li></ul>
    </div>
    </el-card>
    <div id="mapcontrol" style="text-align: center ; margin: 0 auto;"></div>
  </div>

</template>

<style>
  button{margin:0 auto; border:none; background-color:#41b883; color:#fff; font-size:16px; margin-bottom:5px;}
  select{margin:0 auto; border:none; background-color:#41b883; color:#fff; font-size:16px; margin-bottom:5px;}
  .map{margin:0 auto;}
  .box-card{
     width: 80%;
  }
</style>

<script>
import _global from '../global/global'
/* eslint-disable */
  export default {
mounted () {
  // lidanid = _global.userid ;
  // projectid = _global.projectid ;
  var lidanid = 1  ;
  var projectid = 1;
  $(document).ready(function () {
    let data = {'do':'get_info_project','userid':lidanid,'projectid':projectid}
    console.log(data)
    $.ajax({
      type: 'POST',
      url: 'http://localhost/vueApp/index1.php',
      data: data,
      success: function(res) {
        if (res !== 'false' && res !== false){
           console.log(res);
          $('#bkmap').val(res['source_type'])
          $('#bkmap').trigger('change')
          center[1]= parseFloat(res['x'])
          center[0] = parseFloat(res['y'])

          //???
        }
      }
    })

    initmap();
    document.addEventListener('keydown', function(e) {
      if (e.keyCode == 27)
        var interactions = mapcontrol.drawInteractions;
      mapcontrol.drawInteractions[mapcontrol.interaction_key].removeLastPoint() ;
    });



  })
}
  }
</script>
