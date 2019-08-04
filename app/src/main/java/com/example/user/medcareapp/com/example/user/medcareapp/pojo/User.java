package com.example.user.medcareapp.com.example.user.medcareapp.pojo;


import java.util.List;

public class User {
    private String id;
    private String idNo;
    private String  name;
    private String surname;
    private String  contact;
    private String  email;
private  String uriImage;
    private String illnesses;
    private String addresses;
    private String dob;
    private String click;
    private String kins;

    public User() {

    }

    public User(String id, String idNo, String name, String surname, String contact, String email, String illnesses, String addresses, String dob, String click, String kins) {
        this.id = id;
        this.idNo = idNo;
        this.name = name;
        this.surname = surname;
        this.contact = contact;
        this.email = email;
        this.illnesses = illnesses;
        this.addresses = addresses;
        this.dob = dob;
        this.click = click;
        this.kins = kins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(String illnesses) {
        this.illnesses = illnesses;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getKins() {
        return kins;
    }

    public void setKins(String kins) {
        this.kins = kins;
    }
}
