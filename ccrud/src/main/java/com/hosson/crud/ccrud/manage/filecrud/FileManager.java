package com.hosson.crud.ccrud.manage.filecrud;

import com.hosson.crud.ccrud.entity.ConfigInfo;
import com.hosson.crud.ccrud.entity.FileSaveEntity;
import com.hosson.crud.ccrud.utils.FileHelper;

import java.io.File;

/**
 * Created by hosson on 12/03/2018.
 */

public class FileManager {
    private ConfigInfo mConfigInfo;
    private FileSaveEntity mFileSaveEntity;

    // 设置存储的位置
    public FileManager(ConfigInfo configInfo) {
        mConfigInfo = configInfo;
        mFileSaveEntity = new FileSaveEntity(configInfo.getPath(), configInfo.getFileName());
        FileSaveEntity fileSaveEntity = FileHelper.getObjectFromFile(FileSaveEntity.class, configInfo.getPath() + File.separator + configInfo.getFileName());
        if (fileSaveEntity != null) {
            mFileSaveEntity.setFileSaveEntityMap(fileSaveEntity.getFileSaveEntityMap());
        }
    }

    public <T> void save(String key, T t) {
        // 判断列表文件是否已经存在，没有存在就创建
        if (!FileHelper.fileIsExist(mFileSaveEntity.getFilePath(), mFileSaveEntity.getFileName())) {
            FileHelper.createFile(mFileSaveEntity.getFilePath(), mFileSaveEntity.getFileName());
        }
        if (mFileSaveEntity.getEntityTypeMap(key) == null || !FileHelper.fileIsExist(mFileSaveEntity.getFilePath(), key)) {
            // 创建文件
            FileHelper.createFile(mFileSaveEntity.getFilePath(), key);
            // 将列表存在内存中
            mFileSaveEntity.addEntityTypeMap(key, mFileSaveEntity.getFilePath() + File.separator + key);
        }
        // 根据文件路径获取对象
        FileHelper.saveObjectToFile(t, mFileSaveEntity.getEntityTypeMap(key));
        // 将存储记录到列表中
        FileHelper.saveObjectToFile(mFileSaveEntity, mFileSaveEntity.getFilePath() + File.separator + mFileSaveEntity.getFileName());

    }

    public <T> T get(String key, Class<T> classEntity) {
        //  判断列表文件是否存在，如果不存在，返回null
        if (!FileHelper.fileIsExist(mFileSaveEntity.getFilePath(), mFileSaveEntity.getFileName())) {
            return null;
        }
        // 如果存储数据的文件不存在，返回null
        if (!FileHelper.fileIsExist(mFileSaveEntity.getFilePath(), key)) {
            return null;
        }
        // 如果列表中没有记录，则返回null
        if (mFileSaveEntity.getEntityTypeMap(key) == null) {
            return null;
        }
        return FileHelper.getObjectFromFile(classEntity, mFileSaveEntity.getEntityTypeMap(key));
    }
}
