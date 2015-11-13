package com.example.myapp2.model;


/**
 * Created by 孔轩志 on 2015/11/12.
 */
public class talkRecord {

    private int ID;

    private int talkWith; // 和谁聊天
    private String content;  // 聊天内容
    private String  sendTime;  //发送或接收时间
    private String status;  //是否已经送达

    private int backColor;  // 背景色

    private Boolean isSelf;

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTalkWith() {
        return talkWith;
    }

    public void setTalkWith(int talkWith) {
        this.talkWith = talkWith;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }
}
