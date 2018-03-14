package com.hosson.crud.ccrud.service;

import android.content.Context;

/**
 * Created by hosson on 08/03/2018.
 */

public interface CrudService {
    /**
     * 存储字符串数据
     *
     * @param key   略
     * @param value 略
     */
    void save(String key, String value);

    /**
     * 存储整型数据
     *
     * @param key   略
     * @param value 略
     */
    void save(String key, int value);

    /**
     * 存储long 型数据
     *
     * @param key   略
     * @param value 略
     */
    void save(String key, long value);

    /**
     * 存储float 型数据
     *
     * @param key   略
     * @param value 略
     */
    void save(String key, float value);

    /**
     * 存储对象
     *
     * @param key 略
     * @param t   对象
     * @param <T> 对象的类型
     */
    <T> void save(String key, T t);


    /**
     * 获取字符串数据
     *
     * @param key   略
     * @param value 默认数据
     */
    String get(String key, String value);

    /**
     * 获取整型数据
     *
     * @param key   略
     * @param value 略
     */
    int get(String key, int value);

    /**
     * 获取long 型数据
     *
     * @param key   略
     * @param value 略
     */
    long get(String key, long value);

    /**
     * 获取float 型数据
     *
     * @param key   略
     * @param value 略
     */
    float get(String key, float value);

    /**
     * 获取boolean 型数据
     *
     * @param key   略
     * @param value 略
     */
    boolean get(String key, boolean value);

    /**
     * 获取对象
     *
     * @param key
     * @param tClassEntity 对象的类型
     * @param mClassEntity 集合需要设置子对象类型
     * @param <T>
     */
    <T,M> T get(String key, Class<T> tClassEntity , Class<M> mClassEntity);


    //  TODO 待完成
    //  第二个字母是大写的情况要特殊一些 OK
    //  里面的log处理一下 OK
    //  文件存储的话，目前只能构造一个位置的文件存储，选择存储在sd卡还是data/data里面的话，看自己选择
    //  存储图片文件
    //  存储视频文件
    //  存储音频文件
    //  存储集合，目前集合定位三种形式，map，set，list ,返回的话，list返回arrayList ,set 返回HashSet ,map 返回HashMap
    //  线程并发问题没有处理
    //  异步问题的处理
    //  版本测试问题，6.0 OK，7.0 没有测试
    //  对象里面是原子数据，不能出现集合类和数组
    //  目前不支持map数据集合作为value存储，目前考虑这样没有任何的意义
    //  同一个KEY可以存储不同的数据类型
    //  返回泛型数组时，返回的一定要是Object[]数据


}
