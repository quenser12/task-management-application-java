package com.example.taskmanagementapplicationjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DashboardController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane taskContainer;


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

    @FXML
    public void initialize() {
        // Load data when the FXML is initialized
        loadData();
    }
    @FXML
    private void loadData(){
        try{
            Connection con=DBConnection.getConnection();
            con.createStatement();
            String sql = "SELECT * FROM TASKS";
            System.out.println(sql);
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            // Clear existing panes from taskContainer
            taskContainer.getChildren().clear();

            while(resultSet.next()){
                String title = resultSet.getString("title");
                String dueDate = resultSet.getString("due_date");
                String tags = resultSet.getString("tags");
//                String details = resultSet.getString("details");
//                String priority = resultSet.getString("priority");
//                String status = resultSet.getString("status");

                //Create a pane for each row
                Pane pane = createRowPane(title, tags, dueDate);

                // Add the pane to the taskContainer
                taskContainer.getChildren().add(pane);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Pane createRowPane(String title, String dueDate, String tags){
        Pane pane = new Pane();
        // Apply styles to match the FXML styling
        pane.setStyle("-fx-background-color: #F2F3F5; -fx-padding: 5px; -fx-pref-width: 247px; -fx-pref-height: 155px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-margin-top: 10px;");

        // Create labels to display data
        Label titleLabel = new Label( title);
        Label tagsLabel = new Label(tags);
        Label dueDateLabel = new Label(dueDate);

        // Add labels to the pane
//        pane.getChildren().addAll(titleLabel, tagsLabel,dueDateLabel);

        // Set label styles
        titleLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        tagsLabel.setStyle("-fx-font-size: 14px;");
        dueDateLabel.setStyle("-fx-background-color: #E7E8E9; -fx-font-size: 12px; -fx-padding: 5px;");

        // Add labels to the pane
        pane.getChildren().addAll(titleLabel, tagsLabel, dueDateLabel);

        // Set the position of labels
        titleLabel.setLayoutX(14);
        titleLabel.setLayoutY(14);

        tagsLabel.setLayoutX(42);
        tagsLabel.setLayoutY(47);

        dueDateLabel.setLayoutX(21);
        dueDateLabel.setLayoutY(81);
        System.out.println("Pane Created");
        return pane;
    }
}
