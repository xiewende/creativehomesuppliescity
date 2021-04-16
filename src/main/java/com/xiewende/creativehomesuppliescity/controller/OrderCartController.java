package com.xiewende.creativehomesuppliescity.controller;

import com.xiewende.creativehomesuppliescity.pojo.OrderCart;
import com.xiewende.creativehomesuppliescity.service.OrderCartService;
import com.xiewende.creativehomesuppliescity.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "OrderCart",description = "购物车管理接口")
@RestController
@CrossOrigin
@RequestMapping("/orderCart")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    //添加
    @ApiOperation("添加购物车")
    @PostMapping("/insertOrderCart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登录的用户id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsId", value = "添加到购物车的商品id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "添加到购物车的商品个数",
                    dataType = "int", paramType = "query"),
    })
    public Result insertOrderCart(Integer userId,Integer goodsId,Integer num){
        //1、判断是否为空
        if(userId == null || goodsId == null)
        {
            return Result.build(400,"数据不可以为空");
        }
        //若没有选择个数的话，默认为1
        if("".equals(num) || num == null){num = 1;}

        //数量不可以为负数
        if(num <=0){
            return Result.build(400,"选择商品的个数需要大于0");
        }

        //todo  还需要判断是否已经登录的状态，这里需要redis，后续再来完善，没有登录直接返回

        //添加
        Integer integer = orderCartService.insertOrderCart(userId,goodsId,num);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("修改购物车某一项的商品个数")
    @PostMapping("/updateOrderCartNum")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "购物车id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "newNum", value = "新的数量",
                    dataType = "int", paramType = "query")
    })
    public Result updateOrderCartNum(Integer id, Integer newNum){
        //1、判断是否为空
        //若没有选择个数的话，默认为原来，直接返回
        if("".equals(newNum) || newNum == null){
            return Result.build(400,"您不需要改变个数");
        }

        //数量不可以为负数
        if(newNum <=0){
            return Result.build(400,"选择商品的个数需要大于0");
        }

        //进行修改
        Integer integer = orderCartService.updateOrderCartNum(id, newNum);

        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //删除
    @PostMapping("/deleteOrderCart")
    @ApiOperation("删除某一个购物车项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被删除的购物车id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result deleteOrderCart(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Integer integer = orderCartService.deleteOrderCart(id);
        if (integer > 0) {
            return Result.ok();
        }else{
            return Result.build(500, "系统错误！！！");
        }
    }

    //查询全部
    //查询全部用户，若输入了名字则模糊查询，若没有则查询全部
    @PostMapping("/listAllOrderCartByUser")
    @ApiOperation("查询全部购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "需要查询该用户的购物车，该用户的id，一般就是登录的用户id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result listAllOrderCartByUser(Integer userId){

        //执行查询
        List<OrderCart> OrderCarts = orderCartService.listAllOrderCartByUser(userId);

        if (OrderCarts.size() > 0) {
            return Result.build(200, "有数据", OrderCarts);
        }else if(OrderCarts.size() == 0){
            return Result.build(400,"您的购物车为空");
        }else {
            return Result.build(500, "系统错误！！");
        }
    }

}
