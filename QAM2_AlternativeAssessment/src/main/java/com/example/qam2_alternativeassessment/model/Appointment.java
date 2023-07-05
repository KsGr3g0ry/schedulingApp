/**
 * <h1>Appointment class!</h1>
 * This Class saves details for Appointments
 *
 * <p>
 * @author PC
 */
package com.example.qam2_alternativeassessment.model;

/**
 *
 * This Class holds appointment records
 */
public class Appointment {

    /**
     * Declaring Class Attributes
     */
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String startDate;
    private String endDate;
    private int clientID;
    private Style style;

    /**
     * This is a constructor use to initialize all values for appointment
     *
     * @param appointmentID for store Appointment ID
     * @param title for store Appointment Title
     * @param description for store Appointment Description
     * @param location for store Appointment Location
     * @param type for store Appointment Type
     * @param startDate for store Appointment Start Date
     * @param endDate for store Appointment End Date
     * @param clientID for client id
     * @param style for style
     *
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, String startDate, String endDate, int clientID, Style style) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientID = clientID;
        this.style = style;
    }

    /**
     * this is a getter for AppointmentID
     *
     * @return appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * this is a setter/modifier for appointment ID
     *
     * @param appointmentID for modify Appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * this is a getter for title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * this is a setter/modifier for title
     *
     * @param title for modify Appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * this is a setter/modifier for description
     *
     * @param description for modify description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * this is a getter for Location
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * this is a setter for location
     *
     * @param location for modify Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * this is a getter for Type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * this is a setter for type
     *
     * @param type for modify Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * this is a getter for startDateTime of Appointment
     *
     * @return startDateTime
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * this is a setter for startDateTime of Appointment
     *
     * @param startDate for modify StartDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * this is a getter for endDateTime of Appointment
     *
     * @return end date time
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * this is a setter/modifier for endDateTime of Appointment
     *
     * @param endDate for modify EndDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * this is a getter for ClientID
     *
     * @return client ID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * this is a setter for customer ID
     *
     * @param ClientID for modify clientID
     */
    public void setClientID(int ClientID) {
        this.clientID = ClientID;
    }

    /**
     * Getter for style
     *
     * @return style
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Setter for style
     *
     * @param style style
     */
    public void setStyle(Style style) {
        this.style = style;
    }

}
