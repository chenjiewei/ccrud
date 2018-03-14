package com.hosson.crud.ccrud.utils;

import android.util.Log;

/**
 * Created by hosson on 13/03/2018.
 */

public class Logger {
    private static Logger mInstance = new Logger();
    private static final String TAG = "ccrud";
    private static boolean isDebug = false;

    private Logger() {
    }

    public static void initData(boolean isDebugModel) {
        isDebug = isDebugModel;
    }

    public static void V(String tag, String msg) {
        if (isDebug) {
            Log.e(TAG + "|" + tag, msg);
        }
    }

    public static void V(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (isDebug) {
            Log.e(TAG + "|" + tag, msg);
        }
    }

    public static void I(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void E(String tag, String msg) {
        if (isDebug) {
            Log.e(TAG + "|" + tag, msg);
        }
    }

    public static void E(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void D(String tag, String msg) {
        if (isDebug) {
            Log.e(TAG + "|" + tag, msg);
        }
    }

    public static void D(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}
