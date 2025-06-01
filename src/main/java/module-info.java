module com.example.dashboarddesign {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.dashboarddesign to javafx.fxml;
    exports com.example.dashboarddesign;
}