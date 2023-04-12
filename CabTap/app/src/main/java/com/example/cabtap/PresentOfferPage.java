package com.example.cabtap;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PresentOfferPage extends Fragment {

    Button accept;
    Button reject;
    TextView dropOff, approxTime, approxSavings, pickup;
    DispatcherController controller = new DispatcherController();
    
    public static PresentOfferPage newInstance(SessionDetails profile) {
        PresentOfferPage fragment = new PresentOfferPage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }    

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ){
        Bundle args = getArguments();
        accept = (Button) getView().findViewById(R.id.btn_accept);
        reject = (Button) getView().findViewById(R.id.btn_reject);
        pickup = (TextView) getView().findViewById(R.id.pickupText_View);
        dropOff = (TextView) getView().findViewById(R.id.dropOffText_View);
        approxTime = (TextView) getView().findViewById(R.id.rideTimeText_View);
        approxSavings = (TextView) getView().findViewById(R.id.savingsText_View);
        String username = args.getString("username");
        TripDatabase tripDB = null;
        try {
            tripDB = new TripDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TripInformation ride = tripDB.CheckOffersToMe(username);
        //setting each field to display certain info
        populateFields(ride);

        TripDatabase finalTripDB = tripDB;
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalTripDB.ChangeOfferAcceptanceState(username, true);
                TripInformation ride = finalTripDB.CheckOffersToMe(username);
                populateFields(ride);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalTripDB.ChangeOfferAcceptanceState(username, false);
                TripInformation ride = finalTripDB.CheckOffersToMe(username);
            }
        });
    }

    private void populateFields(TripInformation ride){
        while (ride == null){};
        pickup.setText(ride.pickupLocation);
        dropOff.setText(ride.destination);
        approxTime.setText(ride.rideTime.toString());
        approxSavings.setText(Float.toString(ride.rideFare));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_presentofferpage, container, false);}
    
}