package com.example.dashboarddesign;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardFormViewController {
    // UI Components
    @FXML private Label TotalCadetCountLabel;
    @FXML private Label TotalCommandoCountLabel;
    @FXML private Label TotalEquipCountLabel;
    @FXML private Label TotalInstructorCountLabel;
    @FXML private Label TotalMOCountLabel;
    @FXML private Label TotalUserCountLabel;

    // Constants for binary file names
    private static final String CADET_FILE = "Cadet.bin";
    // private static final String COMMANDO_FILE = "Commando.bin";
    // private static final String EQUIPMENT_FILE = "Equipment.bin";
    // private static final String INSTRUCTOR_FILE = "Instructor.bin";
    // private static final String MO_FILE = "MO.bin";
    // private static final String USER_FILE = "User.bin";

    @FXML
    public void initialize() {
        // Update all counts when dashboard loads
        updateTotalCadetCount();
        // updateTotalCommandoCount();
        // updateTotalEquipCount();
        // updateTotalInstructorCount();
        // updateTotalMOCount();
        // updateTotalUserCount();
    }

    // Generic method to count records from any binary file
    private int getRecordCount(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return 0;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<?> records = (List<?>) in.readObject();
            return records.size();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading " + filename + ": " + e.getMessage());
            return 0;
        }
    }

    private void updateTotalCadetCount() {
        int count = getRecordCount(CADET_FILE);
        TotalCadetCountLabel.setText(String.valueOf(count));
    }

    /*private void updateTotalCommandoCount() {
        int count = getRecordCount(COMMANDO_FILE);
        TotalCommandoCountLabel.setText(String.valueOf(count));
    }

    private void updateTotalEquipCount() {
        int count = getRecordCount(EQUIPMENT_FILE);
        TotalEquipCountLabel.setText(String.valueOf(count));
    }

    private void updateTotalInstructorCount() {
        int count = getRecordCount(INSTRUCTOR_FILE);
        TotalInstructorCountLabel.setText(String.valueOf(count));
    }

    private void updateTotalMOCount() {
        int count = getRecordCount(MO_FILE);
        TotalMOCountLabel.setText(String.valueOf(count));
    }

    private void updateTotalUserCount() {
        int count = getRecordCount(USER_FILE);
        TotalUserCountLabel.setText(String.valueOf(count));
    }*/

    // Refresh all counts (can be called from other controllers when data changes)
    public void refreshAllCounts() {
        updateTotalCadetCount();
        /*updateTotalCommandoCount();
        updateTotalEquipCount();
        updateTotalInstructorCount();
        updateTotalMOCount();
        updateTotalUserCount();*/
    }
}