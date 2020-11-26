package com.lidan.Sercurity.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lidan.Model.SerializeUtil;
import com.lidan.Model.User;
import com.lidan.Service.RedisService;
import com.lidan.job.ObjecttoString;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@WebFilter(urlPatterns = "/*")
public class ChannelFilter implements Filter {


    Jedis jedis = new Jedis("127.0.0.1", 6379);
    ObjectMapper objectMapper = new ObjectMapper();
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
            new GenericJackson2JsonRedisSerializer() ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response1 = (HttpServletResponse) response;
        response1.setHeader("Access-Control-Allow-Origin", "*");
        response1.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response1.setHeader("Access-Control-Allow-Credentials", "true");

        HttpServletRequest req = (HttpServletRequest) request;

        String tokenId = req.getParameter("tokenid");
        String username = req.getParameter("name") ;
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req);

        String passwd_maybe = jedis.get(username+"passwd") ;
        String real = req.getParameter("passwd") ;


        if(real!=null && passwd_maybe!=null )
        {
            passwd_maybe = ObjecttoString.ObjecttoString(passwd_maybe) ;
            String temshit = jedis.get(username) ;

            if(passwd_maybe.equals(real))
            {
                String permission = req.getParameter("role") ;
                User temp = ObjecttoString.ObjecttoUser(jedis.get(ObjecttoString.ObjecttoString(jedis.get(username)))) ;
                if(temp.getPermission().equals(permission))
                {
                    respCopy(response,ObjecttoString.ObjecttoString(jedis.get(username)),temp);
                }else{
                    respFail(response) ;
                }
                return ;
            }

        }
        if (tokenId == null || "".equals(tokenId) || tokenId.isEmpty()) {
            requestWrapper.addParameter("permission","nothing");
            chain.doFilter(requestWrapper, response);
            return;
        }



//        User i = serviceDetails.getUser(tokenId) ;
//        if(i==null)
//        {
//            this.respFail(response);
//        }

        User i = null ;
        byte[] bytes = jedis.get(tokenId).getBytes();

        i  = (User)genericJackson2JsonRedisSerializer.deserialize(bytes) ;

//        byte[] result = jedis.get(tokenId.getBytes()) ;

        if(i==null)
        {
            respFail(response) ;
            return ;
        }
        requestWrapper.addObject(i);
        chain.doFilter(requestWrapper, response);
    }

    /** 返回失败结果Json数据 */
    private void respFail(ServletResponse response) throws IOException {


        Map<String, Object> map = new HashMap<>();
        map.put("status", 201);
        map.put("message", "登录失效，请登录");
        map.put("data", null);
        String s = objectMapper.writeValueAsString(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
    }

    private void respCopy(ServletResponse response,String tokenid,User user) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "重复登陆");
        map.put("tokenid",tokenid );
        map.put("data", null);
        map.put("id",user.getId()) ;
        map.put("projectid",user.getProjectid()) ;
        String s = objectMapper.writeValueAsString(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
    }


    @Override
    public void destroy() {

    }
}
