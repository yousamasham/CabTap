package com.example.cabtap;

import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    SessionDetails sessionDetails;
    TextView legalNameTV;
    TextView userNameTV;
    TextView phoneNumberTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String username = getIntent().getStringExtra("username");
        String legalName = getIntent().getStringExtra("legalName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        ArrayList<String> session = new ArrayList<String>(){
            {
                add(username);
                add(legalName);
                add(phoneNumber);
            }
        };
        this.sessionDetails = new SessionDetails(session);

        legalNameTV = (TextView) findViewById(R.id.legalname);
        legalNameTV.setText("Hello, "+sessionDetails.getSessionLegalName()+"!");

        userNameTV = (TextView) findViewById(R.id.userName);
        userNameTV.setText(sessionDetails.getSessionUsername());

        phoneNumberTV = (TextView) findViewById(R.id.phoneNumber);
        phoneNumberTV.setText(sessionDetails.getSessionPhoneNumber());
    }
}
