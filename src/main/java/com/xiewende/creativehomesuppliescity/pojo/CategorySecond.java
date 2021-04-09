package com.xiewende.creativehomesuppliescity.pojo;

public class CategorySecond {
    private Integer id;

    private String categoryName;

    private Integer categoryFirstId;

    private Integer isdelete;

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

    public Integer getCategoryFirstId() {
        return categoryFirstId;
    }

    public void setCategoryFirstId(Integer categoryFirstId) {
        this.categoryFirstId = categoryFirstId;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}