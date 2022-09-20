package com.example.ezkeeper.filter;


import com.alibaba.fastjson2.JSON;
import com.example.ezkeeper.JSONResult;
import com.example.ezkeeper.service.UsersService;
import com.example.ezkeeper.token.ShiroToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;

public class TokenFilter extends AuthenticatingFilter {

    private static final String X_TOKEN = "token";


    /**
     * 创建Token, 支持自定义Token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = this.getToken((HttpServletRequest)servletRequest);
        if(ObjectUtils.isEmpty(token)){
            return null;
        }
        return new ShiroToken(token);
    }

    /**
     * 兼容跨域
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = this.getToken(request);
        if(ObjectUtils.isEmpty(token)){
            this.respUnLogin(request, response);
            return false;
        }

        //token登陆
        ShiroToken shiroToken = new ShiroToken(token);
        try{
            getSubject(servletRequest,servletResponse).login(shiroToken);
        }catch (Exception e){
//            e.printStackTrace();
            this.respUnLogin(request, response);
            return false;
        }

        return true;
    }

    private void respUnLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        response.getWriter().print(JSON.toJSON(JSONResult.unathorzied()));
    }

    /**
     * 获取token
     * 优先从header获取
     * 如果没有，则从parameter获取
     * @param request request
     * @return token
     */
    private String getToken(HttpServletRequest request){
        String token = request.getHeader(X_TOKEN);
        if(ObjectUtils.isEmpty(token)){
            token = request.getParameter(X_TOKEN);
        }
        return token;
    }
}