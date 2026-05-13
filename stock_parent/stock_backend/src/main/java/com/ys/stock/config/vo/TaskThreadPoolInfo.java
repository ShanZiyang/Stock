package com.ys.stock.config.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/6 9:19
 * @description 线程池实体类
 */
@ConfigurationProperties(prefix = "task.pool")
@Data
public class TaskThreadPoolInfo {
    /**
     * 核心线程数（获取硬件）：线程池创建时候初始化的线程数
     */
    private Integer corePoolSize;
    /*
     * 最大线程数
     * */
    private Integer maxPoolSize;
    /*
     * 空闲线程最大存货时间
     * */
    private Integer keepAliveSeconds;
    /*
     * 线程任务队列
     * */
    private Integer queueCapacity;
}
