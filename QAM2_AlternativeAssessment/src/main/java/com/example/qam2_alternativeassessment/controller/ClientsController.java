package com.example.qam2_alternativeassessment.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.qam2_alternativeassessment.model.Client;

/**
 *
 * This Controller Class holds functionality which helps user to
 * add/modify/delete Client Record
 */
public class ClientsController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField email;
    @FXML
    private TextField hairColor;
    @FXML
    private TextField id;
    @FXML
    private ComboBox<String> country;
    @FXML
    private TextField stateProvince;

    @FXML
    private TableColumn<Client, String> countryCol;
    @FXML
    private TableColumn<Client, Integer> idCol;
    @FXML
    private TableColumn<Client, String> nameCol;
    @FXML
    private TableColumn<Client, String> emailCol;
    @FXML
    private TableColumn<Client, String> hairColorCol;
    @FXML
    private TableColumn<Client, String> codeCol;
    @FXML
    private TableColumn<Client, String> activeCol;
    @FXML
    private TableColumn<Client, String> stateCol;
    @FXML
    private CheckBox isActive;
    @FXML
    private TableView<Client> table;

    ArrayList<Client> clients;
    String userID;
    ArrayList<String> countries;
    boolean check;

    private DatabaseManager db;

    /**
     * Initializes the controller class.
     *
     * CONTAINS LAMBDA: To avoid non digit inputs in postal code
     *
     * @param url default parameter from System
     * @param rb default parameter from System
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    //initializing method
        //initialize database controller object
        db = new DatabaseManager();

        countries = db.getCountries();

        check = false;
        //setting column properties of table view
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        hairColorCol.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        clients = db.getClients();      //getting available clients from database
        showData();         //adding client data in table
        //adding countries in combo box

        id.setText("Auto Generated");
        id.setEditable(false);

        postalCode.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.DIGIT1
                    || event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.DIGIT3
                    || event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.DIGIT5
                    || event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7
                    || event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.DIGIT9
                    || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {

                postalCode.setEditable(true);
            } else {
                postalCode.setEditable(false);
            }

        });

    }

    /**
     *
     * @param id sets the value of user id
     */
    public void initData(String id) {
        this.userID = id;

        country.getItems().add("Select from following");
        country.getSelectionModel().select(0);
        for (String c : countries) {
            country.getItems().add(c);
        }

    }

    /**
     * This method is for to add record of client in database
     */
    @FXML
    private void addRecord(ActionEvent event) {     //method invokes if user clicks on add record button
        //condition checks if any attribute is empty
        if (stateProvince.getText().isEmpty() || country.getSelectionModel().getSelectedIndex() == -1 ||
                id.getText().isEmpty() || name.getText().isEmpty() || postalCode.getText().isEmpty() ||
                email.getText().isEmpty() || hairColor.getText().isEmpty() ||
                country.getSelectionModel().getSelectedIndex() == 0) {
            //display error message
            new Alert(Alert.AlertType.ERROR, "Error. No field can be empty").showAndWait();
        } else {        //no field is empty
            //creating client object
            Client client = new Client(-1, name.getText(), email.getText(), hairColor.getText(), postalCode.getText(),
                    stateProvince.getText(), country.getSelectionModel().getSelectedItem(), isActive.isSelected());
            //using db addClient method to add record in database
            db.addClient(client);
            //display message box
            new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
            //reset fields to by default values
            name.setText("");
            postalCode.setText("");
            email.setText("");
            stateProvince.setText("");
            hairColor.setText("");
            country.getSelectionModel().select(0);
            showData();

        }

    }

    /**
     * This method is used to update the client Record in database
     */
    @FXML
    private void UpdateRecord(ActionEvent event) {      //method if user clicks on update record button

        if (stateProvince.getText().isEmpty() || country.getSelectionModel().getSelectedIndex() == -1 ||
                id.getText().isEmpty() || name.getText().isEmpty() || postalCode.getText().isEmpty() ||
                email.getText().isEmpty() || hairColor.getText().isEmpty()) {
            //display error message
            new Alert(Alert.AlertType.ERROR, "Error.Please Select any Field to Update").showAndWait();
        } else {

            Client client = new Client(Integer.parseInt(id.getText()), name.getText(), email.getText(), hairColor.getText(),
                    postalCode.getText(), stateProvince.getText(), country.getSelectionModel().getSelectedItem(),
                    isActive.isSelected());

            //update client data
            db.updateClientDetails(client);
            new Alert(Alert.AlertType.INFORMATION, "Successfully updated Record").showAndWait();
            if (client.isActive() == false && table.getSelectionModel().getSelectedItem().isActive()) {
                new Alert(Alert.AlertType.INFORMATION, "Client is Set InActive. All the record is deleted!").showAndWait();
            }
            check = false;
            id.setText("Auto Generated");
            name.setText("");
            postalCode.setText("");
            email.setText("");
            stateProvince.setText("");
            hairColor.setText("");
            country.getSelectionModel().select(0);

            showData();

        }

    }

    /**
     * This method add Client details in table
     */
    public void showData() {    //show client details in table view
        try {
            table.setItems(data());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * The method gets and store client details in Array list
     *
     * @return client details
     * @throws SQLException throws exception
     */
    public ObservableList<Client> data() throws SQLException {        //using observable list to add values in tableView
        ArrayList<Client> cs = db.getClients();
        ObservableList<Client> cst = FXCollections.observableArrayList();
        for (Client c : cs) {
            cst.add(c);
        }
        return cst;
    }

    /**
     * This method navigates user back to main menu
     *
     * @param event Action event
     */
    @FXML
    private void navigateBack(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
            Parent root = fXMLLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Main Menu");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            MainMenuController controller = fXMLLoader.getController();
            controller.initData(userID, true);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * This method populates client fields when user clicks on any of client
     * record
     *
     * @param event Action event
     */
    @FXML
    private void populateTable(MouseEvent event) {
        Client cs = table.getSelectionModel().getSelectedItem();

        if (cs != null) {
            //setting values in fields from database so that user update

            id.setText(cs.getId() + "");
            name.setText(cs.getName());
            postalCode.setText(cs.getPostalCode());
            email.setText(cs.getEmail() + "");
            hairColor.setText(cs.getHairColor());
            country.getSelectionModel().select(cs.getCountry());
            stateProvince.setText(cs.getState());
            isActive.setSelected(cs.isActive());
        }
    }

}
