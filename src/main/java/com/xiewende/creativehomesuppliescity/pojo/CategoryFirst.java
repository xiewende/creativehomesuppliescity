package com.xiewende.creativehomesuppliescity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.List;

@ApiModel(value = "CategoryFirst")
public class CategoryFirst {

    @ApiModelProperty(value = "id，添加时不用传，修改时需要")
    private Integer id;

    @ApiModelProperty(value = "第一级分类名称")
    private String categoryName;

    @ApiModelProperty(value = "删除标记，不用理会")
    private Integer isdelete;


    @ApiModelProperty(value = "不用理会")
    private List<CategorySecond> categorySeconds;

    public List<CategorySecond> getCategorySeconds() {
        return categorySeconds;
    }

    public void setCategorySeconds(List<CategorySecond> categorySeconds) {
        this.categorySeconds = categorySeconds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}