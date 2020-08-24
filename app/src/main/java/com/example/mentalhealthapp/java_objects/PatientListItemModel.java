package com.example.mentalhealthapp.java_objects;

public class PatientListItemModel {

    public String photoURL;
    public String patientName;
    public String patientEmail;
    public String videoRoom;
    public String dateTime;

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getVideoRoom() {
        return videoRoom;
    }

    public void setVideoRoom(String videoRoom) {
        this.videoRoom = videoRoom;
    }

    public PatientListItemModel() {}

    public PatientListItemModel(String photoURL, String patientName, String patientEmail, String videoRoom, String dateTime) {
        this.photoURL = photoURL;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.videoRoom = videoRoom;
        this.dateTime = dateTime;
    }
}
