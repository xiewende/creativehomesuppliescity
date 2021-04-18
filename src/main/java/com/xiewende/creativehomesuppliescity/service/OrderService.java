package com.xiewende.creativehomesuppliescity.service;

import com.xiewende.creativehomesuppliescity.pojo.Myorder;

import java.util.List;

/**
 * @create 2021-04-17  11:20
 */
public interface OrderService {

    //计算总价，点击购物车得时候
    public Double countTotalPrice(Integer orderCartIdList[]);

    //下单
    public Integer insertOrder(Integer userId,Integer orderCartIdList[],String payTyle,String address,String iphone,String receiveName);

    //管理端查询新的订单
    public List<Myorder> selectNewOrder();

    //管理员查询全部订单，根据用户名，状态查询条件
    public List<Myorder> selectOrderWithDemand(String userName,String statusName);

    //查询某一个订单的详情
    public Myorder selectOrderDetail(Integer id);

    //用户查询自己的订单
    public List<Myorder> selectUserOrder(Integer userId);

    //确认订单,进而进行发货
    public Integer confirmShipments(Integer myorderId,String LogisticsMame);

    //用户确认收货
    public Integer confitmReceiveGoods(Integer myorderId);

    //若完成后可以选择删除某一个订单
    public Integer delectOrder(Integer myorderId);

}
