package com.xiewende.creativehomesuppliescity.vo;

import com.xiewende.creativehomesuppliescity.pojo.Brand;
import com.xiewende.creativehomesuppliescity.pojo.CategorySecond;
import com.xiewende.creativehomesuppliescity.pojo.Style;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @create 2021-04-13  15:41
 */
@ApiModel(value = "GoodsVo")
public class GoodsVo {

    @ApiModelProperty(value = "家居用品名称")
    private String goodName;

    @ApiModelProperty(value = "家居用品单价")
    private Double price;

    @ApiModelProperty(value = "家居用品尺寸")
    private String size;

    @ApiModelProperty(value = "家居用品描述")
    private String description;

    @ApiModelProperty(value = "家居用品库存")
    private Integer store;

    @ApiModelProperty(value = "家居用品所属二级分类名称，点击查询得到")
    private String categorySecondName;

    @ApiModelProperty(value = "家居用品所属风格名称。点击查询得到")
    private String styleName;

    @ApiModelProperty(value = "家居用品所属品牌名称，点击查询得到")
    private String brandName;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getCategorySecondName() {
        return categorySecondName;
    }

    public void setCategorySecondName(String categorySecondName) {
        this.categorySecondName = categorySecondName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
