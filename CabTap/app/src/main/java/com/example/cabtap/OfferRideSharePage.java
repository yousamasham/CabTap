package com.example.cabtap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.time.*;
import java.util.ArrayList;
import java.time.LocalDate;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.checkerframework.checker.units.qual.A;

public class OfferRideSharePage extends Fragment {
    EditText dropOff;
    // private roadmap map;
    EditText availableSeats;
    Button submit;
    private LocalTime approxTime;
    private int approxSaving;
    DispatcherController controller = new DispatcherController();



    public static OfferRideSharePage newInstance(SessionDetails profile) {
        OfferRideSharePage fragment = new OfferRideSharePage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        String username = args.getString("username");
        dropOff = getView().findViewById(R.id.et_dropOff);
        availableSeats = getView().findViewById(R.id.et_availableSeats);
        submit = getView().findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentLocation = "McMaster University, Hamilton";
                // get current location of rider
                if(dropOff.getText().toString() == currentLocation){//add out of range checks to if
                    try {
                        throw new ValidTripException("Invalid pickup and/or dropoff locations entered.");
                    } catch (ValidTripException e) {
                        throw new RuntimeException(e);
                    }
                }

                // calculate the approx route to show to requesters

                TripInformation trip = new TripInformation(currentLocation, dropOff.getText().toString(), username, Integer.parseInt(availableSeats.getText().toString()));
                trip.setPickupLocation(currentLocation);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    trip.setRideTime(LocalTime.now());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    trip.setDate(LocalDate.now());
                }

                controller.setRideOffer(trip);
                SessionDetails sessionDetails;
                try {
                    ProfileDatabase db = new ProfileDatabase();
                    ArrayList<String> profile = db.RetrieveProfile(username);
                    sessionDetails = new SessionDetails(profile);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Fragment fragment = PresentOfferPage.newInstance(sessionDetails);
                replaceFragment(fragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_offersharepage, container, false);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


    public class ValidTripException extends Exception {
        public ValidTripException(String message) {
            super(message);
        }
    }
}