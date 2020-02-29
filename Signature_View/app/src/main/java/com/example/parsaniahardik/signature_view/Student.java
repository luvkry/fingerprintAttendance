package com.example.parsaniahardik.signature_view;

import android.graphics.Bitmap;
import java.util.Date;

public class Student {
    private String name;
    private String password;
    private Integer contact;
    private String studID;

    //private String signString;
    //private Bitmap signature;

    public Student() {
    }

    public Student(String name, String studID, Integer contact, String password) {

        this.name = name;
        this.studID = studID;
        this.contact = contact;
        this.password = password;
    }

    //Date sessionExpiryDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    /*Get */

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getContact() {
        return contact;
    }

    public String getStudID(){
        return studID;
    }
}
