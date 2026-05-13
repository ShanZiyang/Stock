package com.ys.stock.config;

import com.ys.stock.utils.IdWorker;
import com.ys.stock.utils.ParserStockInfoUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 杨森
 * @description: 定义公共配置类
 * @date 2023年06月15日 21:58
 */
@Configuration
public class CommonConfig {
    /**
     * 密码加密器
     * BCryptPasswordEncoder方法采用SHA-256对密码进行加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置基于雪花算法生成全局唯一ID
     * 参与运算的参数：时间戳+ 机房ID + 机器ID + 序列号
     * 保证ID唯一
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public ParserStockInfoUtil parserStockInfoUtil(IdWorker idWorker){
        return new ParserStockInfoUtil(idWorker);
    }
}
