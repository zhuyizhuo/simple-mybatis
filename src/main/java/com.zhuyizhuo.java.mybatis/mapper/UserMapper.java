package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.UserBean;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface UserMapper {

    List<UserBean> selectByExample();

    int testInsert(UserBean ub);
}
