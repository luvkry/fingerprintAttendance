package com.example.parsaniahardik.signature_view;

public class UserObject {

    private String name;
    private String pass;
    private String cfmPass;
    private String contact;
    private boolean loginOption;



    public UserObject(String name, String pass, String cfmPass, String contact, boolean loginOption) {
        this.name = name;
        this.pass = pass;
        this.cfmPass = cfmPass;
        this.contact = contact;
        this.loginOption = true;
    }

    public void setName(String name){ this.name = name;}

    public void setPass(String pass){ this.pass = pass;}

    public void setCfmPass(String cfmPass){ this.cfmPass = cfmPass;}

    public void setContact(String contact){ this.contact = contact;}

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getCfmPass() {
        return cfmPass;
    }

    public String getContact() { return contact;}

    public boolean isLoginOption() {
        return loginOption;
    }
}