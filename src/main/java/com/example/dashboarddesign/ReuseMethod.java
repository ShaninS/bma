package com.example.dashboarddesign;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ReuseMethod {
    public void changeOnlYAnchor(String fxml, AnchorPane mainAnchor) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        AnchorPane anchorPane = loader.load();
        mainAnchor.getChildren().removeAll();
        mainAnchor.getChildren().setAll(anchorPane);


    }

}
