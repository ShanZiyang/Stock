package com.ys.stock;

import com.ys.stock.config.vo.StockInfoConfig;
import com.ys.stock.config.vo.TaskThreadPoolInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.ys.stock.mapper")
@EnableConfigurationProperties({StockInfoConfig.class, TaskThreadPoolInfo.class})//开启配置初始化
//TODO 开启定时任务
//@EnableScheduling
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }
}
