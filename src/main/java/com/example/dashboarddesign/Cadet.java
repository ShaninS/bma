package com.example.dashboarddesign;

import java.io.Serializable;

public class Cadet implements Serializable {
    private String id;
    private String name;
    private String email;

    public Cadet(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters...
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}