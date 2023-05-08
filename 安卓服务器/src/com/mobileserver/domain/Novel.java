package com.mobileserver.domain;

public class Novel {
    /*小说id*/
    private int novelId;
    public int getNovelId() {
        return novelId;
    }
    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    /*小说类别*/
    private int novelClassObj;
    public int getNovelClassObj() {
        return novelClassObj;
    }
    public void setNovelClassObj(int novelClassObj) {
        this.novelClassObj = novelClassObj;
    }

    /*小说名称*/
    private String novelName;
    public String getNovelName() {
        return novelName;
    }
    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    /*小说图片*/
    private String novelPhoto;
    public String getNovelPhoto() {
        return novelPhoto;
    }
    public void setNovelPhoto(String novelPhoto) {
        this.novelPhoto = novelPhoto;
    }

    /*作者*/
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /*出版社*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /*出版日期*/
    private java.sql.Timestamp publishDate;
    public java.sql.Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(java.sql.Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    /*页数*/
    private int novelPageNum;
    public int getNovelPageNum() {
        return novelPageNum;
    }
    public void setNovelPageNum(int novelPageNum) {
        this.novelPageNum = novelPageNum;
    }

    /*字数*/
    private int wordsNum;
    public int getWordsNum() {
        return wordsNum;
    }
    public void setWordsNum(int wordsNum) {
        this.wordsNum = wordsNum;
    }

    /*小说文件*/
    private String novelFile;
    public String getNovelFile() {
        return novelFile;
    }
    public void setNovelFile(String novelFile) {
        this.novelFile = novelFile;
    }

    /*是否推荐*/
    private String tjFlag;
    public String getTjFlag() {
        return tjFlag;
    }
    public void setTjFlag(String tjFlag) {
        this.tjFlag = tjFlag;
    }

    /*阅读量*/
    private int readCount;
    public int getReadCount() {
        return readCount;
    }
    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    /*小说简介*/
    private String novelDesc;
    public String getNovelDesc() {
        return novelDesc;
    }
    public void setNovelDesc(String novelDesc) {
        this.novelDesc = novelDesc;
    }

}