package com.example.qam2_alternativeassessment.model;

/**
 *
 * This Class Stores information of different Reports
 */
public class Reports {

    private int total;
    private String type;
    private int week;
    /**
     * Declaring Class Attributes
     */
    private String str1;
    private String str2;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    private int counter;

    /**
     * Parametric Constructor initialize class variables
     *
     * @param str1 for store string1
     * @param str2 for store string2
     * @param counter for store counter
     */
    public Reports(String str1, String str2, int counter) {
        this.str1 = str1;
        this.str2 = str2;
        this.counter = counter;
    }
    public Reports(int week, String type, int total) {
        this.week = week;
        this.type = type;
        this.total = total;
    }

    /**
     * this is a getter for incoming String1
     *
     * @return string1
     */
    public String getStr1() {
        return str1;
    }

    /**
     * this is a setter for incoming String1
     *
     * @param str1 for store/modify string1
     */
    public void setStr1(String str1) {
        this.str1 = str1;
    }

    /**
     * this is a getter for incoming String2
     *
     * @return string2
     */
    public String getStr2() {
        return str2;
    }

    /**
     * this is a getter for incoming String2
     *
     * @param str2 for store/modify string2
     */
    public void setStr2(String str2) {
        this.str2 = str2;
    }

    /**
     * this is a getter for incoming String3
     *
     * @return string3
     */
    public int getCounter() {
        return counter;
    }

    /**
     * this is a getter for incoming String3
     *
     * @param counter for store/modify string3
     */
    public void setStr3(int counter) {
        this.counter = counter;
    }

    /**
     * method to display class variable values
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "Reports{" + "str1=" + str1 + ", str2=" + str2 + ", str3=" + counter + '}';
    }

}
