package com.hosson.crud.ccrud.service.impl;

import android.content.Context;

import com.hosson.crud.ccrud.manage.sharepreference.SpManager;
import com.hosson.crud.ccrud.service.CrudService;
import com.hosson.crud.ccrud.utils.CheckNull;

/**
 * Created by hosson on 08/03/2018.
 */

public class SpCrudService implements CrudService {

    private SpManager manager;

    public SpCrudService(Context context, String fileName) {
        manager = new SpManager(context, fileName);
    }

    @Override
    public void save(String key, String value) {
        CheckNull.checkNull(manager);
        manager.saveToPreference(key, value);
    }

    @Override
    public void save(String key, int value) {
        CheckNull.checkNull(manager);
        manager.saveToPreference(key, value);
    }

    @Override
    public void save(String key, long value) {
        CheckNull.checkNull(manager);
        manager.saveToPreference(key, value);
    }

    @Override
    public void save(String key, float value) {
        CheckNull.checkNull(manager);
        manager.saveToPreference(key, value);
    }

    @Override
    public <T> void save(String key, T value) {
        CheckNull.checkNull(manager);
        manager.saveToPreference(key, value);
    }

    @Override
    public String get(String key, String value) {
        CheckNull.checkNull(manager);
        return manager.gainStringValue(key, value);
    }

    @Override
    public int get(String key, int value) {
        CheckNull.checkNull(manager);
        return manager.gainIntValue(key, value);
    }

    @Override
    public long get(String key, long value) {
        CheckNull.checkNull(manager);
        return manager.gainLongValue(key, value);
    }

    @Override
    public float get(String key, float value) {
        CheckNull.checkNull(manager);
        return manager.gainFloatValue(key, value);
    }

    @Override
    public boolean get(String key, boolean value) {
        CheckNull.checkNull(manager);
        return manager.gainBooleanValue(key, value);
    }

    @Override
    public <T, M> T get(String key, Class<T> tClassEntity, Class<M> mClassEntity) {
        CheckNull.checkNull(manager);
        return manager.gainTValue(key, tClassEntity);
    }

}
