package com.hosson.crud.crud;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hosson on 08/03/2018.
 */

public class TextDemo implements Serializable {
    int id;
    String name;
    Date age;
    double school;
    short address;
    boolean isRight;
    Boolean rightN;
    Integer integer;
    Double aDouble;
    Short aShort;
    float faolatSmall;
    Float floatBig;

    DemoSecond mDemoSecond;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getAge() {
        return age;
    }

    public double getSchool() {
        return school;
    }

    public short getAddress() {
        return address;
    }

    public boolean isRight() {
        return isRight;
    }

    public Boolean getRightN() {
        return rightN;
    }

    public Integer getInteger() {
        return integer;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public Short getaShort() {
        return aShort;
    }

    public float getFaolatSmall() {
        return faolatSmall;
    }

    public Float getFloatBig() {
        return floatBig;
    }

    public DemoSecond getmDemoSecond() {
        return mDemoSecond;
    }
}
