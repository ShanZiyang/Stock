package com.ys.stock.security.handler;

import com.alibaba.fastjson.JSON;
import com.ys.stock.utils.WebUtils;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  杨森
 * @date  2023/7/11 15:44
 * @version 1.0
 * @description 匿名用户(即未登录时访问资源为匿名访问)无权限处理器
 */

public class AccessAnonymousEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户请求了一个受保护的资源，但是用户没有通过认证，那么抛出异常，
     * AuthenticationEntryPoint. Commence(..)就会被调用。
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        //设置响应数据格式
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        //构建结果
//        R result = R.error(ResponseCode.NOT_PERMISSION.getCode(),ResponseCode.NOT_PERMISSION.getMessage());
//        //将对象序列化为json字符串响应前台
//        response.getWriter().write(new Gson().toJson(result));
//
        authException.printStackTrace();
        System.out.println("message:"+authException.getMessage());
        R result = null;
        if(authException instanceof BadCredentialsException){
            result = R.error(ResponseCode.ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
//            System.out.println("111");
            result = R.error(ResponseCode.LOGIN_ERROR.getMessage());
        }else{
            result = R.error(ResponseCode.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
