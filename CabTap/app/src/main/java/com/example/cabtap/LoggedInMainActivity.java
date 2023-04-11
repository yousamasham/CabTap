package com.example.cabtap;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.cabtap.databinding.ActivityLoggedinMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoggedInMainActivity extends AppCompatActivity {

    SessionDetails sessionDetails;
    ActivityLoggedinMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoggedinMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new RequestRideSharePage());

        String username = getIntent().getStringExtra("username");
        String legalName = getIntent().getStringExtra("legalName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

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


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ride:
                    replaceFragment(new RequestRideSharePage());
                    break;
                case R.id.offer:
                    replaceFragment(new OfferRideSharePage());
                    break;
                case R.id.profile:
                        Fragment fragment = ProfilePage.newInstance(sessionDetails);
                        replaceFragment(fragment);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}