package com.example.cabtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabtap.databinding.ActivityLoggedinMainBinding;

import java.util.ArrayList;

public class UnpauseProfileActivity extends AppCompatActivity {

    TextView label;
    Button unpause;
    SessionDetails sessionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpause_profile);

        label = (TextView) findViewById(R.id.pageTitle);
        unpause = (Button) findViewById(R.id.btn_unpause);

        String username = getIntent().getStringExtra("username");

        label.setText("Your account (username: "+username+") is paused. Would you like to unpause it?");
        
        ArrayList<String> profile;

        try{
            ProfileDatabase profileDB= new ProfileDatabase();
            profile = profileDB.RetrieveProfile(username);
        }
        catch (Exception E){
            try {
                throw E;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        sessionDetails = new SessionDetails(profile);

        unpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Toast.makeText(getApplicationContext(), "Your profile is now unpaused, enjoy!", Toast.LENGTH_LONG).show();
                    ProfileDatabase db = new ProfileDatabase();
                    db.SignalUnpause(sessionDetails.getSessionUsername());
                    Intent intent = new Intent(getApplicationContext(), LoggedInMainActivity.class);
                    intent.putExtra("legalName", sessionDetails.getSessionLegalName());
                    intent.putExtra("username", sessionDetails.getSessionUsername());
                    intent.putExtra("phoneNumber", sessionDetails.getSessionPhoneNumber());
                    intent.putExtra("tripsCompleted", sessionDetails.getSessionTripsCompleted());
                    intent.putExtra("rating", sessionDetails.getSessionRating());
                    intent.putExtra("rewardsBal", sessionDetails.getSessionRewardsBalance());
                    startActivity(intent);
                }
                catch (Exception e) {}
            }
        });
    }
}