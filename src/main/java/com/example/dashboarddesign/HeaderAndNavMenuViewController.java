package com.example.dashboarddesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderAndNavMenuViewController implements Initializable {

    @FXML
    private Button AuditLogsMenuButton;

    @FXML
    private Button CadetMenuButton;

    @FXML
    private Label CadetMenuLabel;

    @FXML
    private Button DashboardMenuButton;

    @FXML
    private Label DashboardMenuLabel;

    @FXML
    private Button LogoutButton;

    @FXML
    private Label LogsMenuLabel;

    @FXML
    private Button ProfileMenuButton;

    @FXML
    private Label ProfileMenuLabel;

    @FXML
    private Button ReportsMenuButton;

    @FXML
    private Label ReportsMenuLabel;

    @FXML
    private Label SettingsMenuLabel;

    @FXML
    private Button SystemSettingsMenuButton;

    @FXML
    private Label UserMenuLabel;

    @FXML
    private Button UsersMenuButton;

    @FXML
    private Label dashBoardLabel;

    @FXML
    private AnchorPane mainAnchor;

    private final ReuseMethod reuseMethod = new ReuseMethod();

    public void DashboardMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(DashboardMenuButton);
        dashBoardLabel.setText(DashboardMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("dashboard-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void CadetMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(CadetMenuButton);
        dashBoardLabel.setText(CadetMenuLabel.getText());
        try {
            reuseMethod.changeOnlYAnchor("cadet-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void ReportsMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(ReportsMenuButton);
        dashBoardLabel.setText(ReportsMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("reports-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void UsersMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(UsersMenuButton);
        dashBoardLabel.setText(UserMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("users-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void SystemSettingsMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(SystemSettingsMenuButton);
        dashBoardLabel.setText(SettingsMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("system-settings-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void AuditLogsMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(AuditLogsMenuButton);
        dashBoardLabel.setText(LogsMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("audit-logs-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void ProfileMenuButtonOnAction(ActionEvent actionEvent) {

        setActiveButton(ProfileMenuButton);
        dashBoardLabel.setText(ProfileMenuLabel.getText());

        try {
            reuseMethod.changeOnlYAnchor("profile-form-view.fxml" , mainAnchor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void LogoutButtonOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            reuseMethod.changeOnlYAnchor("dashboard-form-view.fxml" , mainAnchor);
            setActiveButton(DashboardMenuButton);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setActiveButton(Button activeButton) {
        Button[] allButtons = {
                DashboardMenuButton, CadetMenuButton, ReportsMenuButton,
                UsersMenuButton, SystemSettingsMenuButton, AuditLogsMenuButton, ProfileMenuButton
        };

        for (Button btn : allButtons) {
            // Reset style: transparent background, no border by default
            btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        }

        // Apply border color to active button with transparent background
        activeButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #000000;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 5px;"
        );

        System.out.println("Activated: " + activeButton.getId());
    }



}
