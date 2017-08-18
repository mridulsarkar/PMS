/**
 *   Responsible: Mridul Sarkar
 */
package com.mridul.pms;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// Parking Lot class 
public class ParkingLot {
    private static final int defaultCapacity = 10;
    private int totalParkingSlots = 0;
    private PriorityQueue<ParkingSlot> parkingSlotQueue;
    private Map<Integer, ParkingSlot> occupiedSlots;
    
    // Default Constructor
    public ParkingLot() {
        this(defaultCapacity);
    }

    // Constructor
    public ParkingLot(int totalParkingSlots) {
        this.totalParkingSlots = totalParkingSlots;
        parkingSlotQueue = new PriorityQueue<ParkingSlot>(totalParkingSlots, new ParkingSlotComparator());
        occupiedSlots = new HashMap<Integer, ParkingSlot>(totalParkingSlots);
        // Initialise the priority queue with all the parking slots
        for (int i = 1; i <= totalParkingSlots; i++) {
            parkingSlotQueue.add(new ParkingSlot(i));
        }
        System.out.println("Created a parking lot with " + this.totalParkingSlots + " slots\n");
    }

    // Method to get Total number of slots in the parking slot
    public int getTotalParkingSlots() {
        return totalParkingSlots;
    }
    
    // Method to get Available slots in the parking lot
    public synchronized int availableParkingSlots() {
        return parkingSlotQueue.size();
    }
    
    // Method to get Occupied slots in the parking lot
    public synchronized int occupiedParkingSlots() {
        return occupiedSlots.size();
    }

    // Comparator to sort the parking lots
    class ParkingSlotComparator implements Comparator<ParkingSlot> {
        @Override
        public int compare(ParkingSlot arg0, ParkingSlot arg1) {
            if (arg0.getParkingSlotID() < arg1.getParkingSlotID())
                return -1;
            else if (arg0.getParkingSlotID() > arg1.getParkingSlotID()){
                return 1;
            }
            return 0;
        }       
    }

    // Helper method to print an Array List
    public static void printList(List printList) {
        for (int i = 0 ; i < printList.size(); i++) {
            if(i == printList.size() - 1)
                System.out.println(printList.get(i) + "\n");
            else
                System.out.print(printList.get(i) + ", ");
        }           
    }
    
    // Method to Add a vehicle to the parking slot
    // returns true for a succsfully booking a parking slot
    // returns false if the parking lot is full
    public synchronized boolean parkVehicle(Vehicle vehicle) {
        // If priority queue is not empty which means parking slots are available   
        if (!parkingSlotQueue.isEmpty()) {
            // Get a available parking slot from the queue
            ParkingSlot reserveSlot = (ParkingSlot) parkingSlotQueue.remove();
            // Add to the occupied slot map
            occupiedSlots.put(reserveSlot.getParkingSlotID(), reserveSlot);
            // Book the slot and allocate to the New vehicle coming into the parking lot
            reserveSlot.bookSlot(vehicle);
            System.out.println("Allocated slot number: " + reserveSlot.getParkingSlotID().intValue() + "\n");
            return true;
        } else {
            System.out.println("Sorry, parking lot is full\n");
            return false;
        }   
    }

    // Method to Remove a vehicle from the parking slot
    // returns true for a succsfully freeing a parking slot
    // returns false for an invalid parking slot-id
    public synchronized boolean removeVehicle(int freeThisSlot) { 
        // Check if a valid slot-id has been given
        if (!occupiedSlots.containsKey(freeThisSlot)) {
            System.err.println("Invalid Slot number " + freeThisSlot + "\n");
            return false;
        }
        // Remove this slot from occupied slot map
        ParkingSlot removedSlot = occupiedSlots.remove(freeThisSlot);
        removedSlot.freeSlot();
        // Add this free slot now to the priority queue
        parkingSlotQueue.add(removedSlot);
        System.out.println("Slot number " + freeThisSlot + " is free\n");
        return true;
    }

    // Method prints Regn number of Vehicles parked based on a given colour
    // And returns list of the matched vehicles
    public synchronized List <String> lookupRegnWithColour(String colour) {
        List <String> parkingSlotList = new ArrayList<String>();
        ParkingSlot slot = null;
        for (Map.Entry<Integer, ParkingSlot> prkngSlot  : occupiedSlots.entrySet()) {
            slot = (ParkingSlot) prkngSlot.getValue();
            if ( slot.getParkedVehicle().getColour().equals(colour) ) {
                parkingSlotList.add(slot.getParkedVehicle().getRegnNumber());    
            }
        }
        if ( parkingSlotList.size() > 0 ) {
            ParkingLot.printList(parkingSlotList); 
        } else {
            System.out.println("Not found" + "\n");
        }
        return parkingSlotList;
    }

    // Method prints Occupied Parking Slot numbers based on vehicle colour
    // And returns list of the matched vehicles
    public synchronized List <Integer> lookupSlotWithColour(String colour) {
        List <Integer> parkingSlotList = new ArrayList<Integer>();
        ParkingSlot slot = null;
        for (Map.Entry<Integer, ParkingSlot> prkngSlot  : occupiedSlots.entrySet()) {
            slot = (ParkingSlot) prkngSlot.getValue();
            if ( slot.getParkedVehicle().getColour().equals(colour) ) {
                parkingSlotList.add(slot.getParkingSlotID());    
            }
        }
        if ( parkingSlotList.size() > 0 ) {
            ParkingLot.printList(parkingSlotList);   
        } else {
            System.out.println("Not found");
        }
        return parkingSlotList;
    }

    // Method prints Occupied Parking Slot numbers based on vehicle registration number
    // And returns list of the matched vehicles
    public synchronized List <Integer> lookupSlotWithVehicleRegnNumber(String vehicleRegn) {
        List <Integer> parkingSlotList = new ArrayList<Integer>();
        ParkingSlot slot = null;
        for (Map.Entry<Integer, ParkingSlot> prkngSlot  : occupiedSlots.entrySet()) {
            slot = (ParkingSlot) prkngSlot.getValue();
            if ( slot.getParkedVehicle().getRegnNumber().equals(vehicleRegn) ) {
                parkingSlotList.add(slot.getParkingSlotID());    
            }
        }
        if ( parkingSlotList.size() > 0 ) {
            ParkingLot.printList(parkingSlotList);    
        } else {
            System.out.println("Not found");
        }
        return parkingSlotList;
    }           

    // Method to print parked vehicle details
    public synchronized void printParkedVehicleDetails() {    
        // For every vehicle
        ParkingSlot slot = null;
        System.out.println("Slot No" + "\t" + "Registration No." + "\t" + "Colour\n");
        for (Map.Entry<Integer, ParkingSlot> prkngSlot  : occupiedSlots.entrySet()) {
            slot = (ParkingSlot) prkngSlot.getValue();
            System.out.println(slot.getParkingSlotID().intValue() + "\t" + slot.getParkedVehicle().getRegnNumber() + "\t\t" + slot.getParkedVehicle().getColour()  + "\n");
        }    
    }
}
