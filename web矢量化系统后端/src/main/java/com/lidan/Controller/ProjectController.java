package com.lidan.Controller;

import com.lidan.Model.Extent;
import com.lidan.Model.Point;
import com.lidan.Model.Project;
import com.lidan.Model.User;
import com.lidan.Sercurity.Config.AuthToken;
import com.lidan.Sercurity.Config.ProjectAuthToken;
import com.lidan.Sercurity.Config.RootAuthToken;
import com.lidan.Service.ProjectServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    ProjectServiceDo projectServiceDo ;

    @ResponseBody
    @RootAuthToken
    @PostMapping("/addProject")
    public Map<String, Object> addProject(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        String source_kind = request.getParameter("source_kind") ;
        String center_x = request.getParameter("center_x" ) ;
        String center_y = request.getParameter("center_y") ;
        String extent_min_x = request.getParameter("extent_min_x") ;
        String extent_min_y = request.getParameter("extent_min_y") ;
        String extent_max_x = request.getParameter("extent_max_x" );
        String extent_max_y = request.getParameter("extent_max_y") ;
        String features = request.getParameter("features") ;

        String project_user_name = request.getParameter("project_user_name") ;


        if(source_kind==null|| center_x ==null || center_y ==null
                || extent_min_x ==null || extent_min_y == null
                || extent_max_x==null  || extent_max_y==null||features==null||project_user_name==null)
        {
            respFail(result,"创建项目失败，参数缺失");
            return result ;
        }
        int features_num = Integer.parseInt(features) ;


        Point center = new Point(Double.parseDouble(center_x),Double.parseDouble(center_y)) ;
        Point extent_min = new Point(Double.parseDouble(extent_min_x),Double.parseDouble(extent_min_y)) ;
        Point extent_max = new Point(Double.parseDouble(extent_max_x),Double.parseDouble(extent_max_y)) ;



        int result_status= projectServiceDo.addProject(source_kind,center,extent_max,extent_min ,features_num,project_user_name) ;
        if(result_status>=0)
        {
            respSuccess(result,"创建项目成功",null);

        }else
        {
            respFail_Create(result,"创建项目失败，创建失败");

        }
        return result ;

    }

    @ResponseBody
    @ProjectAuthToken
    @PostMapping("/getProjectInfoByProjectId")
    public Map<String, Object> getProjectInfoByProjectId(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = request.getParameter("id" );
        Project temp = projectServiceDo.getProjectInfoByProjectUserid(Integer.parseInt(id)) ;


        result.put("id",temp.getId()) ;
        result.put("source_type",temp.getSource_kind()) ;
        result.put("center",temp.getCenter_str()) ;
        result.put("extent_min",temp.getExtent_min_str())  ;
        result.put("extent_max",temp.getExtent_max_str()) ;
        result.put("features",temp.getFeatures()) ;
        result.put("name",temp.getProject_user_name()) ;

        if(result.containsValue(null)) {
            respFail(result, "项目信息不完整");
            return result ;
        }
        respSuccess(result,"项目信息检索成功",null);

        return result ;

    }

    @ResponseBody
    @ProjectAuthToken
    @PostMapping("/getProjectInfoByProjectUserAccount")
    public Map<String, Object> getProjectInfoByProjectUserAccount(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String account = request.getParameter("account") ;
        Project temp = projectServiceDo.getProjectInfoByProjectUserAccount(account) ;

        result.put("id",temp.getId()) ;
        result.put("source_type",temp.getSource_kind()) ;
        result.put("center",temp.getCenter_str()) ;
        result.put("extent_min",temp.getExtent_min_str())  ;
        result.put("extent_max",temp.getExtent_max_str()) ;
        result.put("features",temp.getFeatures()) ;
        result.put("name",temp.getProject_user_name()) ;

        if(result.containsValue(null)) {
            respFail(result, "项目信息不完整");
            return result ;
        }
        respSuccess(result,"项目信息检索成功",null);

        return result ;
    }


    @ResponseBody
    @ProjectAuthToken
    @PostMapping("/getProjectUsers")
    public Map<String, Object> getProjectUsers(HttpServletRequest request) throws IOException {
        String project_user_id = request.getParameter("id") ;
        Map<String, Object> result = new HashMap<String, Object>();


        List<User> result_users = projectServiceDo.getUserByProjectid(Integer.parseInt(project_user_id) );
        if(result_users!=null)
        respSuccess(result ,"成功获取对应user",result_users) ;
        else if(result_users==null)
            respFail(result,"获取失败");

        return result ;
    }

    @ResponseBody
    @AuthToken
    @PostMapping("/getExtent")
    public Map<String, Object> getExtent(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = request.getParameter("projectid") ;
        if(id==null)
        {
            respFail(result,"参数不全");
        }else{
            Extent last = projectServiceDo.getExtent(Integer.parseInt(id)) ;
            result.put("maxx", last.extent_max_x) ;
            result.put("maxy",last.extent_max_y) ;
            result.put("minx",last.extent_min_x) ;
            result.put("miny",last.extent_min_y) ;

            respSuccess(result,"成功",null);
        }

        return result ;
    }




    /** 返回失败结果Json数据 */
    private  void  respFail(Map<String, Object> map,String message) throws IOException {
        map.put("status", 302);
        map.put("message", message);
        map.put("data", null);

    }

    private  void  respFail_Create(Map<String, Object> map,String message) throws IOException {
        map.put("status", 301);
        map.put("message", message);
        map.put("data", null);

    }

    private  void  respSuccess(Map<String, Object> map,String message,Object all) throws IOException {
        map.put("status", 300);
        map.put("message", message );
        map.put("data", all);

    }

}
