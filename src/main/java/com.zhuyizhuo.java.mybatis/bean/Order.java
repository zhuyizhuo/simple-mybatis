package com.zhuyizhuo.java.mybatis.bean;

import java.util.Date;

/**
 * Created by yizhuo on 2018/7/14.
 */
public class Order {
    private int id;
    private String orderNo;
    private String userId;
    private Date gmtCreate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
