package com.example.mentalhealthapp.java_objects;

public class ConsultationOfferingModel {

    int image;
    String title, desc;

    public ConsultationOfferingModel(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
