package com.hosson.crud.ccrud;

import android.content.Context;

import com.hosson.crud.ccrud.entity.StorageType;
import com.hosson.crud.ccrud.manage.CcrudManage;
import com.hosson.crud.ccrud.manage.StorageBuilder;
import com.hosson.crud.ccrud.service.CrudService;

/**
 * Created by hosson on 08/03/2018.
 */

public class CCRUD {
    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        CcrudManage.getInstance().init(context);
    }

    /**
     * 构造SP存储器
     * @return
     */
    public static StorageBuilder buildStorage(){
        return CcrudManage .getInstance().buildStorage();
    }

    /**
     * 获取建造的存储器
     *
     * @param storageType
     * @return
     */
    public static CrudService getStorage(StorageType storageType){
        return CcrudManage .getInstance().getCrudService(storageType);
    }

}
