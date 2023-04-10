package com.example.cabtap;
import java.util.ArrayList;

public class DispatcherController {

    //ArrayList<TripInformation> rideRequests = new ArrayList<TripInformation>();

    // user submits requests a ride
    private void setRideRequests(TripInformation ride){
        TripDatabase.InsertTrip(trip);
    }

    private void removeRequestRide(TripInformation ride){
        rideRequests.remove(ride);
        // either 
    }

    private void sendRideShareOffer(TripInformation ride){
        // sends offer to DisplayOpenRidesPage
    }

    protected void recieveRideShareOffer(){
        // gets from offerRideSharePage
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
