package com.example.cabtap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TripDatabase {
    private static FirebaseFirestore firestore;
    private static int rideCounter;

    TripDatabase() throws Exception{
        try{
            firestore = FirebaseFirestore.getInstance();
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static boolean InsertTrip(TripInformation trip){
        Map<String, Object> newTrip = new HashMap<>();
        newTrip.put("destination", trip.getDestination());
        newTrip.put("totalfare", trip.getRideFare());
        newTrip.put("ridetime", trip.getRideTime());
        newTrip.put("usersencountered", trip.getUsersEncountered());

        try{
            firestore.collection("trips").document().set(newTrip);
        }
        catch (Exception E){
            throw E;
        }

        rideCounter++;

        return true;
    }

    protected static ArrayList<TripInformation> FindTrips(String destination){
        ArrayList<TripInformation> result = new ArrayList<TripInformation>();

        try{
            Task queryTask = firestore.collection("trips").whereEqualTo("destination", destination).get();

            while (!queryTask.isComplete());

            QuerySnapshot queryRes = (QuerySnapshot) queryTask.getResult();

            for (QueryDocumentSnapshot docRes : queryRes){
                TripInformation trip = docRes.toObject(TripInformation.class);
                result.add(trip);
            }
        }
        catch (Exception E){
            throw E;
        }

        return result;
    }
}
