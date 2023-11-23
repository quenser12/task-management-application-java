package com.example.taskmanagementapplicationjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Pane inProgressContainer;
    @FXML
    private Pane completedContainer;


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
            String sql = "SELECT * FROM TASKS WHERE status  = 'In Progress'";
            String sqlPendingStat = "SELECT * FROM TASKS WHERE status  = 'Pending'";
            String sqlCompletedStat = "SELECT * FROM TASKS WHERE status  = 'Completed'";
            System.out.println(sql);
            PreparedStatement pstmtInProgress = con.prepareStatement(sql);
            PreparedStatement pstmtPending = con.prepareStatement(sqlPendingStat);
            PreparedStatement pstmtCompleted = con.prepareStatement(sqlCompletedStat);

            ResultSet resultSetPending = pstmtPending.executeQuery();
            ResultSet resultSetInProgress = pstmtInProgress.executeQuery();
            ResultSet resultSetCompleted = pstmtCompleted.executeQuery();

            // Clear existing panes from taskContainer
            taskContainer.getChildren().clear();
            inProgressContainer.getChildren().clear();
            completedContainer.getChildren().clear();

            while(resultSetPending.next()){
                String title = resultSetPending.getString("title");
                String dueDate = resultSetPending.getString("due_date");
                String tags = resultSetPending.getString("tags");
//                String details = resultSet.getString("details");
//                String priority = resultSet.getString("priority");
//                String status = resultSet.getString("status");

                //Create a pane for each row
                Pane pane = createRowPane(title, tags, dueDate);

                // Add the pane to the taskContainer
                taskContainer.getChildren().add(pane);
            }

            while(resultSetInProgress.next()){
                String title = resultSetInProgress.getString("title");
                String dueDate = resultSetInProgress.getString("due_date");
                String tags = resultSetInProgress.getString("tags");
//                String details = resultSet.getString("details");
//                String priority = resultSet.getString("priority");
//                String status = resultSet.getString("status");

                //Create a pane for each row
                Pane pane = createRowPane(title, tags, dueDate);

                // Add the pane to the taskContainer
                inProgressContainer.getChildren().add(pane);
            }

            while(resultSetCompleted.next()){
                String title = resultSetCompleted.getString("title");
                String dueDate = resultSetCompleted.getString("due_date");
                String tags = resultSetCompleted.getString("tags");
//                String details = resultSet.getString("details");
//                String priority = resultSet.getString("priority");
//                String status = resultSet.getString("status");

                //Create a pane for each row
                Pane pane = createRowPane(title, tags, dueDate);

                // Add the pane to the taskContainer
                completedContainer.getChildren().add(pane);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Pane createRowPane(String title, String dueDate, String tags){
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #F2F3F5; -fx-padding: 5px; -fx-pref-width: 247px; -fx-min-height: 155px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-margin-bottom: 10px;");

        Label titleLabel = new Label(title);
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icons/purchase-tag-regular-24-black.png")));
        Label tagsLabel = new Label(tags);
        Label dueDateLabel = new Label(dueDate);
        Button detailsButton = new Button("Details");
        Button updateButton = new Button("Edit");
        // Set styles for labels
        titleLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        tagsLabel.setStyle("-fx-font-size: 12px;");
        dueDateLabel.setStyle("-fx-background-color: #E7E8E9; -fx-font-size: 12px; -fx-padding: 5px;");

        // Set styles for buttons
        detailsButton.setStyle("-fx-background-color: #E7E8E9;-fx-background-radius: 10;");
        updateButton.setStyle("-fx-background-color: #2FD170;-fx-background-radius: 10;");
//        startButton.setStyle("-fx-background-color: #0096FF; -fx-text-fill: white; -fx-font-size: 10.0;");

        // Add nodes to the pane
        pane.getChildren().addAll(titleLabel, imageView, tagsLabel, dueDateLabel, detailsButton, updateButton);

        // Set positions
        titleLabel.setLayoutX(14);
        titleLabel.setLayoutY(14);

        imageView.setLayoutX(14);
        imageView.setLayoutY(44);

        tagsLabel.setLayoutX(21);
        tagsLabel.setLayoutY(81);

        dueDateLabel.setLayoutX(42);
        dueDateLabel.setLayoutY(47);

        detailsButton.setLayoutX(16);
        detailsButton.setLayoutY(110);

        updateButton.setLayoutX(100);
        updateButton.setLayoutY(110);


        System.out.println("Pane Created");
        return pane;
    }
}
