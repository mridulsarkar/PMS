/**
 *    Responsible: Mridul Sarkar
 */
package com.mridul.pms;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class PMJunit {
    private ParkingLot pLot;

    @BeforeClass
    public static void initParkingLot() {
        //pLot = new ParkingLot(2);
    }

    @Before
    public void beforeEachTest() {
        pLot = new ParkingLot(2);
        //System.out.println("This is executed before each Test");
    }

    @After
    public void afterEachTest() {
        pLot = null;
        //System.out.println("This is executed after each Test");
    }

    @Test
    public void testCreateParkingLot() {
        int result = pLot.getTotalParkingSlots();
        assertEquals(2, result);
    }

    @Test
    public void testParkVehicle() {
        // Add First Car
        Vehicle vh = new Car("KA-01-HH-1234", "White");
        pLot.parkVehicle(vh); // Park Car1
        // Add 2nd Car
        vh = new Car("KA-01-HH-9999", "White");
        pLot.parkVehicle(vh); // Park Car2
        int result = pLot.occupiedParkingSlots(); // Get number of occupied slots
        assertEquals(2, result); 
    }

    @Test
    public void testExtraVehicleParking() {
        // Park First Car
        Vehicle vh = new Car("KA-01-HH-1234", "White");
        pLot.parkVehicle(vh); // Park Car1
        // Park Second Car
        vh = new Car("KA-01-HH-9999", "White");
        pLot.parkVehicle(vh); // Park Car2
        // Try Parking Third Car
        vh = new Car("KA-01-HH-7777", "Red");
        boolean available = pLot.parkVehicle(vh);
        assertFalse(available); // Vehicle should not be allowed to park
    }

    @Test
    public void testVehicleRemoval() {
        // Add First Car
        Vehicle vh = new Car("KA-01-HH-1234", "White");
        pLot.parkVehicle(vh); // Park Car1
        // Add Second Car
        vh = new Car("KA-01-HH-9999", "White");
        pLot.parkVehicle(vh); // Park Car2
        // Move-out First Car
        pLot.removeVehicle(1);
        int result = pLot.occupiedParkingSlots(); // Get number of occupied slots
        assertEquals(1, result);
    }  

    @Test
    public void testNearestSlotAllotment() {
        pLot = null;
        // Create a Parking lot with 3 car parks
        pLot = new ParkingLot(3);

        // Add First Car
        Vehicle vh = new Car("KA-01-HH-1234", "White");
        pLot.parkVehicle(vh); // Park Car1
        // Add Second Car
        vh = new Car("KA-01-HH-9999", "White");
        pLot.parkVehicle(vh); // Park Car2
        // Add Third Car
        vh = new Car("KA-01-HH-2701", "Blue");
        pLot.parkVehicle(vh); // Park Car3

        // Move-out First Car
        pLot.removeVehicle(1);
        // Move-out Third Car
        pLot.removeVehicle(3);

        // Park another Car
        vh = new Car("KA-01-HH-3141", "Black");
        pLot.parkVehicle(vh); // Park Car
        List <Integer> parkingSlotList = ( List <Integer>) pLot.lookupSlotWithVehicleRegnNumber(vh.getRegnNumber());
        assertEquals(1, ((Integer)parkingSlotList.get(0)).intValue() ); // The third car should get the Slot# 1 which got empty
    }

    @Test
    public void testVehicleLookUpByRegn() {
        // Add First Car
        Vehicle vh = new Car("park KA-01-HH-3141", "Black");
        pLot.parkVehicle(vh); // Park Car1
        
        // Add Second Car
        vh = new Car("KA-01-HH-7777", "Red");
        pLot.parkVehicle(vh); // Park Car2

        // Lookup for Parking slot# with Regn number "KA-01-HH-7777"
        List <Integer> parkingSlotList = ( List <Integer>) pLot.lookupSlotWithVehicleRegnNumber(vh.getRegnNumber());
        assertEquals(2, ((Integer)parkingSlotList.get(0)).intValue() ); // This should return slot 2
    }

    @Test
    public void testVehicleLookUpByColour() {
        pLot = null;
        // Create a Parking lot with 3 car parks
        pLot = new ParkingLot(3);

        // Add First Car
        Vehicle vh = new Car("KA-01-HH-1234", "White");
        pLot.parkVehicle(vh); // Park Car1
        // Add Second Car
        vh = new Car("KA-01-HH-2701", "Blue");
        pLot.parkVehicle(vh); // Park Car2
        // Add Third Car
        vh = new Car("KA-01-HH-9999", "White");
        pLot.parkVehicle(vh); // Park Car3

        // Lookup for car with "White" colour
        List <String> parkingSlotList = ( List <String>) pLot.lookupRegnWithColour("White");
        assertEquals(2, parkingSlotList.size()); // We should retrieve 2 White cars
    }

    @Test
    public void testParkingSlotLookUpByColour() {
        // Add First Car
        Vehicle vh = new Car("park KA-01-HH-3141", "Black");
        pLot.parkVehicle(vh); // Park Car1
        
        // Add Second Car
        vh = new Car("KA-01-HH-7777", "Red");
        pLot.parkVehicle(vh); // Park Car2

        // Lookup for Parking slot# with colour "Black"
        List <Integer> parkingSlotList = ( List <Integer>) pLot.lookupSlotWithColour("Black");
        assertEquals(1, ((Integer)parkingSlotList.get(0)).intValue() ); // This should return slot 1
    }       
}