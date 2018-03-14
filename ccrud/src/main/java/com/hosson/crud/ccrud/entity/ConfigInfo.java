package com.hosson.crud.ccrud.entity;

/**
 * Created by hosson on 08/03/2018.
 */

public class ConfigInfo {
    /**
     * 存储类型
     */
    private StorageType storageType;

    /**
     * 存储的文件名，如果是数据库，则为数据库名
     */
    private String fileName;

    /**
     * 存储的路径
     */
    private String path;

    /**
     * 版本号
     */
    private int version = 1;

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
