/*
    Responsible: Mridul Sarkar
*/
package com.mridul.pms;

// Class Car implementation
public class Car extends Vehicle {
 
    private String model; // variable to capture model of the car
    
    // Constructors
    public Car() {
        super();
        this.model = "";
    }
    
    public Car(String regn, String colour) {
        super(regn, colour);
    }

    public Car(String regn, String colour, String model) {
        super(regn, colour);
        this.model = model;
    }
    
    // Getter
    public String getModel() {
        return model;
    }
    
    // Setter
    public void setModel (String model) {
        this.model = model;
    }
    
    // Print Method
    public void printCarDetails() {
        super.printVehicleDetails(); // Call superclass method
        System.out.println("Car Model: " + model);
    }
}