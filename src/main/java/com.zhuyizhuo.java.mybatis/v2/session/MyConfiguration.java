package com.zhuyizhuo.java.mybatis.v2.session;

import com.zhuyizhuo.java.mybatis.v2.binding.MyMapperProxy;
import com.zhuyizhuo.java.mybatis.v2.binding.MyMapperRegistry;
import org.apache.ibatis.binding.MapperRegistry;

import java.lang.reflect.Proxy;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:38
 */
public class MyConfiguration {

    protected final MyMapperRegistry mapperRegistry = new MyMapperRegistry();

    public <T> T getMapper(Class<T> clazz, MySqlSession mySqlSession) {
        return mapperRegistry.getMapper(clazz,mySqlSession);
    }
}
