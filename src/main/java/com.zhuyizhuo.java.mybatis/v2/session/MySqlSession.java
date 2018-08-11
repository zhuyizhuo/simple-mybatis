package com.zhuyizhuo.java.mybatis.v2.session;

import com.zhuyizhuo.java.mybatis.v2.binding.MyMapperProxy;
import com.zhuyizhuo.java.mybatis.v2.executor.MyExecutor;
import com.zhuyizhuo.java.mybatis.v2.executor.MySimpleExecutor;
import com.zhuyizhuo.java.mybatis.v2.mapper.TestMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 8:17
 */
public class MySqlSession {

    private MyConfiguration configuration;
    private MyExecutor executor;

    public MySqlSession(MyConfiguration configuration, MySimpleExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }


    public <E> List<E> selectList(String statement, Object parameter) {
        return executor.query(statement,parameter);
    }

    public Object selectOne(String sql, Object args) {
        List<Object> list = this.selectList(sql, args);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }
}
