package com.zhuyizhuo.java.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import com.zhuyizhuo.java.mybatis.resultmap.One2ManyResultMap;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yizhuo on 2018/7/10.
 */
public class TestComplexQuery {

    /**
     *  绝对路径加载配置文件
     */
    public static SqlSession getSqlSessionByAbsolutePath() throws Exception {
        //配置文件
        InputStream configFile = new FileInputStream(
                "E:\\github\\simple-mybatis\\src\\main\\resources\\mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        //加载配置文件得到SqlSessionFactory
        return sqlSessionFactory.openSession();
    }

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
            SqlSession sqlSession = getSqlSession();

            UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

//            queryPageing(sqlSession);

//            testUnionQuery(sqlSession,testMapper);

//            testUnionQueryResult(sqlSession,testMapper);

//            selectOne2Many(sqlSession,testMapper);

            selectUser2OrderListResult(sqlSession,testMapper);
            sqlSession.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 分页查询
     * @param sqlSession
     */
    private static void queryPageing(SqlSession sqlSession) {
        List<Order> list = sqlSession.selectList("com.zhuyizhuo.java.mybatis.mapper.OrderMapper.selectOrder",
                null, new RowBounds(0, 1));
        System.out.println("selectList:" + list.size());
    }

    /**
     *  嵌套查询
     */
    private static void testUnionQuery(SqlSession sqlSession, UserMapper testMapper) {
        UserResultMap userResultMap = testMapper.selectUserOrders(2);
        System.out.println("testUnionQuery userResultMap : " + userResultMap);
        if (userResultMap != null) {
            System.out.println("testUnionQuery userName : " + userResultMap.getName());
            if (userResultMap.getOrder() != null) {
                System.out.println("testUnionQuery orderId : " + userResultMap.getOrder().getId());
            }
        }
    }

    /**
     *  嵌套查询1:N
     */
    private static void selectOne2Many(SqlSession sqlSession, UserMapper testMapper) {
        One2ManyResultMap userResultMap = testMapper.selectOne2Many(1);
        System.out.println("selectOne2Many userResultMap: " + userResultMap);
        if (userResultMap != null) {
            System.out.println("selectOne2Many userName : " + userResultMap.getName());
            if (userResultMap.getOrder() != null) {
                System.out.println("selectOne2Many orderSize : " + userResultMap.getOrder().size());
            }
        }
    }

    /**
     * 嵌套结果
     */
    private static void testUnionQueryResult(SqlSession sqlSession, UserMapper testMapper) {
        UserResultMap userResultMap = testMapper.selectUserOrderResult(1);
        System.out.println("testUnionQueryResult userResultMap : " + userResultMap);
        if (userResultMap != null) {
            System.out.println("testUnionQueryResult name : " + userResultMap.getName());
            if (userResultMap.getOrder() != null) {
                System.out.println("testUnionQueryResult orderId : " + userResultMap.getOrder().getId());
            }
        }
    }

    /**
     * 嵌套结果1;N
     */
    private static void selectUser2OrderListResult(SqlSession sqlSession, UserMapper testMapper) {
        One2ManyResultMap userResultMap = testMapper.selectUser2OrderListResult(1);
        System.out.println("selectUser2OrderListResult userResultMap : " + userResultMap);
        if (userResultMap != null) {
            System.out.println("selectUser2OrderListResult name : " + userResultMap.getName());
            if (userResultMap.getOrder() != null) {
                System.out.println("selectUser2OrderListResult orderSize : " + userResultMap.getOrder().size());
                for (int i = 0; i < userResultMap.getOrder().size(); i++) {
                    Order order = userResultMap.getOrder().get(i);
                    Date gmtCreate = order.getGmtCreate();
                    if (gmtCreate != null) {
                        //TODO 格式化时间和数据库时间不一致?
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = sdf.format(gmtCreate);
                        System.out.println("orderId:" + order.getId() + " -- time:" + format);
                    }
                }
            }
        }
    }
}
