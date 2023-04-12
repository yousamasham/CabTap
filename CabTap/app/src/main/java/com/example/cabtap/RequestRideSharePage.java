package com.example.cabtap;
import static java.util.Objects.isNull;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class RequestRideSharePage extends Fragment {
    EditText dropOff;
    EditText pickUp;
    EditText date;
    EditText time;
    EditText passengerNum;
    Button submit;
    DispatcherController controller = new DispatcherController();

    private void validateTripInfo() {
        try {
            if (isNull(dropOff) || isNull(pickUp) || isNull(date) || isNull(time) || isNull(passengerNum)) {
                throw new ValidTripException("Please fill all fields.");
            }
            if (dropOff == pickUp) {
                throw new ValidTripException("Invalid pickup and/or dropoff locations entered.");
            }
        } catch (Exception E) {

        }
    }

    public static RequestRideSharePage newInstance(SessionDetails profile) {
        RequestRideSharePage fragment = new RequestRideSharePage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        String username = args.getString("username");
        dropOff = (EditText) getView().findViewById(R.id.et_dropOff);
        pickUp = (EditText) getView().findViewById(R.id.et_pickup);
        date = (EditText) getView().findViewById(R.id.et_date);
        time = (EditText) getView().findViewById(R.id.et_time);
        passengerNum = (EditText) getView().findViewById(R.id.et_passengerNum);
        submit = (Button) getView().findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateTripInfo();
                TripInformation trip = new TripInformation(pickUp.getText().toString(), dropOff.getText().toString(), username, Integer.valueOf(passengerNum.getText().toString()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    trip.setRideTime(LocalTime.parse(time.getText().toString()));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    trip.setDate(LocalDate.parse(date.getText().toString()));
                }
                trip.setUsername(username);
                try {
                    controller.recieveRideRequest(trip);
                }
                catch(Exception E){
                }
                Intent intent = new Intent(getActivity(), DisplayOpenRidesPage.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requestsharepage, container, false);
    }


    public class ValidTripException extends Exception {
        public ValidTripException(String message) {
            super(message);
        }
    }
}