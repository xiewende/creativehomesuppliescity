package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.*;
import com.xiewende.creativehomesuppliescity.pojo.*;

import com.xiewende.creativehomesuppliescity.service.OrderService;
import com.xiewende.creativehomesuppliescity.utils.RandomGetString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2021-04-17  15:44
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderCartMapper orderCartMapper;
    @Autowired
    private MyorderMapper myorderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Double countTotalPrice(Integer[] orderCartIdList) {
        double totalPrice = 0;
        for(Integer id : orderCartIdList){
            OrderCart orderCart = orderCartMapper.selectByPrimaryKey(id);
            totalPrice += orderCart.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public Integer insertOrder(Integer userId,Integer[] orderCartIdList, String payTyle, String address, String iphone,String receiveName) {
        //构造Myorderduix
        Myorder myorder = new Myorder();
        //订单编号
        String orderNumber = RandomGetString.getOrderNum();
        myorder.setOrderNumber(orderNumber);
        //订单项目
        myorder.setOrderNum(orderCartIdList.length);
        //用户id
        myorder.setUserId(userId);
        //时间
        myorder.setCreateTime(new Date());
        myorder.setUpdateTime(new Date());
        //支付方式和支付总价
        if("支付宝".equals(payTyle)) myorder.setPayType(0);
        else if("微信".equals(payTyle)) myorder.setPayType(1);
        else if("网银".equals(payTyle)) myorder.setPayType(2);
        else myorder.setPayType(3);
        double totalPrice = 0;
        for(Integer id : orderCartIdList){
            //就算总价和删除购物车的这一项
            OrderCart orderCart = orderCartMapper.selectByPrimaryKey(id);
            totalPrice += orderCart.getTotalPrice();
            //删除
            orderCart.setIsdelete(1);
            orderCartMapper.updateByPrimaryKey(orderCart);
        }
        myorder.setPayPrice(totalPrice);
        //物流  管理员点击发货的时候填充的
//        String lName= RandomGetString.getOrderNum();
//        myorder.setLgisticsNumble(lName);
//        myorder.setLogisticsName(logisticsName);
        //收货人信息地址和电话
        myorder.setAddress(address);
        myorder.setReceiveName(receiveName);
        myorder.setIphone(iphone);
        //状态
        myorder.setStatus(0); //已经下单
        myorder.setIsdelete(0);
        //执行插入
        myorderMapper.insert(myorder);

        //封装订单商品表得信息
        for(Integer orderCartId : orderCartIdList){
            OrderGoods orderGoods = new OrderGoods();
            OrderCart orderCart = orderCartMapper.selectByPrimaryKey(orderCartId);
            orderGoods.setUserId(userId);
            orderGoods.setOrderId(myorder.getId());
            orderGoods.setGoodsId(orderCart.getGoodsId());
            orderGoods.setGoodsNum(orderCart.getNum());
            orderGoods.setTotalPrice(String.valueOf(orderCart.getTotalPrice()));
            //查找good
            Goods goods = goodsMapper.selectByPrimaryKey(orderCart.getGoodsId());
            orderGoods.setGoodsPrice(goods.getPrice());
            orderGoods.setGoodsPic(goods.getImage());
            orderGoods.setGoodName(goods.getGoodName());
            //
            orderGoods.setIsdelete(0);
            //执行
            orderGoodsMapper.insert(orderGoods);
        }
        return 1;
    }

    @Override
    public List<Myorder> selectNewOrder() {

        MyorderExample myorderExample = new MyorderExample();
        MyorderExample.Criteria criteria = myorderExample.createCriteria();
        criteria.andStatusEqualTo(0);//刚刚下单的
        criteria.andIsdeleteEqualTo(0);//没有删除的
        List<Myorder> myorders = myorderMapper.selectByExample(myorderExample);
        //用户封装
        for(Myorder myorder : myorders){
            User user = userMapper.selectByPrimaryKey(myorder.getUserId());
            myorder.setUser(user);
        }
        return myorders;
    }

    @Override
    public List<Myorder> selectOrderWithDemand(String userName, String statusName) {
        MyorderExample myorderExample = new MyorderExample();
        MyorderExample.Criteria criteria = myorderExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);//没有删除的
        if(userName != null){
            List<Integer> userIds = userMapper.selectIdOfLikeUserName(userName);
            criteria.andUserIdIn(userIds);
        }
        if(statusName != null){
            if("等待发货".equals(statusName)) criteria.andStatusEqualTo(0);
            else if("已发货".equals(statusName)) criteria.andStatusEqualTo(1);
            else if("已经收货".equals(statusName)) criteria.andStatusEqualTo(2);
        }
        //开始查询
        List<Myorder> myorders = myorderMapper.selectByExample(myorderExample);
        //用户封装
        for(Myorder myorder : myorders){
            User user = userMapper.selectByPrimaryKey(myorder.getUserId());
            myorder.setUser(user);
        }
        return myorders;
    }

    @Override
    public Myorder selectOrderDetail(Integer id) {
        Myorder myorder = myorderMapper.selectByPrimaryKey(id);
        //封装订单商品
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(myorder.getId());
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        myorder.setOrderGoodsList(orderGoods);
        //用户
        myorder.setUser(userMapper.selectByPrimaryKey(myorder.getUserId()));
        return myorder;
    }

    @Override
    public List<Myorder> selectUserOrder(Integer userId) {
        MyorderExample myorderExample = new MyorderExample();
        MyorderExample.Criteria criteria = myorderExample.createCriteria();
        criteria.andIsdeleteEqualTo(0);//没有删除的
        criteria.andUserIdEqualTo(userId);
        //执行
        List<Myorder> myorders = myorderMapper.selectByExample(myorderExample);
        //用户封装
        for(Myorder myorder : myorders){
            User user = userMapper.selectByPrimaryKey(myorder.getUserId());
            myorder.setUser(user);
        }
        return myorders;
    }

    @Override
    public Integer confirmShipments(Integer myorderId,String LogisticsMame) {

        //find this order
        Myorder myorder = myorderMapper.selectByPrimaryKey(myorderId);
        //update tataus
        myorder.setStatus(1);//已发货
        //物流信息
        String lName= RandomGetString.getOrderNum();
        myorder.setLgisticsNumble(lName);
        myorder.setLogisticsName(LogisticsMame);
        //两个时间
        myorder.setUpdateTime(new Date());
        myorder.setShipTime(new Date());
        //保存
        return myorderMapper.updateByPrimaryKey(myorder);
    }

    @Override
    public Integer confitmReceiveGoods(Integer myorderId) {
        //find this order
        Myorder myorder = myorderMapper.selectByPrimaryKey(myorderId);
        //update tataus
        myorder.setStatus(2);//已经收货
        //物流信息
        //两个时间
        myorder.setUpdateTime(new Date());
        myorder.setReceiveTime(new Date());
        //保存
        return myorderMapper.updateByPrimaryKey(myorder);
    }

    @Override
    public Integer delectOrder(Integer myorderId) {
        Myorder myorder = myorderMapper.selectByPrimaryKey(myorderId);
        myorder.setIsdelete(1);
        myorderMapper.updateByPrimaryKey(myorder);
        //订单商品也是删除
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(myorderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        for(OrderGoods ord : orderGoods){
            ord.setIsdelete(1);
            orderGoodsMapper.updateByPrimaryKey(ord);
        }
        return 1;
    }
}
