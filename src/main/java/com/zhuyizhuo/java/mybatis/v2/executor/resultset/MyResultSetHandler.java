package com.zhuyizhuo.java.mybatis.v2.executor.resultset;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yizhuo
 * @version 1.0
 * @date 2018/8/9 22:26
 */
public class MyResultSetHandler {

    public <E> List<E> handleResultSets(PreparedStatement preparedStatement) {

        return new ArrayList<>();
    }
}
