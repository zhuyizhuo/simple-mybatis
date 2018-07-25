package com.zhuyizhuo.java.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yizhuo on 2018/7/21.
 */
public class TestSimpleQuery {

    /**
     *  相对路径加载配置文件
     */
    public static SqlSession getSqlSession() throws Exception {
        //配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        LogFactory.useSlf4jLogging();
        LogFactory.useStdOutLogging();
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws Exception {
        try {
            testSimpleQuery();

//            testQueryCache();

//            queryResultUseTypeHandler();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 测试简单查询
     * @throws Exception
     */
    private static void testSimpleQuery() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);
        UserBean userBean = new UserBean();
        userBean.setId(1);
        List<UserBean> userBeans = testMapper.simpleQuery(userBean);
        System.out.println("testSimpleQuery : " + userBeans);
        if (userBeans != null){
            System.out.println("testSimpleQuery : " + userBeans.get(0).getName());
        }

        if (sqlSession != null){
            sqlSession.close();
        }
    }

    /**
     *   同一个sqlsession连续两次查询 第二次会命中mybatis一级缓存
     */
    private static void testQueryCache() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setId(1);
        List<UserBean> userBeans = testMapper.simpleQuery(userBean);
        System.out.println("testQuery : " + userBeans.get(0).getName());

        /**
         *  在此处断点 手动修改数据库的值  验证第二次查询命中一级缓存
         *  一级缓存默认打开
         *  如果是此处程序更新数据库 则缓存也会被更新 可打开以下代码验证
         */
//        UserBean test = new UserBean();
////        test.setId(1);
////        test.setName(new Random().nextInt(20)+"");
////        int update = testMapper.simpleUpdate(test);
////        System.out.println("update count : " + update);
////        sqlSession.commit();

        List<UserBean> cacheResult = testMapper.simpleQuery(userBean);
        System.out.println("testQuery : " + cacheResult.get(0).getName());

        if (sqlSession != null){
            sqlSession.close();
        }
    }

    /**
     *   测试typeHandler 查询
     */
    private static void queryResultUseTypeHandler() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);
        UserBean userBean = new UserBean();
        userBean.setId(1);
        List<UserBean> userBeans = testMapper.queryResultUseTypeHandler(userBean);
        System.out.println("testQuery : " + userBeans.get(0).getName());
        if (sqlSession != null){
            sqlSession.close();
        }
    }

}
