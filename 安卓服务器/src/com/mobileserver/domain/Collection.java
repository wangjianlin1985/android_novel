package com.mobileserver.domain;

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
    private int novelObj;
    public int getNovelObj() {
        return novelObj;
    }
    public void setNovelObj(int novelObj) {
        this.novelObj = novelObj;
    }

    /*�ղ��û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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