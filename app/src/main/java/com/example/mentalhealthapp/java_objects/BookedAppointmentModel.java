package com.example.mentalhealthapp.java_objects;

public class BookedAppointmentModel {

    public String date;
    public String doctor_email;
    public String patient_email;
    public int price;
    public String video_room;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVideo_room() {
        return video_room;
    }

    public void setVideo_room(String video_room) {
        this.video_room = video_room;
    }
}
