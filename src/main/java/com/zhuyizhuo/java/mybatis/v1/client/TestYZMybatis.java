package com.zhuyizhuo.java.mybatis.v1.client;

import com.zhuyizhuo.java.mybatis.v1.SimpleExecutor;
import com.zhuyizhuo.java.mybatis.v1.YZConfiguration;
import com.zhuyizhuo.java.mybatis.v1.YZSqlSession;
import com.zhuyizhuo.java.mybatis.v1.mapper.TestMapper;
import com.zhuyizhuo.java.mybatis.v1.pojo.TestResult;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 20:35
 */
public class TestYZMybatis {

    public static void main(String[] args) {
        YZSqlSession yzSqlSession = new YZSqlSession(new YZConfiguration(), new SimpleExecutor());
        TestMapper mapper = yzSqlSession.getMapper(TestMapper.class);
        TestResult testResult = mapper.selectUser(1);
        System.out.println(testResult.getName());
    }
}
