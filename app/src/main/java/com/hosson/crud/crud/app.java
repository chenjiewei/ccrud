package com.hosson.crud.crud;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.hosson.crud.ccrud.CCRUD;
import com.hosson.crud.ccrud.entity.StorageType;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by hosson on 08/03/2018.
 */

public class app extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this, "5aa8c58df29d980277000069", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "8fe35ec40089aefe5c0fb53174f67cca");

        CCRUD.init(this);
        //  建造SP存储框架
        CCRUD.buildStorage().setStorageType(StorageType.SHAREPREFERENCE).setFileName("aaa").complete();

        // 构造SQL 存储框架
        CCRUD.buildStorage().setStorageType(StorageType.SQL).setFileName("aaa").setVersion(10).complete();

        // 建造File 存储框架
        CCRUD.buildStorage().setFileName("aaa.txt").setVersion(11).setFilePath(this.getFilesDir().getAbsolutePath()).setStorageType(StorageType.MFILE).complete();

        initControl();
    }

    private void initControl() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
//      //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("cjw", "mPushAgent register device token ==" + deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {

            }
        });

//        UmengMessageHandler messageHandler = new UmengMessageHandler(){
//
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler(getMainLooper()).post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
//                        boolean isClickOrDismissed = true;
//                        if(isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//
//        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, "cjws"+msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
}
