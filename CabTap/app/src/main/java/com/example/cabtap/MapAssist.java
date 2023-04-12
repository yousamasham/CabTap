package com.example.cabtap;

import android.content.Context;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;

import okhttp3.Route;

public class MapAssist {

    private Geocoder geocoder;
    private Context context;
    private GeoApiContext geoContext;
    private DirectionsApiRequest directions;

    public MapAssist(Context context) {
        this.context = context;
        geocoder = new Geocoder(context);
    }

    // See above methods for short descriptions, some are commented, some aren't.
    // I went through varying stages of laziness and eagerness while writing this.
    // May all the forces acting on this planet let it work.

    // Get users location
    protected LatLng getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        try {
            Location location = fusedLocationProviderClient.getLastLocation().getResult();
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                return new LatLng(latitude, longitude);
            } else {
                // Location not found
                return null;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            // Error getting location
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            // Error getting location
            return null;
        }
    }

    // Get address location
    protected LatLng getLocation(String address) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            Log.e("Geocoder", "Failed to get location from address: " + e.getMessage());
        }
        return new LatLng(43.2609, 79.9192);
    }

    // Get distance from current location to an address
    protected double getDistance(String address) {
        double distance = 0;
        try {
            LatLng origin = getLocation();
            LatLng destination = getLocation(address);

            geoContext = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                    .build();

            DirectionsResult result = DirectionsApi.newRequest(geoContext)
                    .origin(origin.latitude + "," + origin.longitude)
                    .destination(destination.latitude + "," + destination.longitude)
                    .mode(TravelMode.DRIVING)
                    .await();

            if (result != null && result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];
                DirectionsLeg leg = route.legs[0];
                distance = leg.distance.inMeters / 1000;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance;
    }

    // Get distance between two addresses
    protected double getDistance(String start, String end) {
        double distance = 0;
        try {
            LatLng origin = getLocation(start);
            LatLng destination = getLocation(end);

            geoContext = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                    .build();

            DirectionsResult result = DirectionsApi.newRequest(geoContext)
                    .origin(origin.latitude + "," + origin.longitude)
                    .destination(destination.latitude + "," + destination.longitude)
                    .mode(TravelMode.DRIVING)
                    .await();

            if (result != null && result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];
                DirectionsLeg leg = route.legs[0];
                distance = leg.distance.inMeters / 1000;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance;
    }

    // Get time to drive from user location to address in form "Xm Ys"
    //  Where X is minutes of drive and Y is seconds
    protected String getTime(String address) {
        // get current location
        LatLng origin = this.getLocation();

        // setup GeoApiContext
        geoContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                .build();

        // setup DirectionsApiRequest
        directions = DirectionsApi.newRequest(geoContext)
                .origin(origin.latitude + "," + origin.longitude)
                .destination(address)
                .mode(TravelMode.DRIVING);

        try {
            // Call the API and get the response
            DirectionsResult result = directions.await();

            // Get the first route from the response
            DirectionsRoute route = result.routes[0];

            // Get the total duration of the route
            double duration = route.legs[0].duration.inSeconds;

            // Convert the duration to minutes and seconds
            double minutes = Math.floor(duration / 60);
            double seconds = duration % 60;

            // Return the driving time
            return "" + minutes + "m " + seconds + "s";
        } catch (Exception e) {
            Log.e("MapAssist", "Error getting driving time", e);
        }
        return "ERROR";
    }

    // Get time to drive between two addresses, same format as above
    protected String getTime(String origin, String address) {
        // setup GeoApiContext
        geoContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                .build();

        // setup DirectionsApiRequest
        directions = DirectionsApi.newRequest(geoContext)
                .origin(origin)
                .destination(address)
                .mode(TravelMode.DRIVING);

        try {
            // Call the API and get the response
            DirectionsResult result = directions.await();

            // Get the first route from the response
            DirectionsRoute route = result.routes[0];

            // Get the total duration of the route
            double duration = route.legs[0].duration.inSeconds;

            // Convert the duration to minutes and seconds
            double minutes = Math.ceil(duration / 60);
            double seconds = duration % 60;

            // Return the driving time
            return "" + minutes + "m " + seconds + "s";
        } catch (Exception e) {
            Log.e("MapAssist", "Error getting driving time", e);
        }
        return "ERROR";
    }

    // Get the marker options for the users location
    protected MarkerOptions getMarker() {
        // get current location
        LatLng current = this.getLocation();

        // Make a marker for location, with the tag "You".
        return new MarkerOptions().position(current).title("You");
    }

    // Get the marker options for a location
    protected MarkerOptions getMarker(String address, String tag) {
        // get location
        LatLng location = this.getLocation(address);

        // Make a marker for location with the entered tag
        return new MarkerOptions().position(location).title(tag);
    }

    // Get the route from the user to an address
    protected PolylineOptions getRoute(String address) {
        LatLng origin = this.getLocation();
        LatLng destination = this.getLocation(address);

        geoContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                .build();

        directions = DirectionsApi.newRequest(geoContext)
                .origin(origin.latitude + "," + origin.longitude)
                .destination(destination.latitude + "," + destination.longitude);

        try {
            DirectionsResult result = directions.await();

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.RED);
            polylineOptions.width(5);

            List<LatLng> points = new ArrayList<>();
            for (DirectionsRoute route : result.routes) {
                for (DirectionsLeg leg : route.legs) {
                    for (DirectionsStep step : leg.steps) {
                        points.addAll(PolyUtil.decode(step.polyline.getEncodedPath()));
                    }
                }
            }
            polylineOptions.addAll(points);

            return polylineOptions;
        } catch (Exception e) {
            Log.e("MapAssist", "Error getting driving time", e);
        }
        return null;
    }

    // Get the route between two addresses
    protected PolylineOptions getRoute(String start, String end) {
        LatLng origin = this.getLocation(start);
        LatLng destination = this.getLocation(end);

        geoContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCB4GaPkTg9cRS3g6w41LyaF_Nm8IVmWZA")
                .build();

        directions = DirectionsApi.newRequest(geoContext)
                .origin(origin.latitude + "," + origin.longitude)
                .destination(destination.latitude + "," + destination.longitude);

        try {
            DirectionsResult result = directions.await();

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.RED);
            polylineOptions.width(5);

            List<LatLng> points = new ArrayList<>();
            for (DirectionsRoute route : result.routes) {
                for (DirectionsLeg leg : route.legs) {
                    for (DirectionsStep step : leg.steps) {
                        points.addAll(PolyUtil.decode(step.polyline.getEncodedPath()));
                    }
                }
            }
            polylineOptions.addAll(points);

            return polylineOptions;
        } catch (Exception e) {
            Log.e("MapAssist", "Error getting driving time", e);
        }
        return null;
    }
}
