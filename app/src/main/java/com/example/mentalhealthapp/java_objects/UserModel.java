package com.example.mentalhealthapp.java_objects;

public class UserModel {

    int image;
    String uid, date_creation;
    String display_name, first_name, last_name;
    String mobile_number, email;
    boolean isADoctor;

    public UserModel(int image, String uid, String date_creation, String display_name, String first_name, String last_name, String mobile_number, String email, boolean isADoctor) {
        this.image = image;
        this.uid = uid;
        this.date_creation = date_creation;
        this.display_name = display_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.email = email;
        this.isADoctor = isADoctor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isADoctor() {
        return isADoctor;
    }

    public void setToDoctor(boolean ADoctor) {
        isADoctor = ADoctor;
    }
}
