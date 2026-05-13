package com.ys.stock.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ys.stock.exception.BusinessException;
import com.ys.stock.mapper.StockBusinessMapper;
import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.service.PreStockService;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/8/25 16:12
 */
@Service("preStockService")
public class PreStockServiceImpl implements PreStockService {
    @Resource
    private StockBusinessMapper stockBusinessMapper;

    //    @Value("${stock.data.dir}")
//    String dataDir;
//    @Value("http://" + "${spring.redis.host}" + ":5000")
    @Value("http://127.0.0.1:5000")
    String baseUrl;

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder()
            .connectTimeout(60 * 3, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.HOURS)
            .readTimeout(60 * 3, TimeUnit.SECONDS).build();

    @Override
    public void getAllData() {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url(baseUrl + "/getData")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            JSONObject jsonObjectRes = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
            if (jsonObjectRes.getInteger("code") != 200) {
                throw new BusinessException(ResponseCode.GET_DATA_FAIL.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.GET_API_FAIL.getMessage());
        }
    }

    @Override
    public void trainStock() {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("stockName",stockName);

        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(baseUrl + "/train_stock")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            JSONObject jsonObjectRes = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());

            if (jsonObjectRes.getInteger("code") != 200) {
                throw new BusinessException(ResponseCode.TRAIN_STOCK_FAIL.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.GET_API_FAIL.getMessage());
        }

    }

    @Override
    public String predictStock(String code, String day) {
        String res = null;
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("day", day);

        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url(baseUrl + "/predict_stock")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            JSONObject jsonObjectRes = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
            if (jsonObjectRes.getInteger("code") != 200) {
                throw new BusinessException(ResponseCode.PREDICT_STOCK_FAIL.getMessage());

            }
            res = jsonObjectRes.getString("data");
//            System.out.println(res);

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.GET_API_FAIL.getMessage());
        }
        return res;
    }



    @Override
    public void uploadStock(StockBusiness stockBusiness) {
        String stockName = stockBusiness.getSecName();
        String stockCode = stockBusiness.getSecCode();
//        System.out.println("调用了uploadStock：" + stockName);

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stockName", stockName);
        jsonObject.put("stockCode", stockCode);


        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url(baseUrl + "/uploadStock")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            JSONObject jsonObjectRes = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
            if (jsonObjectRes.getInteger("code") != 200) {
                throw new BusinessException(ResponseCode.GET_DATA_FAIL.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.GET_API_FAIL.getMessage());
        }
    }


    @Override
    public R<Map> getList() {
        List<Map> names = stockBusinessMapper.findAllName();
        List<Map> code = stockBusinessMapper.findAllCode();
        System.out.println(code);
        List<Map<String, String>> optionsList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            Map<String, String> optionMap = new HashMap<>();
            optionMap.put("value", "选项" + (i + 1));
            optionMap.put("label", (String) names.get(i).get("sec_name"));
            optionMap.put("code", String.valueOf(code.get(i).get("sec_code")));
            optionsList.add(optionMap);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("options", optionsList);
        return R.ok(resultMap);
    }


}
