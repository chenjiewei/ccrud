package com.hosson.crud.ccrud.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.hosson.crud.ccrud.entity.EntityTypePool;
import com.hosson.crud.ccrud.entity.SqlSaveKeyEntity;
import com.hosson.crud.ccrud.manage.sql.SqlManager;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hosson on 09/03/2018.
 */

public class DBHelper {
    /**
     * @param
     * @description 查询数据并显示数据内容
     * @author ldm
     * @time 2016/11/10 10:18
     */
    public static <T> boolean queryIsExist(SqlManager sqlManager, String key, String slqName) {

        String sql = "select * from " + slqName + " where key = '" + key + "';";
        Cursor cursor = sqlManager.getWritableDatabase().rawQuery(sql, null);
        boolean result = false;
        while (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        return result;
    }

    /**
     * 删除数据
     *
     * @param sqlManager
     * @param key
     * @param slqName
     * @param <T>
     */
    public static <T> void deleteData(SqlManager sqlManager, String key, String slqName) {
        String delSql = "delete from " + slqName + " where key = '" + key + "';";
        sqlManager.getWritableDatabase().execSQL(delSql);
    }

    /**
     * 判断某个数据库的表是否存在
     *
     * @param sqlName
     * @return
     */
    public static boolean IsTableExist(SQLiteDatabase sqLiteDatabase, String sqlName) {
        String isTableExitStr = "select sql from sqlite_master where type = 'table' and name = '%s'";
        isTableExitStr = String.format(isTableExitStr, sqlName);
        Cursor c = sqLiteDatabase.rawQuery(isTableExitStr, null);
        String tableCreateSql = null;
        try {
            if (c != null && c.moveToFirst()) {
                tableCreateSql = c.getString(c.getColumnIndex("sql"));
            }
        } catch (Exception ex) {
            Logger.E("IsTableExist ex", ex.getMessage().toString());
        } finally {
            if (c != null)
                c.close();
        }
        if (tableCreateSql != null && tableCreateSql.contains(sqlName))
            return true;
        return false;

    }

    /**
     * @param
     * @description 查询数据并显示数据内容
     * @author ldm
     * @time 2016/11/10 10:18
     */
    public static <T,M> T query(EntityTypePool entityTypePool, SqlManager sqlManager, String key, String sqlName) {
        String sql = "select * from " + sqlName + " where key = '" + key + "';";
        Object obj;
        Cursor cursor = sqlManager.getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            try {
                obj = Class.forName(StringUtils.replace9527ByPoint(sqlName)).newInstance();
                int columnCount = cursor.getColumnCount();
                for (int i = 1; i < columnCount - 1; ++i) {
                    String columnName = cursor.getColumnName(i);
                    Field nameField = obj.getClass().getDeclaredField(columnName); // 获得name属性
                    nameField.setAccessible(true); //解除封装
                    String typeIndexStr = entityTypePool.getEntityTypeMap(sqlName).get(columnName);
                    /**
                     * int  0;
                     String 1 ;
                     Date 2;
                     double 3;
                     short 4 ;
                     boolean 5;
                     Boolean 6;
                     Integer 7;
                     Double 8;
                     Short 9;
                     float 10;
                     Float 11;
                     */
                    switch (typeIndexStr) {
                        case "0":
                            // int 数据类型
                            nameField.set(obj, cursor.getInt(i));
                            break;
                        case "1":
                            // string 数据类型
                            nameField.set(obj, cursor.getString(i));
                            break;
                        case "2":
                            // date数据类型
                            String strName = cursor.getString(i);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = format.parse(strName);
                            nameField.set(obj, date);
                            break;
                        case "3":
                            nameField.set(obj, cursor.getDouble(i));
                            break;
                        case "4":
                            nameField.set(obj, cursor.getShort(i));
                            break;
                        case "5":
                            int boolInt = cursor.getInt(i);
                            nameField.set(obj, boolInt == 1 ? true : false);
                            break;
                        case "6":
                            int boolIntA = cursor.getInt(i);
                            nameField.set(obj, boolIntA == 1 ? true : false);
                            break;
                        case "7":
                            nameField.set(obj, cursor.getInt(i));
                            break;
                        case "8":
                            nameField.set(obj, cursor.getDouble(i));
                            break;
                        case "9":
                            nameField.set(obj, cursor.getShort(i));
                            break;
                        case "10":
                            nameField.set(obj, cursor.getFloat(i));
                            break;
                        case "11":
                            nameField.set(obj, cursor.getFloat(i));
                            break;
                        default:
                            M tTemp = null;
                            try {
                                Class tClassInstance  = Class.forName(typeIndexStr);
                                String gsonData = cursor.getString(i);
                                if (gsonData == null || gsonData .equals("")){
                                    break;
                                }
                                Gson gson = new Gson();
                                tTemp = (M) gson.fromJson(gsonData, tClassInstance);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            if (tTemp != null) {
                                nameField.set(obj,tTemp);
                            }
                            break;
                    }
                }
                return (T) obj;
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.E("query exception:" + ex.getMessage().toString());
            } finally {

            }
        }
        cursor.close();
        return null;
    }

    /**
     * 从结构表中获取用户的数据结构
     *
     * @param sqlManager
     * @param key
     * @return
     */
    public static HashMap<String, String> searchEntityType(String currentTableName, SqlManager sqlManager, String key) {
        HashMap<String, String> hashMap = new HashMap<>();
        String sql = "select  *  from  " + currentTableName + ";";
        Cursor cursor = sqlManager.getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            try {
                String[] columnNameArrays = cursor.getColumnNames();
                for (int i = 1; i < columnNameArrays.length; ++i) {
                    String itemStr = cursor.getString(i);
                    String temps = columnNameArrays[i];
                    hashMap.put(temps, itemStr);
                }
            } catch (Exception ex) {
                Logger.E("searchEntityType", "error msg : " + ex.getMessage().toString());
            } finally {

            }
        }
        cursor.close();
        return hashMap;
    }

    /**
     * 存储集合key用的数据表
     *
     * @param sqlManager
     * @param tableName
     */
    public static void createSqlTable(SqlManager sqlManager, String tableName) {
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append(" CREATE  TABLE  ").append(tableName).append("(")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,")
                .append("key varchar(255),fullkey varchar(255),keyValue varchar(255));");
        sqlManager.getWritableDatabase().execSQL(sqlStr.toString());
    }

    /**
     * 查询集合key 值存放的数据库
     *
     * @param sqlManager
     * @param tableName
     * @return
     */
    public static Map<String, SqlSaveKeyEntity> querySqlKeyEntity(SqlManager sqlManager, String tableName) {
        String sql = "select *  from  " + tableName + ";";
        Cursor cursor = sqlManager.getWritableDatabase().rawQuery(sql, null);
        Map<String, SqlSaveKeyEntity> sqlMap = new HashMap<>();
        while (cursor.moveToNext()) {
            try {
                SqlSaveKeyEntity sqlSaveKeyEntity = new SqlSaveKeyEntity();
                String key = cursor.getString(1);
                String keyfull = cursor.getString(2);
                String keyValue = cursor.getString(3);
                String[] keyValueArrays = StringUtils.stringToStrArrays(keyValue, "&", keyfull);
                sqlSaveKeyEntity.setKey(key);
                sqlSaveKeyEntity.setKeyValue(keyValue);
                sqlSaveKeyEntity.setPrefixStr(keyfull);
                sqlSaveKeyEntity.setKeyValueArrays(keyValueArrays);
                sqlMap.put(keyfull, sqlSaveKeyEntity);
            } catch (Exception ex) {
                Logger.E("searchEntityType", "error msg : " + ex.getMessage().toString());
            } finally {

            }
        }
        cursor.close();
        return sqlMap;
    }

    /**
     * 保存数据到集合的数据库中
     *
     * @param sqlManager
     * @param tableName
     * @return
     */
    public static void saveSqlKeyEntity(SqlManager sqlManager, String tableName, SqlSaveKeyEntity sqlSaveKeyEntity) {
        //  查询是否存在，如果存在删除记录
        String sqlSelect = "select * from " + tableName + " where fullkey  =  '" + sqlSaveKeyEntity.getPrefixStr() + "'and key = '" + sqlSaveKeyEntity.getKey() + "';";
        Cursor cursor = sqlManager.getWritableDatabase().rawQuery(sqlSelect, null);
        boolean result = false;
        while (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        if (result) {
            // 删除数据
            String delSql = "delete from " + tableName + " where fullkey =  '" + sqlSaveKeyEntity.getPrefixStr() + "'and key = '" + sqlSaveKeyEntity.getKey() + "';";
            sqlManager.getWritableDatabase().execSQL(delSql);
        }

        String sql = "insert into   " + tableName + "(key,fullkey,keyValue)" + "values('" + sqlSaveKeyEntity.getKey() + "','" + sqlSaveKeyEntity.getPrefixStr() + "','" + sqlSaveKeyEntity.getKeyValue() + "');";
        sqlManager.getWritableDatabase().execSQL(sql);
    }
}

