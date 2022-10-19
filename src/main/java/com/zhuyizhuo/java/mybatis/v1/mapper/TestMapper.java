package com.zhuyizhuo.java.mybatis.v1.mapper;

import com.zhuyizhuo.java.mybatis.v1.pojo.TestResult;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 20:58
 */
public interface TestMapper {

    TestResult selectUser(Integer id);
}
