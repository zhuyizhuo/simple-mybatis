package com.zhuyizhuo.java.mybatis.plugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by yizhuo on 2018/7/12.
 */
@Intercepts({@Signature(
        type= StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class})})
public class StatementHandlerPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("----StatementHandlerPlugin start----");
        return invocation.proceed();
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    @Override
    public void setProperties(Properties properties) {

    }
}
