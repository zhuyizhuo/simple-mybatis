package com.zhuyizhuo.java.mybatis.v2.executor.statement;

import com.zhuyizhuo.java.mybatis.v2.executor.resultset.MyResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/9 22:03
 */
public class MyStatementHandler {

    private String statement;
    private Object parameter;
    private String sql;
    protected final MyResultSetHandler resultSetHandler;

    public MyStatementHandler(String statement, Object parameter) {
        this.statement = statement;
        this.parameter = parameter;
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", getHandler(parameter));
        this.sql = genericTokenParser.parse(statement);
        resultSetHandler = new MyResultSetHandler();
    }

    private TokenHandler getHandler(final Object parameter) {
        return new TokenHandler() {
            @Override
            public String handleToken(String content) {
                try {
                    System.out.println("MySimpleExecutor.getHandler:" + content);
                    Method method = parameter.getClass().getMethod("get"+firstUpper(content), new Class[]{});
                    Object invoke = method.invoke(parameter, new Object[]{});
                    return String.valueOf(invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "{0}";
            }
        };
    }

    private String firstUpper(String content) {
        String substring = content.substring(0, 1);
        String substring1 = content.substring(1);
        return substring.toUpperCase() + substring1;
    }

    public PreparedStatement prepareStatement() {
        Connection conn = getConnection();
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/yizhuo?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
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

    public <E> List<E> query(Statement stmt) {
        PreparedStatement preparedStatement = (PreparedStatement)stmt;
        try {
            preparedStatement.execute();
            return resultSetHandler.handleResultSets(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
