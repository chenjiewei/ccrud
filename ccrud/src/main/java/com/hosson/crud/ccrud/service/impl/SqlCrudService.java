package com.hosson.crud.ccrud.service.impl;

import android.content.Context;

import com.hosson.crud.ccrud.entity.SpType;
import com.hosson.crud.ccrud.manage.CcrudManage;
import com.hosson.crud.ccrud.manage.sql.SqlManager;
import com.hosson.crud.ccrud.service.CrudService;

/**
 * Created by hosson on 08/03/2018.
 */

public class SqlCrudService implements CrudService {

    private CrudService spCrudService;
    private SqlManager mSqlManager;

    public SqlCrudService(Context context, String dbName, int dbVersion) {
        mSqlManager = new SqlManager(context, dbName, dbVersion);
        spCrudService = CcrudManage.getInstance().getCrudService(SpType.SQL_SP);
    }

    @Override
    public void save(String key, String value) {
        // 采用sp
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, int value) {
        // 采用sp
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, long value) {
        // 采用sp
        spCrudService.save(key, value);
    }

    @Override
    public void save(String key, float value) {
        // 采用sp
        spCrudService.save(key, value);
    }

    @Override
    public <T> void save(String key, T t) {
        mSqlManager.save(key, t);
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
        return mSqlManager.get(key, tClassEntity,mClassEntity);
    }
}
