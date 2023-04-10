package com.example.cabtap;
import java.util.ArrayList;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import javax.swing.event.TreeWillExpandListener;

public class DispatcherController {

    //Ride in progress has been offered to share
    private void setRideOffer(TripInformation ride){
        TripDatabase.InsertTrip(ride);
        sendRideShareOffer(ride);
    }

    private void removeRequestRide(TripInformation ride){
        TripDatabase.RemoveRequest(ride);
    }

    //Adding an added trip to the open rides for requesters
    private void sendRideShareOffer(TripInformation ride){
        DisplayOpenRidesPage.updateRides(ride);
    }

    //Database recieves ride request
    protected void recieveRideRequest(TripInformation ride){
        TripDatabase.InsertRequest(ride);
    }

    protected void pairRiders(TripInformation ride, String username){
        TripInformation tripRequested = TripDatabase.GetRequest(username);
        TripDatabase.pushOffer(ride.getUsername(), tripRequested);
        while(TripDatabase.checkOffer()==null)
        if(TripDatabase.checkOffer()){
            //switch waiting to accept to inTransit page
            TripDatabase.RemoveRequest(username);
        }
        else{
            //switch waiting to accept to display open rides page
        }
    }

    protected ArrayList<TripInformation> getRides(){
        ArrayList<TripInformation> rideOffers = new ArrayList<TripInformation>();
        rideOffers = TripDatabase.AvailableTrips();
        return rideOffers;

    }

}
