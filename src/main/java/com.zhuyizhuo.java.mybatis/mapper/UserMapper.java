package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface UserMapper {

    List<UserBean> simpleQuery(UserBean userBean);

    List<UserBean> queryResultUseTypeHandler(UserBean userBean);

    int simpleInsert(UserBean ub);

    int insertUseGeneratedKeys(UserBean ub);

    int insertUseTypeHandler(UserBean ub);

    int simpleUpdate(UserBean userBean);

    /**
     * 关联的嵌套查询
     * @param userId
     * @return
     */
    UserResultMap selectUserOrders(@Param("id") int userId);

    /**
     * 关联的嵌套结果
     * @param userId
     * @return
     */
    UserResultMap selectUserOrderResult(@Param("id") int userId);
}
