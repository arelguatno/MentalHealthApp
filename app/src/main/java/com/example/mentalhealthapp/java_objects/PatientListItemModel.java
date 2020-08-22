package com.example.mentalhealthapp.java_objects;

public class PatientListItemModel {

    public String photoURL;
    public String patientName;
    public String time;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PatientListItemModel(String photoURL, String patientName, String time) {
        this.photoURL = photoURL;
        this.patientName = patientName;
        this.time = time;
    }
}
