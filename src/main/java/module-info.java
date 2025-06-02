module com.example.dashboarddesign {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dashboarddesign to javafx.fxml;
    exports com.example.dashboarddesign;
}