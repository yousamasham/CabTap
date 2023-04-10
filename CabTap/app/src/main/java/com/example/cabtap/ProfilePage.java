package com.example.cabtap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfilePage extends Fragment {
    TextView legalNameTV;
    TextView userNameTV;
    TextView phoneNumberTV;
    TextView tripsCompletedTV;
    TextView avgRatingTV;
    TextView rewardsBalTV;
    Button changeLegalName;
    Button changePhoneNumber;
    Button changePassword;
    Button pauseProfile;
    Button deleteProfile;
    Button logout;


    public static ProfilePage newInstance(SessionDetails profile) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        args.putString("legalName", profile.getSessionLegalName());
        args.putString("phoneNumber", profile.getSessionPhoneNumber());
        args.putString("rating", profile.getSessionRating());
        args.putString("tripsCompleted", profile.getSessionTripsCompleted());
        args.putString("rewardsBal", profile.getSessionRewardsBalance());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changePassword = (Button) getView().findViewById(R.id.btn_ModifyPassword);
        changeLegalName = (Button) getView().findViewById(R.id.btn_ModifyLegalName);
        changePhoneNumber = (Button) getView().findViewById(R.id.btn_ModifyPhoneNumber);
        pauseProfile = (Button) getView().findViewById(R.id.btn_pauseProfile);
        deleteProfile = (Button) getView().findViewById(R.id.btn_deleteProfile);
        logout = (Button) getView().findViewById(R.id.btn_logout);
        Bundle args = getArguments();
        String username = args.getString("username");
        String legalName = args.getString("legalName");
        String phoneNumber = args.getString("phoneNumber");
        String rating = args.getString("rating");
        String tripsCompleted = args.getString("tripsCompleted");
        String rewardsBal = args.getString("rewardsBal");

        legalNameTV = (TextView)  getView().findViewById(R.id.legalname);
        legalNameTV.setText("Hello, "+ legalName+"!");
        userNameTV = (TextView)  getView().findViewById(R.id.userName);
        userNameTV.setText("Username: "+username);
        phoneNumberTV = (TextView)  getView().findViewById(R.id.phoneNumber);
        phoneNumberTV.setText("Phone Number: "+phoneNumber);
        avgRatingTV = (TextView)  getView().findViewById(R.id.rating);
        avgRatingTV.setText("Average rating: "+rating);
        tripsCompletedTV = (TextView)  getView().findViewById(R.id.tripCompleted);
        tripsCompletedTV.setText("Total trips completed: "+tripsCompleted);
        rewardsBalTV = (TextView)  getView().findViewById(R.id.rewardBal);
        rewardsBalTV.setText("Available rewards balance: "+rewardsBal);

        ProfileDatabase db;
        ArrayList<String> profile;
        try {
            db = new ProfileDatabase();
            profile = db.RetrieveProfile(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pauseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ModifyProfilePage.newInstance(false, new SessionDetails(profile));
                replaceFragment(fragment);
            }
        });

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ModifyProfilePage.newInstance(true, new SessionDetails(profile));
                replaceFragment(fragment);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.SignalLogout(username);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Fragment fragment = ModifyProfilePage.newInstance(ProfileField.PASSWORD, new SessionDetails(profile));
                    replaceFragment(fragment);
                }
                catch (Exception E){
                    throw E;
                }
            }
        });

        changeLegalName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Fragment fragment = ModifyProfilePage.newInstance(ProfileField.LEGALNAME, new SessionDetails(profile));
                    replaceFragment(fragment);
                }
                catch (Exception E){
                    throw E;
                }
            }
        });

        changePhoneNumber.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Fragment fragment = ModifyProfilePage.newInstance(ProfileField.PHONENUMBER, new SessionDetails(profile));
                    replaceFragment(fragment);
                }
                catch (Exception E){
                    throw E;
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}