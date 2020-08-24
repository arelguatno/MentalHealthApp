package com.example.mentalhealthapp.java_objects;

public class DoctorListItemModel {

    public String photoURL;
    public String docName;
    public String docEmail;

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String rating;
    public String time;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DoctorListItemModel(String s, String s1, String x, String v, String s2) {
        photoURL = s;
        docName = s1;
        docEmail = x;
        rating = v;
        time = s2;
    }




}
