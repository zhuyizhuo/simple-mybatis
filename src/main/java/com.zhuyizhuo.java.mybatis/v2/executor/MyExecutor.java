package com.zhuyizhuo.java.mybatis.v2.executor;

import java.util.List;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:17
 */
public interface MyExecutor {

    <E> List<E> query(String statement, Object parameter);
}