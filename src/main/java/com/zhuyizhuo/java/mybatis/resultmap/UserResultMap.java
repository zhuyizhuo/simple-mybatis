package com.zhuyizhuo.java.mybatis.resultmap;

import com.zhuyizhuo.java.mybatis.bean.Order;

/**
 * Created by yizhuo on 2018/7/15.
 */
public class UserResultMap {

    private int id;
    private String name;
    private String age;;
    private Order order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
