package com.ys.stock.security.handler;

import com.alibaba.fastjson.JSON;
import com.ys.stock.utils.WebUtils;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  杨森
 * @date  2023/7/11 15:45
 * @version 1.0
 * @description 认证用户无权限访问资源时处理器
 */

public class StockAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        //设置响应数据格式
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        //构建结果
//        R result = R.error(ResponseCode.NOT_PERMISSION.getCode(),ResponseCode.NOT_PERMISSION.getMessage());
//        //将对象序列化为json字符串响应前台
//        response.getWriter().write(new Gson().toJson(result));

        accessDeniedException.printStackTrace();
        R result = R.error(ResponseCode.NOT_PERMISSION.getMessage());
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
