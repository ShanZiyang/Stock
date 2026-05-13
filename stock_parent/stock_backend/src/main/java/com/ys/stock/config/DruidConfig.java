package com.ys.stock.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/15 8:41
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){

        return new DruidDataSource();
    }
    /**
     * 后台监控功能
     * 后台监控:web.xml ServletRegistrationBean
     * 因为SpringBoot内置了servlet容器,所以没有web.xml
     * 替代方法:ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean statViewServlet (){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        /**
         * 账号密码配置
         */
        HashMap<String, String> initParameters = new HashMap<>();
        /**
         * 登录的key是固定的 loginUsername loginPassword
         *
         */

        //TODO sql监控前端跨域问题

//        initParameters.put("loginUsername","admin");
        initParameters.put("loginUsername","");
//        initParameters.put("loginPassword","123456");
        initParameters.put("loginPassword","");

        //允许谁可以访问
        initParameters.put("allow","");

        //禁止谁可以访问
        /**
         * initParameters.put("dawei","192.168.200.1");
         */


        bean.setInitParameters(initParameters);
        return bean;
    }
    /**
     * Filter
     */
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new WebStatFilter());
        HashMap<String, String> initParameters = new HashMap<>();
        initParameters.put("exclusions","*.js,*.css");

        bean.setInitParameters(initParameters);

        return bean;
    }

}
