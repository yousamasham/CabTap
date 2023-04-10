package com.example.cabtap;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.time.*;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class OfferRideSharePage extends Fragment {
    EditText dropOff;
    // private roadmap map;
    EditText availableSeats;
    Button submit;
    private LocalTime approxTime;
    private int approxSaving;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ){
        dropOff = (EditText) getView().findViewById(R.id.et_dropOff);
        availableSeats = (EditText) getView().findViewById(R.id.et_availableSeats);
        submit = (Button) getView().findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current location of rider
                /*if(dropOff == currentLocation){//add out of range checks to if
                    throw new ValidTripException("Invalid pickup and/or dropoff locations entered.");
                }*/
                // calculate the approx route to show to requesters
                
                TripInformation trip = new TripInformation();
                //trip.setPickupLocation(currentLocation);
                trip.setDestination(dropOff);
                trip.setRideTime(Time.now());
                trip.setDate(Date.now());
                trip.setCapacity(availableSeats);
                controller.setRideRequests(trip);

                //displayConfirmation();

                // THIS IS TESTER CODE FOR MAPS ~Cieran
                String drop = dropOff.getText().toString().trim();
                String seats = availableSeats.getText().toString().trim();
                if (drop != null && seats != null) {
                    Intent intent = new Intent(getActivity(), InTransitPage.class);
                    intent.putExtra("destination", drop);
                    intent.putExtra("seats", seats);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_offersharepage, container, false);
        }

    // opens present offer page with a confirmation.
    private void displayConfirmation(){
        Intent intent = new Intent(getActivity(), PresentOfferPage.class);
        startActivity(intent);
    }
    
    private ArrayList<Object> displayRouteDetails(){
        ArrayList<Object> routeDetails = new ArrayList<Object>();
        routeDetails.add(availableSeats);
        routeDetails.add(approxTime);
        routeDetails.add(approxSaving);
        return routeDetails;
    }
}
public class ValidTripException extends Exception{
    public ValidTripException(String message){
        super(message);
    }
}