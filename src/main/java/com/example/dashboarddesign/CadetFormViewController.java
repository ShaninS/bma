package com.example.dashboarddesign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadetFormViewController {
    // UI Components
    @FXML private TextField CadetIDTextField;
    @FXML private TextField CadetNameTextField;
    @FXML private TextField CadetEmailTextField;
    @FXML private TextField CadetUsernameTextField;
    @FXML private TextField CadetPasswordTextField;
    @FXML private TextField CadetBatchTextField;

    @FXML private TableView<Cadet> CadetEntryTable;
    @FXML private TableColumn<Cadet, String> idColumn;
    @FXML private TableColumn<Cadet, String> nameColumn;
    @FXML private TableColumn<Cadet, String> emailColumn;
    @FXML private TableColumn<Cadet, String> usernameColumn;
    @FXML private TableColumn<Cadet, String> passwordColumn;
    @FXML private TableColumn<Cadet, String> batchColumn;

    // Data
    private final ObservableList<Cadet> cadetList = FXCollections.observableArrayList();
    private static final String FILE_NAME = "Cadet.bin";
    private static final String FILE_NAME_AUDIT = "CadetAudit.bin";

    @FXML
    public void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        batchColumn.setCellValueFactory(new PropertyValueFactory<>("batch"));

        // Set table data
        CadetEntryTable.setItems(cadetList);

        // Load existing data
        loadCadetData();

        // Set up selection listener
        CadetEntryTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showCadetDetails(newSelection)
        );
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
            showAlert("Error", "Failed to load cadet data: " + e.getMessage());
        }
    }

    private void saveCadetData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(new ArrayList<>(cadetList));
        } catch (IOException e) {
            showAlert("Error", "Failed to save cadet data: " + e.getMessage());
        }
    }

    private void saveAuditLog(String action, Cadet cadet) {
        List<CadetAudit> auditLogs = loadAuditLogs();
        auditLogs.add(new CadetAudit(
                new Date(),
                action,
                cadet.getId(),
                cadet.getName(),
                cadet.getEmail(),
                cadet.getUsername(),
                cadet.getBatch()
        ));

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME_AUDIT))) {
            out.writeObject(auditLogs);
        } catch (IOException e) {
            showAlert("Error", "Failed to save audit log: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<CadetAudit> loadAuditLogs() {
        File file = new File(FILE_NAME_AUDIT);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME_AUDIT))) {
                return (List<CadetAudit>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Error", "Failed to load audit logs: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    private void showCadetDetails(Cadet cadet) {
        if (cadet != null) {
            CadetIDTextField.setText(cadet.getId());
            CadetNameTextField.setText(cadet.getName());
            CadetEmailTextField.setText(cadet.getEmail());
            CadetUsernameTextField.setText(cadet.getUsername());
            CadetPasswordTextField.setText(cadet.getPassword());
            CadetBatchTextField.setText(cadet.getBatch());
        }
    }

    @FXML
    private void CadetAddButtonOnAction() {
        String id = CadetIDTextField.getText().trim();
        String name = CadetNameTextField.getText().trim();
        String email = CadetEmailTextField.getText().trim();
        String username = CadetUsernameTextField.getText().trim();
        String password = CadetPasswordTextField.getText().trim();
        String batch = CadetBatchTextField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() ||
                username.isEmpty() || password.isEmpty() || batch.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields");
            return;
        }

        if (cadetList.stream().anyMatch(c -> c.getId().equals(id))) {
            showAlert("Error", "Cadet ID already exists");
            return;
        }

        Cadet newCadet = new Cadet(id, name, email, username, password, batch, "Phase 1");
        cadetList.add(newCadet);
        saveCadetData();
        saveAuditLog("ADD", newCadet);
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
        String username = CadetUsernameTextField.getText().trim();
        String password = CadetPasswordTextField.getText().trim();
        String batch = CadetBatchTextField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty() ||
                password.isEmpty() || batch.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields");
            return;
        }

        // Create a copy of the original cadet for audit purposes
        Cadet originalCadet = new Cadet(
                selectedCadet.getId(),
                selectedCadet.getName(),
                selectedCadet.getEmail(),
                selectedCadet.getUsername(),
                selectedCadet.getPassword(),
                selectedCadet.getBatch(),
                selectedCadet.getPhase()
        );

        selectedCadet.setName(name);
        selectedCadet.setEmail(email);
        selectedCadet.setUsername(username);
        selectedCadet.setPassword(password);
        selectedCadet.setBatch(batch);

        CadetEntryTable.refresh();
        saveCadetData();
        saveAuditLog("UPDATE", originalCadet);
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
        saveAuditLog("DELETE", selectedCadet);
        clearFields();
        showAlert("Success", "Cadet deleted successfully");
    }

    @FXML
    private void CadetClearButtonOnAction() {
        clearFields();
        CadetEntryTable.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        CadetIDTextField.clear();
        CadetNameTextField.clear();
        CadetEmailTextField.clear();
        CadetUsernameTextField.clear();
        CadetPasswordTextField.clear();
        CadetBatchTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    record CadetAudit(Date timestamp, String action, String cadetId, String cadetName, String cadetEmail,
                      String cadetUsername, String cadetBatch) implements Serializable {

        @Override
            public String toString() {
                return "CadetAudit{" +
                        "timestamp=" + timestamp +
                        ", action='" + action + '\'' +
                        ", cadetId='" + cadetId + '\'' +
                        ", cadetName='" + cadetName + '\'' +
                        ", cadetEmail='" + cadetEmail + '\'' +
                        ", cadetUsername='" + cadetUsername + '\'' +
                        ", cadetBatch='" + cadetBatch + '\'' +
                        '}';
            }
        }

}

