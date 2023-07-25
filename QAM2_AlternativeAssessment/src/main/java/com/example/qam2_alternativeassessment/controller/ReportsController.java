package com.example.qam2_alternativeassessment.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.qam2_alternativeassessment.model.Appointment;
import com.example.qam2_alternativeassessment.model.Reports;
import com.example.qam2_alternativeassessment.model.Style;

/**
 * Appointment Reports Controller class
 * This Class have functionality to display Appointment Reports by different
 * ways
 */
public class ReportsController implements Initializable {

    @FXML
    public TableView<Reports> tableAppointmentTypeAndWeek;
    public TableColumn<Reports, Integer> week;
    public TableColumn<Reports,String> weekAppt;
    public TableColumn<Reports, Integer> weekCount;
    String id;
    ArrayList<Style> styles;
    ArrayList<Appointment> appointments;
    private DatabaseManager db;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @FXML
    private TableView<Reports> tableAppointmentTypeAndMonth;
    @FXML
    private ComboBox<String> styleCombo;
    @FXML
    private TableView<Appointment> table3;
    @FXML
    private TableColumn<Appointment, String> appointmentIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<?, ?> startDateCol;
    @FXML
    private TableColumn<?, ?> endDateCol;
    @FXML
    private TableColumn<Appointment, String> clientIDCol;
    @FXML
    private TableView<Reports> table2;

    @FXML
    private TableColumn<Reports, String> serialCol3;
    @FXML
    private TableColumn<Reports, String> monthCol3;
    @FXML
    private TableColumn<Reports, Integer> numCol3;

    @FXML
    private TableColumn<Reports, String> month;
    @FXML
    private TableColumn<Reports, String> appType;
    @FXML
    private TableColumn<Reports, Integer> count;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DatabaseManager();
        appointments = new ArrayList<>();
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));

        styles = new ArrayList<>();
        styles = db.getStyles();

        month.setCellValueFactory(new PropertyValueFactory<>("str1"));
        appType.setCellValueFactory(new PropertyValueFactory<>("str2"));
        count.setCellValueFactory(new PropertyValueFactory<>("counter"));

        week.setCellValueFactory(new PropertyValueFactory<>("week"));
        weekAppt.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekCount.setCellValueFactory(new PropertyValueFactory<>("total"));

        serialCol3.setCellValueFactory(new PropertyValueFactory<>("str1"));
        monthCol3.setCellValueFactory(new PropertyValueFactory<>("str2"));
        numCol3.setCellValueFactory(new PropertyValueFactory<>("counter"));


        try {
            showAppointmentsByTypesAndMonth();
            tableAppointmentTypeAndWeek.setItems(db.getWeeklyAppointment());
            showActiveInactiveClients();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    /**
     * Initialize User ID
     *
     * @param id id
     */
    public void initData(String id) {
        this.id = id;

        for (Style style : styles) {
            styleCombo.getItems().add(style.getId() + ":" + style.getName());
        }
        styleCombo.getSelectionModel().select(0);

    }

    /**
     * This method add the appointment by contact name filter in observable list
     * and adds data in table
     *
     * @param style contactName
     * @throws SQLException it throws exception
     */
    public void data(String style) throws SQLException {
        ArrayList<Appointment> cs = db.getAppointmentsDetails();
        ArrayList<Appointment> ap = new ArrayList<>();
        String[] styleName = style.split(":");
        for (Appointment appointment : cs) {

            if (Integer.parseInt(styleName[0]) == appointment.getStyle().getId()) {
                ap.add(new Appointment(appointment.getAppointmentID(), appointment.getTitle(), appointment.getDescription(),
                        appointment.getLocation(), appointment.getType(), appointment.getStartDate(),
                        appointment.getEndDate(), appointment.getClientID(), appointment.getStyle()));
            }
        }
        ObservableList<Appointment> cst = FXCollections.observableArrayList();
        for (Appointment Appointment : ap) {
            cst.add(Appointment);
        }

        try {
            table3.setItems(cst);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Whenever user selects any contact, this method add appointments in table
     * having this contact name
     *
     */
    @FXML
    private void styleSelection(ActionEvent event) {
        try {
            data(styleCombo.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * This method is for report 3
     *
     * @throws SQLException throw Exception
     */
    public void showActiveInactiveClients() throws SQLException {

        ObservableList<Reports> cst = FXCollections.observableArrayList();
        cst.add(new Reports("1", "Active", db.getActiveClientsCount()));
        cst.add(new Reports("2", "InActive", db.getInActiveClientsCount()));

        try {
            table2.setItems(cst);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * This method navigates user back to main menu screen
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
            controller.initData(id, true);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * This method filters the appointments by month and type and shows in table
     *
     * @throws ParseException throw Exception
     */
    public void showAppointmentsByTypesAndMonth() throws ParseException {

        appointments = db.getAppointmentsDetails();
        ObservableList<Reports> typeMonth = FXCollections.observableArrayList();

        SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        for (Appointment app : appointments) {

            Date date = dateFormatter.parse(app.getStartDate());
            String month = monthFormatter.format(date);

            if (typeMonth.isEmpty()) {
                typeMonth.add(new Reports(month, app.getType(), 1));
            } else {

                boolean flag = false;

                for (Reports reports : typeMonth) {
                    if (reports.getStr1().equals(month)
                            && reports.getStr2().equals(app.getType())) {
                        reports.setStr3(reports.getCounter() + 1);
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    typeMonth.add(new Reports(month, app.getType(), 1));
                }

            }

        }

        sortList(typeMonth);

    }

    private void sortList(ObservableList<Reports> typeMonth) {

        String[] month = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

        ObservableList<Reports> filterList = FXCollections.observableArrayList();

        for (String monthName : month) {
            if (typeMonth.size() == filterList.size()) {
                break;
            } else {
                for (Reports reports : typeMonth) {
                    if (reports.getStr1().equals(monthName)) {
                        filterList.add(reports);
                    }
                }
            }
        }
        tableAppointmentTypeAndMonth.setItems(filterList);

    }

}
