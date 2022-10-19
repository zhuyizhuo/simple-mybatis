package com.zhuyizhuo.java.mybatis.enums;

/**
 * Created by yizhuo on 2018/7/25.
 */
public enum MybatisEnums2 {
    /** 表名 测试xml引用枚举*/
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
