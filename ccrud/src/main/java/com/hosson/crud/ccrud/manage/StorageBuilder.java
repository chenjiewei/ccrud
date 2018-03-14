package com.hosson.crud.ccrud.manage;

import com.hosson.crud.ccrud.entity.ConfigInfo;
import com.hosson.crud.ccrud.entity.StorageType;

/**
 * Created by hosson on 08/03/2018.
 */

public class StorageBuilder {
    private ConfigInfo mConfigInfo = null;

    public StorageBuilder() {
        mConfigInfo = new ConfigInfo();
    }

    public StorageBuilder setStorageType(StorageType storageType) {
        mConfigInfo.setStorageType(storageType);
        return this;
    }

    public StorageBuilder setFileName(String fileName) {
        mConfigInfo.setFileName(fileName);
        return this;
    }

    public StorageBuilder setFilePath(String path) {
        mConfigInfo.setPath(path);
        return this;
    }

    public StorageBuilder setVersion(int version) {
        mConfigInfo.setVersion(version);
        return this;
    }

    public void complete() {
        //  生成数据结构
        CcrudManage.getInstance().buildSpCommit(mConfigInfo);
    }
}
