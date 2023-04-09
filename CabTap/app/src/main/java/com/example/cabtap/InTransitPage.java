package com.example.cabtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class InTransitPage extends AppCompatActivity {

    private GoogleMap mMap;
    private Button mPlotRouteButton;
    private TextView mOutputTextView;
    private String destination;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intransit);

        // Initialize views
        mPlotRouteButton = findViewById(R.id.plotRouteButton);
        mOutputTextView = findViewById(R.id.outputTextView);

        // Grab Intent
        Intent intent = getIntent();
        destination = intent.getStringExtra("destination");

        // Initialize Geocoder
        geocoder = new Geocoder(this);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // Add any additional map setup code here
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocation(destination), 15));
            }
        });
    }

    private LatLng getLocation(String address) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                LatLng location = new LatLng(latitude, longitude);
                return location;
            }
        } catch (IOException e) {
            Log.e("Geocoder", "Failed to get location from address: " + e.getMessage());
        }
        return new LatLng(43.2609, 79.9192);
    }
}
