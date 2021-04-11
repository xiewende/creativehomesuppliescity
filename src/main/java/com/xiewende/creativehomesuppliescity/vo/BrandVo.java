package com.xiewende.creativehomesuppliescity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @create 2021-04-11  21:27
 */
@ApiModel(value = "BrandVo")
public class BrandVo {

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌描述")
    private String description;

    @ApiModelProperty(value = "设计师名字，需要选择查询设计师选取一个即可")
    private String designerName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
}
