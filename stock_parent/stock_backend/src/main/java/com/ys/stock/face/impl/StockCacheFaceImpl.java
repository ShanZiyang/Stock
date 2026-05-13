package com.ys.stock.face.impl;

import com.ys.stock.face.StockCacheFace;
import com.ys.stock.mapper.StockBusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  杨森
 * @date  2023/7/13 9:28
 * @version 1.0
 * @description
 */

@Component
@CacheConfig(cacheNames = "stock")
public class StockCacheFaceImpl implements StockCacheFace {
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Cacheable(key = "#root.method.getName()")
    @Override
    public List<String> getAShareStockCodes() {
        List<String> stockIds = stockBusinessMapper.getStockCode();

        return stockIds;
    }
}
