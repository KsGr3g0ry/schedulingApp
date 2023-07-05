package com.example.qam2_alternativeassessment.model;

/**
 * Style class
 */
public class Style {

    private int id;
    private String name;

    /**
     * Default Parameterized constructor
     *
     * @param id the id
     * @param name the name
     */
    public Style(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * getter for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    

}
