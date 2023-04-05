package com.example.cabtap;
import java.time.*;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class RequestRideSharePage extends Fragment{
    EditText dropOff;
    EditText pickup;
    EditText date;
    EditText time;
    EditText passengerNum;
    Button submit;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        dropOff = (EditText) getView().findViewById(R.id.et_dropOff);
        pickUp = (EditText) getView().findViewById(R.id.et_pickup);
        date = (EditText) getView().findViewById(R.id.et_date);
        time = (EditText) getView().findViewById(R.id.et_time);
        passengerNum = (EditText) getView().findViewById(R.id.et_passengerNum);
        submit = (Button) getView().findViewById(R.id.btn_submit);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CONNECT WITH BACKEND TO VALIDATE
            }
        });

    }
    
    // vaidating should be done by rideshare controller

    // private void validateTripInfo(){
    //     //calculate the route cost
    //     //call display open rides 
    // }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_sharepage, containter, false);
    }

}