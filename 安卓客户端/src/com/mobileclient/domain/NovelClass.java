package com.mobileclient.domain;

import java.io.Serializable;

public class NovelClass implements Serializable {
    /*分类id*/
    private int classId;
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /*分类名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

}