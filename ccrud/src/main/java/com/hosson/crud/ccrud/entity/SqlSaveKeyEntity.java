package com.hosson.crud.ccrud.entity;

/**
 * Created by hosson on 13/03/2018.
 * 存储数据库所需要的数据对象
 */


public class SqlSaveKeyEntity {
    private String key;  // key +list(or set or map or arrays) + 类的全称名
    private String keyValue;  // 0&1&2&3..... 这样的数据结构
    private String[] keyValueArrays;
    private String prefixStr;//  前缀字符串


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String[] getKeyValueArrays() {
        return keyValueArrays;
    }

    public void setKeyValueArrays(String[] keyValueArrays) {
        this.keyValueArrays = keyValueArrays;
    }

    public String getPrefixStr() {
        return prefixStr;
    }

    public void setPrefixStr(String prefixStr) {
        this.prefixStr = prefixStr;
    }
}
