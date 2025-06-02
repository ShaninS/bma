package com.example.dashboarddesign;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.util.ArrayList;
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
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<Cadet> cadetList = FXCollections.observableArrayList();
    private static final String USER_FILE = "User.bin";
    private static final String CADET_FILE = "Cadet.bin";

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
            showAlert("Error", "Failed to load user data: " + e.getMessage());
        }

        // Load cadets
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CADET_FILE))) {
            List<Cadet> savedCadets = (List<Cadet>) in.readObject();
            cadetList.setAll(savedCadets);
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - that's okay
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load cadet data: " + e.getMessage());
        }

        refreshTable();
    }

    private void showDetails(Object item) {
        if (item == null) return;

        if (item instanceof User) {
            User user = (User) item;
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
        else if (item instanceof Cadet) {
            Cadet cadet = (Cadet) item;
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
    }

    @FXML
    private void UserAddButtonOnAction(ActionEvent event) {
        String id = UserIDTextfield.getText().trim();
        String name = UserNameTextfield.getText().trim();
        String email = UserEmailTextfield.getText().trim();
        String type = userTypeComboBox.getValue();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert("Error", "ID and Name are required!");
            return;
        }

        if (isDuplicateId(id)) {
            showAlert("Error", "ID already exists!");
            return;
        }

        if ("User".equals(type)) {
            String username = UserUserNameTextfield.getText().trim();
            String password = UserUserPasswordTextfield.getText().trim();
            String role = userRoleComboBox.getValue();
            String status = userStatusComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null || status == null) {
                showAlert("Error", "All fields are required for Users!");
                return;
            }

            User newUser = new User(id, name, email, username, password, role, status);
            userList.add(newUser);
            saveUserData();
        } else {
            String username = UserUserNameTextfield.getText().trim();
            String password = UserUserPasswordTextfield.getText().trim();
            String batch = UserBatchTextfield.getText().trim();

            if (username.isEmpty() || password.isEmpty() || batch.isEmpty()) {
                showAlert("Error", "All fields are required for Cadets!");
                return;
            }

            Cadet newCadet = new Cadet(id, name, email, username, password, batch, "Phase 1");
            cadetList.add(newCadet);
            saveCadetData();
        }

        refreshTable();
        clearForm();
    }


    @FXML
    private void UserUpdateButtonOnAction(ActionEvent event) {
        Object selected = UserDataEntryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No item selected!");
            return;
        }

        String id = UserIDTextfield.getText().trim();
        String name = UserNameTextfield.getText().trim();
        String email = UserEmailTextfield.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert("Error", "ID and Name are required!");
            return;
        }

        if (selected instanceof User) {
            User user = (User) selected;
            user.setName(name);
            user.setEmail(email);
            user.setUsername(UserUserNameTextfield.getText().trim());
            user.setPassword(UserUserPasswordTextfield.getText().trim());
            user.setRole(userRoleComboBox.getValue());
            user.setStatus(userStatusComboBox.getValue());
            saveUserData();
        }
        else if (selected instanceof Cadet) {
            Cadet cadet = (Cadet) selected;
            cadet.setName(name);
            cadet.setEmail(email);
            saveCadetData();
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void UserDeleteButtonOnAction(ActionEvent event) {
        Object selected = UserDataEntryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "No item selected!");
            return;
        }

        if (selected instanceof User) {
            userList.remove(selected);
            saveUserData();
        }
        else if (selected instanceof Cadet) {
            cadetList.remove(selected);
            saveCadetData();
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
            showAlert("Error", "Failed to save user data: " + e.getMessage());
        }
    }

    private void saveCadetData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CADET_FILE))) {
            out.writeObject(new ArrayList<>(cadetList));
        } catch (IOException e) {
            showAlert("Error", "Failed to save cadet data: " + e.getMessage());
        }
    }

    private void clearForm() {
        UserIDTextfield.clear();
        UserNameTextfield.clear();
        UserEmailTextfield.clear();
        UserUserNameTextfield.clear();
        UserUserPasswordTextfield.clear();
        userTypeComboBox.getSelectionModel().selectFirst();
        userRoleComboBox.getSelectionModel().clearSelection();
        userStatusComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}