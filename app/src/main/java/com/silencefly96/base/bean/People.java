package com.silencefly96.base.bean;

import com.run.silencebases.beans.HeadDataBean;

public class People extends HeadDataBean{
    private String sex;

    public People(String sex) {
        this.sex = sex;

        setHeadProperty(sex);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
