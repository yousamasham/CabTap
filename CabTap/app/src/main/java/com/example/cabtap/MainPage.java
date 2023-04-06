package com.example.cabtap;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MainPage extends Fragment {
    Button logout;
    Button editProfile;
    Button requestARide;
    Button offerARide;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        logout = (Button) getView().findViewById(R.id.btn_logout);
        editProfile = (Button) getView().findViewById(R.id.btn_editProfile);
        requestARide = (Button) getView().findViewById(R.id.btn_requestARide);
        offerARide = (Button) getView().findViewById(R.id.btn_offerARide);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditProfilePage();
            }
        });

        requestARide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRequestRidePage();
            }
        });

        offerARide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOfferRidePage();
            }
        });
    }

    private void goToLoginPage(){
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
    }

    private void goToEditProfilePage(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goToRequestRidePage(){
        Intent intent = new Intent(getActivity(), RequestRideSharePage.class);
        startActivity(intent);
    }

    private void goToOfferRidePage(){
        Intent intent = new Intent(getActivity(), OfferRideSharePage.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.fragment_mainpage, container, false);
    }
}
