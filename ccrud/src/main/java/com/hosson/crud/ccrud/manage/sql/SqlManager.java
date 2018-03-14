package com.hosson.crud.ccrud.manage.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hosson.crud.ccrud.entity.EntityTypePool;
import com.hosson.crud.ccrud.entity.SqlSaveKeyEntity;
import com.hosson.crud.ccrud.utils.DBHelper;
import com.hosson.crud.ccrud.utils.Logger;
import com.hosson.crud.ccrud.utils.MRuntimeException;
import com.hosson.crud.ccrud.utils.ReflectNewObject;
import com.hosson.crud.ccrud.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hosson on 11/03/2018.
 */

public class SqlManager extends SQLiteOpenHelper {
    // 存储集合用的数据表名
    public static final String SQL_KEY_TABLE_NAME = "sql_key_table_name";
    public static final String TABLE_CONSTRUCT_NAME = "table_cons";
    private SQLiteDatabase mSQLiteDatabase;
    private EntityTypePool mEntityTypePool;

    public SqlManager(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        mEntityTypePool = new EntityTypePool();
        handleCollectionsSql();


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public <T, M> void save(String key, T t) {
        if (t == null) {
            throw new MRuntimeException("can not save null!");
        }
        String tName = t.getClass().getName();
        // 如果t是list ,map ，set,那么要存储的是对象的集合
        if (t.getClass().isArray()) {
            //  数组,
            //  先依次存储原子对象，并保留相应的key
            M[] mArrays = (M[]) t;
            if (mArrays == null || mArrays.length == 0) {
                throw new MRuntimeException("please not save empty!");
            }
            String realSaveKeyPrefix = key + tName + mArrays[0].getClass().getName();
            String keyValue = "";
            String[] saveValues = new String[mArrays.length];
            for (int i = 0; i < mArrays.length; ++i) {
                saveValues[i] = realSaveKeyPrefix + i;
                save(realSaveKeyPrefix + i, mArrays[i]);
                if (i == mArrays.length - 1) {
                    keyValue += i;
                } else {
                    keyValue += i + "&";
                }
            }
            // 存储数组的key值，并在内存里面存储一份,是否曾经有一份
            SqlSaveKeyEntity sqlSaveKeyEntity = new SqlSaveKeyEntity();
            sqlSaveKeyEntity.setKey(key);
            sqlSaveKeyEntity.setKeyValue(keyValue);
            sqlSaveKeyEntity.setPrefixStr(realSaveKeyPrefix);
            sqlSaveKeyEntity.setKeyValueArrays(saveValues);
            DBHelper.saveSqlKeyEntity(this, SQL_KEY_TABLE_NAME, sqlSaveKeyEntity);
            mEntityTypePool.addSqlSaveKeyEntity(realSaveKeyPrefix, sqlSaveKeyEntity);

        } else if (t instanceof java.util.List) {
            // list，存储每个原子对象key，肯定是用数据库存
            List<M> mList = (List<M>) t;
            if (mList == null || mList.size() == 0) {
                throw new MRuntimeException("please not save empty!");
            }
            String realSaveKeyPrefix = key + tName + mList.get(0).getClass().getName();
            String keyValue = "";
            String[] saveValues = new String[mList.size()];
            for (int i = 0; i < mList.size(); ++i) {
                saveValues[i] = realSaveKeyPrefix + i;
                save(realSaveKeyPrefix + i, mList.get(i));
                if (i == mList.size() - 1) {
                    keyValue += i;
                } else {
                    keyValue += i + "&";
                }
            }
            // 存储数组的key值，并在内存里面存储一份,是否曾经有一份
            SqlSaveKeyEntity sqlSaveKeyEntity = new SqlSaveKeyEntity();
            sqlSaveKeyEntity.setKey(key);
            sqlSaveKeyEntity.setKeyValue(keyValue);
            sqlSaveKeyEntity.setPrefixStr(realSaveKeyPrefix);
            sqlSaveKeyEntity.setKeyValueArrays(saveValues);
            DBHelper.saveSqlKeyEntity(this, SQL_KEY_TABLE_NAME, sqlSaveKeyEntity);
            mEntityTypePool.addSqlSaveKeyEntity(realSaveKeyPrefix, sqlSaveKeyEntity);

        } else if (t instanceof java.util.Set) {
            // set,
            Set<M> mSet = (Set<M>) t;
            M[] mSetArrays = (M[]) mSet.toArray();
            if (mSet == null || mSet.size() == 0) {
                throw new MRuntimeException("please not save empty!");
            }
            String realSaveKeyPrefix = key + tName + mSetArrays[0].getClass().getName();
            String keyValue = "";
            String[] saveValues = new String[mSetArrays.length];
            for (int i = 0; i < mSetArrays.length; ++i) {
                saveValues[i] = realSaveKeyPrefix + i;
                save(realSaveKeyPrefix + i, mSetArrays[i]);
                if (i == mSetArrays.length - 1) {
                    keyValue += i;
                } else {
                    keyValue += i + "&";
                }
            }
            // 存储数组的key值，并在内存里面存储一份,是否曾经有一份
            SqlSaveKeyEntity sqlSaveKeyEntity = new SqlSaveKeyEntity();
            sqlSaveKeyEntity.setKey(key);
            sqlSaveKeyEntity.setKeyValue(keyValue);
            sqlSaveKeyEntity.setPrefixStr(realSaveKeyPrefix);
            sqlSaveKeyEntity.setKeyValueArrays(saveValues);
            DBHelper.saveSqlKeyEntity(this, SQL_KEY_TABLE_NAME, sqlSaveKeyEntity);
            mEntityTypePool.addSqlSaveKeyEntity(realSaveKeyPrefix, sqlSaveKeyEntity);
        } else if (t instanceof java.util.Map) {
            // 如果是map集合的话没有意义啊，此种存储目前不支持map集合
            throw new MRuntimeException("nonsupport map collections");
        } else {
            // 原子存储
            saveAtomic(key, t);
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @param tClassEntity
     * @param mClassEntity
     * @param <T>
     * @param <M>
     * @return
     */
    public <T, M> T get(String key, Class<T> tClassEntity, Class<M> mClassEntity) {
        String tName = tClassEntity.getName();
        Object object = null;
        if (!tClassEntity.isArray()) {
            try {
                object = tClassEntity.newInstance();
            } catch (Exception ex) {
                Logger.E("get object error msg:" + ex.getMessage().toString());
            }
            if (object == null) {
                return null;
            }
        }
        if (tClassEntity.isArray()) {
            //  数组,先̤̮找̤̮KEY值̤̮，在̤̮循̤̮环̤̮获̤̮取̤̮对̤̮象̤̮
            String realSaveKeyPrefix = key + tName + mClassEntity.getName();
            SqlSaveKeyEntity sqlSaveKeyEntity = mEntityTypePool.getSqlSaveKeyEntity(realSaveKeyPrefix);
            if (sqlSaveKeyEntity == null || sqlSaveKeyEntity.getKeyValueArrays() == null || sqlSaveKeyEntity.getKeyValueArrays().length == 0) {
                return null;
            }
            try {
                M[] mArrays = (M[]) new Object[sqlSaveKeyEntity.getKeyValueArrays().length];
                for (int i = 0; i < sqlSaveKeyEntity.getKeyValueArrays().length; ++i) {
                    mArrays[i]=getAtomic(sqlSaveKeyEntity.getKeyValueArrays()[i], mClassEntity);
                }
                return (T) mArrays;

            } catch (Exception ex) {
                Logger.E("get arrays ex msg:" + ex.getMessage().toString());
            }

        } else if (object instanceof java.util.List) {
            // list，存储每个原子对象key，肯定是用数据库存
            String realSaveKeyPrefix = key + tName + mClassEntity.getName();
            SqlSaveKeyEntity sqlSaveKeyEntity = mEntityTypePool.getSqlSaveKeyEntity(realSaveKeyPrefix);
            if (sqlSaveKeyEntity == null || sqlSaveKeyEntity.getKeyValueArrays() == null || sqlSaveKeyEntity.getKeyValueArrays().length == 0) {
                return null;
            }
            try {
                ArrayList<M> arrayList = new ArrayList<>();
                for (int i = 0; i < sqlSaveKeyEntity.getKeyValueArrays().length; ++i) {
                    arrayList.add(getAtomic(sqlSaveKeyEntity.getKeyValueArrays()[i], mClassEntity));
                }
                return (T) arrayList;
            } catch (Exception ex) {
                Logger.E("get list ex msg:" + ex.getMessage().toString());
            }
        } else if (object instanceof java.util.Set) {
            // list，存储每个原子对象key，肯定是用数据库存
            String realSaveKeyPrefix = key + tName + mClassEntity.getName();
            SqlSaveKeyEntity sqlSaveKeyEntity = mEntityTypePool.getSqlSaveKeyEntity(realSaveKeyPrefix);
            if (sqlSaveKeyEntity == null || sqlSaveKeyEntity.getKeyValueArrays() == null || sqlSaveKeyEntity.getKeyValueArrays().length == 0) {
                return null;
            }
            try {
                Set<M> arraySet = new HashSet<>();
                for (int i = 0; i < sqlSaveKeyEntity.getKeyValueArrays().length; ++i) {
                    arraySet.add(getAtomic(sqlSaveKeyEntity.getKeyValueArrays()[i], mClassEntity));
                }
                return (T) arraySet;
            } catch (Exception ex) {
                Logger.E("get set ex msg:" + ex.getMessage().toString());
            }
        } else if (object instanceof java.util.Map) {
            // 如果是map集合的话没有意义啊，此种存储目前不支持map集合
            throw new MRuntimeException("nonsupport map collections");
        } else {
            // 原子存储
            saveAtomic(key, mClassEntity);
        }
        return null;
    }

    /**
     * 存储原子对象，即存储原始对象的集合
     *
     * @param key
     * @param t
     * @param <T>
     */
    private <T> void saveAtomic(String key, T t) {
        if (mSQLiteDatabase == null) {
            mSQLiteDatabase = getWritableDatabase();
        }
        if (mSQLiteDatabase == null) {
            throw new MRuntimeException("database equal null");
        }
        if (t == null) {
            throw new MRuntimeException("class<?> type equal null");
        }
        // 查找是否有该数据库，如果没有则创建数据库
        String currentTableName = t.getClass().getName();
        currentTableName = StringUtils.replacePointBy9527(currentTableName);
        String[] createSqlStr = new String[2];
        createSqlStr = ReflectNewObject.getInstance(mEntityTypePool, key, t);
        if (!DBHelper.IsTableExist(mSQLiteDatabase, currentTableName)) {
            // 创建数据表
            mSQLiteDatabase.execSQL(createSqlStr[0]);
            // 存储一份数据结构，这份数据结构用一个新的数据表存储，主要存储的是，哪个字段是哪种数据类型
            if (!DBHelper.IsTableExist(mSQLiteDatabase, currentTableName+TABLE_CONSTRUCT_NAME)) {
                //  不存在，建表
                String[] constructStrArrays = ReflectNewObject.selectEntityConstructStr(currentTableName+TABLE_CONSTRUCT_NAME,mEntityTypePool.getEntityTypeMap(currentTableName));
                if (constructStrArrays != null){
                    mSQLiteDatabase.execSQL(constructStrArrays[0]);
                    mSQLiteDatabase.execSQL(constructStrArrays[1]);
                }
            }
        }
        // 查询是否存在该KEY对应的数据，if存在{删除}
        if (DBHelper.queryIsExist(this, key, currentTableName)) {
            DBHelper.deleteData(this, key, currentTableName);
        }
        // 插入数据
        mSQLiteDatabase.execSQL(createSqlStr[1]);
    }

    /**
     * 得到原子对象，即非集合对象
     *
     * @param key
     * @param classEntity
     * @param <T>
     * @return
     */
    private <T> T getAtomic(String key, Class<T> classEntity) {
        if (mSQLiteDatabase == null) {
            mSQLiteDatabase = getWritableDatabase();
        }
        if (mSQLiteDatabase == null) {
            throw new MRuntimeException("database equal null");
        }
        String currentTableName = StringUtils.replacePointBy9527(classEntity.getName());
        // 是否存在表
        if (!DBHelper.IsTableExist(mSQLiteDatabase, currentTableName)) {
            return null;
        }
        // 存在表的话，查看是否内存有原来的数据结构，没有的话从数据库中获取
        if (mEntityTypePool.getEntityTypeMap(currentTableName) == null) {
            HashMap<String, String> mapArrays = DBHelper.searchEntityType(currentTableName+TABLE_CONSTRUCT_NAME,this, currentTableName);
            mEntityTypePool.addEntityTypeMap(currentTableName, mapArrays);
        }
        Object object = DBHelper.query(mEntityTypePool, this, key, currentTableName);
        return (T) object;
    }

    /**
     * 处理集合和数组类型的key值问题
     */
    private void handleCollectionsSql() {
        //判断存储集合类的key数据表是否建立，如果没有建立的话，创建，如果已经创建的话，将数据缓存到内存中
        if (!DBHelper.IsTableExist(this.getWritableDatabase(), SQL_KEY_TABLE_NAME)) {
            DBHelper.createSqlTable(this, SQL_KEY_TABLE_NAME);
        } else {
            //从数据库中获取缓存数据,并存储在内存
            Map<String, SqlSaveKeyEntity> mapValue = DBHelper.querySqlKeyEntity(this, SQL_KEY_TABLE_NAME);
            mEntityTypePool.setSqlMap(mapValue);
        }
    }
}
