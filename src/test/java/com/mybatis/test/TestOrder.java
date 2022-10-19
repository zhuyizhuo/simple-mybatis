package com.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.mapper.OrderMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * 测试order简单查询
 * create date:  2018/7/15.
 * @since 1.0
 * @version 1.0
 * @author  yizhuo
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
            OrderMapper testMapper = sqlSession.getMapper(OrderMapper.class);
            Order order = testMapper.selectOrder("1");
            System.out.println(order.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
