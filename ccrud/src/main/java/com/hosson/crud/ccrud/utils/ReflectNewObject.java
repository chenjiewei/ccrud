package com.hosson.crud.ccrud.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.hosson.crud.ccrud.entity.EntityTypePool;
import com.hosson.crud.ccrud.manage.sql.SqlManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//  目前约定只有对象里面的数据都是原子数据是可以存储的

/**
 * Created by hosson on 08/03/2018.
 */

public class ReflectNewObject {

    /**
     * 把一个字符串的第一个字母大写、效率是最高的
     *
     * @param fildeName
     * @return
     * @throws Exception
     */
    public static String getMethodName(String fildeName) throws Exception {
        // 如果第二个字符是大写的话，那就不用更改了
        if (fildeName != null && fildeName.length() >= 2 && Character.isUpperCase(fildeName.charAt(1))) {
            return fildeName;
        }
        //如果第二个字母是大写的话，那就不需要更改
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 根据对象的数据结构
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T, M> String[] getInstance(EntityTypePool entityTypePool, String key, T t) {
        String currentTableName = StringUtils.replacePointBy9527(t.getClass().getName());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table ")
                .append(currentTableName)
                .append("(");
        stringBuffer.append("key" + " varchar(255) primary key,");

        StringBuffer insertBuffer = new StringBuffer();

        String insertSqlStr = "insert into " + currentTableName + " values ( '" + key + "',";
        insertBuffer.append(insertSqlStr);
        Field[] fields = t.getClass().getDeclaredFields();
        Map<String, String> entityMap = new HashMap<>();
        for (Field field : fields) {
            String tempStr = field.getName();
            // 如果类型是String
            if (field.getGenericType().toString().equals(
                    "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                // 拿到该属性的gettet方法
                /**
                 * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                 * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                 * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                 */
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(1));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    String val = (String) m.invoke(t);// 调用getter方法获取属性值
                    stringBuffer.append(tempStr).append(" varchar(255), ");
                    if (val != null) {
                        Logger.I("String type:" + val);
                    }
                    insertBuffer.append("'").append(val).append("',");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }

            } else if (field.getGenericType().toString().equals(
                    "class java.lang.Integer")) {
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(7));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Integer val = (Integer) m.invoke(t);
                    if (val != null) {
                        Logger.I("Integer type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" Integer, ");
                    insertBuffer.append(val).append(",");

                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }

            } else if (field.getGenericType().toString().equals(
                    "class java.lang.Double")) {
                // 如果类型是Double
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(8));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Double val = (Double) m.invoke(t);
                    if (val != null) {
                        Logger.I("Double type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" REAL, ");
                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "class java.lang.Boolean")) {
                // 如果类型是Boolean 是封装类
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(6));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Boolean val = (Boolean) m.invoke(t);
                    if (val != null) {
                        Logger.I("Boolean type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" Integer, ");
                    int booleanResult = val ? 1 : 0;
                    insertBuffer.append(booleanResult).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals("boolean")) {
                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(5));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(t);
                    if (val != null) {
                        Logger.I("boolean type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" Integer, ");
                    int booleanResult = val ? 1 : 0;
                    insertBuffer.append(booleanResult).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "class java.util.Date")) {
                // 如果类型是Date
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(2));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Date val = (Date) m.invoke(t);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatStr = format.format(val);
                    if (val != null) {
                        Logger.I("Date type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" varchar(255), ");
                    insertBuffer.append("'").append(formatStr).append("',");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "class java.lang.Short")) {
                // 如果类型是Short
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(9));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Short val = (Short) m.invoke(t);
                    if (val != null) {
                        Logger.I("Short type:" + val);
                    }
                    stringBuffer.append(tempStr).append(" INTEGER, ");

                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "int")) {
                //  类型是int
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(0));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    int val = (int) m.invoke(t);
                    Logger.I("int type:" + val);
                    stringBuffer.append(tempStr).append(" INTEGER, ");

                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "double")) {
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(3));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    double val = (double) m.invoke(t);
                    stringBuffer.append(tempStr).append(" REAL, ");

                    insertBuffer.append(val).append(",");

                    Logger.I("double type:" + val);

                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "short")) {
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(4));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    short val = (short) m.invoke(t);
                    stringBuffer.append(tempStr).append(" INTEGER, ");
                    Logger.I("short type:" + val);
                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "class java.lang.Float")) {
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(11));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Float val = (Float) m.invoke(t);
                    stringBuffer.append(tempStr).append(" REAL, ");
                    Logger.I("Float type:" + val);
                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else if (field.getGenericType().toString().equals(
                    "float")) {
                /**
                 * int  0;
                 String 1 ;
                 Date 2;
                 double 3;
                 short 4 ;
                 boolean 5;
                 Boolean 6;
                 Integer 7;
                 Double 8;
                 Short 9;
                 float 10;
                 Float 11;
                 */
                entityMap.put(tempStr, String.valueOf(10));
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    float val = (float) m.invoke(t);
                    stringBuffer.append(tempStr).append(" REAL, ");
                    Logger.I("float type:" + val);
                    insertBuffer.append(val).append(",");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            } else {
                //说明不是基础数据类型
                Logger.E("cjw msg:==" + ((Class)field.getGenericType()).getName());
                entityMap.put(tempStr, ((Class)field.getGenericType()).getName());
                try {
                    Method m = (Method) t.getClass().getMethod(
                            "get" + ReflectNewObject.getMethodName(field.getName()));
                    Object val = m.invoke(t);
                    String result = "";
                    try {
                        result = new Gson().toJson(val);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    stringBuffer.append(tempStr).append(" varchar(255), ");
                    Logger.I("complex type:" + val);
                    insertBuffer.append("'").append(result).append("',");
                } catch (Exception ex) {
                    throw new MRuntimeException("class must had get and set method !");
                }
            }
        }
        // 数据放入
        entityTypePool.addEntityTypeMap(currentTableName, entityMap);
        stringBuffer.append("extends varchar(31) );");
        insertBuffer.append("'none'").append(");");
        Logger.I("stringBuffer:" + stringBuffer.toString());
        Logger.I("insertBuffer:" + insertBuffer.toString());
        String[] stringArrays = new String[2];
        stringArrays[0] = stringBuffer.toString();
        stringArrays[1] = insertBuffer.toString();
        return stringArrays;
    }


    /**
     * 建类型数据库，主要用于类型存储
     *
     * @param tableName
     * @param map
     * @return
     */
    public static String[] selectEntityConstructStr(String tableName, Map<String, String> map) {
        //    目前是11中数据类型，在加上一个key值
        /**
         * int  0;
         String 1 ;
         Date 2;
         double 3;
         short 4 ;
         boolean 5;
         Boolean 6;
         Integer 7;
         Double 8;
         Short 9;
         float 10;
         Float 11;
         */
        if (map == null) {
            return null;
        }
        String[] resultStr = new String[2];
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table ")
                .append(tableName)
                .append("(");
        StringBuffer insertSb = new StringBuffer();
        insertSb.append("insert into ")
                .append(tableName)
                .append("  values (");
        for (Map.Entry<String, String> mapEntry : map.entrySet()) {
            stringBuffer.append(mapEntry.getKey()).append("   varchar(255),  ");
            insertSb.append("'").append(mapEntry.getValue()).append("',");
        }
        stringBuffer.append("extands varchar(255)").append(");");
        insertSb.append("'").append("none").append("');");
        Logger.I(stringBuffer.toString());
        resultStr[0] = stringBuffer.toString();
        resultStr[1] = insertSb.toString();
        return resultStr;
    }

}
