package com.ys.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ys.stock.constants.Constants;
import com.ys.stock.constants.MagicValue;
import com.ys.stock.manager.AsyncManager;
import com.ys.stock.manager.factory.AsyncFactory;
import com.ys.stock.mapper.SysUserMapper;
import com.ys.stock.pojo.SysUser;
import com.ys.stock.utils.JwtTokenUtil;
import com.ys.stock.utils.MessageUtils;
import com.ys.stock.vo.resp.LoginRespVo;
import com.ys.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/11 15:43
 * @description 自定义登录过滤器
 */
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private RedisTemplate redisTemplate;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 自定义的登录路径
     *
     * @param loginUrl
     */
    public JwtLoginAuthenticationFilter(String loginUrl) {
        super(loginUrl);
    }

    /**
     * 认证过滤
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 从输入流中获取到登录的信息
        // 创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证
        String username = null;
        String password = null;
        //验证码
        String checkCode = null;
        //redis中rkeyS
        String rkey = null;
        //判断当前是form登录还是ajax登录
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) ||
                request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            try {
                ServletInputStream in = request.getInputStream();
                HashMap<String, String> map = new ObjectMapper().readValue(in, HashMap.class);
                username = map.get("username");
                password = map.get("password");
                //获取校验码
                checkCode = map.get("code");
                rkey = map.get("rkey");


            } catch (IOException e) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                e.printStackTrace();
            }
        } else {
            //支持form表单提交方式
            username = request.getParameter("username");
            password = request.getParameter("password");
            checkCode = request.getParameter("code");
            rkey = request.getParameter("rkey");
        }

        int user = sysUserMapper.userExists(username);
        String pwd = sysUserMapper.selectByName(username);
        String rkeyValue = (String) redisTemplate.opsForValue().get(rkey);

        if (user == MagicValue.SQL_ZERO) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
//                throw new BusinessException(ResponseCode.SYSTEM_USERNAME_NOT_EXISTS.getMessage());
//                throw new RuntimeException("用户不存在");
        }


        if (password.isEmpty() || !passwordEncoder.matches(password, pwd)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//                throw new BusinessException(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
            throw new RuntimeException("密码错误");
        }

        if (rkeyValue == null || !rkeyValue.equals(checkCode)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//            throw new BusinessException(ResponseCode.SYSTEM_VERIFY_CODE_ERROR.getMessage());
            throw new RuntimeException("验证码错误");
        }


        //删除验证码
        redisTemplate.delete(rkey);
        //生成认证token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * 认证成功后的处理方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser user = (SysUser) authResult.getPrincipal();
        // 从User中获取权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 创建Token
        String token = JwtTokenUtil.createToken(user.getUsername(), authorities.toString());
        //设置响应编码格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //组装响应结果
        LoginRespVo loginResult = LoginRespVo.builder().id(user.getId())
                .nickName(user.getNickName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .menus(user.getMenus())
                .permissions(user.getPermissions())
                .accessToken(token)
                .build();
        System.out.println(user.getUsername());
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        R<LoginRespVo> ok = R.ok(loginResult);
        //转化成json字符串响应前端
        String result = new Gson().toJson(ok);
        //响应数据
        response.getWriter().write(result);
    }

    /**
     * 认证失败后的处理方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        //TODO 鉴权获取的异常只有InsufficientAuthenticationException
//
//        String returnData = "";
//        System.out.println("failed:" + failed.getMessage());
//
//
//        // 用户过期
//        if (failed instanceof AccountExpiredException) {
//            returnData = "用户过期";
//        }
//        // 密码错误
//        else if (failed instanceof BadCredentialsException) {
//            returnData = "密码错误";
//        }
//        // 密码过期
//        else if (failed instanceof CredentialsExpiredException) {
//            returnData = "密码过期";
//        }
//        // 账号不可用
//        else if (failed instanceof DisabledException) {
//            returnData = "账号不可用";
//        }
//        //账号锁定
//        else if (failed instanceof LockedException) {
//            returnData = "账号锁定";
//        }
//        // 用户不存在
//        else if (failed instanceof InternalAuthenticationServiceException) {
//            returnData = "用户不存在";
//        }
//        // 其他错误
//        else {
//            returnData = "未知异常";
//        }
//        // 处理编码方式 防止中文乱码
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        // 将反馈塞到HttpServletResponse中返回给前台
//        R result = R.error(returnData);
//        response.getWriter().write(new Gson().toJson(result));
//        WebUtils.renderString(response, JSON.toJSONString(result));
//
//    }

}
