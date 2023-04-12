package com.example.cabtap;
import java.util.ArrayList;

public class DispatcherController {

    //Ride in progress has been offered to share
    protected void setRideOffer(TripInformation ride){
        TripDatabase.InsertTrip(ride);
        sendRideShareOffer(ride);
    }

    private void removeRequestRide(TripInformation ride){
        TripDatabase.RemoveRequest(ride.getUsername());
    }

    private void sendRideShareOffer(TripInformation ride){
        // sends offer to DisplayOpenRidesPage
    }

    protected void recieveRideShareOffer(){
        // gets from offerRideSharePage
    }

    //Sends request to user then informs requestee of the result
    protected boolean pairRiders(TripInformation ride, String username){
        TripInformation tripRequested = TripDatabase.GetRequest(username);
        TripDatabase.PushOffer(ride.getUsername(), tripRequested);
        while(Boolean.valueOf(TripDatabase.CheckOffer(tripRequested)) == null){};
        if(Boolean.valueOf(TripDatabase.CheckOffer(tripRequested))){
            TripDatabase.RemoveRequest(username);
            TripDatabase.RemoveOffer(username);
            return true;
        }
        else{
            TripDatabase.RemoveOffer(username);
            return false; 
        }
    }

    protected ArrayList<TripInformation> getRides(){
        ArrayList<TripInformation> rideOffers = new ArrayList<TripInformation>();
        //search available rides and add rides that are applicable
        return rideOffers;

    }

}
