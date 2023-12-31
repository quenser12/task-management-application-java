package com.example.taskmanagementapplicationjava;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class RegistrationController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void register(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setTitle("Dashboard");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
