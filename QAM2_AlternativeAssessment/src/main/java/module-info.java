module com.example.qam2_alternativeassessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.qam2_alternativeassessment.controller to javafx.fxml;
    opens com.example.qam2_alternativeassessment.model to javafx.base;
    opens com.example.qam2_alternativeassessment to javafx.fxml;

    exports com.example.qam2_alternativeassessment;
    exports com.example.qam2_alternativeassessment.model;
    exports com.example.qam2_alternativeassessment.controller;
}