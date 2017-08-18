/**
 *   Responsible: Mridul Sarkar
 */
package com.mridul.pms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map; 

// Parking Management class 
public class ParkingManagement {

    private static final Map<String, Integer> commandMap = new HashMap<String, Integer>();
    static { 
        commandMap.put("create_parking_lot", 1);
        commandMap.put("park", 2);
        commandMap.put("leave", 3);
        commandMap.put("status", 4);
        commandMap.put("registration_numbers_for_cars_with_colour", 5);
        commandMap.put("slot_numbers_for_cars_with_colour", 6);
        commandMap.put("slot_number_for_registration_number", 7);
    }

    private ParkingLot myPrkngLot; // ParkingLot member variable for lot actions
    
    // Getter and Setters
    public ParkingLot getParkingLot () {
        return this.myPrkngLot;
    }

    public void setParkingLot (ParkingLot myPrkngLot) {
        this.myPrkngLot = myPrkngLot;
    }
   
    private void initializeParkingLot() {
        if ( null == myPrkngLot ) {
            myPrkngLot = new ParkingLot();
        }
    }

    // Helper method validates an Integer
    public static boolean validInteger(String intArg) {
        try {
            Integer.parseInt(intArg);
        } catch (NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    private void action (String commandLine) {
        // Extract the command assuming the first word in the line is the action
        String[] parts = commandLine.split("\\s+"); // Tokenize based on white space
        String command = parts[0].trim(); // Trim any leading white space in the command
        Integer commandID = commandMap.get(command);
        boolean validCmd = false;

        if ( null != commandID ) {
            switch(commandID.intValue()) {
                case 1:
                        if ( parts.length == 2 && validInteger(parts[1]) ) {
                            myPrkngLot = new ParkingLot(Integer.valueOf(parts[1]));
                            validCmd = true;
                        }
                        break;
                case 2:
                        if ( parts.length == 3 ) {
                            initializeParkingLot();
                            myPrkngLot.parkVehicle(new Car(parts[1], parts[2]));
                            validCmd = true;
                        }
                        break;
                case 3:
                        if ( parts.length == 2 && validInteger(parts[1]) ) {
                            initializeParkingLot();
                            myPrkngLot.removeVehicle(Integer.valueOf(parts[1]));
                            validCmd = true;                            
                        }                
                        break;
                case 4:
                        if ( parts.length == 1 ) {
                            initializeParkingLot();
                            myPrkngLot.printParkedVehicleDetails();
                            validCmd = true;                           
                        }                
                        break;
                case 5:
                        if ( parts.length == 2 ) {
                            initializeParkingLot();
                            myPrkngLot.lookupRegnWithColour(parts[1]);
                            validCmd = true;                            
                        }
                        break;
                case 6:
                        if ( parts.length == 2 ) {
                            initializeParkingLot();
                            myPrkngLot.lookupSlotWithColour(parts[1]);
                            validCmd = true;                            
                        }                
                        break;
                case 7:
                        if ( parts.length == 2 ) {
                            initializeParkingLot();
                            myPrkngLot.lookupSlotWithVehicleRegnNumber(parts[1]);
                            validCmd = true;                           
                        }                
                        break;
                default:
                        // do nothing
            }            
        }

        // Print error if not a valid command 
        if( !validCmd ) {
            System.err.println("Invalid Command: " + commandLine + "\n");
        }
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        ParkingManagement pmMgmtInstance = null;
        String input = null;
        
        try {
            // Instansiate a Parking Mgmt object
            pmMgmtInstance = new ParkingManagement();
            if ( args.length > 0 && null != args[0] ) { // Process an input file              
                br = new BufferedReader(new FileReader(args[0]));
                input = br.readLine();
                while ( null != input ) {
                    pmMgmtInstance.action(input);
                    input = br.readLine();
                }
            } else { // Process command prompt                
                br = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    input = br.readLine();
                    if ("q".equals(input)) {
                        System.out.println("Exited PMS!!!");
                        System.exit(0);
                    }
                    pmMgmtInstance.action(input);
                }
            }
        } catch (IOException exp) {
            System.err.println("Error: " + exp.getMessage());
        } finally {
            if ( null != br ) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

}
