package com.zhuyizhuo.java.mybatis.v2.binding;

import com.zhuyizhuo.java.mybatis.v2.session.MySqlSession;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:43
 */
public class MyMapperRegistry {

    public static final Map<String,Map<String,String>> nameSpaceMap = new ConcurrentHashMap<>();

    static {
        bind();
    }

    public <T> T getMapper(Class<T> clazz, MySqlSession mySqlSession) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(mySqlSession));
    }

    private static void bind(){
        Map<String,String> sqlMap = new HashMap<>();
        sqlMap.put("selectUser"," select t.id,t.name,t.age from simple_mybatis_user t where t.id = #{id}");
        nameSpaceMap.put("com.zhuyizhuo.java.mybatis.v2.mapper.TestMapper",sqlMap);
    }
}
