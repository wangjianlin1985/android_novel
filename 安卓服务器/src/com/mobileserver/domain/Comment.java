package com.mobileserver.domain;

public class Comment {
    /*����id*/
    private int commentId;
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /*����С˵*/
    private int novelObj;
    public int getNovelObj() {
        return novelObj;
    }
    public void setNovelObj(int novelObj) {
        this.novelObj = novelObj;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*������*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String commentTime;
    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

}