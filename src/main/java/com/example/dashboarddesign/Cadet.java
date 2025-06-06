package com.example.dashboarddesign;

import java.io.Serial;
import java.io.Serializable;

public class Cadet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String batch;
    private String trainingPhase;

    // Constructor
    public Cadet(String id, String name, String email, String username,
                 String password, String batch, String trainingPhase) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.batch = batch;
        this.trainingPhase = trainingPhase;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getBatch() { return batch; }
    public String getTrainingPhase() { return trainingPhase; }

    // This method is kept for backward compatibility
    public String getPhase() {
        return trainingPhase;
    }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setBatch(String batch) { this.batch = batch; }
    public void setTrainingPhase(String trainingPhase) { this.trainingPhase = trainingPhase; }

    // Optional: toString() method for debugging
    @Override
    public String toString() {
        return "Cadet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", batch='" + batch + '\'' +
                ", trainingPhase='" + trainingPhase + '\'' +
                '}';
    }
}