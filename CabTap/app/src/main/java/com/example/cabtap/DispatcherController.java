package com.example.cabtap;
import java.util.ArrayList;

public class DispatcherController {

    //ArrayList<TripInformation> rideRequests = new ArrayList<TripInformation>();

    private void setRideRequests(TripInformation ride){
        TripDatabase.InsertTrip(trip);
    }

    private void removeRequestRide(TripInformation ride){
        TripDatabase.RemoveTrips(ride);
    }

    private void sendRideShareOffer(TripInformation ride){
        DisplayOpenRidesPage.updateRides(ride);
    }

    //I think this can be removed and we just use setRideRequests
    protected void recieveRideShareOffer(){
        // gets from offerRideSharePage
    }

    protected void pairRiders(){
        getRides();
        recieveRideShareOffer();
        sendRideShareOffer();
    }

    protected ArrayList<TripInformation> getRides(){
        ArrayList<TripInformation> rideOffers = new ArrayList<TripInformation>();
        rideOffers = TripDatabase.AvailableTrips();
        return rideOffers;

    }

}
