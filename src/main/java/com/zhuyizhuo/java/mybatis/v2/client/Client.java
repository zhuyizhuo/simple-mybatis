package com.zhuyizhuo.java.mybatis.v2.client;

import com.zhuyizhuo.java.mybatis.v2.executor.MySimpleExecutor;
import com.zhuyizhuo.java.mybatis.v2.mapper.TestMapper;
import com.zhuyizhuo.java.mybatis.v2.mapper.UserBean;
import com.zhuyizhuo.java.mybatis.v2.session.MyConfiguration;
import com.zhuyizhuo.java.mybatis.v2.session.MySqlSession;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:28
 */
public class Client {

    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession(new MyConfiguration(),new MySimpleExecutor());

        TestMapper mapper = mySqlSession.getMapper(TestMapper.class);

        UserBean ub = new UserBean();
        UserBean userBean = mapper.selectUser(ub);
        System.out.println(userBean);
    }
}
