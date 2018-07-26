package com.zhuyizhuo.java.mybatis.v1;

import java.lang.reflect.Proxy;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 20:20
 */
public class YZSqlSession {

    private YZConfiguration configuration;
    private Executor executor;

    public YZSqlSession(YZConfiguration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz},new YZMapperProxy(this));
    }

    public <T> T selectOne(String statement, Object parameters) {
        return executor.query(statement,parameters);
    }
}
