package com.mobileclient.domain;

import java.io.Serializable;

public class Collection implements Serializable {
    /*收藏id*/
    private int collectId;
    public int getCollectId() {
        return collectId;
    }
    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    /*被收藏小说*/
    private int novelObj;
    public int getNovelObj() {
        return novelObj;
    }
    public void setNovelObj(int novelObj) {
        this.novelObj = novelObj;
    }

    /*收藏用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*收藏时间*/
    private String collectTime;
    public String getCollectTime() {
        return collectTime;
    }
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

}