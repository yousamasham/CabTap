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

    protected static boolean InsertRequest(TripInformation trip){
        Map<String, Object> newTrip = new HashMap<>();
        newTrip.put("destination", trip.getDestination());
        newTrip.put("pickupLocation", trip.getPickupLocation());
        newTrip.put("username", trip.getUsersEncountered());
        try{
            firestore.collection("requests").document.set(newTrip);
        }
        catch(Exception E){
            throw E;
        }
        return true;
    }

    protected static void RemoveRequest(String name){
        try{
            firestore.collection("requests").whereEqualTo("username", username).remove();
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static TripInformation GetRequest(String name){
        try{
            firestore.collection("requests").whereEqualTo("username", username).get();
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
        newTrip.put("username", trip.getUsername());
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

    protected static TripInformation FindTrips(String username){
        try{
            Task queryTask = firestore.collection("trips").whereEqualTo("username", username).get();

            while (!queryTask.isComplete());

            QuerySnapshot queryRes = (QuerySnapshot) queryTask.getResult();

            for (QueryDocumentSnapshot docRes : queryRes){
                TripInformation trip = docRes.toObject(TripInformation.class);
            }
        }
        catch (Exception E){
            throw E;
        }

        return trip;
    }

    protected static void RemoveTrips(TripInformation trip){
        try{
            firestore.collection("trips").document().remove(trip);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static ArrayList<TripInformation> AvailableTrips(){
        ArrayList<TripInformation> result = new ArrayList<TripInformation>();
        try{
            task query = firestore.collection("trips").document().get();
            for(QueryDocumentSnapshot docRes : query){ 
                TripInformation trip = docRes.toObject(TripInformation.class);
                result.add(trip);
            }
        }
        catch (Exception E){
            throw E;
        }
        return result;
    }
    protected static void PushOffer(String checkingUsername, TripInformation request){
        Map<String, Object> newOffer = new HashMap<>();
        newOffer.put("username", checkingUsername);
        newOffer.put("request", request);
        try{
            firestore.collection("offers").document().set(newOffer);
        }
        catch (Exception E){
            throw E;
        }
    }

    prot
}
