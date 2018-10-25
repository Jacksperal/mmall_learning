package com.mmall.pojo;

import java.util.Date;

public class Category {
    private Integer id;

    private Integer parenty;

    private String categoryName;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    public Category(Integer id, Integer parenty, String categoryName, Boolean status, Integer sortOrder, Date createTime, Date updateTime) {
        this.id = id;
        this.parenty = parenty;
        this.categoryName = categoryName;
        this.status = status;
        this.sortOrder = sortOrder;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Category() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParenty() {
        return parenty;
    }

    public void setParenty(Integer parenty) {
        this.parenty = parenty;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}