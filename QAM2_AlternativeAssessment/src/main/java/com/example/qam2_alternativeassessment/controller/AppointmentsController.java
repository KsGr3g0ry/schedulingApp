package com.example.qam2_alternativeassessment.controller;

import static com.example.qam2_alternativeassessment.controller.DateTimeConverter.convertTimeDateUTC;
import static com.example.qam2_alternativeassessment.controller.DateTimeConverter.dateToLocalDateTimeTimezone;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.qam2_alternativeassessment.model.Appointment;
import com.example.qam2_alternativeassessment.model.Client;
import com.example.qam2_alternativeassessment.model.Style;
import static com.example.qam2_alternativeassessment.controller.DateTimeConverter.get8am;
import static com.example.qam2_alternativeassessment.controller.DateTimeConverter.get10pm;

/**
 *
 * This Class has functionality which user use to schedule/modify/delete
 * appointments
 */
public class AppointmentsController implements Initializable {

    ArrayList<Client> clients;
    ArrayList<Style> styles;

    Alert alert;
    boolean check;
    String id;

    @FXML
    private TableView<Appointment> table;
    @FXML
    private TextField appointmentID;
    @FXML
    private TextField title;
    @FXML
    private TextField location;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField clientID;

    @FXML
    private ComboBox<Style> styleCombo;
    @FXML
    private TextField description;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TableColumn<Appointment, String> appointmentIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<?, ?> startDateCol;
    @FXML
    private TableColumn<?, ?> endDateCol;
    @FXML
    private TableColumn<Appointment, Integer> clientIDCol;
    @FXML
    private TableColumn<Appointment, Style> styleCol;

    @FXML
    private ToggleGroup rb1;

    private DatabaseManager db;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * This method initialize all the values
     *
     * @param url u
     * @param rb r
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        //method to initialize variables
        db = new DatabaseManager();
        styleCombo.getItems().clear();
        clients = db.getClients();
        styles = db.getStyles();
        styleCombo.getItems().addAll(styles);
        styleCombo.getSelectionModel().select(0);
        check = false;
        alert = new Alert(Alert.AlertType.INFORMATION);
        //setting table view column properties
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        styleCol.setCellValueFactory(new PropertyValueFactory<>("style"));
        showData();     //show appointment details in tableView
        appointmentID.setEditable(false);

    }

    /**
     * This method initialize or sets UserID. Contains lambda #2 that replaces for
     * loop to add getContactID() to the contact comboBox.
     *
     * @param id set User ID
     */
    public void initData(String id) {

        type.getItems().add("Style");
        type.getItems().add("Color and Cut");

        this.id = id;

    }

    /**
     * This is an add Record method which add the record of appointment in MySql
     * database
     *
     * @param event ActionEvent
     */
    @FXML
    private void addRecord(ActionEvent event) {     //adding detail in database

//condition to check if all field are empty or not
        if (type.getSelectionModel().getSelectedIndex() == -1 || startDate.getText().isEmpty()
                || title.getText().isEmpty() || endDate.getText().isEmpty()
                || description.getText().isEmpty() || clientID.getText().isEmpty()
                || location.getText().isEmpty()
                || styleCombo.getSelectionModel().getSelectedIndex() == -1) {
            //display error message
            new Alert(Alert.AlertType.ERROR, "Error. No field can be empty").showAndWait();

        } else {
            //string format to validate date textfield
            String format = "[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}[ ]{1}[0-9]{2}[:]{1}[0-9]{2}[:]{1}[0-9]{2}";
            //validating date field
            if (Pattern.matches(format, startDate.getText()) && Pattern.matches(format, endDate.getText())) {
                boolean flag = false;
                for (Client client : clients) {
                    if (client.getId() == Integer.parseInt(clientID.getText())) {
                        flag = true;
                    }
                }

                if (flag) {
                    boolean match = true;
                    try {
                        //creating appointment object to add value in database

                        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate.getText());
                        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate.getText());

                        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
                        LocalDateTime ldt1 = dateToLocalDateTimeTimezone(start, ZoneId.of("America/Denver"));
                        LocalDateTime ldt2 = dateToLocalDateTimeTimezone(end, ZoneId.of("America/Denver"));

                        if (start.getDay() >= DayOfWeek.MONDAY.getValue()
                                && end.getDay() <= DayOfWeek.FRIDAY.getValue()) {

                            //System.out.println("Week Day");
                        } else {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Outside Business Days(Mon-Fri)").showAndWait();
                        }

                        int year = Year.now().getValue();
                        LocalDate holiday1 = scheduleHoliday("Thanksgiving", year, 11, 23);
                        // November 23rd
                        LocalDate holiday2 = scheduleHoliday("Thanksgiving", year, 11, 24);
                        // November 24th

                        // July 4th or observed the Friday before
                        LocalDate holiday3 = scheduleHoliday("Independence Day", year, 7, 4);
                        // July 4th (observed)

                        // January 1st or observed the Monday after
                        LocalDate holiday4 = scheduleHoliday("New Year's Day", year, 1, 1);
                        // January 1st (observed)

                        if (match) {

                            if (ldt1.toLocalTime().isBefore(get8am(ldt1.toLocalDate()).toLocalTime()) ||
                                    ldt1.toLocalTime().isAfter(get10pm(ldt1.toLocalDate()).toLocalTime())
                                    || ldt2.toLocalTime().isBefore(get8am(ldt1.toLocalDate()).toLocalTime()) ||
                                    ldt2.toLocalTime().isAfter(get10pm(ldt1.toLocalDate()).toLocalTime())) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Outside Business Hours. (8am-10pm)").showAndWait();
                            }
                        }

                        if (match) {
                            if (start.after(end)) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Appointment has start date after the end date").showAndWait();
                            }
                        }

                        Date startHours = new Date(), endHours = new Date();

                        if (match) {
                            if (start.after(end)) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Appointment has start date after the end date").showAndWait();
                            }
                        }

                        if (match) {

                            formatter1 = new SimpleDateFormat("HH:mm:ss");
                            startHours = formatter1.parse(formatter1.format(start));
                            endHours = formatter1.parse(formatter1.format(end));

                            if (startHours.equals(endHours)) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Start Hours shouldn't be equal to End hours").showAndWait();
                            }
                        }

                        if (match) {
                            if (startHours.after(endHours)) {
                                new Alert(Alert.AlertType.ERROR, "Appointment has start time after the end time").showAndWait();
                                match = false;
                                System.out.println("Appointment has start time after the end time");
                            }
                        }
                        if (match) {
                            if (holiday1.equals(ldt1.toLocalDate()) || holiday2.equals(ldt1.toLocalDate())
                                    || holiday3.equals(ldt1.toLocalDate()) || holiday4.equals(ldt1.toLocalDate())) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Holiday on start date").showAndWait();
                            } else if (holiday1.equals(ldt2.toLocalDate()) || holiday2.equals(ldt2.toLocalDate())
                                    || holiday3.equals(ldt2.toLocalDate()) || holiday4.equals(ldt2.toLocalDate())) {
                                match = false;
                                new Alert(Alert.AlertType.ERROR, "Holiday on end date").showAndWait();
                            }
                        }

                    } catch (Exception e) {

                        System.out.println(e);
                        match = false;
                    }

                    if (match) {

                        String startUTC = convertTimeDateUTC(startDate.getText());
                        String endUTC = convertTimeDateUTC(endDate.getText());
                        LocalDateTime sDateTime = LocalDateTime.parse(startUTC, formatter);
                        LocalDateTime eDateTime = LocalDateTime.parse(endUTC, formatter);
                        Appointment appointment = new Appointment(-1, title.getText(), description.getText(),
                                location.getText(), type.getSelectionModel().getSelectedItem(), sDateTime.toString(),
                                eDateTime.toString(), Integer.parseInt(clientID.getText()),
                                styleCombo.getSelectionModel().getSelectedItem());
                        //using method to add appointment detail in database

                        db.addAppointment(appointment);
                        //display message information
                        new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                        //reset values to default values
                        appointmentID.setText("Auto Generated");

                        title.setText("");
                        description.setText("");
                        location.setText("");
                        startDate.setText("");
                        endDate.setText("");
                        clientID.setText("");
                        styleCombo.getSelectionModel().select(0);

                        showData();

                    }

                } else {
                    //display error message
                    new Alert(Alert.AlertType.ERROR, "Client ID not Found").showAndWait();
                }
            } else {
                //display date&time format error message
                new Alert(Alert.AlertType.ERROR, "Error. Date Format is yyyy-MM-dd hh:mm:ss").showAndWait();
            }

        }
    }

    /**
     * This method has functionality to delete appointment record in MySql
     * database
     *
     * @param event Action event
     */
    @FXML
    private void deleteRecord(ActionEvent event) {

        if (table.getSelectionModel().getSelectedIndex() == -1) {    //condition to check if appointment id is empty
            //display error message
            new Alert(Alert.AlertType.ERROR, "Please Select any appointment to Delete").showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "");
            alert.initModality(Modality.APPLICATION_MODAL);

            alert.getDialogPane().setContentText("Please Confirm to delete Record.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Appointment ap = table.getSelectionModel().getSelectedItem();
                //using method to delete record in database
                db.deleteAppointmentRecord(ap.getAppointmentID());
                //display message information
                new Alert(Alert.AlertType.INFORMATION, "Successfully delete Appointment With ID " +
                        ap.getAppointmentID() + " and type: " + ap.getType()).showAndWait();
                appointmentID.setText("");
                showData();     //update details in tableView
                appointmentID.setText(new Random().nextInt(2000) + "");
            } else if (result.get() == ButtonType.CANCEL) {
                new Alert(Alert.AlertType.INFORMATION, "Cancelled Deletion").showAndWait();
            }
            appointmentID.setText("Auto Generated");

            title.setText("");
            description.setText("");
            location.setText("");
            startDate.setText("");
            endDate.setText("");
            clientID.setText("");
            styleCombo.getSelectionModel().select(0);

        }
    }

    /**
     * This method has functionality to updated appointment record in database
     * and shows updated data in table
     *
     * @param event Action event
     */
    @FXML
    private void updateRecord(ActionEvent event) {

        if (type.getSelectionModel().getSelectedIndex() == -1 || appointmentID.getText().isEmpty() || startDate.getText().isEmpty()
                || title.getText().isEmpty() || endDate.getText().isEmpty()
                || description.getText().isEmpty() || clientID.getText().isEmpty()
                || location.getText().isEmpty()
                || styleCombo.getSelectionModel().getSelectedIndex() == -1) {
            //display error message
            new Alert(Alert.AlertType.ERROR, "Please first select any Record to update or no field should be empty").showAndWait();

        } else {
            Appointment ap = null;
            //using condition check
            Client cs = db.getClients(Integer.parseInt(clientID.getText()));
            if (cs != null) {
                //creating appointment object to update values

                boolean match = true;
                try {
                    //creating appointment object to add value in database

                    Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate.getText());
                    Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate.getText());

                    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
                    LocalDateTime ldt1 = dateToLocalDateTimeTimezone(start, ZoneId.of("America/Denver"));
                    LocalDateTime ldt2 = dateToLocalDateTimeTimezone(end, ZoneId.of("America/Denver"));

                    if (start.getDay() >= DayOfWeek.MONDAY.getValue()
                            && end.getDay() <= DayOfWeek.FRIDAY.getValue()) {


                    } else {
                        match = false;
                        new Alert(Alert.AlertType.ERROR, "Outside Business Days(Mon-Fri)").showAndWait();
                    }

                    if (match) {

                        if (ldt1.toLocalTime().isBefore(get8am(ldt1.toLocalDate()).toLocalTime()) ||
                                ldt1.toLocalTime().isAfter(get10pm(ldt1.toLocalDate()).toLocalTime())
                                || ldt2.toLocalTime().isBefore(get8am(ldt1.toLocalDate()).toLocalTime()) ||
                                ldt2.toLocalTime().isAfter(get10pm(ldt1.toLocalDate()).toLocalTime())) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Outside Business Hours. (8am-10pm)").showAndWait();
                        }
                    }

                    if (match) {
                        if (start.after(end)) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Appointment has start date after the end date").showAndWait();
                        }
                    }

                    Date startHours = new Date(), endHours = new Date();
                    if (match) {

                        formatter1 = new SimpleDateFormat("HH:mm:ss");
                        startHours = formatter1.parse(formatter1.format(start));
                        endHours = formatter1.parse(formatter1.format(end));

                        if (startHours.equals(endHours)) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Start Hours shouldn't be equal to End hours").showAndWait();
                        }
                    }

                    if (match) {
                        if (startHours.after(endHours)) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Appointment has start time after the end time").showAndWait();
                            System.out.println("Appointment has start time after the end time");
                        }
                    }

                    if (match) {
                        int year = Year.now().getValue();
                        LocalDate holiday1 = scheduleHoliday("Thanksgiving", year, 11, 23);
                        // November 23rd
                        LocalDate holiday2 = scheduleHoliday("Thanksgiving", year, 11, 24);
                        // November 24th

                        // July 4th or observed the Friday before
                        LocalDate holiday3 = scheduleHoliday("Independence Day", year, 7, 4);
                        // July 4th (observed)

                        // January 1st or observed the Monday after
                        LocalDate holiday4 = scheduleHoliday("New Year's Day", year, 1, 1);
                        // January 1st (observed)

                        if (holiday1.equals(ldt1.toLocalDate()) || holiday2.equals(ldt1.toLocalDate())
                                || holiday3.equals(ldt1.toLocalDate()) || holiday4.equals(ldt1.toLocalDate())) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Holiday on start date").showAndWait();
                        } else if (holiday1.equals(ldt2.toLocalDate()) || holiday2.equals(ldt2.toLocalDate())
                                || holiday3.equals(ldt2.toLocalDate()) || holiday4.equals(ldt2.toLocalDate())) {
                            match = false;
                            new Alert(Alert.AlertType.ERROR, "Holiday on end date").showAndWait();
                        }
                    }

                } catch (Exception e) {

                    System.out.println(e);
                    match = false;
                }

                if (match) {

                    String startUTC = convertTimeDateUTC(startDate.getText());
                    String endUTC = convertTimeDateUTC(endDate.getText());
                    LocalDateTime sDateTime = LocalDateTime.parse(startUTC, formatter);
                    LocalDateTime eDateTime = LocalDateTime.parse(endUTC, formatter);
                    Appointment appointment = new Appointment(Integer.parseInt(appointmentID.getText()),
                            title.getText(), description.getText(), location.getText(),
                            type.getSelectionModel().getSelectedItem(), sDateTime.toString(),
                            eDateTime.toString(), Integer.parseInt(clientID.getText()),
                            styleCombo.getSelectionModel().getSelectedItem());

                    db.updateAppointmentDetails(appointment);
                    //display message information
                    new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
                    //reset attribute fields to by default values
                    check = false;
                    appointmentID.setText("Auto Generated");

                    title.setText("");
                    description.setText("");
                    location.setText("");
                    startDate.setText("");
                    endDate.setText("");
                    clientID.setText("");
                    styleCombo.getSelectionModel().select(0);

                    showData();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Error. Customer ID not found").showAndWait();
            }
        }

    }

    /**
     * This method add appointment details in table
     */
    public void showData() {
        try {
            table.setItems(data());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is to filter monthly appointments
     *
     * @return appointments
     * @throws SQLException throw exception
     */
    public ObservableList<Appointment> monthlyData() throws SQLException {
        Date date = new Date();
        SimpleDateFormat ft1 = new SimpleDateFormat("MM");
        String month = ft1.format(date);
        ArrayList<Appointment> cs = db.getAppointmentsDetails();
        ArrayList<Appointment> ap = new ArrayList<>();
        for (Appointment c : cs) {
            String sDateTime = c.getStartDate();
            String sD[] = sDateTime.split(" ");
            String a[] = sD[0].split("-");

            if (a[1].equals(month)) {
                ap.add(c);
            }
        }
        ObservableList<Appointment> cst = FXCollections.observableArrayList();

        for (Appointment appointment : ap) {
            cst.add(appointment);
        }

        return cst;
    }

    /**
     * This method is to filter weekly appointments
     *
     * @return appointments
     * @throws SQLException throws exception
     */
    public ObservableList<Appointment> Weeklydata() throws SQLException {
        Date date = new Date();
        SimpleDateFormat ft1 = new SimpleDateFormat("dd");
        String day = ft1.format(date);

        ArrayList<Appointment> cs = db.getAppointmentsDetails();
        ArrayList<Appointment> ap = new ArrayList<>();
        int prevDate;
        int prevMonth = 0;
        String month;
        Date date1 = new Date();
        month = ft1.format(date1);
        SimpleDateFormat ft11 = new SimpleDateFormat("MM");
        month = ft1.format(date1);
        if ((Integer.parseInt(day) - 7) > 0) {
            prevDate = Integer.parseInt(day) - 7;

        } else {

            prevMonth = Integer.parseInt(month) - 1;

            if (prevMonth == 2) {
                prevDate = (Integer.parseInt(day) - 7) + 28;
            } else {
                if (prevMonth == 1 || prevMonth == 3 || prevMonth == 5 || prevMonth == 7 || prevMonth == 8 ||
                        prevMonth == 10 || prevMonth == 12) {
                    prevDate = (Integer.parseInt(day) - 7) + 31;
                } else {
                    prevDate = (Integer.parseInt(day) - 7) + 30;
                }
            }

        }
        prevDate = prevDate + 1;

        for (Appointment c : cs) {
            String sDateTime = c.getStartDate();
            String sD[] = sDateTime.split(" ");

            String a[] = sD[0].split("-");

            if (prevMonth == 0) {
                Date dt = new Date();

                SimpleDateFormat f = new SimpleDateFormat("MM");
                String m = f.format(dt);

                int calendarDate = Integer.parseInt(a[2]);
                int currentDay = Integer.parseInt(day);
                int calendarMonth = Integer.parseInt(a[1]);
                int currentMonth = Integer.parseInt(m);

                if ((calendarDate >= prevDate && calendarDate <= currentDay) && calendarMonth == currentMonth) {
                    ap.add(c);
                }
            } else {
                if ((Integer.parseInt(a[1]) == Integer.parseInt(month) && Integer.parseInt(a[0]) <= Integer.parseInt(day)) || (prevMonth == Integer.parseInt(a[1]) && Integer.parseInt(a[2]) >= prevDate)) {
                    ap.add(c);

                }
            }

        }
        ObservableList<Appointment> cst = FXCollections.observableArrayList();
        for (Appointment c : ap) {
            cst.add(c);
        }
        return cst;
    }

    public ObservableList<Appointment> data() throws SQLException {
        ArrayList<Appointment> cs = db.getAppointmentsDetails();
        ObservableList<Appointment> cst = FXCollections.observableArrayList();
        for (Appointment c : cs) {
            cst.add(c);
        }
        return cst;
    }

    /**
     * This method filters appointments by week and shows in table
     *
     * @param event Action event
     */
    @FXML
    private void weeklyAppointments(ActionEvent event) {        //display weekly appointments in tableView
        try {
            table.setItems(Weeklydata());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method filters and shows appointments by month
     *
     * @param event Action event
     */
    @FXML
    private void mothlyData(ActionEvent event) {
        try {
            table.setItems(monthlyData());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method shows all the appointments in table
     *
     * @param event Action event
     */
    @FXML
    private void allAppointments(ActionEvent event) {
        showData();
    }

    /**
     * This method navigated user back to main menu screen
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
     * Method is to populate data in fields when user clicks any of appointment
     *
     * @param event Action event
     */
    @FXML
    private void populateData(MouseEvent event) {
        Appointment appointment = table.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            appointmentID.setText(appointment.getAppointmentID() + "");
            styleCombo.getSelectionModel().select(appointment.getStyle());
            title.setText(appointment.getTitle());
            description.setText(appointment.getDescription());
            location.setText(appointment.getLocation());
            type.getSelectionModel().select(appointment.getType());
            startDate.setText(appointment.getStartDate());
            endDate.setText(appointment.getEndDate());
            clientID.setText(appointment.getClientID() + "");

        }
    }

    private static LocalDate scheduleHoliday(String holidayName, int year, int month, int dayOfMonth) {
        LocalDate holidayDate = LocalDate.of(year, month, dayOfMonth);
        DayOfWeek dayOfWeek = holidayDate.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            holidayDate = holidayDate.minusDays(1); // Observed the Friday before
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            holidayDate = holidayDate.plusDays(1); // Observed the Monday after
        }

        System.out.println("Scheduled holiday: " + holidayName + " - " + holidayDate);

        return holidayDate;
    }

}
