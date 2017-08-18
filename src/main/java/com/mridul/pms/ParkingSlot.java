/**
 *   Responsible: Mridul Sarkar
 */
package com.mridul.pms;

// Parking Slot class 
public class ParkingSlot {
    private Integer slotID;
    private boolean isAvailable;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotid) {
        this.slotID = new Integer(slotid);
        this.isAvailable = true;        
    }

    // Getters and Setters
    public Integer getParkingSlotID() {
        return slotID;
    }

    public void setParkingSlotID(int slotid) {
        this.slotID = new Integer(slotid);    
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void bookSlot(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
        isAvailable = false;
    }

    public void freeSlot() {
        this.parkedVehicle = null;
        isAvailable = true;
    }

}
