package com.example.user.medcareapp.com.example.user.medcareapp.pojo;

public class Progress {
    private String date;
    private int num;

    public Progress() {

    }

    public Progress(String date, int num) {
        this.date = date;
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
