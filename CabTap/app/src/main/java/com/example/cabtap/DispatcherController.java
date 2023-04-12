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

    //Adding an added trip to the open rides for requesters
    private void sendRideShareOffer(TripInformation ride){
        DisplayOpenRidesPage.updateRides(ride);
    }

    //Database recieves ride request
    protected void recieveRideRequest(TripInformation ride) throws Exception {
        try {
            TripDatabase db = new TripDatabase();
            db.InsertRequest(ride);
        }
        catch(Exception E){
            throw E;
        }
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
        rideOffers = TripDatabase.AvailableTrips();
        return rideOffers;
    }

}
