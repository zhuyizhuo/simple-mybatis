package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.bean.UserBean;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface OrderMapper {

    Order selectOrder(String orderNo);
}
