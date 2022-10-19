package com.zhuyizhuo.java.mybatis.v1;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 21:11
 */
public interface Executor {

    <T> T query(String statement, Object parameters);

}
