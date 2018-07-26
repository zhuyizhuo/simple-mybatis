package com.zhuyizhuo.java.mybatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 20:34
 */
public class YZMapperProxy implements InvocationHandler {

    private YZSqlSession sqlSession;

    public YZMapperProxy( YZSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getDeclaringClass().getName();
        if(YZConfiguration.mapping.nameSpace.equals(name)){
            String s = YZConfiguration.mapping.methodMap.get(method.getName());
            return sqlSession.selectOne(s,args[0]);
        }
        return method.invoke(args);
    }

}
