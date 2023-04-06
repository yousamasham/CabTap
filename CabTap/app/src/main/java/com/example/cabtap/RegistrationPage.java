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
                try{
                    ProfileDatabase profileDB = new ProfileDatabase();
                    profileDB.InsertProfile(legalName.getText().toString(), userName.getText().toString(),
                            password.getText().toString(), phoneNumber.getText().toString());
                    System.out.println(profileDB.RetrieveProfile(userName.getText().toString()));
                    ArrayList<String> userDetails = profileDB.RetrieveProfile(userName.getText().toString());
                    SessionDetails session = new SessionDetails(userDetails);
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("legalName", session.getSessionLegalName());
                    intent.putExtra("username", session.getSessionUsername());
                    intent.putExtra("phoneNumber", session.getSessionPhoneNumber());
                    startActivity(intent);
                }
                catch(Exception E){
                    try {
                        throw E;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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