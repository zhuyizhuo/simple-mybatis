package com.zhuyizhuo.java.mybatis;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public class TestMybatis {

    public static SqlSession getSqlSession() throws Exception {
        //配置文件
       /* InputStream configFile = new FileInputStream(
                "E:\\github\\simple-mybatis\\src\\main\\resources\\mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        //加载配置文件得到SqlSessionFactory
        return sqlSessionFactory.openSession();*/
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws Exception {
        try {
            SqlSession sqlSession = getSqlSession();
            UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

            testQuery(testMapper);

            testInsert(sqlSession, testMapper);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testInsert(SqlSession sqlSession, UserMapper testMapper) {
        UserBean userBean = new UserBean();
        userBean.setName("33-");
        int i = testMapper.testInsert(userBean);
        System.out.println("done.." + i);

        sqlSession.commit();
        sqlSession.close();
    }

    private static void testQuery(UserMapper testMapper) {
        List<UserBean> userBeans = testMapper.selectByExample();
        System.out.println(userBeans.get(0).getName());
    }
}
