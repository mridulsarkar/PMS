/*
    Responsible: Mridul Sarkar
*/
package com.mridul.pms;

// Vehicle super class
public abstract class Vehicle {
 
    private String regn;        // Regn number of vehicle
    private String colour;      // Colour of vehicle
    
    // Default constructor
    public Vehicle() {
    
    }
    
    // Constructor
    public Vehicle(String regn, String colour) {
        this.regn = regn;
        this.colour = colour;
    }
    
    // Getters & Setters
    public String getRegnNumber() {
        return regn;
    }

    public void setRegnNumber (String regn) {
        this.regn = regn;
    }
    
    public String getColour() {
        return colour;
    }
    
    public void setColour(String colour) {
        this.colour = colour;
    }
    
    // Print Method for debugging
    public void printVehicleDetails() {
        System.out.println("The Regn number of this vehicle is " + regn);
        System.out.println("The Colour of this vehicle is " + colour);
    }
}