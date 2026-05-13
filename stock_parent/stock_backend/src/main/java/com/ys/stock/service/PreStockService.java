package com.ys.stock.service;


import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.vo.resp.R;

import java.util.Map;

public interface PreStockService {
    public void getAllData();

    public void trainStock();

    public String predictStock(String code, String day);

    void uploadStock(StockBusiness stockBusiness);


    R<Map> getList();


}
