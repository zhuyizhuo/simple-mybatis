package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.bean.UserBean;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface OrderMapper {

    List<Order> selectByExample(Order order);

    int testInsert(Order order);

    int update(Order order);

    Order selectOrder(String orderNo);
}
