package com.example.cabtap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class InTransitPage extends AppCompatActivity {

    private GoogleMap mMap;

    private Button gameButton;
    private TextView mOutputTextView;
    private String destination;
    private Geocoder geocoder;
    private MapAssist mapAssist;
    private Marker userMarker;
    private Marker destinationMarker;
    private Polyline route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intransit);


        // Initialize views
        gameButton = findViewById(R.id.gameButton);
        mOutputTextView = findViewById(R.id.outputTextView);

        //Initialize MapAssist
        mapAssist = new MapAssist(this);

        // Setup gameButton
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InTransitPage.this, GamePage.class);
                startActivity(intent);
            }
        });

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

                //Request permission for location and get initial position
                LatLng initialLocation = mapAssist.getLocation();
                if (initialLocation == null) {
                    initialLocation = new LatLng(43.2609, -79.9192);
                }
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(initialLocation)
                        .title("You!");
                userMarker = mMap.addMarker(markerOptions);
//                //destinationMarker = mMap.addMarker(mapAssist.getMarker(destination, "Destination!"));
//                //PolylineOptions polylineOptions = mapAssist.getRoute(mapAssist.llString(initialLocation), destination);
//
//                //route = mMap.addPolyline(polylineOptions);
//                // Receive location updates and update the marker position accordingly
//                FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(InTransitPage.this);
//                try {
//                    LocationRequest locationRequest = LocationRequest.create()
//                            .setInterval(5000) // Update interval in milliseconds
//                            .setFastestInterval(2500) // Fastest update interval in milliseconds
//                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                    locationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
//                        @Override
//                        public void onLocationResult(LocationResult locationResult) {
//                            if (locationResult != null) {
//                                Location location = locationResult.getLastLocation();
//                                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                                // Update the marker position
//                                userMarker.setPosition(userLatLng);
//                            }
//                        }
//                    }, null);
//                } catch (SecurityException e) {
//                    e.printStackTrace();
//                }
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
