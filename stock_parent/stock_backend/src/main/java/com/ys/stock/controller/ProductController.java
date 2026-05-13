package com.ys.stock.controller;

import com.ys.stock.pojo.Product;
import com.ys.stock.service.ProductService;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/8/22 11:20
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Resource
    private ProductService productService;

//    @ApiOperation("测试接口")
//    @GetMapping("/test")
//    public R test(){
//
//        return R.ok("now",new Date());
//    }

    @ApiOperation("商品列表")
    @GetMapping("/list")
    public R list(){

        List<Product> list = productService.list();
//        System.out.println(list);
        return R.ok("productList", list);
    }

}
