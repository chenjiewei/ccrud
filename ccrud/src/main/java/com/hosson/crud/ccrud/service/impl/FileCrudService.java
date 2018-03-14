package com.hosson.crud.ccrud.service.impl;

import com.hosson.crud.ccrud.entity.ConfigInfo;
import com.hosson.crud.ccrud.entity.SpType;
import com.hosson.crud.ccrud.manage.CcrudManage;
import com.hosson.crud.ccrud.manage.filecrud.FileManager;
import com.hosson.crud.ccrud.service.CrudService;

/**
 * Created by hosson on 08/03/2018.
 */

public class FileCrudService implements CrudService {

    private FileManager mFileManager;
    private CrudService spCrudService;

    public FileCrudService(ConfigInfo configInfo) {
        mFileManager = new FileManager(configInfo);
        spCrudService = CcrudManage.getInstance().getCrudService(SpType.FILE_SP);
    }

    @Override
    public void save(String key, String value) {
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, int value) {
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, long value) {
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, float value) {
        spCrudService.save(key, value);
    }

    @Override
    public <T> void save(String key, T t) {
        mFileManager.save(key, t);
    }

    @Override
    public String get(String key, String value) {
        return spCrudService.get(key, value);
    }

    @Override
    public int get(String key, int value) {
        return spCrudService.get(key, value);
    }

    @Override
    public long get(String key, long value) {
        return spCrudService.get(key, value);
    }

    @Override
    public float get(String key, float value) {
        return spCrudService.get(key, value);
    }

    @Override
    public boolean get(String key, boolean value) {
        return spCrudService.get(key, value);
    }

    @Override
    public <T, M> T get(String key, Class<T> tClassEntity, Class<M> mClassEntity) {
        return  mFileManager.get(key, tClassEntity);
    }
}
