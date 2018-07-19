package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface UserMapper {

    List<UserBean> selectByExample(UserBean userBean);

    int testInsert(UserBean ub);

    int update(UserBean userBean);

    UserResultMap selectUserOrders(@Param("id") int userId);
}
