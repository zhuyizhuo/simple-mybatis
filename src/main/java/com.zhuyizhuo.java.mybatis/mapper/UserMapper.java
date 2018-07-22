package com.zhuyizhuo.java.mybatis.mapper;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.resultmap.One2ManyResultMap;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public interface UserMapper {

    /**
     * 简单的查询
     * @param userBean
     * @return
     */
    List<UserBean> simpleQuery(UserBean userBean);

    /**
     * 查询结果使用TypeHandler处理
     * @param userBean
     * @return
     */
    List<UserBean> queryResultUseTypeHandler(UserBean userBean);

    /**
     * 简单的新增
     * @param ub
     * @return
     */
    int simpleInsert(UserBean ub);

    /**
     * UseGeneratedKeys =true 获取插入信息的ID
     * @param ub
     * @return
     */
    int insertUseGeneratedKeys(UserBean ub);

    /**
     * 使用TypeHandler插入
     * @param ub
     * @return
     */
    int insertUseTypeHandler(UserBean ub);

    /**
     * 简单的更新
     * @param userBean
     * @return
     */
    int simpleUpdate(UserBean userBean);

    /**
     * 关联的嵌套查询1:1
     * @param userId
     * @return
     */
    UserResultMap selectUserOrders(@Param("id") int userId);

    /**
     * 关联的嵌套查询1:N
     * @param userId
     * @return
     */
    One2ManyResultMap selectOne2Many(@Param("id") int userId);

    /**
     * 关联的嵌套结果1:1
     * @param userId
     * @return
     */
    UserResultMap selectUserOrderResult(@Param("id") int userId);

    /**
     * 关联的嵌套结果1:N
     * @param userId
     * @return
     */
    One2ManyResultMap selectUser2OrderListResult(@Param("id") int userId);
}
