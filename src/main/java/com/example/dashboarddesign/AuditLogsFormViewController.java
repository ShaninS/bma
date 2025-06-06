package com.example.dashboarddesign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AuditLogsFormViewController implements Initializable {

    @FXML private TableView<AuditEntry> auditLogsTable;
    @FXML private TableColumn<AuditEntry, Date> timestampColumn;
    @FXML private TableColumn<AuditEntry, String> actionColumn;
    @FXML private TableColumn<AuditEntry, String> entityTypeColumn;
    @FXML private TableColumn<AuditEntry, String> idColumn;
    @FXML private TableColumn<AuditEntry, String> nameColumn;
    @FXML private TableColumn<AuditEntry, String> emailColumn;
    @FXML private TableColumn<AuditEntry, String> usernameColumn;
    @FXML private TableColumn<AuditEntry, String> rolePhaseColumn;
    @FXML private TableColumn<AuditEntry, String> statusColumn;
    @FXML private TableColumn<AuditEntry, String> batchColumn;

    private final ObservableList<AuditEntry> auditLogs = FXCollections.observableArrayList();
    private static final String USER_AUDIT_FILE = "UserAudit.bin";
    private static final String CADET_AUDIT_FILE = "CadetAudit.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        loadAuditLogs();
    }

    private void configureTableColumns() {
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        entityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("entityType"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        rolePhaseColumn.setCellValueFactory(new PropertyValueFactory<>("rolePhase"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        batchColumn.setCellValueFactory(new PropertyValueFactory<>("batch"));


        timestampColumn.setCellFactory(column -> new TableCell<AuditEntry, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void loadAuditLogs() {
        List<AuditEntry> allAudits = new ArrayList<>();

        File userAuditFile = new File(USER_AUDIT_FILE);
        if (userAuditFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_AUDIT_FILE))) {
                List<UsersFormViewController.UserAudit> userAudits =
                        (List<UsersFormViewController.UserAudit>) in.readObject();
                allAudits.addAll(userAudits.stream()
                        .map(ua -> new AuditEntry(
                                ua.timestamp(),
                                ua.action(),
                                ua.entityType(),
                                ua.userId(),
                                ua.userName(),
                                ua.userEmail(),
                                ua.username(),
                                ua.role(),
                                ua.status(),
                                ua.batch()
                        ))
                        .toList());
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Failed to load user audit logs: " + e.getMessage());
            }
        }


        File cadetAuditFile = new File(CADET_AUDIT_FILE);
        if (cadetAuditFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CADET_AUDIT_FILE))) {
                List<CadetFormViewController.CadetAudit> cadetAudits =
                        (List<CadetFormViewController.CadetAudit>) in.readObject();
                allAudits.addAll(cadetAudits.stream()
                        .map(ca -> new AuditEntry(
                                ca.timestamp(),
                                ca.action(),
                                "Cadet",
                                ca.cadetId(),
                                ca.cadetName(),
                                ca.cadetEmail(),
                                ca.cadetUsername(),
                                "Phase 1", // Default phase for cadets
                                "Active",   // Default status for cadets
                                ca.cadetBatch()
                        ))
                        .toList());
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Failed to load cadet audit logs: " + e.getMessage());
            }
        }

        auditLogs.setAll(allAudits);
        auditLogsTable.setItems(auditLogs);
    }

    @FXML
    private void handleRefresh() {
        loadAuditLogs();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) auditLogsTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class AuditEntry {
        private final Date timestamp;
        private final String action;
        private final String entityType;
        private final String id;
        private final String name;
        private final String email;
        private final String username;
        private final String rolePhase;
        private final String status;
        private final String batch;

        public AuditEntry(Date timestamp, String action, String entityType,
                          String id, String name, String email,
                          String username, String rolePhase,
                          String status, String batch) {
            this.timestamp = timestamp;
            this.action = action;
            this.entityType = entityType;
            this.id = id;
            this.name = name;
            this.email = email;
            this.username = username;
            this.rolePhase = rolePhase;
            this.status = status;
            this.batch = batch;
        }

        // Add proper getters
        public Date getTimestamp() { return timestamp; }
        public String getAction() { return action; }
        public String getEntityType() { return entityType; }
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getUsername() { return username; }
        public String getRolePhase() { return rolePhase; }
        public String getStatus() { return status; }
        public String getBatch() { return batch; }
    }
}