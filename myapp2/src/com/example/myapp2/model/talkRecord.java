package com.example.myapp2.model;


/**
 * Created by ����־ on 2015/11/12.
 */
public class talkRecord {

    private int ID;

    private int talkWith; // ��˭����
    private String content;  // ��������
    private String  sendTime;  //���ͻ����ʱ��
    private String status;  //�Ƿ��Ѿ��ʹ�

    private int backColor;  // ����ɫ

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
