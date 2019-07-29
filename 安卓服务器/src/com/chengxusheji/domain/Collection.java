package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Collection {
    /*收藏id*/
    private int collectId;
    public int getCollectId() {
        return collectId;
    }
    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    /*被收藏小说*/
    private Novel novelObj;
    public Novel getNovelObj() {
        return novelObj;
    }
    public void setNovelObj(Novel novelObj) {
        this.novelObj = novelObj;
    }

    /*收藏用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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