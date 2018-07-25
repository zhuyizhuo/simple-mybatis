package com.zhuyizhuo.java.mybatis.enums;

/**
 * Created by yizhuo on 2018/7/25.
 */
public enum MybatisEnums2 {
    SIMPLE_MYBATIS_USER("SIMPLE_MYBATIS_USER")
    ;
    private String tableName;

    MybatisEnums2(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
