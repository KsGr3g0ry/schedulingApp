package com.example.qam2_alternativeassessment.model;

/**
 * This Class stores customers information
 *
 */
public class Client {

    /**
     * Declaring Class Attributes
     */
    private int id;
    private String name;
    private String email;
    private String hairColor;
    private String postalCode;
    private String state;
    private String country;
    private boolean isActive;

    /**
     * This is a constructor use to initialize all values for Client
     *
     * @param id for store ClientID
     * @param name for store ClientName
     * @param postalCode for store PostalCode
     * @param email for store email
     * @param hairColor for store hairColor
     * @param country for store Country
     * @param state for store state
     * @param isActive for activeness
     */
    public Client(int id, String name, String email, String hairColor, String postalCode, String state, String country, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hairColor = hairColor;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
        this.isActive = isActive;
    }

    /**
     * this is getter for ClientID
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * this is setter for id
     *
     * @param id for store ClientID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * this is getter for ClientName
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * this is setter/modifier for ClientName
     *
     * @param name for store ClientName
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * this is getter for PostalCode
     *
     * @return postal Code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * this is setter for postal code
     *
     * @param postalCode for store PostalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * this is getter for Email
     *
     * @return phone number
     */
    public String getEmail() {
        return email;
    }

    /**
     * this method use to Display Client Details
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", email=" + email + ", hairColor=" + hairColor + ", postalCode=" + postalCode + ", state=" + state + ", country=" + country + ", isActive=" + isActive + '}';
    }

    /**
     * this is a setter for phone number
     *
     * @param email for store Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * this is a getter for HairColor
     *
     * @return hairColor
     */
    public String getHairColor() {
        return hairColor;
    }

    /**
     * this is a setter for HairColor
     *
     * @param hairColor for store HairColor
     */
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * this is a getter for Country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * this is a getter for country
     *
     * @param country for store country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * this is a getter for State
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * this is a setter for state
     *
     * @param state for store state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * getter for active
     *
     * @return boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * getter for active
     *
     * @return boolean
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * Setter for active
     *
     * @param isActive for active
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
