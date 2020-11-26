package com.lidan.Sercurity.Config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lidan.Model.User;
import com.lidan.Sercurity.Contants.ConstantKit;
import com.lidan.Service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    ObjectMapper objectMapper = new ObjectMapper();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        HandlerMethod method = (HandlerMethod) handler;
        String permission = request.getParameter("permission") ;


        if(permission!=null&&(!permission.equals("nothing"))&&method.getMethod().getName().equals("login"))
        {
           respTrue(response) ;
           return false;
        }
        AuthToken login = method.getMethodAnnotation(AuthToken.class);
        ProjectAuthToken projectAuthToken = method.getMethodAnnotation(ProjectAuthToken.class) ;
        RootAuthToken rootAuthToken = method.getMethodAnnotation(RootAuthToken.class) ;

        if((permission==null||permission.equals("nothing"))&&login==null&&projectAuthToken==null&&rootAuthToken==null) return true ;
        if(permission.equals("root")) return true ;
        if(permission.equals("project")&&rootAuthToken==null)  return true ;
        if(permission.equals("auth")&&projectAuthToken==null&&rootAuthToken==null) return true ;

        respFailByAccess(response) ;
        return false ;


//        String[] memberIds = request.getParameterValues("memberId");
//        MethodParameter[] methodParameters = method.getMethodParameters();
//        if (methodParameters.length > 0) {
//            for (MethodParameter methodParameter : methodParameters) {
//                Type genericParameterType = methodParameter.getGenericParameterType();
//                String typeName = genericParameterType.getTypeName();
//                if (!typeName.equals(User.class.getTypeName())) continue;
//                if (memberIds == null || memberIds.length <= 0) return this.respFail(response);
//                break;
//            }
//        }



    }


    /** 返回失败结果Json数据 */
    private boolean respTrue(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "请勿重复登陆");
        map.put("data", null);
        String s= objectMapper.writeValueAsString(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
        return false;
    }


    /** 返回失败结果Json数据 */
    private boolean respFail(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "登录失效，请登录");
        map.put("data", null);
        String s= objectMapper.writeValueAsString(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
        return false;
    }
    /** 返回失败结果Json数据 */
    private boolean respFailByAccess(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "权限不足,无法查看");
        map.put("data", null);
        String s= objectMapper.writeValueAsString(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
        return false;
    }




}