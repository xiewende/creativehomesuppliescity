package com.xiewende.creativehomesuppliescity.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "风格信息")
public class Style {

    @ApiModelProperty(value = "id（前端不需要输入）")
    private Integer id;

    @ApiModelProperty(value = "风格名称")
    private String typeName;

    @ApiModelProperty(value = "风格描述")
    private String description;

    @ApiModelProperty(value = "删除标记，前端不用理会")
    private Integer isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}