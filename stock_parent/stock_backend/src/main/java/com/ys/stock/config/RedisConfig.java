package com.ys.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 杨森
 * @description: 自定义key序列化
 * @date 2023年06月23日 16:01
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //自定义key的序列化工具对象
        //设置redis中key的序列化
        template.setKeySerializer(new StringRedisSerializer());
        //设置hash中field序列化
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }


}
