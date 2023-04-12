package com.example.cabtap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TripInformation {
    protected String pickupLocation;
    protected String destination;
    protected LocalTime rideTime;
    protected float rideFare;
    protected int capacity;
    protected ArrayList<String> usersEncountered;
    protected LocalDate rideDate;
    protected String username;


    public TripInformation(String pickupLocation, String destination, String username, int desiredCap){
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        usersEncountered.add(username);
        this.capacity = desiredCap;
    }

    protected void setPickupLocation(String pickup){
        pickupLocation = pickup;
    }

    protected String getPickupLocation(){
        return pickupLocation;
    }

    protected void setDate(LocalDate date){
        rideDate = date;
    }

    protected String getDate(){
        return rideDate.toString();
    }

    protected void setDestination(String dest){
        destination = dest;
    }

    protected String getDestination(){
        return destination;
    }

    protected int getCapacity(){
        return capacity;
    }

    protected void setRideTime(LocalTime time){
        rideTime = time;
    }

    protected LocalTime getRideTime(){
        return rideTime;
    }

    protected void setRideFare(float fare){
        rideFare = fare;
    }

    protected float getRideFare(){
        return rideFare;
    }

    // once offer is accepted, user ID will be added to the list of riders for the ride.
    protected void addUserEncountered(String username){
        usersEncountered.add(username);
    }

    protected ArrayList<String> getUsersEncountered(){
        return usersEncountered;
    }

    // list of riders for the ride will be deleted once the ride is completed.
    protected void finishRide(){
        usersEncountered.clear();;
    }
}