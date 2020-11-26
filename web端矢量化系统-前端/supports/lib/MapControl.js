/**
 * 地图控件类
 * @param {json objects} opts
 * 参数含义如下
 * {
 *  target:"divid",            //地图控件div的id
 *  width:800,                 //地图宽度,像素
 *  height:600,                //地图高度，像素
 *  center:[x,y],              //中心点坐标，使用当前坐标系下的坐标值
 *  zoom:10,                   //放大级别
 *  srid:3857,                 //投影id,目前仅仅支持
 *  backgroundMap:"GOOGLE_SATELLITE"    //背景地图类型，天地图，google，腾讯，高德，osm，bing等
 * }
 * @returns MapControl
 */

var current_click = "" ;
var lidanid = 0 ;
var projectid = 0 ;
var center = [120,20];


MapControl = function (opts) {
    var me = this;
    console.log(me);
    me.projUtils = new ProjectionUtils();
    me.options = $.extend({
        width: 700,
        height: 500,
        center: ol.proj.transform(center, "EPSG:4326", "EPSG:" + (opts.srid ? opts.srid : 3857)),
        zoom: 10,
        srid: 3857,
        keys: {
          google: "AIzaSyDG8e7oxBemSJCZMH_0kXUxXrsV8Us0ak8",
          bing: "As1HiMj1PvLPlqc_gtM7AqZfBL8ZL3VrjaS3zIb22Uvb9WKhuJObROC-qUpa81U5",
          tianditu: "2e1e0958d50f81774142f573f690f97d",
          amap: "369582c5767cb4a40a1d558316b04b82"

        },
        apiurls: {

            google: "https://maps.googleapis.com/maps/api/js?key=",

            google_china: "support/js/googlecn.js?key=",
            tianditu: "support/js/tianditu.js?v=4.0&tk=",
            amap: "support/js/amap.js?v=1.4.14&key="
        }
    }, opts);
    if (!me.options.target) {
        console.log("You must specify a div id to MapControl !");
        return;
    }
    me.el = $("#" + me.options.target).css({
        width: me.options.width + "px",
        height: me.options.height + "px",
        position: "relative"
    }).addClass("mapcontrol");

    console.log(me.options.target)


    me.xyzBackgroundMaps = [
        "GOOGLE_SATELLITE",
        "GOOGLE_ROADMAP",
        "GOOGLE_HYBRID",
        "OPEN_STREET_MAP",
        "OSM",
        "unknow"
    ];


    me.olmapid = uuid();
    me.bkmapid = uuid();

    $("<div>").attr("id", me.olmapid).addClass("olmap").css({"z-index": 2}).appendTo(me.el);


    $("<div>").attr("id", me.bkmapid).addClass("bkmap").css({"z-index": 1}).appendTo(me.el);



    me.save_nums = {
        "POINT": 0 ,
        "LINESTRING":0,
        "POLYGON": 0
    }

    me.save_features = {
        "POINT": [] ,
        "LINESTRING":[],
        "POLYGON": []
    };

    me.all_save_featuers = {
        "POINT":[] ,
        "LINESTRING":[] ,
        "POLYGON":[]
    };

    me.saved_features ={
        "POINT": [] ,
        "LINESTRING":[],
        "POLYGON": []
    }

    me.updatefeatures = {
      "POINT":[] ,
      "LINESTRING":[] ,
      "POLYGON":[]
    } ;


    me.vector_sources = {
        "POINT": new ol.source.Vector() ,
        "LINESTRING":new ol.source.Vector(),
        "POLYGON": new ol.source.Vector()
    };





    me.nodeSource = new ol.source.Vector();
    me.nodeLayer = new ol.layer.Vector({
        source: me.nodeSource
    });

    me.lineSource = new ol.source.Vector();
    me.lineLayer = new ol.layer.Vector({
        source: me.lineSource
    });

    me.polySource = new ol.source.Vector();
    me.polyLayer = new ol.layer.Vector({
        source: me.polySource
    });

    me.vector_layers = {  //矢量层数组存储
        "POINT": me.nodeLayer,
        "LINESTRING":me.lineLayer,
        "POLYGON": me.polyLayer
    };


    me._createOlMap();

    me.resolution = me.olMap.getView().getResolution();
    console.log(me.resolution);
    me.olMap.getView().on("change", function () {
        me._onUpdateView();
        me._updateBackgroundMap();

    });
    me.olMap.on("pointerdrag", function () {
        me._updateBackgroundMap();
    });

    me._initBackGroundMap();

    me._initDrawIngteractions();


    me.selectlayer = 'POINT' ;//默认值

    me.max_num = 10 ;
    return me;
};

MapControl.prototype._initBackGroundMap = function () {
    var me = this;
    // 根据传入的backgroundMap值判断使用哪一个地图作为底图
    if (me.xyzBackgroundMaps.indexOf(me.options.backgroundMap) >= 0) {
        me._initXYZBackgroundMap();
    } else if (["BING_ROAD", "BING_SATELLITE"].indexOf(me.options.backgroundMap) >= 0) {
        me._initBingBackgroundMap();
    } else if ([
        "GOOGLE_ROADMAP_CHINA",
        "GOOGLE_SATELLITE_CHINA",
        "GOOGLE_HBRID_CHINA"].indexOf(me.options.backgroundMap) >= 0) {
        me._initGoogleBackgroundMap();
    } else if (me.options.backgroundMap === "TIANDITU_ROAD") {
        me._initTiandituBackgroundMap();
    } else if (me.options.backgroundMap === "AMAP_SATELLITE" ||
            me.options.backgroundMap === "AMAP_ROAD") {
        me._initGaodeMapBackgroundMap();
    }
};


MapControl.prototype._onUpdateView = function (ev) {
    var me = this;
    me.options.center = me.olMap.getView().getCenter();
    me.options.zoom = me.olMap.getView().getZoom();
    me.resolution = me.olMap.getView().getResolution() ;
};


MapControl.prototype._createOlMap = function () {
    var me = this;

    var center = ol.proj.transform(me.options.center,
            "EPSG:" + me.options.srid,
            "EPSG:" + 4326);

    me.render_extent = [ center[0]-20,center[1]-20,center[0]+20,center[1]+20];//默认extent

     console.log(me.render_extent)

    me.node_server_layer = new ol.layer.Tile({
        source: new ol.source.TileWMS({
            url:"http://localhost:8080/geoserver/Chinamap/wms" ,
            params:{'LAYERS':'ChinaMap:temp_points','TILED':true,'TIMESTAMP': new Date().getTime()} ,
            serverType:"geoserver",
            transition: 0,

        })
    });
    me.line_server_layer = new ol.layer.Tile({
        source: new ol.source.TileWMS({
            url:"http://localhost:8080/geoserver/Chinamap/wms" ,
            params:{'LAYERS':'ChinaMap:temp_lines','TILED':true,'TIMESTAMP': new Date().getTime()} ,
            serverType:"geoserver",
            transition: 0
        })
    });

    me.poly_server_layer = new ol.layer.Tile({
        source: new ol.source.TileWMS({
            url:"http://localhost:8080/geoserver/Chinamap/wms" ,
            params:{'LAYERS':'ChinaMap:temp_polygon','TILED':true,'TIMESTAMP': new Date().getTime()} ,//params参数：layers指定了geoserver中的工作空间名和图层名
            serverType:"geoserver",
            transition: 0
        })
    });

    me.all_server_layer = new ol.layer.Tile({
        source: new ol.source.TileWMS({
            url:"http://localhost:8080/geoserver/Chinamap/wms" ,
            params:{'LAYERS':'ChinaMap:all123','TILED':true,'TIMESTAMP': new Date().getTime()} ,
            serverType:"geoserver",
            transition: 0
    })
    });



    me.serverlayers = {
    "POINT":me.all_server_layer ,
    "LINESTRING":me.all_server_layer ,
    "POLYGON":me.all_server_layer
  };





    if (!me.olMap) {
        console.log(projectid);

        $.ajax({
            type: 'POST',
            url: "http://192.168.8.132:8095/getExtent",
            data: {
                'do':'get_extent',
                //不知道有没有projectid
                'projectid':projectid
            },
            success: function(data) {

                var poly_coor = [[parseFloat(data['minx']),parseFloat(data['miny'])],
                                 [parseFloat(data['minx']),parseFloat(data['maxy'])],
                                 [parseFloat(data['maxx']),parseFloat(data['maxy'])],
                                 [parseFloat(data['maxx']),parseFloat(data['miny'])],
                                 [parseFloat(data['minx']),parseFloat(data['miny'])]] ;
                console.log(poly_coor);
                var polygon = new ol.geom.Polygon([poly_coor]);
                polygon.transform('EPSG:4326', 'EPSG:3857');
                console.log(polygon);
                var feature = new ol.Feature(polygon);
                console.log(feature);
                me.polySource.addFeature(feature);
                //openlayers add feature in that spoly_layer
                console.log(data);


             }

        }) ;
        me.olMap = new ol.Map({
            layers: [

                me.all_server_layer ,

                me.nodeLayer ,
                me.lineLayer ,
                me.polyLayer,


            ],
            target: me.olmapid,
            view: new ol.View({
                center: me.options.center,
                zoom: me.options.zoom
            }),

            interactions: ol.interaction.defaults({}).extend([
                new ol.interaction.PinchZoom({
                    constrainResolution: true
                }),
                new ol.interaction.MouseWheelZoom({
                    constrainResolution: true,
                    zoomDuration: 0
                })
            ])
        });
    }


    // 在这个位置加上对应的$ajax get the extent 加入到poly_layer 里面
    //然后整个位置就成功了


    var shit = me ;
    me.olMap.on('dblclick', function(evt) {
        console.log('doubled');
        current_click = evt.coordinate ;
        current_click  = ol.proj.transform(evt.coordinate,"EPSG:3857","EPSG:4326");
        shit.selectByCenter() ;
    });
};

MapControl.prototype._getXYZUrl = function () {
    var me = this;
    var bkxyz_url = "";
    if (me.options.backgroundMap === "GOOGLE_SATELLITE") {
        bkxyz_url = "https://mt{0-3}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}";
    } else if (me.options.backgroundMap === "GOOGLE_ROADMAP") {
        bkxyz_url = "https://mt{0-3}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}";
    } else if (me.options.backgroundMap === "GOOGLE_HYBRID") {
        bkxyz_url = "https://mt{0-3}.google.com/vt/lyrs=y&x={x}&y={y}&z={z}";
    } else if (me.options.backgroundMap === "OPEN_STREET_MAP" || me.options.backgroundMap === "OSM") {
        bkxyz_url = "http://tile.openstreetmap.org/{z}/{x}/{y}.png";
    } else if (me.options.backgroundMap === "AMAP_SATELLITE") {
        bkxyz_url = "https://webst0{1-4}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}"
    } else if (me.options.backgroundMap === "AMAP_ROAD") {
        bkxyz_url = "https://webst0{1-4}.is.autonavi.com/appmaptile?lang=zh_cn&style=7&x={x}&y={y}&z={z}"
    }
    return bkxyz_url;
};

MapControl.prototype._initXYZBackgroundMap = function () {
    var me = this;
    me.innerBkTile = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: me._getXYZUrl()
        })
    });
    me.olMap.getLayers().insertAt(0, me.innerBkTile);
};



MapControl.prototype._initTiandituBackgroundMap = function () {
    var me = this;
    if (me._tiandituJSReady) {
        me._initTiandituMap();
    } else {
        $.getScript(me.options.apiurls.tianditu + me.options.keys.tianditu, function () {
            me._tiandituJSReady = true;
            me._initTiandituMap();
        });
    }
};

MapControl.prototype._initTiandituMap = function () {
    var me = this;

    var tdt_srid = 4326;
    if (me.options.srid === 3857 || me.options.srid === 900913) {
        tdt_srid = 900913;
    }
    var center = ol.proj.transform(me.options.center,
            "EPSG:" + me.options.srid,
            "EPSG:" + 4326);
    me.bkMap = new T.Map(me.bkmapid, {
        projection: "EPSG:" + tdt_srid
    });
    me.bkMap.centerAndZoom(new T.LngLat(center[0], center[1]), me.options.zoom);
    me.bkMap.disableInertia();

    me._updateTiandituMap();

};


MapControl.prototype._updateTiandituMap = function () {
    var me = this;
    if (!me._tiandituJSReady) {
        return;
    }
    var center = ol.proj.transform(me.olMap.getView().getCenter(),
            "EPSG:" + me.options.srid,
            "EPSG:" + 4326);
    me.bkMap.centerAndZoom(new T.LngLat(center[0], center[1]), me.olMap.getView().getZoom());


};




MapControl.prototype._initBingBackgroundMap = function () {
    var me = this;
    var imagerySet = "Road";
    if (me.options.backgroundMap === "BING_ROAD") {
        imagerySet = "Road";
    } else if (me.options.backgroundMap === "BING_SATELLITE") {
        imagerySet = "Aerial";
    }
    me.innerBkTile = new ol.layer.Tile({
        visible: true,
        preload: Infinity,
        source: new ol.source.BingMaps({
            key: me.options.keys.bing,
            imagerySet: imagerySet
        })
    });

    me.olMap.getLayers().insertAt(0, me.innerBkTile);
};


MapControl.prototype._initGaodeMapBackgroundMap = function () {
    var me = this;
    if (me._amapJSReady) {
        me._initGaodeMap();
    } else {
        $.getScript(me.options.apiurls.amap + me.options.keys.amap, function () {
            me._amapJSReady = true;
            me._initGaodeMap();
        });
    }
};

MapControl.prototype._initGaodeMap = function () {
    var me = this;

    var center = ol.proj.transform(me.options.center,
            "EPSG:" + me.options.srid,
            "EPSG:" + 4326);
    center = me.projUtils.wgs84togcj02(center[0], center[1]);

    var amaplayer = null;
    if (me.options.backgroundMap === "AMAP_ROAD") {
        amaplayer = new AMap.TileLayer();
    } else if (me.options.backgroundMap === "AMAP_SATELLITE") {
        amaplayer = new AMap.TileLayer.Satellite();
    }

    me.bkMap = new AMap.Map(me.bkmapid, {
        zoom: me.options.zoom,
        center: center,
        layers: [amaplayer],
        animateEnable: false
    });

    me._updateGaodeMap();

};


MapControl.prototype._updateGaodeMap = function () {
    var me = this;
    if (!me._amapJSReady) {
        return;
    }
    var v = me.olMap.getView();
    var center = v.getCenter();
    center = ol.proj.transform(center, "EPSG:" + me.options.srid, "EPSG:4326");
    center = me.projUtils.wgs84togcj02(center[0], center[1]);
    me.bkMap.setZoomAndCenter(
            v.getZoom(),
            center
            );


};





MapControl.prototype._initDrawIngteractions = function () {
    var me = this;
    me.drawInteractions = {
        "POINT": new ol.interaction.Draw({
            source: me.nodeSource,
            type: 'Point'
        }),
        "LINESTRING": new ol.interaction.Draw({
            source: me.lineSource,
            type: 'LineString'
        }),
        "POLYGON": new ol.interaction.Draw({
            source: me.polySource,
            type: 'Polygon'
        }),
        "POINTMODIFY": new ol.interaction.Modify ({
            source: me.nodeSource,
         }),
        "LINESTRINGMODIFY":new ol.interaction.Modify({
            source:me.lineSource ,
        }),
        "POLYGONMODIFY":new ol.interaction.Modify({
          source:me.polySource ,
        }),

        "POINTMODIFYFEATURE":new ol.interaction.ModifyFeature({
           sources: me.nodeSource,
        }),
        "LINESTRINGMODIFYFEATURE":new ol.interaction.ModifyFeature({
          sources:me.lineSource ,
        }),
        "POLYGONMODIFYFEATURE":new  ol.interaction.ModifyFeature({
          sources:me.polySource ,
        })
    };




    for (var key in me.drawInteractions) {
        me.olMap.addInteraction(me.drawInteractions[key]);
        var shit = me ;
        if(key.indexOf("MODIFYFEATURE")<0 && key.indexOf("MODIFY")<0)
        {
          me.drawInteractions[key].on('drawend', function (event) {
            // src = 'EPSG:3857'
            // dest = 'EPSG:4326'

            // event.feature.getGeometry().transform(src, dest);
            shit.last_feature= event.feature;
            console.log(shit.last_feature);
            shit.saveallfeature() ;

          });
        }else if(key.indexOf("MODIFYFEATURE")>=0)
        {
          var temp_me = me ;
          me.drawInteractions[key].on(['modifystart', 'modifying'], function(e) {
            // First modify feature
            var f = e.features[0];
            if (e.type==='modifystart') $('.options p').html('');
            var info = e.type + ' '+ (e.features.length||'??') + ' feature(s) : ';
            info += (f ? f.getGeometry().getType() : '');
            if (f && f.getGeometry().flatCoordinates) info += ' '+(f ? (f.getGeometry().flatCoordinates.length/2) : '?') + ' point(s)'
            $('.options .'+e.type).text(info);
          });

          me.drawInteractions[key].on('modifyend',function(e){
              var f = e.features[0] ;
              var type = temp_me.current_action ;
              var writer = new ol.format.GeoJSON();
            //now
              var flag = false ;

              var temp_features = temp_me.save_features[type] ;
              for(var i = 0 ;i <temp_features.length;i++  )
              {
                 if(writer.writeFeature(temp_feature[i])==writer.writeFeature(f))
                 {
                    temp_features[i] = f ;
                    flag = true ;
                 }
              }

              if(flag==false)
              {
                alert("we can't zhichi") ;
                return ;
              }
              alert("修改成功") ;


          });

        }else if(key.indexOf("MODIFY")>=0)
        {
          var temp_me = me ;
          me.drawInteractions[key].on(['modifystart', 'modifying'], function(e) {
            // Try to get the modified features
            var f = temp_me.drawInteractions[key].getModifiedFeatures()[0];
            if (e.type==='modifystart') $('.options p').html('');
            var info = e.type + ' '+ (e.features.length||'??') + ' feature(s) : ';
            info += (f ? f.getGeometry().getType() : '');
            if (f && f.getGeometry().flatCoordinates) info += ' '+(f ? (f.getGeometry().flatCoordinates.length/2) : '?') + ' point(s)'
            $('.options .'+e.type).text(info);
          });
          me.drawInteractions[key].on(['modifyend'])
          {
            var f = temp_me.drawInteractions[key].getModifiedFeatures()[0];
            var type = temp_me.current_action ;
            var writer = new ol.format.GeoJSON();
            //now
            var flag = false ;

            var temp_features = temp_me.save_features[type] ;
            for(var i = 0 ;i <temp_features.length;i++  )
            {
              if(writer.writeFeature(temp_feature[i])==writer.writeFeature(f))
              {
                temp_features[i] = f ;
                flag = true ;
              }
            }

            if(flag==false)
            {
              alert("we can't zhichi") ;
              return ;
            }

          }
        }

    }
    me.deactiveDrawIngteractions();
};


MapControl.prototype.saveallfeature = function(){
    var me = this ;
    var temp = ["POINT", "LINESTRING", "POLYGON"].indexOf(me.current_action) ;
    if(temp>=0)
    {
        var type = me.current_action ;
        if(me.save_nums[type]<me.max_num)
        {
            me.save_features[type].push(me.last_feature) ;
            me.save_nums[type]+=1 ;
        }else{
            me.sendFeature() ;
            me.save_features[type].push(me.last_feature) ;
            me.save_nums[type]+=1 ;
            me.refreshtile();

        }
    }
}

MapControl.prototype.refreshtile = function(){
    var me = this ;
    var type = me.current_action ;
    var layer = me.serverlayers[type] ;
    var params = layer.getSource().getParams();
    // layer.getSource().updateParams(params);
    params['TIMESTAMP']= new Date().getTime() ;
    layer.getSource().updateParams(params);

}
MapControl.prototype.updateFeature = function(){
  var me= this ;
  var type = me.current_action ;
  var temp_feature  = me.updateFeature[type] ;
}
MapControl.prototype.sendFeature = function(){
    var me = this ;
    var type = me.current_action ;
    var temp_features = me.save_features[type] ;
    var writer = new ol.format.GeoJSON();
    var src = 'EPSG:3857' ;
    var dest = 'EPSG:4326' ;
    for(var i = 0; i< temp_features.length;i++)
    {
        temp_features[i].getGeometry().transform(src, dest);
    }
    var geojsonStr = writer.writeFeatures(temp_features);

    $.ajax({
        type: 'POST',
        url: "http://192.168.8.132:8095/addPoint",
        data: {
            'geojson':geojsonStr,
            'type':type ,
            'userid':lidanid,
            'projectid':projectid,
            'tokenid':localStorage.getItem('temp')
        },
        success: function(data) {
            console.log(data);
            for(var i = 0; i< data.length;i+=1)
            {
                console.log('we add '+data[i]);
                me.all_save_featuers[type].push(parseInt(data[i])) ;
            }
         }

    }) ;

    for(var i = 0; i< temp_features.length;i++)
    {
        temp_features[i].getGeometry().transform(dest, src);
        me.saved_features[type].push(temp_features[i]);
    }


    me.save_features[type] = [] ;
    me.save_nums[type] = 0 ;
}


MapControl.prototype.deactiveDrawIngteractions = function () {
    var me = this;

    for (var key in me.drawInteractions) {
        me.drawInteractions[key].setActive(false);
    }
    me.current_action = "" ;
    me.olMap.un("singleclick", me._onMapClick);

};


MapControl.prototype.setDrawOperation = function (op) {
    var me = this;
     me.interaction_key = op ;
    me.deactiveDrawIngteractions();
    // var temp = ["POINT", "LINESTRING", "POLYGON"].indexOf(op);
    if(op==="SELECT"){
      me.olMap.on("singleclick", me._onMapClick);
      me.current_action ="" ;
    }else{
      if(op.indexOf("POINT")>=0)
      {
        me.current_action = "POINT" ;
      }else  if(op.indexOf("LINESTRING">=0))
      {
        me.current_action = "LINESTRING" ;
      }else if(op.indexOf("POLYGON"))
      {
        me.current_action = "POLYGON" ;
      }
      me.drawInteractions[op].setActive(true);
    }

};

MapControl.prototype._onMapClick = function (ev) {
    var me = this;
    current_click = ev.coordinate ;
    current_click  = ol.proj.transform(ev.coordinate,"EPSG:3857","EPSG:4326");
    console.log(current_click);
};



MapControl.prototype._updateBackgroundMap = function () {
    var me = this;


    if (me.xyzBackgroundMaps.indexOf(me.options.backgroundMap) >= 0) {
    } else if (me.options.backgroundMap === "TIANDITU_ROAD") {

        me._updateTiandituMap();

    } else if ([
        "GOOGLE_ROADMAP_CHINA",
        "GOOGLE_SATELLITE_CHINA",
        "GOOGLE_HBRID_CHINA"].indexOf(me.options.backgroundMap) >= 0) {
        me._updateGoogleMap();

    } else if (me.options.backgroundMap === "AMAP_SATELLITE" ||
            me.options.backgroundMap === "AMAP_ROAD") {
        me._updateGaodeMap();
    }
};


MapControl.prototype.setBackgroundMap = function (bkmap) {
    var me = this;
    if (me.xyzBackgroundMaps.indexOf(me.options.backgroundMap) >= 0 ||
            ["BING_ROAD", "BING_SATELLITE"].indexOf(me.options.backgroundMap) >= 0) {
        if (me.innerBkTile) {
            me.olMap.removeLayer(me.innerBkTile);
            me.innerBkTile = null;
        }
    } else {
        me.bkMap = null;
        $("#" + me.bkmapid).remove();
        $("<div>").attr("id", me.bkmapid).addClass("bkmap").css({"z-index": 1}).appendTo(me.el);
    }
    me.options.backgroundMap = bkmap;

    me._initBackGroundMap();

};

MapControl.prototype._initGoogleBackgroundMap = function () {
    var me = this;
    if (me._googleJSReady) {
        me._initGoogleMap();
    } else {
        var gurl = "";
        if ([
            "GOOGLE_ROADMAP_CHINA",
            "GOOGLE_SATELLITE_CHINA",
            "GOOGLE_HBRID_CHINA"].indexOf(me.options.backgroundMap) >= 0) {
            gurl = me.options.apiurls.google_china;
        }
        $.getScript(gurl + me.options.keys.google, function () {
            me._googleJSReady = true;
            me._initGoogleMap();
        });
    }
};

MapControl.prototype._initGoogleMap = function () {
    var me = this;

    var uluru = {
        lat: me.options.center[1],
        lng: me.options.center[0]
    };

    var maptype = me.options.backgroundMap.split("_")[1].toLowerCase();

    me._bkMap = new google.maps.Map(document.getElementById(me.bkmapid), {
        zoom: me.options.zoom,
        center: uluru,
        mapTypeId: maptype
    });

    me._updateGoogleMap();

};


MapControl.prototype._updateGoogleMap = function () {
    var me = this;
    if (!me._googleJSReady) {
        return;
    }

    var center = me.olMap.getView().getCenter();

    center = ol.proj.transform(center, "EPSG:" + me.options.srid, "EPSG:4326");
    if ([
        "GOOGLE_ROADMAP_CHINA",
        "GOOGLE_SATELLITE_CHINA",
        "GOOGLE_HBRID_CHINA"].indexOf(me.options.backgroundMap) >= 0) {

        center = me.projUtils.wgs84togcj02(center[0], center[1]);
    }
    me._bkMap.setOptions({
        center: {lng: center[0], lat: center[1]},
        zoom: me.olMap.getView().getZoom()
    });

};

MapControl.prototype.cancel = function()
{
    var me = this ;
    var type = me.current_action ;
    var temp = me.current_action ;
    if(me.save_features[temp].length>0)
    {
        var temp_feature = me.save_features[temp].pop() ;
        me.vector_layers[temp].getSource().removeFeature(temp_feature) ;
    }else if (me.all_save_featuers[type].length>0)
    {
        var temp = me.all_save_featuers[type].pop();
        if(type=="POINT")
        {
          url = 'DeletePoint' ;
        }else if(type=="LINESTRING")
        {
          url = 'DeleteLineString' ;
        }else if(type=='POLYGON')
        {
          url='DeletePolygon' ;
        }
        console.log(temp) ;
        $.ajax({
            type: 'POST',
            url: "http://192.168.8.132/"+url,
            data: {
              'featureid':temp ,
            },
            success: function(data) {
                console.log(data);
             }

        }) ;
        var temp_feature = me.saved_features[type].pop() ;
        me.vector_layers[type].getSource().removeFeature(temp_feature) ;
    }
    me.refreshtile() ;


}
MapControl.prototype.savebyuser = function(){
    var me = this ;
    me.sendFeature() ;
    me.refreshtile() ;
}
MapControl.prototype.setLayer = function(layer){
    var me = this ;
    me.selectlayer = layer ;
}

MapControl.prototype.selectByCenter = function(){
    var me = this ;
    var type = me.selectlayer ;
    var temp_coordinate =  current_click ;
    var resolution = me.olMap.getView().getResolution();
    var distance = 5*resolution ;

    $.ajax({
        type: 'POST',
        url: "http://localhost/vueApp/index1.php",
        data: {

            'coordinate':temp_coordinate ,
            'type':type  ,
            'radius':distance
        },
        success: function(data) {

            console.log(data);
            if(data!=false&&data!="false")
            {
                console.log(data);
                data = data['data'];
                me.select_id = data['id'] ;
                me.select_user_name = data['name'] ;
                me.select_feature = data['json'] ;

                me.show() ;
            }


         }

    }) ;

}

MapControl.prototype.show = function(){
    var me = this ;


    $("#dialog").dialog({
        autoOpen:false , //设置对话框打开的方式 不是自动打开
        show:"blind",  //打开时的动画效果
        hide:"explode",  //关闭时的动画效果
        modal:true, //遮罩效果true  false 非遮罩效果
         buttons:[
           {
                text:"ok",
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
         title:"选择要素" ,//对话框的标题
         position:"top", //对话框弹出的位置 可以使top left right center  bottom 默认值是 center
         width:600,  //对话框的宽度
         height:400, //对话框的高度
         resizable:false ,//是否可以改变的操作   false 则不可以改变尺寸  true可以改变尺寸   默认值是true
         zIndex:6,
         drag:function(event,ui){
                //可以测试出 对话框的当前坐标位置
             }
      });
      $("#dialog").html(" <p class='validateTips'>所选要素</p><form><fieldset> \
        <label for='name'>矢量化工作人员名字</label>   \
        <label id='name' class='text ui-widget-content ui-corner-all'> "+me.select_user_name+"</label>  \
        <label for='email'>要素id</label>   \
        <label class='text ui-widget-content ui-corner-all'>"+me.select_id+"</label> \
        <label for='password'>要素</label>   \
        <label  name='password' id='password' class='text ui-widget-content ui-corner-all'> "+me.select_feature+" \
      </fieldset> \
      </form>") ;
      $("#dialog").dialog("open");

}




