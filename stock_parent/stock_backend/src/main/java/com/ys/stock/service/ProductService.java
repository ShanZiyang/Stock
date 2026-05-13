package com.ys.stock.service;

import com.ys.stock.pojo.Product;

import java.util.List;

/**
* @author YS
* @description 针对表【t_product】的数据库操作Service
* @createDate 2023-08-22 11:19:57
*/
public interface ProductService {

    List<Product> list();

}
