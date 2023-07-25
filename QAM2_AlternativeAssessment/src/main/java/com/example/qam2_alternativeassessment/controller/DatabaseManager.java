package com.example.qam2_alternativeassessment.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.qam2_alternativeassessment.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.example.qam2_alternativeassessment.controller.DateTimeConverter.convertTimeDateLocal;

/**
 *
 * This Class is to establish connection with database and perform activities
 * relating to database such as adding, modifying or deleting scheduled
 * appointment or clients record
 */
public class DatabaseManager {

    /**
     * This method establish connection with database
     *
     * @return Connection to database
     */
    public Connection getConnection() {

        Connection Con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/altdb", "sqlUsers", "Passw0rd!");    //Connecting to Database
        } catch (Exception e) {
            System.out.println(e);
        }
        return Con;         //returning Connection

    }

    /**
     * The method add Client detail in database
     *
     * @param client set Client object
     */
    public void addClient(Client client) {
        try {
            Connection con = getConnection();           //establish Connection with database
            //query for client data insertion
            String query = "insert INTO client (`name`, `email`, `haircolor`, `pcode`, `st_pv`, `country`, `Active`)" +
                    " values(?,?,?,?,?,?,?)";
            PreparedStatement PR = con.prepareStatement(query);
            //inserting values using prepared statement
            PR.setString(1, client.getName());
            PR.setString(2, client.getEmail());
            PR.setString(3, client.getHairColor());
            PR.setString(4, client.getPostalCode());
            PR.setString(5, client.getState());
            PR.setString(6, client.getCountry());
            PR.setInt(7, client.isActive() ? 1 : 0);
            PR.execute();    //executing statement to add data
            con.close();        //closing connection after done inserting data in database
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * The method delete the client Detail in database
     *
     * @param ID give Client-ID to delete Client from MySql.
     */
    public void delClient(int ID) {

        //delete that client data in appointment table too
        try {
            String query = "DELETE FROM appt WHERE clientid=?";
            Connection Con = getConnection();
            PreparedStatement pst = Con.prepareStatement(query);
            pst.setInt(1, ID);
            pst.executeUpdate();
            Con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method updated Client detail in database
     *
     * @param client set Client object
     */
    public void updateClientDetails(Client client) {
        try {
            Connection con = getConnection();                //establish connection with database
            //query for update data in database
            String query = "Update client SET `name` = ?, `email` = ?, `haircolor` = ?, `pcode` = ?, `st_pv` = ?, " +
                    "`country` = ?, `Active` = ? WHERE (`idclient` = ?);";
            //using prepared statement to execute query
            PreparedStatement ps = con.prepareStatement(query); //using prepared statement to execute query
            //inserting updated client attributes
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getHairColor());
            ps.setString(4, client.getPostalCode());
            ps.setString(5, client.getState());
            ps.setString(6, client.getCountry());
            ps.setInt(7, client.isActive() ? 1 : 0);
            ps.setInt(8, client.getId());
            ps.executeUpdate(); //executing query
            con.close();            //connection closing

            if (!client.isActive()) {
                delClient(client.getId());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * The method delete appointment detail in database
     *
     * @param ID give Appointment-ID to get delete Appointment from MySql.
     */
    public void deleteAppointmentRecord(int ID) {
        try {

            String query = "DELETE FROM appt WHERE idappt=?";  //query for deletion data from database
            Connection Con = getConnection();            //establish connection with database
            PreparedStatement pst = Con.prepareStatement(query);     //using prepared statement to execute query
            pst.setInt(1, ID);           //matching condition
            pst.executeUpdate();              //execute statement
            Con.close();                         //close connection
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * The method add appointment details in DatabaseManager
     *
     * @param appointment set Appointment object
     */
    public void addAppointment(Appointment appointment) {
        System.out.println(appointment.toString());
        try {
            Connection con = getConnection();       //establish Connection with database
            //query for appointment data insertion
            String query = "insert INTO appt (`titlle`, `descr`, `location`, `type`, `start`, `end`, `clientid`, `stylid`) " +
                    "values(?,?,?,?,?,?,?,?)";
            PreparedStatement PR = con.prepareStatement(query);
            //inserting values using prepared statement
            PR.setString(1, appointment.getTitle());
            PR.setString(2, appointment.getDescription());
            PR.setString(3, appointment.getLocation());
            PR.setString(4, appointment.getType());
            PR.setString(5, appointment.getStartDate());
            PR.setString(6, appointment.getEndDate());
            PR.setInt(7, appointment.getClientID());
            PR.setInt(8, appointment.getStyle().getId());

            PR.execute();       //executing statement to add data
            con.close();        //closing connection after done inserting data in database
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method updated appointment detail in database
     *
     * @param appointment set Appointment object
     */
    public void updateAppointmentDetails(Appointment appointment) {
        System.out.println(appointment.getStartDate());
        try {
            Connection con = getConnection();       //establish database connection
            //query to update appointment details
            String query = "Update appt SET `titlle` = ?, `descr` = ?, `location` = ?, `type` = ?, `start` = ?, `end` = ?, " +
                    "`clientid` = ?, `stylid` = ? WHERE (`idappt` ='" + appointment.getAppointmentID() + "')";
            //using prepared Statement to execute query
            PreparedStatement ps = con.prepareStatement(query);
            //inserting updated values
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setString(5, appointment.getStartDate());
            ps.setString(6, appointment.getEndDate());
            ps.setInt(7, appointment.getClientID());
            ps.setInt(8, appointment.getStyle().getId());

            ps.executeUpdate();     //executing update
            con.close();        //closing connection to database
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @return available clients from database
     */
    public ArrayList<Client> getClients() {
        ArrayList<Client> list = new ArrayList<>();  //arraylist to store all clients
        Client client = null;
        try {

            Connection Con = getConnection();       //establish connection to database
            Statement St = Con.createStatement();
            String Query = "Select * from  client";       //create query to get all clients from database
            ResultSet RS = St.executeQuery(Query);
            while (RS.next()) {     //using while loop to get all clients

                client = new Client(RS.getInt(1), RS.getString(2), RS.getString(3),
                        RS.getString(4), RS.getString(5), RS.getString(6),
                        RS.getString(7), RS.getInt(8) == 1);
                //add client in arraylist
                list.add(client);
            }
            Con.close();        //close connection
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;

    }

    /**
     *
     * @return available users from database
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();  //arraylist to store all users
        User user = null;
        try {

            Connection Con = getConnection();       //establish connection to database
            Statement St = Con.createStatement();
            String Query = "Select * from  User";       //create query to get all clients from database
            ResultSet RS = St.executeQuery(Query);
            while (RS.next()) {     //using while loop to get all users
                //store client detail in client object
                user = new User(RS.getString(1), RS.getString(2), RS.getString(3));
                //add user in arraylist
                users.add(user);
            }
            Con.close();        //close connection
        } catch (Exception e) {
            System.out.println(e);
        }

        return users;

    }

    /**
     *
     * @return available Styles from database
     */
    public ArrayList<Style> getStyles() {
        ArrayList<Style> list = new ArrayList<>();  //arraylist to store all Styles
        Style style = null;
        try {

            Connection Con = getConnection();       //establish connection to database
            Statement St = Con.createStatement();
            String Query = "Select * from  stylist";       //create query to get all contacts from database
            ResultSet RS = St.executeQuery(Query);
            while (RS.next()) {     //using while loop to get all Styles
                //store Style detail in client object
                style = new Style(RS.getInt(1), RS.getString(2));
                //add Style in arraylist
                list.add(style);
            }
            Con.close();        //close connection
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;

    }

    /**
     * make log of user login activity
     *
     * @param attemptNo set attemptNo
     * @param DateTime set dateTime
     * @param Status set status
     */
    public void userLog(int attemptNo, String DateTime, String password, String Status) {
        try {
            //writing data in file using fileWriter and buffered
            FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Attempt No: " + attemptNo);
            bw.write(", ");
            bw.write("Date Time: "+DateTime);
            bw.write(", ");
            bw.write("Password Used: "+password);
            bw.write(", ");
            bw.write("Status: "+Status);
            bw.newLine();
            //closing files
            bw.close();
            fw.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @return specific appointment detail
     */
    public ObservableList<Reports> getWeeklyAppointment() {

        Appointment appointment = null;
        ObservableList<Reports> weeklyAppointments = FXCollections.observableArrayList();
        ArrayList<Style> styles = getStyles();
        try {

            Connection Con = getConnection();       //establish database connection
            Statement St = Con.createStatement();
            //String Query = "Select * from  appt where idappt='" + ID + "'";   //create query
            String weekReport =  "Select count(idappt) as total, type, week(start) as week from appt group by type, week";
            ResultSet RS = St.executeQuery(weekReport);
            while (RS.next()) {        //if appointment with provided appointment id exists
                int total = RS.getInt("total");
                String type = RS.getString("type");
                int week = RS.getInt("week");

                Reports reports1 = new Reports(week, type, total);
                weeklyAppointments.add(reports1);
                }


            Con.close();        //close connection
        } catch (Exception e) {
            System.out.println(e);
        }
        return weeklyAppointments;     //return appointment object with details
    }

    /**
     *
     * @return all available details from database
     */
    public ArrayList<Appointment> getAppointmentsDetails() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        ArrayList<Style> styles = getStyles();
        try {

            Connection Con = getConnection();       //establish connection
            Statement St = Con.createStatement();
            String Query = "Select * from  appt";        //create query to get details
            ResultSet RS = St.executeQuery(Query);
            while (RS.next()) {     //using while loop to get all details present in database
                //adding each appointment detail in arraylist
                Style style = null;
                for (Style s : styles) {
                    if (s.getId() == RS.getInt(9)) {
                        style = s;
                        break;
                    }
                }
                appointments.add(new Appointment(RS.getInt(1), RS.getString(2), RS.getString(3),
                        RS.getString(4), RS.getString(5), convertTimeDateLocal(RS.getString(6)),
                        convertTimeDateLocal(RS.getString(7)), RS.getInt(8), style));
            }
            Con.close();        //close connection
        } catch (Exception e) {
            System.out.println(e);
        }

        return appointments;        //return arraylist
    }

    /**
     *
     * @return all the available countries
     */
    public ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("USA");
        countries.add("Spain");
        return countries;        //return arraylist
    }

    /**
     *
     * @param ID give Client-ID to get Client object accordingly
     * @return specific client from database
     */
    public Client getClients(int ID) {
        Client client = null;
        try {

            Connection Con = getConnection();       //establish database connection
            Statement St = Con.createStatement();
            String Query = "Select * from  client where idclient='" + ID + "'"; //create query to get detail
            ResultSet RS = St.executeQuery(Query);
            if (RS.next()) {        //if client detail exist with provided id
                client = new Client(RS.getInt(1), RS.getString(2), RS.getString(3),
                        RS.getString(4), RS.getString(5), RS.getString(6),
                        RS.getString(7), RS.getInt(8) == 1);
            }
            Con.close();        //closing connection
        } catch (Exception e) {
            System.out.println(e);
        }
        return client;
    }

    public int getActiveClientsCount() {
        ArrayList<Client> clients = getClients();
        int count = 0;
        for (Client client : clients) {
            if (client.isActive()) {
                count++;
            }
        }
        return count;
    }

    public int getInActiveClientsCount() {
        ArrayList<Client> clients = getClients();
        int count = 0;
        for (Client client : clients) {
            if (!client.isActive()) {
                count++;
            }
        }
        return count;
    }

}
