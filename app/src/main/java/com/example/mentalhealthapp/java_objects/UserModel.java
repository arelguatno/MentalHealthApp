package com.example.mentalhealthapp.java_objects;

public class UserModel {

    int image;
    String uid;
    String display_name, first_name, last_name;
    String mobile_number;
    boolean isADoctor;

    public UserModel(int image, String uid, String display_name, String first_name, String last_name, String mobile_number, boolean isADoctor) {
        this.image = image;
        this.uid = uid;
        this.display_name = display_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
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

    public boolean isADoctor() {
        return isADoctor;
    }

    public void setADoctor(boolean ADoctor) {
        isADoctor = ADoctor;
    }
}
