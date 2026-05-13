package com.ys.stock.config.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 杨森
 * @description:定义 A股大盘编码 和 外盘编码 集合
 * @date 2023年06月24日 9:54
 */
@ConfigurationProperties(prefix = "stock")
@Data
public class StockInfoConfig {
    //A股大盘ID集合
    private List<String> inner;
    //外盘ID集合
    private List<String> outer;

    //涨幅List集合
    private List<String> upDownRange;

    //大盘参数获取url
    private String marketUrl;
    //板块参数获取url
    private String blockUrl;
}
