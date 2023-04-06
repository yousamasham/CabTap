package com.example.cabtap;
import java.util.*;

import androidx.fragment.app.Fragment;
public class PresentOfferPage extends Fragment{

    Button accept;
    Button reject;

    private void dispalyRequestRides(){
        ArrayList<TripInformation> rides = DispatcherController.getRides();
        //display all options, create an accept and reject button for each
        if (isNull(rides)){
            // show message saying no offers yet
        }
    }
    
    // called when accept button is pressed
    private void acceptRide(){
        // calls dispatcher and dispatcher pairs riders together
    }

    // called when reject button is pressed
    private void rejectRide(){
        // dispatcher removes from list of requests for the rider.
    }

}