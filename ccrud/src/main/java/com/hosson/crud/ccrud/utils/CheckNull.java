package com.hosson.crud.ccrud.utils;

/**
 * Created by hosson on 08/03/2018.
 */

public class CheckNull {
    public static <T> void checkNull(T t){
        if (t == null){
            throw new NullPointerException("error:"+t.getClass().getSimpleName());
        }
    }
}
