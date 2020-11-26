package com.lidan.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lidan.Model.User;
import com.lidan.Service.UserServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonRegister {
    @Autowired
    UserServiceDo doUser;
    ObjectMapper objectMapper = new ObjectMapper();

    @ResponseBody
    @PostMapping("/register")
    public Map<String, Object> register(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        String account = request.getParameter("account") ;
        String passwd = request.getParameter("passwd") ;
        String name = request.getParameter("name") ;
        String type = request.getParameter("role") ;
        String requestcode = request.getParameter("requestcode") ;
        if(type.equals("auth"))
        {
            List<User> temp =  doUser.selectByAccount(account);
            if(temp.size()!=0)
            {
                respFailOfExist(result);
                return result ;
            }else{
                User add_to = new User() ;
                add_to.setAccount(account);
                add_to.setName(name);
                add_to.setPasswd(passwd);
                add_to.setPermission("auth");


                try{
                    int temp_result = doUser.addUser(add_to,requestcode);
                    if(temp_result>=0)
                    {
                        respTrue(result);
                    }else{
                        respFail(result);
                    }


                }catch (Exception e)
                {
                    System.out.println(e) ;
                    respFail(result);
                }finally {
                    return result ;
                }
            }

        }else if(type.equals("project")){
            User  temp = doUser.getProjectUserByaccount(account) ;
            if(temp!=null)
            {
                respFailOfExist(result) ;
                return result ;
            }else{
                User add_to = new User() ;
                add_to.setAccount(account);
                add_to.setName(name);
                add_to.setPasswd(passwd);
                add_to.setPermission("project");
                try{
                    doUser.addProjectUser(add_to);
                    respTrue(result);
                }catch (Exception e)
                {
                    System.out.println(e) ;
                    respFail(result);
                }finally {
                    return result ;
                }
            }

        }
        return result ;
    }




    /** 返回失败结果Json数据 */
    private  void  respFail(Map<String, Object> map) throws IOException {
        map.put("status", 301);
        map.put("message", "注册失败");
        map.put("data", null);

    }

    /** 返回失败结果Json数据 */
    private void respTrue(Map<String, Object> map) throws IOException {

        map.put("status", 300);
        map.put("message", "注册成功");
        map.put("data", null);

    }

    /** 返回失败结果Json数据 */
    private void respFailOfExist(Map<String, Object> map) throws IOException {

        map.put("status", 303);
        map.put("message", "您已经注册");
        map.put("data", null);
    }


}
