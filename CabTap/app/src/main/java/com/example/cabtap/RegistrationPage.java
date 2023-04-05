package com.example.cabtap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class RegistrationPage extends Fragment {

    EditText legalName;
    EditText userName;
    EditText phoneNumber;
    EditText password;
    EditText rePassword;
    Button register;

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

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
                //TODO CONNECT WITH BACKEND TO VALIDATE
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