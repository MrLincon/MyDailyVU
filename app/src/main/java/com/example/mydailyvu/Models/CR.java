package com.example.mydailyvu.Models;

import com.google.firebase.firestore.Exclude;

public class CR {
    public String name, section, email, id, semester, contact, imageUrl;

    @Exclude
    private String userId;

    public CR() {
    }

    public CR(String name, String section, String email, String id, String semester, String contact, String imageUrl) {
        this.name = name;
        this.section = section;
        this.email = email;
        this.id = id;
        this.semester = semester;
        this.contact = contact;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getSemester() {
        return semester;
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
