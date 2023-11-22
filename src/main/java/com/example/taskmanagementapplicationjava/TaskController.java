package com.example.taskmanagementapplicationjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskController {

    @FXML
    private TextField taskTitleTxtField;
    @FXML
    private DatePicker dueDateField;
    @FXML
    private ComboBox<String> tagsCBox;
    @FXML
    private ComboBox<String> statusCBox;
    @FXML
    private ComboBox<String> priorityCbox;
    @FXML
    private TextArea detailsTxtArea;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        // Initialize ComboBox items
        tagsCBox.getItems().addAll("Tag1", "Tag2", "Tag3", "Tag4");
        statusCBox.getItems().addAll("In Progress", "Completed", "Pending");
        priorityCbox.getItems().addAll("High", "Medium", "Low");

    }
    @FXML
    public void createTask(ActionEvent event){
        try{
            Connection con=DBConnection.getConnection();
            con.createStatement();
            String sql = "INSERT INTO tasks (user_id, title, due_date, tags, details, task_priority, status, created_at, updated_at) VALUES (1,?,?,?,?,?,?,?,?)";
            System.out.println(sql);
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1,taskTitleTxtField.getText());
            pstmt.setString(2,dueDateField.getValue().format(formatter));
            pstmt.setString(3,tagsCBox.getValue());
            pstmt.setString(4,detailsTxtArea.getText());
            pstmt.setString(5,priorityCbox.getValue());
            pstmt.setString(6,statusCBox.getValue());
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);
            // Set the date parameter in the PreparedStatement
            pstmt.setDate(7, sqlDate);
            pstmt.setDate(8, sqlDate);
            int rs = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}
