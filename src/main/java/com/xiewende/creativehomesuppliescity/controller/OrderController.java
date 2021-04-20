package com.xiewende.creativehomesuppliescity.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiewende.creativehomesuppliescity.pojo.Myorder;
import com.xiewende.creativehomesuppliescity.pojo.OrderCart;
import com.xiewende.creativehomesuppliescity.service.OrderService;
import com.xiewende.creativehomesuppliescity.utils.ConstantProperties;
import com.xiewende.creativehomesuppliescity.utils.Result;
import com.xiewende.creativehomesuppliescity.vo.CountPriveVo;
import com.xiewende.creativehomesuppliescity.vo.MyorderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @create 2021-04-10  21:54
 */

@Api(tags = "Order(9)",description = "订单管理接口")
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ConstantProperties constantProperties;

    //添加
    @ApiOperation("选择购物车得时候计算总价")
    @PostMapping("/countTotalPrice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "购物车id集合，多个用逗号隔开传入", required = true,
                    dataType = "string", paramType = "query"),
    })
    public Result countTotalPrice(String idList){
        //1、判断是否为空
        if(idList == null || "".equals(idList)) return Result.build(400,"数据不可以为空"); ;
        String[] orderCartIdList = idList.split(",");
        Integer ids[] = new Integer[orderCartIdList.length];
        for(int i=0;i<ids.length;i++){
            ids[i] = Integer.valueOf(orderCartIdList[i]);
        }

        //计算
        Double totalPrice = orderService.countTotalPrice(ids);
        CountPriveVo countPriveVo = new CountPriveVo(totalPrice);
        if (countPriveVo != null) {
            return Result.build(200,"查到了",countPriveVo);
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //修改
    @ApiOperation("下单操作")
    @PostMapping("/insertOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "idList", value = "购物车id集合，多个用逗号隔开传入", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "payTyle", value = "支付方式（支付宝微信等）", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "收货地址", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iphone", value = "收货人电话", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "receiveName", value = "收货人姓名", required = true,
                    dataType = "string", paramType = "query"),
    })
    public Result insertOrder(Integer userId,String idList, String payTyle, String address, String iphone,String receiveName){
        //1、判断是否为空
        if(userId == null || idList == null || "".equals(idList)
        || payTyle == null || "".equals(payTyle)
        || address == null || "".equals(address)
        || iphone == null || "".equals(iphone)
        || receiveName == null || "".equals(receiveName))
        {
            return Result.build(400,"数据不可以为空");
        }

        String[] orderCartIdList = idList.split(",");
        Integer ids[] = new Integer[orderCartIdList.length];
        for(int i=0;i<ids.length;i++){
            ids[i] = Integer.valueOf(orderCartIdList[i]);
        }

        //对电话号码的校验
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(iphone);
        if(!m.matches())return Result.build(400,"电话号码格式错误！！！");

        //下单
        Integer integer = orderService.insertOrder(userId, ids, payTyle,address, iphone, receiveName);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //管理端查询新的订单
    @PostMapping("/selectNewOrder")
    @ApiOperation("管理端查询新的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询页码", required = true,
                    dataType = "Int", paramType = "query",defaultValue = "1")
    })
    public Result selectNewOrder(Integer pageNum){
        //分页
        PageHelper.startPage(pageNum,constantProperties.getPageSize());
        List<Myorder> myorders = orderService.selectNewOrder();
        PageInfo<Myorder> myorderPageInfo = new PageInfo<>(myorders);
        if(myorders.size() == 0) return Result.build(400, "没有新的订单");
        //封装一下给前端展示
        ArrayList<MyorderVo> myorderVoList = new ArrayList<>();
        for(Myorder myorder : myorders){
            MyorderVo myorderVo = new MyorderVo();
            BeanUtils.copyProperties(myorder,myorderVo); // 注意导入的包不一样，顺序不一样
            //其余不可直接复制
            myorderVo.setUserName(myorder.getUser().getUserName());
            if(myorder.getCreateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getCreateTime());
                myorderVo.setCreateTimeStr(dateString);
            }
            if(myorder.getUpdateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getUpdateTime());
                myorderVo.setUpdateTimeStr(dateString);
            }
            if(myorder.getShipTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getShipTime());
                myorderVo.setShipTimeStr(dateString);
            }
            if(myorder.getReceiveTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getReceiveTime());
                myorderVo.setReceiveTimeStr(dateString);
            }

            if(myorder.getPayType() == 0) myorderVo.setPayTypeStr("支付宝");
            else if(myorder.getPayType() == 1) myorderVo.setPayTypeStr("微信");
            else if(myorder.getPayType() == 2) myorderVo.setPayTypeStr("网银");
            else myorderVo.setPayTypeStr("其他");

            if(myorder.getStatus() == 0) myorderVo.setStatusStr("等待发货");
            else if(myorder.getStatus() == 1) myorderVo.setStatusStr("已发货");
            else if(myorder.getStatus() == 2) myorderVo.setStatusStr("已经收货");

            // add list
            myorderVoList.add(myorderVo);
        }

        PageInfo<MyorderVo> myorderPageInfo1 = new PageInfo<>(myorderVoList);
        BeanUtils.copyProperties(myorderPageInfo,myorderPageInfo1); // 注意导入的包不一样，顺序不一样
        myorderPageInfo1.setList(myorderVoList);
        return Result.build(200, "有数据", myorderPageInfo1);
    }

    //管理员查询全部订单，根据用户名，状态查询条件
    @PostMapping("/selectOrderWithDemand")
    @ApiOperation("管理员查询全部订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "下单人名字",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "statusName", value = "状态名字",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "查询页码", required = true,
                    dataType = "Int", paramType = "query",defaultValue = "1")
    })
    public Result selectOrderWithDemand(Integer pageNum,String userName, String statusName){

        if("".equals(userName)) userName = null;
        if("".equals(statusName)) statusName = null;

        //分页
        PageHelper.startPage(pageNum,constantProperties.getPageSize());
        //执行查询
        List<Myorder> myorders = orderService.selectOrderWithDemand(userName, statusName);
        PageInfo<Myorder> myorderPageInfo = new PageInfo<>(myorders);
        if(myorders.size() == 0) return Result.build(400, "没有订单");
        //封装一下给前端展示
        ArrayList<MyorderVo> myorderVoList = new ArrayList<>();
        for(Myorder myorder : myorders) {
            MyorderVo myorderVo = new MyorderVo();
            BeanUtils.copyProperties(myorder, myorderVo); // 注意导入的包不一样，顺序不一样
            //其余不可直接复制
            myorderVo.setUserName(myorder.getUser().getUserName());
            if (myorder.getCreateTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getCreateTime());
                myorderVo.setCreateTimeStr(dateString);
            }
            if (myorder.getUpdateTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getUpdateTime());
                myorderVo.setUpdateTimeStr(dateString);
            }
            if (myorder.getShipTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getShipTime());
                myorderVo.setShipTimeStr(dateString);
            }
            if (myorder.getReceiveTime() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getReceiveTime());
                myorderVo.setReceiveTimeStr(dateString);
            }

            if (myorder.getPayType() == 0) myorderVo.setPayTypeStr("支付宝");
            else if (myorder.getPayType() == 1) myorderVo.setPayTypeStr("微信");
            else if (myorder.getPayType() == 2) myorderVo.setPayTypeStr("网银");
            else myorderVo.setPayTypeStr("其他");

            if(myorder.getStatus() == 0) myorderVo.setStatusStr("等待发货");
            else if(myorder.getStatus() == 1) myorderVo.setStatusStr("已发货");
            else if(myorder.getStatus() == 2) myorderVo.setStatusStr("已经收货");

            // add list
            myorderVoList.add(myorderVo);
        }

        PageInfo<MyorderVo> myorderPageInfo1 = new PageInfo<>(myorderVoList);
        BeanUtils.copyProperties(myorderPageInfo,myorderPageInfo1); // 注意导入的包不一样，顺序不一样
        myorderPageInfo1.setList(myorderVoList);
        return Result.build(200, "有数据", myorderPageInfo1);
    }

    //根据id查询一个
    //根据id查询某一位用户
    @PostMapping("/selectOrderDetail")
    @ApiOperation("查询某一个订单的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要被查询订单id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectOrderDetail(Integer id){
        if(id == null ) return Result.build(400,"数据不可以为空");

        Myorder myorder = orderService.selectOrderDetail(id);
        if (myorder == null) return Result.build(200,"没有找到相应数据");

        //封装给前端
        MyorderVo myorderVo = new MyorderVo();
        BeanUtils.copyProperties(myorder, myorderVo); // 注意导入的包不一样，顺序不一样
        //其余不可直接复制
        myorderVo.setUserName(myorder.getUser().getUserName());
        if (myorder.getCreateTime() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(myorder.getCreateTime());
            myorderVo.setCreateTimeStr(dateString);
        }
        if (myorder.getUpdateTime() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(myorder.getUpdateTime());
            myorderVo.setUpdateTimeStr(dateString);
        }
        if (myorder.getShipTime() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(myorder.getShipTime());
            myorderVo.setShipTimeStr(dateString);
        }
        if (myorder.getReceiveTime() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(myorder.getReceiveTime());
            myorderVo.setReceiveTimeStr(dateString);
        }

        if (myorder.getPayType() == 0) myorderVo.setPayTypeStr("支付宝");
        else if (myorder.getPayType() == 1) myorderVo.setPayTypeStr("微信");
        else if (myorder.getPayType() == 2) myorderVo.setPayTypeStr("网银");
        else myorderVo.setPayTypeStr("其他");

        if(myorder.getStatus() == 0) myorderVo.setStatusStr("等待发货");
        else if(myorder.getStatus() == 1) myorderVo.setStatusStr("已发货");
        else if(myorder.getStatus() == 2) myorderVo.setStatusStr("已经收货");

        //
        return Result.build(200, "有数据", myorderVo);
    }


    //用户查询自己的订单
    @PostMapping("/selectUserOrder")
    @ApiOperation("用户查询自己的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登录用户Id", required = true,
                    dataType = "int", paramType = "query")
    })
    public Result selectUserOrder(Integer userId){

        List<Myorder> myorders = orderService.selectUserOrder(userId);

        if(myorders.size() == 0) return Result.build(400, "没有订单");

        //封装一下给前端展示
        ArrayList<MyorderVo> myorderVoList = new ArrayList<>();
        for(Myorder myorder : myorders){
            MyorderVo myorderVo = new MyorderVo();
            BeanUtils.copyProperties(myorder,myorderVo); // 注意导入的包不一样，顺序不一样
            //其余不可直接复制
            myorderVo.setUserName(myorder.getUser().getUserName());
            if(myorder.getCreateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getCreateTime());
                myorderVo.setCreateTimeStr(dateString);
            }
            if(myorder.getUpdateTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getUpdateTime());
                myorderVo.setUpdateTimeStr(dateString);
            }
            if(myorder.getShipTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getShipTime());
                myorderVo.setShipTimeStr(dateString);
            }
            if(myorder.getReceiveTime() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(myorder.getReceiveTime());
                myorderVo.setReceiveTimeStr(dateString);
            }

            if(myorder.getPayType() == 0) myorderVo.setPayTypeStr("支付宝");
            else if(myorder.getPayType() == 1) myorderVo.setPayTypeStr("微信");
            else if(myorder.getPayType() == 2) myorderVo.setPayTypeStr("网银");
            else myorderVo.setPayTypeStr("其他");

            if(myorder.getStatus() == 0) myorderVo.setStatusStr("等待发货");
            else if(myorder.getStatus() == 1) myorderVo.setStatusStr("已发货");
            else if(myorder.getStatus() == 2) myorderVo.setStatusStr("已经收货");

            // add list
            myorderVoList.add(myorderVo);
        }
        return Result.build(200, "有数据", myorderVoList);
    }

    //确认订单,进而进行发货
    @ApiOperation("管理端确认订单,进而进行发货")
    @PostMapping("/confirmShipments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true,
                    dataType = "", paramType = "query"),
            @ApiImplicitParam(name = "logisticsMame", value = "物流名称", required = true,
                    dataType = "", paramType = "query")
    })
    public Result confirmShipments(Integer id,String logisticsMame){
        //1、判断是否为空
        if(logisticsMame == null || "".equals(logisticsMame)){
            logisticsMame = "邮政"; //默认物流
        }
        //发货
        Integer integer = orderService.confirmShipments(id, logisticsMame);

        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }


    //确认订单,进而进行发货
    @ApiOperation("用户确认收货")
    @PostMapping("/confitmReceiveGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true,
                    dataType = "", paramType = "query"),
    })
    public Result confitmReceiveGoods(Integer id){
        //1、判断是否为空
        if(id == null || "".equals(id)) return Result.build(400,"数据不可以为空");
        //确定收货
        Integer integer = orderService.confitmReceiveGoods(id);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

    //若完成后可以选择删除某一个订单
    @ApiOperation("若完成后可以选择删除某一个订单")
    @PostMapping("/delectOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true,
                    dataType = "", paramType = "query"),
    })
    public Result delectOrder(Integer id){
        //1、判断是否为空
        if(id == null || "".equals(id)) return Result.build(400,"数据不可以为空");
        //确定删除
        Integer integer = orderService.delectOrder(id);
        if (integer > 0) {
            return Result.ok();
        }else {
            return Result.build(500, "系统错误！！！");
        }
    }

}
