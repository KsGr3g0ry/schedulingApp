package com.example.qam2_alternativeassessment.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.qam2_alternativeassessment.model.Appointment;

/**
 *
 * This Class holds functionality that controls user navigation choice (Perform
 * which functionality) functionality
 */
public class MainMenuController implements Initializable {

    String id;
    ArrayList<Appointment> appointments;

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Label h1;
    String logMsg = "";
    private DatabaseManager db;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * This method initialize all attributes Contains lambda #3 to set on Logout
     * button as Action use to redirect from Menu to login page
     *
     * @param url initialize method default parameter
     * @param rb initialize method default parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DatabaseManager();
        appointments = db.getAppointmentsDetails();

        //lambda #3
        btn4.setOnAction(e -> {

            new Alert(Alert.AlertType.INFORMATION, "Successfully Logout").showAndWait();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Login");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }

        });
    }

    /**
     *
     * @param id for store/set UserID
     * @param value set boolean value
     */
    public void initData(String id, boolean value) {
        this.id = id;

    }

    /**
     * Initialize values
     *
     * @param id for set UserID
     */
    public void initData(String id) {
        this.id = id;

        ArrayList<Appointment> temp = new ArrayList<>();
        Date date = new Date();

        for (Appointment appointment : appointments) {
            try {
                Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointment.getStartDate());
                long diff = d1.getTime() - date.getTime();
                long days = TimeUnit.MILLISECONDS.toDays(diff);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (days == 0) {
                    if (minutes <= 15 && minutes >= 0) {
                        temp.add(appointment);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (temp.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message...");
            alert.setHeaderText("Meeting Details");
            alert.setContentText("You have no upcoming meetings");
            alert.showAndWait();
        } else {

            String detail = "";
            for (Appointment appointment : temp) {
                detail = detail + "\n" + appointment.getAppointmentID() + "\t\t\t" + appointment.getStartDate() + "";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message...");
            alert.setHeaderText("You have " + temp.size() + " Upcoming meeting");
            alert.setContentText("Appointment ID\t\t\tDate&Time\n" + detail);
            alert.showAndWait();

        }

    }

    /**
     * Navigates user to clients Record Screen
     *
     * @param event Action event
     */
    @FXML
    private void manageClients(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/ClientsManage.fxml"));
            Parent root = fXMLLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Clients Record");
            Scene scene = new Scene(root);
            ClientsController controller = fXMLLoader.getController();
            controller.initData(id);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Navigates User to Appointment Schedule Screen
     *
     * @param event Action event
     */
    @FXML
    private void scheduleAppointments(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/ManageAppointments.fxml"));
            Parent root = fXMLLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AppointmentsController controller = fXMLLoader.getController();
            controller.initData(id);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * This method navigates user to reports Screen
     *
     * @param event Action event
     */
    @FXML
    private void viewReports(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/Reports.fxml"));
            Parent root = fXMLLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Reports");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ReportsController controller = fXMLLoader.getController();
            controller.initData(id);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
