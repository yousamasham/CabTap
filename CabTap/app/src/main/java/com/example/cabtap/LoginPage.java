package com.example.cabtap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class LoginPage extends Fragment {

    EditText username;
    EditText password;
    Button login;


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        username = (EditText) getView().findViewById(R.id.et_username);
        password = (EditText) getView().findViewById(R.id.et_password);
        login = (Button) getView().findViewById(R.id.btn_login);

        //sendLoginCredentials
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
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