package com.example.cabtap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class LoginPage extends Fragment {

    EditText userName;
    EditText password;
    Button login;
    LoginController controller;


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        userName = (EditText) getView().findViewById(R.id.et_username);
        password = (EditText) getView().findViewById(R.id.et_password);
        login = (Button) getView().findViewById(R.id.btn_login);
        controller = new LoginController();

        //sendLoginCredentials
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SessionDetails session = new SessionDetails(controller.login(userName,  password));
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
                R.layout.fragment_login, container, false);
    }

}