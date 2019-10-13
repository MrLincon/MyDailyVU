package com.example.mydailyvu.Models;

import com.google.firebase.firestore.Exclude;

public class Teacher {
    public String name, dept, desg, code, room, email, contact, imageUrl;

    @Exclude
    private String userId;

    public Teacher() {
    }

    public Teacher(String name, String dept, String desg, String code, String room, String email, String contact, String imageUrl) {
        this.name = name;
        this.dept = dept;
        this.desg = desg;
        this.code = code;
        this.room = room;
        this.email = email;
        this.contact = contact;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getDesg() {
        return desg;
    }

    public String getCode() {
        return code;
    }

    public String getRoom() {
        return room;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
