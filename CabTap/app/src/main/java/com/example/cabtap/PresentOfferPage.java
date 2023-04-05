package com.example.cabtap;
import java.util.*;

import androidx.fragment.app.Fragment;
public class PresentOfferPage extends Fragment{

    Button accept;
    Button reject;

    private void dispalyRequestRides(){
        ArrayList<TripInformation> rides = new ArrayList<>();
        rides = DispatcherController.getRides();
        //display all options, create an accept and reject button for each
    }
    
    // called when accept button is pressed
    private void acceptRide(){



    }

    // called when reject button is pressed
    private void rejectRide(){

    }

}