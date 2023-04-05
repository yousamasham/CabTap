package com.example.cabtap;
import java.time.*;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class RequestRideSharePage extends Fragment{
    EditText arrivalTime;
    EditText dropOff;
    EditText pickip;
    EditText date;
    EditText time;
    EditText passengerNumber;
    //private RoadMap map;
    private int cost;
    
    private void validateTripInfo(){
        //calculate the route cost
        //call display open rides 
    }

    private ArrayList<Object> displayOpenRides(){
        ArrayList<Object> availableRides = new ArrayList<>();
        // use offerridesharepage and google maps to find 
        // closest available rides and display them.
        return (availableRides);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_sharepage, containter, false);
    }

}