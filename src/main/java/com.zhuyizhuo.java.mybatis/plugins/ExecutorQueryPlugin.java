package com.zhuyizhuo.java.mybatis.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by yizhuo on 2018/7/12.
 * 这个插件配置的 拦截Executor中的update(MappedStatement,Object)方法
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
//        args = {MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class,CacheKey.class,BoundSql.class}
        args = {MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class}
        )})
public class ExecutorQueryPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("----ExecutorQueryPlugin start----");
        Object[] args = invocation.getArgs();
        Method method = invocation.getMethod();
        System.out.println("method name : " + method.getName());
        System.out.println("args : " + Arrays.toString(args));

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
        System.out.println(String.format("sql = %s ,\n param = %s", boundSql.getSql(),boundSql.getParameterObject()));
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
