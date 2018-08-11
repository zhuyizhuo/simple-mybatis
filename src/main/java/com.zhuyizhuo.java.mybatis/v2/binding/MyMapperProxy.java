package com.zhuyizhuo.java.mybatis.v2.binding;

import com.zhuyizhuo.java.mybatis.v2.session.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:33
 */
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;

    public MyMapperProxy(MySqlSession mySqlSession) {
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String nameSpace = method.getDeclaringClass().getName();
        if (MyMapperRegistry.nameSpaceMap.containsKey(nameSpace)
                && MyMapperRegistry.nameSpaceMap.get(nameSpace).containsKey(method.getName())){
            String sql = MyMapperRegistry.nameSpaceMap.get(nameSpace).get(method.getName());
            //简单版 支持单条和多条查询 如需支持删改查 则需判断
/**        源码是通过解析xml知道类型
 *  SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));*/
            Class<?> returnType = method.getReturnType();
            Object realArg = args[0];
            if(Collection.class.isAssignableFrom(returnType) || returnType.isArray()){
                return mySqlSession.selectList(sql,realArg);
            } else {
                return mySqlSession.selectOne(sql, realArg);
            }
        }
        return method.invoke(proxy,args);
    }
}
