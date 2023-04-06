package com.example.cabtap;
import java.time.*;
import java.util.ArrayList;

public class OfferRideSharePage {
    private String arrivalTime;

    //private roadmap map;
    private int availableSeats;
    private int cost;
    private LocalTime approxTime;
    private int approxSaving;

    private void displayConfirmationPage(){

    }
    private ArrayList<Object> displayRouteDetails(){
        ArrayList<Object> routeDetails = new ArrayList<Object>();
        routeDetails.add(availableSeats);
        routeDetails.add(cost);
        routeDetails.add(approxTime);
        routeDetails.add(approxSaving);
        return routeDetails;
    }
}