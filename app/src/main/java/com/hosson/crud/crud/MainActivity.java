package com.hosson.crud.crud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hosson.crud.ccrud.CCRUD;
import com.hosson.crud.ccrud.entity.StorageType;
import com.hosson.crud.ccrud.utils.Logger;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushAgent.getInstance(this.getApplicationContext()).onAppStart();

        //  数据存储点
        //  数据统一接口
        //  数据结构

        //  存储的方式
        //  键值对，存储原子数据
        //  对象，存储对象
        //  存储，图片，图片的三级缓存
    }

    private void initController() {

    }

    public void btnonclick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn1:
                CCRUD.getStorage(StorageType.SHAREPREFERENCE).save("123", "12343");
                break;
            case R.id.btn2:
                TextDemo textDemo1 = new TextDemo();
                textDemo1.address = 1;
                textDemo1.age = new Date();
                textDemo1.id = 1;
                textDemo1.name = "zhangsan";
                textDemo1.school = 9981;
                textDemo1.isRight = true;
                textDemo1.rightN = false;
                textDemo1.aDouble = 998.1;
                textDemo1.aShort = 32;
                textDemo1.integer = 3323;
                textDemo1.faolatSmall = 3.1f;
                textDemo1.floatBig = 3.3f;
                DemoSecond demoSecond = new DemoSecond();
                demoSecond.secondId = 1000;
                demoSecond.secondName = "zhangsan1";
                textDemo1.mDemoSecond = demoSecond;

                TextDemo textDemo2 = new TextDemo();
                textDemo2.address = 1;
                textDemo2.age = new Date();
                textDemo2.id = 1;
                textDemo2.name = "zhangsan";
                textDemo2.school = 9981;
                textDemo2.isRight = true;
                textDemo2.rightN = false;
                textDemo2.aDouble = 998.1;
                textDemo2.aShort = 32;
                textDemo2.integer = 3323;
                textDemo2.faolatSmall = 3.1f;
                textDemo2.floatBig = 3.3f;
                DemoSecond demoSecond1 = new DemoSecond();
                demoSecond1.secondId = 9999;
                demoSecond1.secondName = "zhangsan2";
                textDemo2.mDemoSecond = demoSecond1;



                TextDemo[] list = new TextDemo[2];

                list[0] = (textDemo1);
                list[1] = (textDemo2);

                CCRUD.getStorage(StorageType.SQL).save("9660", list);

                break;
            case R.id.btn3:

                CCRUD.getStorage(StorageType.SHAREPREFERENCE).get("543", null, TextDemo.class);
                break;
            case R.id.btn4:
                Object[] textDemosds = CCRUD.getStorage(StorageType.SQL).get("9660", new TextDemo[2].getClass(), TextDemo.class);
                Logger.E("ccc");
                break;
            case R.id.btn5:
                TextDemo textDemo = new TextDemo();
                textDemo.address = 1;
                textDemo.age = new Date();
                textDemo.id = 1;
                textDemo.name = "zhangsan";
                textDemo.school = 9981;
                textDemo.isRight = true;
                textDemo.rightN = false;
                textDemo.aDouble = 998.1;
                textDemo.aShort = 32;
                textDemo.integer = 3323;
                textDemo.faolatSmall = 3.1f;
                textDemo.floatBig = 3.3f;
                CCRUD.getStorage(StorageType.MFILE).save("88443", textDemo);
                break;
            case R.id.btn6:
                TextDemo textDemo5 = CCRUD.getStorage(StorageType.MFILE).get("88443", null, TextDemo.class);
                break;
        }

    }
}
