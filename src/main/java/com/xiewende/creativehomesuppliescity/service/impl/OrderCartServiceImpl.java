package com.xiewende.creativehomesuppliescity.service.impl;

import com.xiewende.creativehomesuppliescity.mapper.GoodsMapper;
import com.xiewende.creativehomesuppliescity.mapper.OrderCartMapper;
import com.xiewende.creativehomesuppliescity.pojo.Goods;
import com.xiewende.creativehomesuppliescity.pojo.OrderCart;
import com.xiewende.creativehomesuppliescity.pojo.OrderCartExample;
import com.xiewende.creativehomesuppliescity.pojo.UserExample;
import com.xiewende.creativehomesuppliescity.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2021-04-16  11:01
 */
@Service
public class OrderCartServiceImpl implements OrderCartService {

    @Autowired
    private OrderCartMapper orderCartMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Integer insertOrderCart(Integer userId,Integer goodsId,int num) {
        //建立对象
        OrderCart orderCart = new OrderCart();
        orderCart.setUserId(userId);
        orderCart.setGoodsId(goodsId);
        orderCart.setNum(num);
        //寻找商品，计算总价
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        Double totalPrice = goods.getPrice() * num ;
        orderCart.setTotalPrice(totalPrice);
        //其余相关信息
        orderCart.setCreateTime(new Date());
        orderCart.setUpdateTime(new Date());
        orderCart.setIsdelete(0);

        return orderCartMapper.insert(orderCart);
    }

    @Override
    public Integer updateOrderCartNum(Integer id, int newNum) {

        //先找，然改，最保存
        OrderCart orderCart = orderCartMapper.selectByPrimaryKey(id);
        int oldNum = orderCart.getNum();
        double oldTotalPrice = orderCart.getTotalPrice();
        double newTotalPrice = (oldTotalPrice / oldNum) * newNum;
        orderCart.setTotalPrice(newTotalPrice);
        orderCart.setNum(newNum);
        return orderCartMapper.updateByPrimaryKey(orderCart);
    }

    @Override
    public Integer deleteOrderCart(Integer id) {
        OrderCart orderCart = orderCartMapper.selectByPrimaryKey(id);
        if(orderCart == null) return -1;
        orderCart.setIsdelete(1);
        return orderCartMapper.updateByPrimaryKey(orderCart);
    }

    @Override
    public List<OrderCart> listAllOrderCartByUser(Integer userId) {

        //根据用户id查询用户自身的
        OrderCartExample orderCartExample = new OrderCartExample();
        OrderCartExample.Criteria criteria = orderCartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsdeleteEqualTo(0);//没有删除
        List<OrderCart> orderCarts = orderCartMapper.selectByExample(orderCartExample);
        //封装一下商品的信息
        for(OrderCart orderCart : orderCarts){
            orderCart.setGoods(goodsMapper.selectByPrimaryKey(orderCart.getGoodsId()));
        }
        return orderCarts;
    }
}
