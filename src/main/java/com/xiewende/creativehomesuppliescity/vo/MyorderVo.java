package com.xiewende.creativehomesuppliescity.vo;

import com.xiewende.creativehomesuppliescity.pojo.OrderGoods;

import java.util.Date;
import java.util.List;

/**
 * @create 2021-04-17  19:27
 */
public class MyorderVo {

    private Integer id;

    private String orderNumber;

    private Integer orderNum;

    //
    private String userName;

    private String createTimeStr;

    private String updateTimeStr;

    private String shipTimeStr;

    private String receiveTimeStr;

    private String payTypeStr;
    //

    private Double payPrice;

    private String logisticsName;

    private String lgisticsNumble;

    private String statusStr; //

    private String address;

    private String iphone;

    private String receiveName;

    private Integer status;

    private List<OrderGoods> orderGoodsList;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getShipTimeStr() {
        return shipTimeStr;
    }

    public void setShipTimeStr(String shipTimeStr) {
        this.shipTimeStr = shipTimeStr;
    }

    public String getReceiveTimeStr() {
        return receiveTimeStr;
    }

    public void setReceiveTimeStr(String receiveTimeStr) {
        this.receiveTimeStr = receiveTimeStr;
    }

    public String getPayTypeStr() {
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLgisticsNumble() {
        return lgisticsNumble;
    }

    public void setLgisticsNumble(String lgisticsNumble) {
        this.lgisticsNumble = lgisticsNumble;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
}
