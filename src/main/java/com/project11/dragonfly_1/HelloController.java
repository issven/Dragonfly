package com.project11.dragonfly_1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    protected String fxmlAddress = "DynamicSystemSolvers.fxml";

    @FXML
    protected void onDynamicSystemsClick() throws IOException {openNewWindow();}

    @FXML
    private void openNewWindow() throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlAddress));
        Parent root = fxmlLoader.load();

        // Create a new stage
        Stage newStage = new Stage();
        newStage.setTitle("Dynamic Systems");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}