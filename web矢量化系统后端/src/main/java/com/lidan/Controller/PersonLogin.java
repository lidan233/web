package com.lidan.Controller;

import com.lidan.Sercurity.Config.ProjectAuthToken;
import com.lidan.Sercurity.Config.RootAuthToken;
import com.lidan.Service.RedisService;
import com.lidan.job.Md5TokenGenerator;
import com.lidan.Model.User;
import com.lidan.Service.UserServiceDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PersonLogin {

    @Autowired
    UserServiceDo doUser;

    @Autowired
    Md5TokenGenerator tokenGenerator;

    @Autowired
    RedisService redisService ;


    private final Logger log = LoggerFactory.getLogger(getClass());
    long TIME_ALL = 60*60 ;

    @ResponseBody
    @RequestMapping("/fuck")
    public Map<String,Object> fuck(HttpServletRequest request)
    {
        Map<String, Object> reback = new HashMap<String, Object>();

        reback.put("status","200") ;
        reback.put("message","fuck") ;
        return  reback ;


    }

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object>  login(HttpServletRequest request, HttpServletResponse resp) {

        String username=request.getParameter("name");
        String password=request.getParameter("passwd");
        String role = request.getParameter("role") ;
        String permission = request.getParameter("permission") ;
        String id = request.getParameter("id") ;
        Map<String, Object> reback = new HashMap<String, Object>();
        User result = null ;


//        if((permission!=null)&&!permission.equals("nothing"))
//        {
//            reback.put("status","200") ;
//            reback.put("message","请勿重复登陆") ;
//            return  reback ;
//        }




        if(role.equals("auth"))
        {

            List<User> temp_users= doUser.selectByAccount(username) ;
            if(temp_users.size()>0)
            {
                result = temp_users.get(0) ;
            }else{
                result = null ;
            }

            if(result!=null)
            {
                result.setPermission("auth");
                String tokenid = tokenGenerator.generate(username+password) ;
                redisService.set(tokenid,result,TIME_ALL) ;
                redisService.set(username,tokenid,TIME_ALL) ;
                redisService.set(username+"passwd" ,password,TIME_ALL) ;


                reback.put("status","200") ;
                reback.put("tokenid",tokenid) ;
                reback.put("id",result.getId()) ;
                reback.put("projectid",result.getProjectid()) ;
            }else{
                reback.put("status","201") ;
                reback.put("message","登陆失败") ;
            }

        }else if(role.equals("project"))
        {
            result = doUser.getProjectUserByaccount(username) ;
            if(result!=null)
            {
                result.setPermission("project");
                String tokenid = tokenGenerator.generate(username+password) ;
                redisService.set(tokenid,result,TIME_ALL) ;
                redisService.set(username,tokenid,TIME_ALL) ;
                redisService.set(username+"passwd" ,password,TIME_ALL) ;

                reback.put("status",200) ;
                reback.put("tokenid",tokenid) ;
                reback.put("id",result.getId()) ;
                reback.put("projectid",result.getProjectid()) ;


            }else{
                reback.put("status","201" ) ;
                reback.put("message","登陆失败") ;
            }

        }else if(role.equals("root")){
            result = doUser.getRootUser() ;
            if(result!=null)
            {
                String tokenid = tokenGenerator.generate(username+password) ;
                redisService.set(tokenid,result,TIME_ALL) ;
                redisService.set(username,tokenid,TIME_ALL) ;
                redisService.set(username+"passwd" ,password,TIME_ALL) ;

                reback.put("status",200) ;
                reback.put("tokenid",tokenid) ;

            }else{
                reback.put("status","201" ) ;
                reback.put("message","登陆失败") ;
            }

        }



        return reback ;
    }

    @RootAuthToken
    @ResponseBody
    @PostMapping("/GetAllUser")
    public List<User> giveAll()
    {
        List<User> result = doUser.getAllUsers() ;
        return result;
    }

    @ProjectAuthToken
    @ResponseBody
    @PostMapping("/getAllUserByProjectId")
    public List<User> giveAllByProjectId(HttpServletRequest request, HttpServletResponse resp)
    {
        String projectid = request.getParameter("projectid") ;
        int projectid_num  = Integer.parseInt(projectid) ;
        return doUser.selectByProjectid(projectid) ;
    }


}


