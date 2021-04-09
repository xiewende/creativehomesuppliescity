package com.xiewende.creativehomesuppliescity.pojo;

import java.util.Date;

public class FinnishProgram {
    private Integer id;

    private Integer userId;

    private Integer designerId;

    private String goodName;

    private String size;

    private String otherDemand;

    private String finishPic;

    private String finisnIdea;

    private Date createTime;

    private Date finishTime;

    private Integer isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designerId) {
        this.designerId = designerId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getOtherDemand() {
        return otherDemand;
    }

    public void setOtherDemand(String otherDemand) {
        this.otherDemand = otherDemand == null ? null : otherDemand.trim();
    }

    public String getFinishPic() {
        return finishPic;
    }

    public void setFinishPic(String finishPic) {
        this.finishPic = finishPic == null ? null : finishPic.trim();
    }

    public String getFinisnIdea() {
        return finisnIdea;
    }

    public void setFinisnIdea(String finisnIdea) {
        this.finisnIdea = finisnIdea == null ? null : finisnIdea.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}