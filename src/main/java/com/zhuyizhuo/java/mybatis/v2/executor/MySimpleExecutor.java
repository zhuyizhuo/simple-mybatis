package com.zhuyizhuo.java.mybatis.v2.executor;

import com.zhuyizhuo.java.mybatis.v2.executor.statement.MyStatementHandler;

import java.sql.SQLException;
import java.sql.Statement;
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
