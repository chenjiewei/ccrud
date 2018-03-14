package com.hosson.crud.ccrud.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by hosson on 12/03/2018.
 */

public class FileHelper {

    /**
     * 判断文件是否存在
     *
     * @param path
     * @param fileName
     * @return
     */
    public static boolean fileIsExist(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        return file.exists();
    }

    /**
     * 创建文件
     *
     * @param path
     * @param fileName
     * @return
     */
    public static void createFile(String path, String fileName) {

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            //创建文件
            file.createNewFile();
            Logger.I("createFile", "make file success");
        } catch (IOException e) {
            Logger.I("createFile", "make file failure exception = " + e.getMessage().toString());
        }
        return;
    }

    /**
     * 保存对象到文件中
     *
     * @param t
     * @param pathAndName
     * @param <T>
     */
    public static <T> void saveObjectToFile(T t, String pathAndName) {
        File file = new File(pathAndName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t); //写入
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
                if (oos != null) oos.close();
            } catch (IOException e) {
                Logger.E("saveObjectToFile ", e.getMessage().toString());
            }
        }
    }

    /**
     * 从文件中获取对象
     *
     * @param tClass
     * @param pathAndName
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromFile(Class<T> tClass, String pathAndName) {
        Object obj;
        File sdFile = new File(pathAndName);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(sdFile); //获得输入流
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            return (T) obj;
        } catch (IOException | ClassNotFoundException e) {
            Logger.E("getObjectFromFile", e.getMessage().toString());
        } finally {
            try {
                if (fis != null) fis.close();
                if (ois != null) ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
