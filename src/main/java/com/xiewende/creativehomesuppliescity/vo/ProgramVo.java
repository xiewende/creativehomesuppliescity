package com.xiewende.creativehomesuppliescity.vo;

import com.xiewende.creativehomesuppliescity.pojo.Designer;
import com.xiewende.creativehomesuppliescity.pojo.User;

import java.util.Date;

/**
 * @create 2021-04-18  19:33
 */
public class ProgramVo {
    private Integer id;

    private Integer userId;

    private Integer designerId;

    private String goodName;

    private String size;

    private String otherDemand;

    private String finishPic;

    private String finisnIdea;

    private Integer status;

    //
    private String statusStr;

    private String createTimeStr;

    private String finishTimeStr;

    private String isAcceptStr;
    //
    private User user;

    private Designer designer;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

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
        this.goodName = goodName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOtherDemand() {
        return otherDemand;
    }

    public void setOtherDemand(String otherDemand) {
        this.otherDemand = otherDemand;
    }

    public String getFinishPic() {
        return finishPic;
    }

    public void setFinishPic(String finishPic) {
        this.finishPic = finishPic;
    }

    public String getFinisnIdea() {
        return finisnIdea;
    }

    public void setFinisnIdea(String finisnIdea) {
        this.finisnIdea = finisnIdea;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getFinishTimeStr() {
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    public String getIsAcceptStr() {
        return isAcceptStr;
    }

    public void setIsAcceptStr(String isAcceptStr) {
        this.isAcceptStr = isAcceptStr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
