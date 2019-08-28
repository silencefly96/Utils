package com.silencefly96.base.bean;

import com.run.utils.beans.TailDataBean;

public class Monster extends TailDataBean {
    private String name;
    private String belong;
    private String property;

    public Monster(String name, String belong, String property) {
        this.name = name;
        this.belong = belong;
        this.property = property;

        setFootProperty(belong);
    }

    public Monster(String name, String property) {
        this.name = name;
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
