package com.example.cabtap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class RegistrationPage extends Fragment {

    EditText legalName;
    EditText userName;
    EditText phoneNumber;
    EditText password;
    EditText rePassword;
    Button register;
    RegistrationController controller;

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        controller = new RegistrationController();

        legalName = (EditText) getView().findViewById(R.id.et_name);
        userName = (EditText) getView().findViewById(R.id.et_username);
        phoneNumber = (EditText) getView().findViewById(R.id.et_number);
        password = (EditText) getView().findViewById(R.id.et_password);
        rePassword = (EditText) getView().findViewById(R.id.et_repassword);
        register = (Button) getView().findViewById(R.id.btn_register);

        //sendRegistration
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VALIDATE REGISTRATION
                try{
                    SessionDetails session = new SessionDetails(controller.validateCredentials(legalName, userName,  phoneNumber, password,  rePassword));
                    Intent intent = new Intent(getActivity(), LoggedInMainActivity.class);
                    intent.putExtra("legalName", session.getSessionLegalName());
                    intent.putExtra("username", session.getSessionUsername());
                    intent.putExtra("phoneNumber", session.getSessionPhoneNumber());
                    intent.putExtra("tripsCompleted", session.getSessionTripsCompleted());
                    intent.putExtra("rating", session.getSessionRating());
                    intent.putExtra("rewardsBal", session.getSessionRewardsBalance());
                    startActivity(intent);
                }
                catch(Exception E){

                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(
                R.layout.fragment_register, container, false);
    }

}