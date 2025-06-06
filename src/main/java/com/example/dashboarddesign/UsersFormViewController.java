package com.example.dashboarddesign;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersFormViewController {

    // FXML Components
    @FXML private TableView<Object> UserDataEntryTable;
    @FXML private TableColumn<Object, String> userColumnID;
    @FXML private TableColumn<Object, String> userColumnName;
    @FXML private TableColumn<Object, String> userColumnEmail;
    @FXML private TableColumn<Object, String> userColumnUserName;
    @FXML private TableColumn<Object, String> userColumnPassword;
    @FXML private TableColumn<Object, String> userColumnRole;
    @FXML private TableColumn<Object, String> userColumnStatus;
    @FXML private TableColumn<Object, String> userColumnType;
    @FXML private TableColumn<Object, String> userColumnBatch;

    @FXML private TextField UserIDTextfield;
    @FXML private TextField UserNameTextfield;
    @FXML private TextField UserEmailTextfield;
    @FXML private TextField UserUserNameTextfield;
    @FXML private TextField UserUserPasswordTextfield;
    @FXML private TextField UserBatchTextfield;

    @FXML private ComboBox<String> userTypeComboBox;
    @FXML private ComboBox<String> userRoleComboBox;
    @FXML private ComboBox<String> userStatusComboBox;

    // Data
    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObservableList<Cadet> cadetList = FXCollections.observableArrayList();
    private static final String USER_FILE = "User.bin";
    private static final String CADET_FILE = "Cadet.bin";
    private static final String AUDIT_FILE = "UserAudit.bin";

    @FXML
    public void initialize() {
        // Configure table columns
        configureTableColumns();

        // Initialize combo boxes
        userTypeComboBox.setItems(FXCollections.observableArrayList("User", "Cadet"));
        userTypeComboBox.getSelectionModel().selectFirst();

        userRoleComboBox.setItems(FXCollections.observableArrayList(
                "Admin", "Cadet", "Commandant", "Drill Master",
                "Instructor", "Medical Officer", "Logistics Officer"
        ));

        userStatusComboBox.setItems(FXCollections.observableArrayList("Active", "Inactive"));

        // Load data
        loadAllData();

        // Set up selection listener
        UserDataEntryTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> showDetails(newSelection)
        );

        // Handle type selection changes
        userTypeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> handleTypeChange(newVal)
        );
    }

    private void configureTableColumns() {
        userColumnID.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getId());
            } else {
                return new SimpleStringProperty(((Cadet) item).getId());
            }
        });

        userColumnName.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getName());
            } else {
                return new SimpleStringProperty(((Cadet) item).getName());
            }
        });

        userColumnEmail.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getEmail());
            } else {
                return new SimpleStringProperty(((Cadet) item).getEmail());
            }
        });

        userColumnUserName.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getUsername());
            } else {
                return new SimpleStringProperty(((Cadet) item).getUsername());
            }
        });

        userColumnPassword.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getPassword());
            } else {
                return new SimpleStringProperty(((Cadet) item).getPassword());
            }
        });

        userColumnRole.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getRole());
            } else {
                return new SimpleStringProperty("Cadet");
            }
        });

        userColumnStatus.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof User) {
                return new SimpleStringProperty(((User) item).getStatus());
            } else {
                return new SimpleStringProperty("Active");
            }
        });

        userColumnType.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            return new SimpleStringProperty(item instanceof User ? "User" : "Cadet");
        });

        userColumnBatch.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof Cadet) {
                return new SimpleStringProperty(((Cadet) item).getBatch());
            }
            return new SimpleStringProperty("");
        });
    }

    private void handleTypeChange(String newType) {
        if ("User".equals(newType)) {
            UserUserNameTextfield.setVisible(true);
            UserUserPasswordTextfield.setVisible(true);
            UserBatchTextfield.setVisible(false);
            userRoleComboBox.setDisable(false);
            userStatusComboBox.setDisable(false);
        } else {
            UserUserNameTextfield.setVisible(true);
            UserUserPasswordTextfield.setVisible(true);
            UserBatchTextfield.setVisible(true);
            userRoleComboBox.getSelectionModel().select("Cadet");
            userRoleComboBox.setDisable(true);
            userStatusComboBox.getSelectionModel().select("Active");
            userStatusComboBox.setDisable(true);
        }
    }

    private void loadAllData() {
        // Load users
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            List<User> savedUsers = (List<User>) in.readObject();
            userList.setAll(savedUsers);
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - that's okay
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Failed to load user data: " + e.getMessage());
        }

        // Load cadets
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CADET_FILE))) {
            List<Cadet> savedCadets = (List<Cadet>) in.readObject();
            cadetList.setAll(savedCadets);
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - that's okay
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Failed to load cadet data: " + e.getMessage());
        }

        refreshTable();
    }

    private void showDetails(Object item) {
        switch (item) {
            case null -> {
                return;
            }
            case User user -> {
                UserIDTextfield.setText(user.getId());
                UserNameTextfield.setText(user.getName());
                UserEmailTextfield.setText(user.getEmail());
                UserUserNameTextfield.setText(user.getUsername());
                UserUserPasswordTextfield.setText(user.getPassword());
                UserBatchTextfield.setText("");
                userTypeComboBox.getSelectionModel().select("User");
                userRoleComboBox.getSelectionModel().select(user.getRole());
                userStatusComboBox.getSelectionModel().select(user.getStatus());
            }
            case Cadet cadet -> {
                UserIDTextfield.setText(cadet.getId());
                UserNameTextfield.setText(cadet.getName());
                UserEmailTextfield.setText(cadet.getEmail());
                UserUserNameTextfield.setText(cadet.getUsername());
                UserUserPasswordTextfield.setText(cadet.getPassword());
                UserBatchTextfield.setText(cadet.getBatch());
                userTypeComboBox.getSelectionModel().select("Cadet");
                userRoleComboBox.getSelectionModel().select("Cadet");
                userStatusComboBox.getSelectionModel().select("Active");
            }
            default -> {
            }
        }

    }

    @FXML
    private void UserAddButtonOnAction(ActionEvent event) {
        String id = UserIDTextfield.getText().trim();
        String name = UserNameTextfield.getText().trim();
        String email = UserEmailTextfield.getText().trim();
        String type = userTypeComboBox.getValue();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert("ID and Name are required!");
            return;
        }

        if (isDuplicateId(id)) {
            showAlert("ID already exists!");
            return;
        }

        if ("User".equals(type)) {
            String username = UserUserNameTextfield.getText().trim();
            String password = UserUserPasswordTextfield.getText().trim();
            String role = userRoleComboBox.getValue();
            String status = userStatusComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null || status == null) {
                showAlert("All fields are required for Users!");
                return;
            }

            User newUser = new User(id, name, email, username, password, role, status);
            userList.add(newUser);
            saveUserData();
            saveAuditLog("ADD", newUser);
        } else {
            String username = UserUserNameTextfield.getText().trim();
            String password = UserUserPasswordTextfield.getText().trim();
            String batch = UserBatchTextfield.getText().trim();

            if (username.isEmpty() || password.isEmpty() || batch.isEmpty()) {
                showAlert("All fields are required for Cadets!");
                return;
            }

            Cadet newCadet = new Cadet(id, name, email, username, password, batch, "Phase 1");
            cadetList.add(newCadet);
            saveCadetData();
            saveAuditLog("ADD", newCadet);
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void UserUpdateButtonOnAction(ActionEvent event) {
        Object selected = UserDataEntryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No item selected!");
            return;
        }

        String id = UserIDTextfield.getText().trim();
        String name = UserNameTextfield.getText().trim();
        String email = UserEmailTextfield.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert("ID and Name are required!");
            return;
        }

        if (selected instanceof User user) {
            User originalUser = new User(user.getId(), user.getName(), user.getEmail(),
                    user.getUsername(), user.getPassword(),
                    user.getRole(), user.getStatus());

            user.setName(name);
            user.setEmail(email);
            user.setUsername(UserUserNameTextfield.getText().trim());
            user.setPassword(UserUserPasswordTextfield.getText().trim());
            user.setRole(userRoleComboBox.getValue());
            user.setStatus(userStatusComboBox.getValue());

            saveUserData();
            saveAuditLog("UPDATE", originalUser);
        }
        else if (selected instanceof Cadet cadet) {
            Cadet originalCadet = new Cadet(cadet.getId(), cadet.getName(), cadet.getEmail(),
                    cadet.getUsername(), cadet.getPassword(),
                    cadet.getBatch(), cadet.getTrainingPhase());

            cadet.setName(name);
            cadet.setEmail(email);
            cadet.setUsername(UserUserNameTextfield.getText().trim());
            cadet.setPassword(UserUserPasswordTextfield.getText().trim());
            cadet.setBatch(UserBatchTextfield.getText().trim());

            saveCadetData();
            saveAuditLog("UPDATE", originalCadet);
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void UserDeleteButtonOnAction(ActionEvent event) {
        Object selected = UserDataEntryTable.getSelectionModel().getSelectedItem();
        switch (selected) {
            case null -> {
                showAlert("No item selected!");
                return;
            }
            case User user -> {
                saveAuditLog("DELETE", user);
                userList.remove(selected);
                saveUserData();
            }
            case Cadet cadet -> {
                saveAuditLog("DELETE", cadet);
                cadetList.remove(selected);
                saveCadetData();
            }
            default -> {
            }
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void UserClearButtonOnAction(ActionEvent event) {
        clearForm();
    }

    private boolean isDuplicateId(String id) {
        return userList.stream().anyMatch(u -> u.getId().equals(id)) ||
                cadetList.stream().anyMatch(c -> c.getId().equals(id));
    }

    private void refreshTable() {
        ObservableList<Object> allData = FXCollections.observableArrayList();
        allData.addAll(userList);
        allData.addAll(cadetList);
        UserDataEntryTable.setItems(allData);
        UserDataEntryTable.refresh();
    }

    private void saveUserData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(new ArrayList<>(userList));
        } catch (IOException e) {
            showAlert("Failed to save user data: " + e.getMessage());
        }
    }

    private void saveCadetData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CADET_FILE))) {
            out.writeObject(new ArrayList<>(cadetList));
        } catch (IOException e) {
            showAlert("Failed to save cadet data: " + e.getMessage());
        }
    }

    private void saveAuditLog(String action, Object entity) {
        List<UserAudit> auditLogs = loadAuditLogs();

        if (entity instanceof User user) {
            auditLogs.add(new UserAudit(
                    new Date(),
                    action,
                    "User",
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getRole(),
                    user.getStatus(),
                    ""
            ));
        } else if (entity instanceof Cadet cadet) {
            auditLogs.add(new UserAudit(
                    new Date(),
                    action,
                    "Cadet",
                    cadet.getId(),
                    cadet.getName(),
                    cadet.getEmail(),
                    cadet.getUsername(),
                    "Cadet",
                    "Active",
                    cadet.getBatch()
            ));
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(AUDIT_FILE))) {
            out.writeObject(auditLogs);
        } catch (IOException e) {
            showAlert("Failed to save audit log: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<UserAudit> loadAuditLogs() {
        File file = new File(AUDIT_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(AUDIT_FILE))) {
                return (List<UserAudit>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Failed to load audit logs: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    private void clearForm() {
        UserIDTextfield.clear();
        UserNameTextfield.clear();
        UserEmailTextfield.clear();
        UserUserNameTextfield.clear();
        UserUserPasswordTextfield.clear();
        UserBatchTextfield.clear();
        userTypeComboBox.getSelectionModel().selectFirst();
        userRoleComboBox.getSelectionModel().clearSelection();
        userStatusComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public record UserAudit(Date timestamp, String action, String entityType, String userId, String userName,
                            String userEmail, String username, String role, String status,
                            String batch) implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;

        @Override
            public String toString() {
                return "UserAudit{" +
                        "timestamp=" + timestamp +
                        ", action='" + action + '\'' +
                        ", entityType='" + entityType + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userEmail='" + userEmail + '\'' +
                        ", username='" + username + '\'' +
                        ", role='" + role + '\'' +
                        ", status='" + status + '\'' +
                        ", batch='" + batch + '\'' +
                        '}';
            }
        }
}