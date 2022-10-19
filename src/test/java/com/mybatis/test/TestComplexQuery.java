package com.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.mapper.UserMapper;
import com.zhuyizhuo.java.mybatis.resultmap.One2ManyResultMap;
import com.zhuyizhuo.java.mybatis.resultmap.UserResultMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestComplexQuery {

    private SqlSession sqlSession;
    private UserMapper testMapper;

    {
        try {
            sqlSession = getSqlSession();
            testMapper = sqlSession.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询
     * 物理分页
     */
    @Test
    public void queryPageing() {
        List<Order> list = sqlSession.selectList("com.zhuyizhuo.java.mybatis.mapper.OrderMapper.selectOrder",
                "1", new RowBounds(0, 1));
        System.out.println("selectList:" + list.size());
    }

    /**
     *  嵌套查询
     */
    @Test
    public void testUnionQuery() {
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
     *  嵌套查询 N+1问题
     *  查询出来的是个集合
     *  对于集合的每个关联字段 再次查询导致N+1问题
     *  官方不建议使用嵌套查询
     */
    @Test
    public void selectUserOrderLists() {
        List<UserResultMap> userList = testMapper.selectUserOrderLists();
        System.out.println("testUnionQuery userResultMap : " + userList);
        if (userList != null) {
            System.out.println("testUnionQuery userName : " + userList.size());
        }
    }

    /**
     *  嵌套查询1:N
     */
    @Test
    public void selectOne2Many() {
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
    @Test
    public void testUnionQueryResult() {
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
    @Test
    public void selectUser2OrderListResult() {
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
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = sdf.format(gmtCreate);
                        System.out.println("orderId:" + order.getId() + " -- time:" + format);
                    }
                }
            }
        }
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
}
