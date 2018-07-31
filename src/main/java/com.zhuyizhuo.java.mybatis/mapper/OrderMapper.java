package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.bean.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface OrderMapper {

    /**
     * 查询订单
     * @param orderNo
     * @return
     */
    Order selectOrder(@Param("orderNo") String orderNo);
}
