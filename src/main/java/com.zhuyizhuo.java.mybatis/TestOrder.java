package com.zhuyizhuo.java.mybatis;

import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by yizhuo on 2018/7/15.
 */
public class TestOrder {

    public static SqlSession getSqlSession() throws Exception {
        //配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws Exception {
        try {
            SqlSession sqlSession = getSqlSession();
            UserMapper testMapper = sqlSession.getMapper(UserMapper.class);
            testMapper.selectUserOrders(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
