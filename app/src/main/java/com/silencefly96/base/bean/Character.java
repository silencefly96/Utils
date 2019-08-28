package com.silencefly96.base.bean;

import com.run.silencebases.beans.MediumDataBean;

public class Character extends MediumDataBean {

    private int order;
    private String sex;
    private String name;
    private String old;
    private String from;

    public Character(String sex, int order, String name, String old, String from) {
        this.sex = sex;
        this.order = order;
        this.name = name;
        this.old = old;
        this.from = from;

        setHeadProperty(sex);
        setFootProperty(name);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
