package com.example.qam2_alternativeassessment.model;

/**
 * This Class stores users information
 *
 */
public class User {

    /**
     * Declaring Class Attributes
     */
    private String userID;
    private String userName;
    private String userPassword;

    /**
     * This is a constructor use to initialize all values for Division
     *
     * @param userID for store UserID
     * @param userName for store UserName
     * @param userPassword for store UserPassword
     */
    public User(String userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * this is a getter for UserID
     *
     * @return ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * this is a setter for UserID
     *
     * @param userID for store/modify userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * this is a getter for UserName
     *
     * @return name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * this is a setter for UserName
     *
     * @param userName for store/modify UserName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * this is a getter for Password
     *
     * @return password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * this is a setter for Password
     *
     * @param userPassword for store/modify UserPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * method to display User Details
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", userPassword=" + userPassword + '}';
    }

}
