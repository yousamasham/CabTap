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

public class ProfileActivity extends AppCompatActivity {

    SessionDetails sessionDetails;
    TextView legalName;
    TextView userName;
    TextView phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.sessionDetails = (SessionDetails) getIntent().getSerializableExtra("session", SessionDetails.class);
        }

        legalName = (TextView) findViewById(R.id.legalname);
        legalName.setText("Hello, "+sessionDetails.getSessionLegalName()+"!");

        userName = (TextView) findViewById(R.id.userName);
        userName.setText(sessionDetails.getSessionUsername());

        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(sessionDetails.getSessionPhoneNumber());
    }
}
