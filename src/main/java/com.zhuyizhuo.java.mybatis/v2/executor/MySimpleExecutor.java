package com.zhuyizhuo.java.mybatis.v2.executor;

import com.zhuyizhuo.java.mybatis.v2.executor.statement.MyStatementHandler;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/8 20:26
 */
public class MySimpleExecutor implements MyExecutor {

    @Override
    public <E> List<E> query(String statement, Object parameter) {
        Statement stmt = null;
        try {
            MyStatementHandler handler = new MyStatementHandler(statement,parameter);
            stmt = handler.prepareStatement();
            return handler.query(stmt);
        } finally {
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
