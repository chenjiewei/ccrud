package com.hosson.crud.ccrud.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hosson on 12/03/2018.
 */

public class FileSaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String filePath;

    // 存储字典的文件名
    private String fileName;
    // 存储的是文件路径，文件名和相应的key值
    private Map<String, String> fileSaveEntityMap = new HashMap<>();

    public FileSaveEntity(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public void addEntityTypeMap(String key, String pathAndName) {
        fileSaveEntityMap.put(key, pathAndName);
    }

    public String getEntityTypeMap(String key) {
        return fileSaveEntityMap.get(key);
    }


    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileSaveEntityMap(Map<String, String> map) {
        fileSaveEntityMap = map;
    }

    public Map<String, String> getFileSaveEntityMap() {
        return fileSaveEntityMap;
    }
}
