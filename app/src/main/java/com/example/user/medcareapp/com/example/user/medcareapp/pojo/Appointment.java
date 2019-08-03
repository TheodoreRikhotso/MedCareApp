package com.example.user.medcareapp.com.example.user.medcareapp.pojo;

public class Appointment {
    private String id;
    private String date;
    private String time;
    private String clinic;
    private String contact;


    public Appointment() {
    }

    public Appointment(String id, String date, String time, String clinc, String contact) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.clinic = clinc;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
