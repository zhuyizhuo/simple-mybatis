package com.zhuyizhuo.java.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author yizhuo
 * 新增和更新会触发自定义插件 因为插件定义的拦截新增更新方法
 */
public class TestInsertUpdate {

    /**
     *  相对路径加载配置文件
     */
    public static SqlSession getSqlSession() throws Exception {
        //配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws Exception {
        try {
//            testInsert();

            insertUseGeneratedKeys();

//            insertUseTypeHandler();

//            testUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 新增 获取不到数据库的自增ID
     * @throws Exception
     */
    private static void testInsert() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setName(System.currentTimeMillis()+"");
        int i = testMapper.simpleInsert(userBean);
        System.out.println("testInsert done.." + i);
        System.out.println(userBean.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * useGeneratedKeys （仅对 insert 和 update 有用） 默认值：false
     * useGeneratedKeys="true"
     * 这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键
     * （比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段）
     * @throws Exception
     */
    private static void insertUseGeneratedKeys() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setName(System.currentTimeMillis()+"");
        int i = testMapper.insertUseGeneratedKeys(userBean);
        System.out.println("insertUseGeneratedKeys done.." + i);
        System.out.println("id:" + userBean.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     *
     * @throws Exception
     */
    private static void insertUseTypeHandler() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setName(System.currentTimeMillis()+"");
        int i = testMapper.insertUseTypeHandler(userBean);
        System.out.println("insertUseTypeHandler done.." + i);
        System.out.println("id:" + userBean.getId());
        System.out.println("name:" + userBean.getName());
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 简单更新
     * @throws Exception
     */
    private static void testUpdate() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);
        UserBean test = new UserBean();
        test.setId(1);
        test.setName("cccc");
        test.setAge("456");
        test.setOrderNo("testUpdate");
        int update = testMapper.simpleUpdate(test);
        System.out.println("update count:" + update);
        sqlSession.commit();
        sqlSession.close();
    }
}
