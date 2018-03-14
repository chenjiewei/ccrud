package com.hosson.crud.ccrud.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hosson on 11/03/2018.
 */

public class EntityTypePool {
    //  数据结构内容，每个属性后面加上0~9的数字，这样，每个数字代表一种数据类型，，这个东西是表私有的那么久建一张表的生活从表中获取

    private Map<String, Map<String, String>> entityTypeMap = new HashMap<>();

    // 约定。

    /**
     * int  0;
     * String 1 ;
     * Date 2;
     * double 3;
     * short 4 ;
     * boolean 5;
     * Boolean 6;
     * Integer 7;
     * Double 8;
     * Short 9;
     * float 10;
     * Float 11;
     */

    public void addEntityTypeMap(String key, Map<String, String> map) {
        entityTypeMap.put(key, map);
    }

    public Map<String, String> getEntityTypeMap(String key) {
        return entityTypeMap.get(key);
    }


    private Map<String, SqlSaveKeyEntity> sqlMap = new HashMap<>();

    public void addSqlSaveKeyEntity(String key, SqlSaveKeyEntity sqlSaveKeyEntity) {
        sqlMap.put(key, sqlSaveKeyEntity);
    }

    public SqlSaveKeyEntity getSqlSaveKeyEntity(String key) {
        return sqlMap.get(key);
    }


    public void setSqlMap(Map<String, SqlSaveKeyEntity> sqlMap) {
        this.sqlMap = sqlMap;
    }
}
