module com.example.taskmanagementapplicationjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.taskmanagementapplicationjava to javafx.fxml;
    exports com.example.taskmanagementapplicationjava;
}