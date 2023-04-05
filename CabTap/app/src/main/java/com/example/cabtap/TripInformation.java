package com.example.cabtap;
import java.time.LocalTime;
import java.util.ArrayList;

public class TripInformation {
    protected String pickupLocation;
    protected String destination;
    protected LocalTime rideTime;
    protected float rideFare;
    protected ArrayList usersEncountered;

    protected void setPickupLocation(String pickup){
        pickupLocation = pickup;
    }

    protected String getPickupLocation(){
        return pickupLocation;
    }

    protected void setDestination(String dest){
        destination = dest;
    }

    protected String getDestination(){
        return destination;
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
    protected void setUsersEncountered(String users){
        usersEncountered.add(users);
    }

    protected ArrayList<String> getUsersEncountered(){
        return usersEncountered;
    }

    // list of riders for the ride will be deleted once the ride is completed.
    protected void clearUsers(){
        usersEncountered.clear();;
    }
}