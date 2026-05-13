package com.ys.stock.controller;

import com.ys.stock.annotation.Log;
import com.ys.stock.common.domain.PredictRequestDomain;
import com.ys.stock.enums.BusinessType;
import com.ys.stock.mapper.StockBusinessMapper;
import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.service.PreStockService;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/8/10 16:56
 */
@Api(tags = "预测股票")
@RestController
@RequestMapping("/api/predict")
public class PreStockController {
    @Resource
    private PreStockService preStockService;

    @Resource
    private StockBusinessMapper stockBusinessMapper;


    @ApiOperation("获取全部股票数据")
    @PostMapping("/getAllData")
    public void getAllData() {
        preStockService.getAllData();
    }

    @ApiOperation("获取股票列表")
    @GetMapping("/list")
    public R<Map> getList() {
        return preStockService.getList();
    }


    @ApiOperation("上传股票数据")
    @Log(title = "上传股票",businessType = BusinessType.UPLOAD_STOCK)
    @PostMapping("/uploadStock")
    public R<Map> uploadStock(@RequestBody StockBusiness stockBusiness) {
        String secCode = stockBusiness.getSecCode();
        String secName = stockBusiness.getSecName();

        preStockService.uploadStock(stockBusiness);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("secCode",secCode);
        map.put("secName",secName);

        return R.ok(map);
    }

    @ApiOperation("训练股票")
    @Log(title = "训练股票",businessType = BusinessType.TRAIN)
    @PostMapping("/trainStock")
    public void trainStock() {
        preStockService.trainStock();

    }


    @ApiOperation("预测股票")
    @Log(title = "预测股票",businessType = BusinessType.PREDICT)
    @PostMapping("/predict_stock")
    public R<List<Map<String, String>>> predict(@RequestBody PredictRequestDomain predictRequestDomain) {

        String name = predictRequestDomain.getStockName();
        String day = predictRequestDomain.getDay();
        String code = stockBusinessMapper.findId(name);

        List<String> baseDays = Arrays.asList("5day", "15day", "30day");
        List<Map<String, String>> responseData = new ArrayList<>();

        if (day.isEmpty()) {
            for (String baseDay : baseDays) {
                String res = preStockService.predictStock(code, baseDay);

                LocalDate today = LocalDate.now();
                LocalDate tomorrow = today.plusDays(1); // 增加一天


                Map<String, String> responseItem = new HashMap<>();
                responseItem.put("day", baseDay);
                responseItem.put("date", tomorrow.toString());
                responseItem.put("name", name);
                responseItem.put("res", res);

                responseData.add(responseItem);
            }
        } else {
            String res = preStockService.predictStock(code, day);

            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1); // 增加一天

            Map<String, String> responseItem = new HashMap<>();
            responseItem.put("day", day);
            responseItem.put("date", tomorrow.toString());
            responseItem.put("name", name);
            responseItem.put("res", res);

            responseData.add(responseItem);
        }

        return R.ok(responseData);
    }



}

