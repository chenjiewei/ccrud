package com.hosson.crud.ccrud.utils;

import android.text.TextUtils;

/**
 * Created by hosson on 11/03/2018.
 */

public class StringUtils {

    /**
     * 用9527代替.
     *
     * @param originStr
     * @return
     */
    public static String replacePointBy9527(String originStr) {
        if (originStr == null || TextUtils.isEmpty(originStr)) {
            throw new MRuntimeException("originStr EQUAL NULL");
        }
        originStr = originStr.replace(".", "9527");
        return originStr;
    }

    /**
     * 用.代替9527
     *
     * @param originStr
     * @return
     */
    public static String replace9527ByPoint(String originStr) {
        if (originStr == null || TextUtils.isEmpty(originStr)) {
            throw new MRuntimeException("originStr EQUAL NULL");
        }
        originStr = originStr.replace("9527", ".");
        return originStr;
    }

    /**
     * 字符串分解为字符数组
     *
     * @param string
     * @param spliteStr
     * @param prefixStr 前缀字符串
     * @return
     */
    public static String[] stringToStrArrays(String string,String spliteStr,String prefixStr) {
        if (string == null){
            return null;
        }
        String[] result = string.split(spliteStr);
        for (int i= 0 ; i < result.length ; ++i) {
            result[i] = prefixStr+result[i];
        }
        return result;
    }
}
