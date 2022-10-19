package com.mybatis.test;

import com.zhuyizhuo.java.mybatis.bean.Order;
import com.zhuyizhuo.java.mybatis.bean.UserBean;
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
    private UserMapper userMapper;

    {
        try {
            sqlSession = getSqlSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增 获取不到数据库的自增ID
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setName(System.currentTimeMillis()+"");
        int i = testMapper.simpleInsert(userBean);
        System.out.println("testInsert done.." + i);
        System.out.println("user id : " + userBean.getId());
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
    @Test
    public void insertUseGeneratedKeys() throws Exception {
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
    @Test
    public void insertUseTypeHandler() throws Exception {
        SqlSession sqlSession = getSqlSession();
        UserMapper testMapper = sqlSession.getMapper(UserMapper.class);

        UserBean userBean = new UserBean();
        userBean.setName("张三");
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
    @Test
    public void testUpdate() throws Exception {
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
        UserResultMap userResultMap = userMapper.selectUserOrders(2);
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
        List<UserResultMap> userList = userMapper.selectUserOrderLists();
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
        One2ManyResultMap userResultMap = userMapper.selectOne2Many(1);
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
        UserResultMap userResultMap = userMapper.selectUserOrderResult(1);
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
        One2ManyResultMap userResultMap = userMapper.selectUser2OrderListResult(1);
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
