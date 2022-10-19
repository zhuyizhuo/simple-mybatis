package com.zhuyizhuo.java.mybatis.v1;

import com.zhuyizhuo.java.mybatis.constant.MybatisConstants;
import com.zhuyizhuo.java.mybatis.v1.pojo.TestResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/7/26 21:12
 */
public class SimpleExecutor implements Executor{

    @Override
    public <T> T query(String statement, Object parameters) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(String.format(statement, Integer.parseInt(String.valueOf(parameters))));
            ResultSet rs = pstmt.executeQuery();
            TestResult test = new TestResult();
            while (rs.next()) {
                test.setId(rs.getInt(1));
                test.setName(rs.getString(2));
            }
            return (T) test;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String driver = MybatisConstants.JDBC_DRIVER;
        String url = MybatisConstants.URL;
        String username = MybatisConstants.USERNAME;
        String password = MybatisConstants.PASSWORD;
        Connection conn = null;
        try {
            //classLoader,加载对应驱动
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
