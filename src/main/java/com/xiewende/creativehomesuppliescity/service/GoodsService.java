package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Goods;

import java.util.List;

/**
 * @create 2021-04-13  11:07
 */
public interface GoodsService {

    //添加
    Integer insertGoods(Goods goods,String categorySecondName,String typeName,String BrandName);

    //修改
    Integer updateGoods(Goods goods,String categorySecondName,String typeName,String BrandName);

    //删除
    Integer deleteGoods(Integer id);

    //查询全部。按照商品名，分类，风格，品牌模糊查询
    List<Goods> listGoodsWithSomeDemand(String categoryFirstName,String categorySecondName,String typeName,String BrandName,String goodName,Integer pageNum);

    //id查询
    Goods findGoodById(Integer id);

    //查询商品的详情
    Goods selectGoodsDetails(Integer id);

}
