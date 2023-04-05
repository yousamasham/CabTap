package com.example.cabtap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class ProfilePage extends Fragment {

    SessionDetails sessionDetails;
    TextView legalName;
    TextView userName;

    TextView phoneNumber;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        legalName = (TextView) getView().findViewById(R.id.legalname);
        legalName.setText("Hello, "+sessionDetails.getSessionLegalName()+"!");

        userName = (TextView) getView().findViewById(R.id.userName);
        userName.setText(sessionDetails.getSessionUsername());

        phoneNumber = (TextView) getView().findViewById(R.id.phoneNumber);
        phoneNumber.setText(sessionDetails.getSessionPhoneNumber());
    }
}
