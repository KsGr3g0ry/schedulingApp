package com.example.qam2_alternativeassessment.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.qam2_alternativeassessment.model.User;

/**
 *
 * This class controls the functionality of Login
 */
public class LoginController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField password;
    @FXML
    private Label location;
    int attempt;
    Date date;
    private DatabaseManager db;

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;

    @FXML
    private Label lblLocation;

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;

    /**
     * Initialize all variables/controls. Contains lambda #1 set on Exit button
     * as Action use to Exit from Application
     *
     * @param url initialize method default parameter
     * @param rb initialize method default parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //lambda #1
        btnExit.setOnAction(e -> {
            System.exit(0);
        });

        db = new DatabaseManager();
        attempt = 1;
        date = new Date();
        location.setText(ZoneId.systemDefault().toString());

        try {

            Locale locale = Locale.getDefault();
           Locale.setDefault(locale);
           // System.out.println(locale.getLanguage());
          //  Locale.setDefault(new Locale("SP"));

            rb = ResourceBundle.getBundle("language/login", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("ES"))
                System.out.println(rb.getString("Login") + " " + rb.getString("title"));

            lblTitle.setText(rb.getString("title"));
            lblUsername.setText(rb.getString("username"));
            lblPassword.setText(rb.getString("password"));
            lblLocation.setText(rb.getString("location"));
            btnLogin.setText(rb.getString("Login"));
            btnExit.setText(rb.getString("exit"));

        } catch (MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Login User
     *
     * @param event Action event
     */
    @FXML
    private void login(ActionEvent event) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("language/login", Locale.getDefault());

            if (id.getText().isEmpty() || password.getText().isEmpty()) {   //check if id & password field is populated or not
                //display error message
                new Alert(Alert.AlertType.INFORMATION, rb.getString("fillAllFields")).showAndWait();
                SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a zzz");
                String time = ft1.format(date);
                //use userLog method to add login detail in text file
                db.userLog(attempt, time, password.getText(),"Login Failed");
                attempt++;

            } else {
                String ID = null;
                ArrayList<User> users = db.getUsers();
                boolean flag = false;
                for (User user : users) {

                    if (id.getText().equals(user.getUserName()) && password.getText().equals(user.getUserPassword())) {
                        flag = true;
                        ID = user.getUserID();
                    }
                }
                if (flag) { //if id and password matches
                    SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a zzz");
                    String time = ft1.format(date);
                    db.userLog(attempt, time, password.getText(), "Login Successful");
                    //navigate to customer Record screen

                    new Alert(Alert.AlertType.INFORMATION, "Successfully Login").showAndWait();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Customer Record");
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        MainMenuController controller = fxmlLoader.getController();
                        controller.initData(ID);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }

                } else {
                    //display error message
                    new Alert(Alert.AlertType.INFORMATION, rb.getString("incorrect")).showAndWait();
                    ZoneId zone = ZoneId.systemDefault();
                    location.setText(zone + "");
                    SimpleDateFormat ft1 = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss a zzz");
                    String time = ft1.format(date);
                    db.userLog(attempt, time, password.getText(), "Login Failed");
                    attempt++;
                }
            }

        } catch (MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        }
    }

}
