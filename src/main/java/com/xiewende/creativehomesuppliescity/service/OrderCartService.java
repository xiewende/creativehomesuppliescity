package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.OrderCart;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @create 2021-04-16  10:31
 */
public interface OrderCartService {

    //添加到购物车
    public Integer insertOrderCart(Integer userId,Integer goodsId,int num);

    //修改某一项购物车的商品数量
    public Integer updateOrderCartNum(Integer id,int newNum);

    //删除某一购物车
    public Integer deleteOrderCart(Integer id);

    //查询某一位用户的全部购物车内容 根据的是用户的id
    public List<OrderCart> listAllOrderCartByUser(Integer userId);
}
