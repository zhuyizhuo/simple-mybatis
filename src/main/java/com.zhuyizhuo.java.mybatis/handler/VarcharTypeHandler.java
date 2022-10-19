package com.zhuyizhuo.java.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * 自定义TypeHandler 重写了VARCHAR类型的TypeHandler
 * Created by yizhuo on 2018/7/10.
 */
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class VarcharTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter + new Random().nextInt());
        System.out.println("VarcharTypeHandler is use!");
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String string = rs.getString(columnName);
        string += " query-with-typeHandler";
        return string;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getString(columnIndex);
    }
}
