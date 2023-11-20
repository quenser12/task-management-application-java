package com.example.taskmanagementapplicationjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void view(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setTitle("Dashboard");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new-task.fxml"));
        Parent root = loader.load();

        // Create the dialog stage
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Dialog Window");
        dialogStage.setScene(new Scene(root));

        // Show the dialog and wait
        dialogStage.showAndWait();
    }
}
