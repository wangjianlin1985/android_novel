package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Novel {
    /*С˵id*/
    private int novelId;
    public int getNovelId() {
        return novelId;
    }
    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    /*С˵���*/
    private NovelClass novelClassObj;
    public NovelClass getNovelClassObj() {
        return novelClassObj;
    }
    public void setNovelClassObj(NovelClass novelClassObj) {
        this.novelClassObj = novelClassObj;
    }

    /*С˵����*/
    private String novelName;
    public String getNovelName() {
        return novelName;
    }
    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    /*С˵ͼƬ*/
    private String novelPhoto;
    public String getNovelPhoto() {
        return novelPhoto;
    }
    public void setNovelPhoto(String novelPhoto) {
        this.novelPhoto = novelPhoto;
    }

    /*����*/
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /*������*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /*��������*/
    private Timestamp publishDate;
    public Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    /*ҳ��*/
    private int novelPageNum;
    public int getNovelPageNum() {
        return novelPageNum;
    }
    public void setNovelPageNum(int novelPageNum) {
        this.novelPageNum = novelPageNum;
    }

    /*����*/
    private int wordsNum;
    public int getWordsNum() {
        return wordsNum;
    }
    public void setWordsNum(int wordsNum) {
        this.wordsNum = wordsNum;
    }

    /*С˵�ļ�*/
    private String novelFile;
    public String getNovelFile() {
        return novelFile;
    }
    public void setNovelFile(String novelFile) {
        this.novelFile = novelFile;
    }

    /*�Ƿ��Ƽ�*/
    private String tjFlag;
    public String getTjFlag() {
        return tjFlag;
    }
    public void setTjFlag(String tjFlag) {
        this.tjFlag = tjFlag;
    }

    /*�Ķ���*/
    private int readCount;
    public int getReadCount() {
        return readCount;
    }
    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    /*С˵���*/
    private String novelDesc;
    public String getNovelDesc() {
        return novelDesc;
    }
    public void setNovelDesc(String novelDesc) {
        this.novelDesc = novelDesc;
    }

}