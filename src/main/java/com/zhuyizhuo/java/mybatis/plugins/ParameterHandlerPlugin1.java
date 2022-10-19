package com.zhuyizhuo.java.mybatis.plugins;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by yizhuo on 2018/7/12.
 */
@Intercepts({@Signature(
        type= ParameterHandler.class,
        method = "setParameters",
        args = PreparedStatement.class)})
public class ParameterHandlerPlugin1 implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("----ParameterHandlerPlugin11111 start----");
        Object[] args = invocation.getArgs();
        Method method = invocation.getMethod();
        System.out.println("method 1111name : " + method.getName());
        System.out.println("args1111 : " + Arrays.toString(args));

        PreparedStatement mappedStatement = (PreparedStatement) invocation.getArgs()[0];

        System.out.println("----ParameterHandlerPlugin invocation.proceed() start----");
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
