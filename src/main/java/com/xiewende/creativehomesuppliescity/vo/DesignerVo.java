package com.xiewende.creativehomesuppliescity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @create 2021-04-10  22:19
 */
@ApiModel(value = "DesignerVo")
public class DesignerVo {

    @ApiModelProperty(value = "设计师名字")
    private String designerName;

    @ApiModelProperty(value = "设计师电话 ")
    private String iphone;

    @ApiModelProperty(value = "设计师性别  男 or 女")
    private String sex;

    @ApiModelProperty(value = "设计师邮箱")
    private String email;

    @ApiModelProperty(value = "设计师职称")
    private String professionalTitle;

    @ApiModelProperty(value = "设计师设计理念")
    private String designConcept;

    @ApiModelProperty(value = "设计师设计风格 从风格那边查询选择一个即可")
    private String type;

    @ApiModelProperty(value = "设计师从业时间")
    private String time;

    @ApiModelProperty(value = "设计师毕业大学")
    private String college;

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public String getDesignConcept() {
        return designConcept;
    }

    public void setDesignConcept(String designConcept) {
        this.designConcept = designConcept;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
