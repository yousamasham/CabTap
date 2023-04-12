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
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;

public class InTransitPage extends AppCompatActivity {

    private GoogleMap mMap;
    private Button gameButton;
    private String origin;
    private String destination;
    private String pickup;
    private String dropoff;
    private Geocoder geocoder;
    protected MapAssist mapAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intransit);

        // Initialize views
        gameButton = findViewById(R.id.gameButton);

        // Grab Intent
        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");
        destination = intent.getStringExtra("destination");
        pickup = intent.getStringExtra("pickup");
        dropoff = intent.getStringExtra("dropoff");

        // Initialize Geocoder and mapAssist
        geocoder = new Geocoder(this);
        mapAssist = new MapAssist(this);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // Add any additional map setup code here
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocation(origin), 15));

                // Plot the markers
                mMap.addMarker(mapAssist.getMarker(origin, "You"));
                mMap.addMarker(mapAssist.getMarker(pickup, "Pickup"));
                mMap.addMarker(mapAssist.getMarker(dropoff, "Drop Off"));
                mMap.addMarker(mapAssist.getMarker(destination, "Destination"));

                // Plot the routes
                mMap.addPolyline(mapAssist.getRoute(origin, pickup));
                mMap.addPolyline(mapAssist.getRoute(pickup, dropoff));
                mMap.addPolyline(mapAssist.getRoute(dropoff, destination));
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
