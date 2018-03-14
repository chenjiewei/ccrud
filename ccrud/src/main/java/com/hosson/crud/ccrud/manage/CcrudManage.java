package com.hosson.crud.ccrud.manage;

import android.content.Context;

import com.hosson.crud.ccrud.entity.ConfigInfo;
import com.hosson.crud.ccrud.entity.SpType;
import com.hosson.crud.ccrud.entity.StorageType;
import com.hosson.crud.ccrud.service.CrudService;
import com.hosson.crud.ccrud.service.impl.SqlCrudService;
import com.hosson.crud.ccrud.service.impl.SpCrudService;
import com.hosson.crud.ccrud.service.impl.FileCrudService;
import com.hosson.crud.ccrud.utils.MRuntimeException;

/**
 * Created by hosson on 08/03/2018.
 */

public class CcrudManage {
    private static CcrudManage instance;
    private Context context;

    private CcrudManage() {
    }

    public static CcrudManage getInstance() {
        if (instance == null) {
            instance = CcrudManage.ManageIMPL.instanceimpl;
        }
        return instance;
    }

    private static class ManageIMPL {
        private static final CcrudManage instanceimpl = new CcrudManage();
    }

    private SpCrudService mSPCrudService;
    private FileCrudService mFileCrudService;
    private SqlCrudService mSqlCrudService;


    private SpCrudService mSqlDefaultSPCrudService;
    private SpCrudService mFileDefaultSPCrudService;

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 构造存储器
     *
     * @return
     */
    public StorageBuilder buildStorage() {
        StorageBuilder storageBuilder = new StorageBuilder();
        return storageBuilder;
    }

    /**
     * 确认建造
     *
     * @param configInfo
     */
    public void buildSpCommit(ConfigInfo configInfo) {
        if (context == null || configInfo == null || configInfo.getFileName() == null) {
            throw new MRuntimeException("no init");
        }
        if (configInfo.getStorageType() == StorageType.SHAREPREFERENCE) {
            mSPCrudService = new SpCrudService(context, configInfo.getFileName());

        } else if (configInfo.getStorageType() == StorageType.SQL) {

            mSqlDefaultSPCrudService = new SpCrudService(context, configInfo.getFileName());
            mSqlCrudService = new SqlCrudService(context, configInfo.getFileName(), configInfo.getVersion());

        } else if (configInfo.getStorageType() == StorageType.MFILE) {
            // 调用顺序反了会崩溃
            mFileDefaultSPCrudService = new SpCrudService(context, configInfo.getFileName());
            mFileCrudService = new FileCrudService(configInfo);

        } else {
            throw new MRuntimeException("storagetype error");
        }


    }

    public CrudService getCrudService(StorageType storageType) {
        if (storageType == StorageType.SHAREPREFERENCE) {
            if (mSPCrudService == null) {
                throw new MRuntimeException("sharepreference no build");
            }
            return mSPCrudService;
        } else if (storageType == StorageType.SQL) {
            if (mSqlCrudService == null) {
                throw new MRuntimeException("SQL no build");
            }
            return mSqlCrudService;
        } else {
            if (mFileCrudService == null) {
                throw new MRuntimeException("file no build");
            }
            return mFileCrudService;
        }
    }

    /**
     * 提供给内部的SQL 和 File存储用
     *
     * @param spType
     * @return
     */
    public CrudService getCrudService(SpType spType) {
        if (spType == SpType.SQL_SP) {
            if (mSqlDefaultSPCrudService == null) {
                throw new MRuntimeException("SpType.SQL_SP no build");
            }
            return mSqlDefaultSPCrudService;
        } else if (spType == SpType.FILE_SP) {
            if (mFileDefaultSPCrudService == null) {
                throw new MRuntimeException("SpType.FILE_SP no build");
            }
            return mFileDefaultSPCrudService;
        }
        return null;
    }
}
