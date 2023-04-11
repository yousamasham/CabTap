package com.example.cabtap;
import static java.util.Objects.isNull;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class RequestRideSharePage extends Fragment{
    EditText dropOff;
    EditText pickUp;
    EditText date;
    EditText time;
    EditText passengerNum;
    Button submit;

    private void validateTripInfo(){
        if(isNull(dropOff) || isNull(pickUp) || isNull(date) || isNull(time) || isNull(passengerNum)){
            //display error message to fill all fields
        }
        if(dropOff == pickUp){
            //add out of range checks
            //display message that ride is invalid
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        dropOff = (EditText) getView().findViewById(R.id.et_dropOff);
        pickUp = (EditText) getView().findViewById(R.id.et_pickup);
        date = (EditText) getView().findViewById(R.id.et_date);
        time = (EditText) getView().findViewById(R.id.et_time);
        passengerNum = (EditText) getView().findViewById(R.id.et_passengerNum);
        submit = (Button) getView().findViewById(R.id.btn_accept);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateTripInfo();
                sendInfo();
                //send info to Dispatch controller
                //bring user to new page
            }
        });
    }

    private void sendInfo(){
        Intent intent = new Intent(getActivity(), DisplayOpenRidesPage.class);
        startActivity(intent);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState) {
        return null;// (ViewGroup) inflater.inflate(R.layout.fragment_submit, containter, false);
    }

}