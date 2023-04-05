package com.example.cabtap;
import java.util.ArrayList;

public class DispatcherController {

    ArrayList<TripInformation> rideRequests = new ArrayList<TripInformation>();

    // user submits requests a ride
    private void setRideRequests(String arrivalTime, String dropOff, String pickup){

    }

    private void removeRequestRide(TripInformation ride){
        rideRequests.remove(ride);
    }

    private void sendRideShare(){

    }

    protected void recieveRideShareOffer(){

    }

    protected void pairRiders(){
        // calls getRides(), recieveRideShareOffer(), and the answer of the offerer
    }

    protected ArrayList<TripInformation> getRides(){
        ArrayList<TripInformation> rideOffers = new ArrayList<TripInformation>();
        //search available rides and add rides that are applicable
        return rideOffers;

    }

}
