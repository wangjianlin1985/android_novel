package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Collection {
    /*�ղ�id*/
    private int collectId;
    public int getCollectId() {
        return collectId;
    }
    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    /*���ղ�С˵*/
    private Novel novelObj;
    public Novel getNovelObj() {
        return novelObj;
    }
    public void setNovelObj(Novel novelObj) {
        this.novelObj = novelObj;
    }

    /*�ղ��û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*�ղ�ʱ��*/
    private String collectTime;
    public String getCollectTime() {
        return collectTime;
    }
    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

}