package com.lidan.Controller;

import com.lidan.Model.LineString;
import com.lidan.Model.Point;
import com.lidan.Model.Polygon;
import com.lidan.Sercurity.Config.AuthToken;
import com.lidan.Sercurity.Config.RootAuthToken;
import com.lidan.Service.Impl.LineStringServiceDo;
import com.lidan.Service.Impl.PointServiceDo;
import com.lidan.Service.Impl.PolygonServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FeatureController {

    @Autowired
    PointServiceDo points ;
    @Autowired
    LineStringServiceDo lineStrings ;
    @Autowired
    PolygonServiceDo polygonServiceDo ;



    @AuthToken
    @ResponseBody
    @PostMapping("/addPoint")
    public Map<String, Object> addProject(HttpServletRequest request) throws IOException {

        String userid = request.getParameter("id") ;
        String json = request.getParameter("json") ;
        String project = request.getParameter("projectid") ;


        Map<String, Object> reback = new HashMap<String, Object>();

        Point insert = new Point(0,0) ;
        insert.setJson(json) ;
        insert.setUserid(Integer.parseInt(userid));
        insert.setOrderid(0) ;
        insert.setCoordinate_system(4326) ;
        insert.setProjectid(Integer.parseInt(project));
        List<Integer> i  = points.addFeature(insert) ;
        if(i==null)
        {
            respFail(reback,"插入失败",null);
        }else{
            respSuccess(reback,"插入成功",i);
        }

        return reback ;
    }



    @AuthToken
    @ResponseBody
    @PostMapping("/DeletePoint")
    public Map<String, Object> DeletePoint(HttpServletRequest request) throws IOException {

        Map<String, Object> reback = new HashMap<String, Object>();
        String featureid1 = request.getParameter("featureid") ;

        if(featureid1==null) respFail(reback,"没有pointid",null);

        int featureid = Integer.parseInt(featureid1) ;


        int result = points.DeleteFeatureByid(featureid) ;
        if(result<=0)
        {
            respFail(reback,"删除失败",null);
        }else{
            respSuccess(reback,"删除成功",null);
        }

        return reback ;
    }



    @AuthToken
    @ResponseBody
    @PostMapping("/selectPoint")
    public Map<String, Object> selectPoint(HttpServletRequest request) throws IOException {
        double x = Double.parseDouble(request.getParameter("x" ) );
        double y = Double.parseDouble(request.getParameter("y")) ;
        double distance = Double.parseDouble(request.getParameter("distance")) ;


        Point temp = new Point(x,y) ;
        Map<String, Object> reback = new HashMap<String, Object>();
        Point result = (Point)points.selectFeature(temp,distance) ;
        if(result==null)
        {
            respFail(reback,"选择不成功",result);
        }
        respSuccess(reback,"选择成功",result) ;
        return reback ;

    }





    @AuthToken
    @ResponseBody
    @PostMapping("/addLineString")
    public Map<String, Object> addLineString(HttpServletRequest request) throws IOException {

        String userid = request.getParameter("id") ;
        String json = request.getParameter("json") ;
        String project = request.getParameter("projectid") ;


        Map<String, Object> reback = new HashMap<String, Object>();

        LineString insert = new LineString(json) ;;
        insert.setUserid(Integer.parseInt(userid));
        insert.setProjectid(Integer.parseInt(project));

        List<Integer> i  = lineStrings.addFeature(insert) ;
        if(i==null)
        {
            respFail(reback,"插入失败",null);
        }else{
            respSuccess(reback,"插入成功",i);
        }

        return reback ;
    }



    @AuthToken
    @ResponseBody
    @PostMapping("/DeleteLineString")
    public Map<String, Object> DeleteLineString(HttpServletRequest request) throws IOException {

        Map<String, Object> reback = new HashMap<String, Object>();
        String featureid1 = request.getParameter("featureid") ;

        if(featureid1==null) respFail(reback,"没有linestringid",null);

        int featureid = Integer.parseInt(featureid1) ;


        int result = lineStrings.DeleteFeatureById(featureid) ;

        if(result<=0)
        {
            respFail(reback,"删除失败",null);
        }else{
            respSuccess(reback,"删除成功",null);
        }

        return reback ;
    }



    @AuthToken
    @ResponseBody
    @PostMapping("/selectLineString")
    public Map<String, Object> selectLineString(HttpServletRequest request) throws IOException {
        double x = Double.parseDouble(request.getParameter("x" ) );
        double y = Double.parseDouble(request.getParameter("y")) ;
        double distance = Double.parseDouble(request.getParameter("distance")) ;


        Point temp = new Point(x,y) ;
        Map<String, Object> reback = new HashMap<String, Object>();
        LineString result = (LineString)lineStrings.selectFeature(temp,distance) ;
        if(result==null)
        {
            respFail(reback,"选择不成功",result);
        }
        respSuccess(reback,"选择成功",result) ;
        return reback ;

    }



    @AuthToken
    @ResponseBody
    @PostMapping("/addPolygon")
    public Map<String,Object> addPolygon(HttpServletRequest request) throws IOException {
        String userid = request.getParameter("id") ;
        String json = request.getParameter("json") ;
        String project = request.getParameter("projectid") ;


        Map<String, Object> reback = new HashMap<String, Object>();
        Polygon insert = new Polygon(json) ;;
        insert.setUserid(Integer.parseInt(userid));
        insert.setProjectid(Integer.parseInt(project));


        List<Integer> i  = polygonServiceDo.addFeature(insert) ;
        if(i==null)
        {
            respFail(reback,"插入失败",null);
        }else{
            respSuccess(reback,"插入成功",i);
        }

        return reback ;

    }



    @AuthToken
    @ResponseBody
    @PostMapping("/DeletePolygon")
    public Map<String,Object> DeletePolygon(HttpServletRequest request) throws IOException {
        Map<String, Object> reback = new HashMap<String, Object>();
        String featureid1 = request.getParameter("featureid") ;

        if(featureid1==null) respFail(reback,"没有polygonid",null);

        int featureid = Integer.parseInt(featureid1) ;


        int result = polygonServiceDo.DeleteFeatureById(featureid) ;

        if(result<=0)
        {
            respFail(reback,"删除失败",null);
        }else{
            respSuccess(reback,"删除成功",null);
        }

        return reback ;
    }


    @AuthToken
    @ResponseBody
    @PostMapping("/selectPolygon")
    public Map<String, Object> selectPolygon(HttpServletRequest request) throws IOException {
        double x = Double.parseDouble(request.getParameter("x" ) );
        double y = Double.parseDouble(request.getParameter("y")) ;
        double distance = Double.parseDouble(request.getParameter("distance")) ;


        Point temp = new Point(x,y) ;
        Map<String, Object> reback = new HashMap<String, Object>();
        Polygon result = (Polygon)polygonServiceDo.selectFeature(temp,distance) ;
        if(result==null)
        {
            respFail(reback,"选择不成功",result);
        }else{
            respSuccess(reback,"选择成功",result) ;
        }

        return reback ;

    }

    @AuthToken
    @ResponseBody
    @RequestMapping("/updateFeature")
    public Map<String,Object> updateFeature(HttpServletRequest request)
    {
        Map<String,Object> result = new HashMap<>() ;
        String type = request.getParameter("type" );
        String featureid = request.getParameter("featureid") ;
        String json = request.getParameter("json" );

//        if(type.equals("POINT"))
//        {
//            points
//        }

        return result ;
    }

//    @AuthToken
//    @ResponseBody
//    @RequestMapping("/getFeatureAll")
//    public Map<String,Object> getFeatureAll(HttpServletRequest request)
//    {
//        String type = request.getParameter("type") ;
//        Map<String,Object> result = new HashMap<>() ;
//        if(type.equals("POINT"))
//        {
//
//            result.put("",)
//        }
//
//        result.put("",)
//
//
//        return result ;
//    }




    /** 返回失败结果Json数据 */
    private  void  respFail(Map<String, Object> map,String message,Object data) throws IOException {
        map.put("status", 502);
        map.put("message", message);
        map.put("data", data);

    }


    private  void  respSuccess(Map<String, Object> map,String message,Object data) throws IOException {
        map.put("status", 500);
        map.put("message", message);
        map.put("data", data);

    }



}
