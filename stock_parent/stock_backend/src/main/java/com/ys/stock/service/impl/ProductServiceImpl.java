package com.ys.stock.service.impl;

import com.ys.stock.mapper.ProductMapper;
import com.ys.stock.pojo.Product;
import com.ys.stock.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author YS
* @description 针对表【t_product】的数据库操作Service实现
* @createDate 2023-08-22 11:19:57
*/
@Service
public class ProductServiceImpl implements ProductService{
    @Resource
    ProductMapper productMapper;

    @Override
    public List<Product> list() {
        return productMapper.list();
    }
}




