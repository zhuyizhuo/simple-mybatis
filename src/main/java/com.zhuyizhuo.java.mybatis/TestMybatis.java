package com.zhuyizhuo.java.mybatis;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.bean.UserBean;
import com.zhuyizhuo.java.mybatis.bean.UserOrder;
import com.zhuyizhuo.java.mybatis.mapper.OrderMapper;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
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

    private static SqlSession sqlSession = null;
    //绝对路径加载配置文件
    public static SqlSession getSqlSession1() throws Exception {
        //配置文件
        InputStream configFile = new FileInputStream(
                "E:\\github\\simple-mybatis\\src\\main\\resources\\mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        //加载配置文件得到SqlSessionFactory
        return sqlSessionFactory.openSession();
    }

    //相对路径加载配置文件
    public static SqlSession getSqlSession() throws Exception {
        //配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) throws Exception {
        try {
            sqlSession = getSqlSession();

            UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

            testQuery(testMapper);

            testInsert(testMapper);

            testUpdate(testMapper);

            testUnionQuery(testMapper);

            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

            testQueryOrder(orderMapper);

            sqlSession.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testQueryOrder(OrderMapper testMapper) {
        Order order = testMapper.selectOrder("2order_no");
        System.out.println(order);
    }

    //关联查询
    private static void testUnionQuery(UserMapper testMapper) {
        UserBean userBean = new UserBean();
        userBean.setId(1);
        UserResultMap userResultMap = testMapper.selectUserOrders(1);
        System.out.println("testUnionQuery : " + userResultMap.getName());
        System.out.println("testUnionQuery : " + userResultMap.getOrder().getId());
    }

    //test plugin
    private static void testUpdate(UserMapper testMapper) {
        UserBean test = new UserBean();
        test.setId(1);
        test.setName("cccc");
        int update = testMapper.update(test);
        System.out.println(update);
        sqlSession.commit();
    }

    //测试typeHandler插入
    //test plugin
    private static void testInsert(UserMapper testMapper) {
        UserBean userBean = new UserBean();
        userBean.setName("33-");
        int i = testMapper.testInsert(userBean);
        System.out.println("done.." + i);

        sqlSession.commit();
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
