package com.hosson.crud.ccrud.manage.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hosson.crud.ccrud.utils.CheckNull;

/**
 * Created by hosson on 08/03/2018.
 */

public class SpManager {

    private SharedPreferences mSharedPreferences;

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param spName  sp名
     */
    public SpManager(Context context, String spName) {
        CheckNull.checkNull(spName);
        mSharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 存储整型
     *
     * @param key
     * @param value
     */
    public void saveToPreference(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 存储float
     *
     * @param key
     * @param value
     */
    public void saveToPreference(String key, float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 存储long 型
     *
     * @param key
     * @param value
     */
    public void saveToPreference(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 存储布尔值
     *
     * @param key
     * @param value
     */
    public void saveToPreference(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 存储字符串型
     *
     * @param key
     * @param value
     */
    public void saveToPreference(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 存储对象
     *
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void saveToPreference(String key, T t) {
        if (t != null) {
            try {
                Gson gson = new Gson();
                String result = gson.toJson(t);
                saveToPreference(key, result);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    /**
     * 获取布尔类型的值
     *
     * @param paramString
     * @return
     */
    public boolean gainBooleanValue(String paramString,boolean defaultValu) {
        return this.mSharedPreferences.getBoolean(paramString, defaultValu);
    }

    /**
     * 获取整型的值
     *
     * @param paramString
     * @return
     */
    public int gainIntValue(String paramString,int defaultValue) {
        return this.mSharedPreferences.getInt(paramString, defaultValue);
    }

    /**
     * 获取long的值
     *
     * @param paramString
     * @return
     */
    public long gainLongValue(String paramString,long defaultValue) {
        return this.mSharedPreferences.getLong(paramString, defaultValue);
    }

    /**
     * 获取字符串型的值
     *
     * @param paramString
     * @return
     */
    public String gainStringValue(String paramString,String defaultValue) {
        if (defaultValue == null ){
            defaultValue = "";
        }
        return this.mSharedPreferences.getString(paramString, defaultValue);
    }

    /**
     * 获取float型的值
     *
     * @param paramString
     * @return
     */
    public float gainFloatValue(String paramString,float defaultValue) {
        return this.mSharedPreferences.getFloat(paramString, defaultValue);
    }

    /**
     * 获取对象
     *
     * @param key
     * @param <T>
     */
    public <T> T gainTValue(String key, Class<T> t) {
        try {
            String result = gainStringValue(key,null);
            if (result .equals("")){
                return null;
            }
            Gson gson = new Gson();
            return (T) gson.fromJson(result, t.getClass());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
