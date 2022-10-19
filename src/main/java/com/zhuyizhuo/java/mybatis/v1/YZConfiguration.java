package com.zhuyizhuo.java.mybatis.v1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 20:32
 */
public class YZConfiguration {


    public static class mapping{
        public static final String NAME_SPACE = "com.zhuyizhuo.java.mybatis.v1.mapper.TestMapper";

        public static Map<String,String> methodMap = new ConcurrentHashMap();

        static{
            methodMap.put("selectUser","select * from simple_mybatis_user where id = %s");
        }
    }
}
