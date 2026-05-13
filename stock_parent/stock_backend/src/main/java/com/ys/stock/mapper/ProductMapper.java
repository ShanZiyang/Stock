package com.ys.stock.mapper;

import com.ys.stock.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author YS
* @description 针对表【t_product】的数据库操作Mapper
* @createDate 2023-08-22 11:19:57
* @Entity com.ys.stock.pojo.Product
*/
public interface ProductMapper{

    List<Product> list();

    Product selectById(@Param("productId") Long productId);

}




