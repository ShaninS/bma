package com.example.dashboarddesign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CadetFormViewController {
    // UI Components
    @FXML private TextField CadetIDTextField;
    @FXML private TextField CadetNameTextField;
    @FXML private TextField CadetEmailTextField;
    @FXML private TableView<Cadet> CadetEntryTable;
    @FXML private TableColumn<Cadet, String> idColumn;
    @FXML private TableColumn<Cadet, String> nameColumn;
    @FXML private TableColumn<Cadet, String> emailColumn;

    // Data
    private final ObservableList<Cadet> cadetList = FXCollections.observableArrayList();
    private static final String FILE_NAME = "Cadet.bin";

    @FXML
    public void initialize() {
        // Initialize only if components are not null
        if (idColumn != null && nameColumn != null && emailColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        }

        if (CadetEntryTable != null) {
            CadetEntryTable.setItems(cadetList);
        }

        loadCadetData();

        if (CadetEntryTable != null) {
            CadetEntryTable.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldSelection, newSelection) -> showCadetDetails(newSelection));
        }
    }

    private void loadCadetData() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                    List<Cadet> savedCadets = (List<Cadet>) in.readObject();
                    cadetList.setAll(savedCadets);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading cadet data: " + e.getMessage());
        }
    }



    private void saveCadetData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(new ArrayList<>(cadetList));
        } catch (IOException e) {
            showAlert("Error", "Failed to save cadet data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupSelectionListener() {
        CadetEntryTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showCadetDetails(newSelection));
    }

    private void showCadetDetails(Cadet cadet) {
        if (cadet != null) {
            CadetIDTextField.setText(cadet.getId());
            CadetNameTextField.setText(cadet.getName());
            CadetEmailTextField.setText(cadet.getEmail());
        }
    }



    // Button Actions
    @FXML
    private void CadetAddButtonOnAction() {
        String id = CadetIDTextField.getText().trim();
        String name = CadetNameTextField.getText().trim();
        String email = CadetEmailTextField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields");
            return;
        }

        if (cadetList.stream().anyMatch(c -> c.getId().equals(id))) {
            showAlert("Error", "Cadet ID already exists");
            return;
        }

        cadetList.add(new Cadet(id, name, email));
        saveCadetData();
        clearFields();

        showAlert("Success", "Cadet added successfully");
    }

    @FXML
    private void CadetUpdateButtonOnAction() {
        Cadet selectedCadet = CadetEntryTable.getSelectionModel().getSelectedItem();
        if (selectedCadet == null) {
            showAlert("Error", "No cadet selected");
            return;
        }

        String name = CadetNameTextField.getText().trim();
        String email = CadetEmailTextField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields");
            return;
        }

        selectedCadet.setName(name);
        selectedCadet.setEmail(email);
        CadetEntryTable.refresh();
        saveCadetData();
        showAlert("Success", "Cadet updated successfully");
    }

    @FXML
    private void CadetDeleteButtonOnAction() {
        Cadet selectedCadet = CadetEntryTable.getSelectionModel().getSelectedItem();
        if (selectedCadet == null) {
            showAlert("Error", "No cadet selected");
            return;
        }

        cadetList.remove(selectedCadet);
        saveCadetData();
        clearFields();

        showAlert("Success", "Cadet deleted successfully");
    }

    @FXML
    private void CadetClearButtonOnAction() {
        clearFields();
        CadetEntryTable.getSelectionModel().clearSelection();
    }

    // Helper Methods
    private void clearFields() {
        CadetIDTextField.clear();
        CadetNameTextField.clear();
        CadetEmailTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}