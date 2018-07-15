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

//            testInsert(sqlSession, testMapper);

//            testUpdate(sqlSession,testMapper);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testUpdate(SqlSession sqlSession,UserMapper testMapper) {
        UserBean test = new UserBean();
        test.setId(1);
        test.setName("cccc");
        int update = testMapper.update(test);
        System.out.println(update);
        sqlSession.commit();
    }

    //测试typeHandler插入
    private static void testInsert(SqlSession sqlSession, UserMapper testMapper) {
        UserBean userBean = new UserBean();
        userBean.setName("33-");
        int i = testMapper.testInsert(userBean);
        System.out.println("done.." + i);

        sqlSession.commit();
        sqlSession.close();
    }

    //同一个sqlsession连续两次查询 第二次会命中mybatis一级缓存
    //测试typeHandler 查询
    private static void testQuery(UserMapper testMapper) {
        UserBean userBean = new UserBean();
        userBean.setId(1);
        List<UserBean> userBeans = testMapper.selectByExample(userBean);
        System.out.println("testQuery : " + userBeans.get(0).getName());
        //在此处断点 修改数据库的值  验证第二次查询命中一级缓存
        UserBean userBean1 = new UserBean();
        userBean1.setId(1);
        List<UserBean> userBeans1 = testMapper.selectByExample(userBean1);
        System.out.println("testQuery : " + userBeans1.get(0).getName());
    }
}
