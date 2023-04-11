package com.example.cabtap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TripDatabase {
    private static FirebaseFirestore firestore;

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
            firestore.collection("requests").document(trip.getUsername()).set(newTrip);
        }
        catch(Exception E){
            throw E;
        }
        return true;
    }

    protected static void RemoveRequest(String username){
        try{
            firestore.collection("requests").document(username).delete();
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static TripInformation GetRequest(String username){
        TripInformation request = null;
        try{
            Task query =firestore.collection("requests").whereEqualTo("username", username).get();
            while (!query.isComplete());
            QuerySnapshot queryRes = (QuerySnapshot) query.getResult();

            for (QueryDocumentSnapshot docRes : queryRes){
                request = docRes.toObject(TripInformation.class);
            }
        }
        catch (Exception E){
            throw E;
        }
        return request;
    }

    protected static boolean InsertTrip(TripInformation trip){
        Map<String, Object> newTrip = new HashMap<>();
        newTrip.put("destination", trip.getDestination());
        newTrip.put("totalfare", trip.getRideFare());
        newTrip.put("ridetime", trip.getRideTime());
        newTrip.put("username", trip.getUsername());
        newTrip.put("usersencountered", trip.getUsersEncountered());

        try{
            firestore.collection("trips").document(trip.getUsername()).set(newTrip);
        }
        catch (Exception E){
            throw E;
        }

        return true;
    }

    protected static TripInformation FindTrips(String username){
        TripInformation trip = null;
        try{
            Task queryTask = firestore.collection("trips").whereEqualTo("username", username).get();

            while (!queryTask.isComplete());

            QuerySnapshot queryRes = (QuerySnapshot) queryTask.getResult();

            for (QueryDocumentSnapshot docRes : queryRes){
                trip = docRes.toObject(TripInformation.class);
            }
        }
        catch (Exception E){
            throw E;
        }

        return trip;
    }

    protected static void RemoveTrips(TripInformation trip){
        try{
            firestore.collection("trips").document(trip.getUsername()).delete();
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static ArrayList<TripInformation> AvailableTrips(){
        ArrayList<TripInformation> result = new ArrayList<TripInformation>();
        try{
            Task query = firestore.collection("trips").document().get();
            while (!query.isComplete());
            QuerySnapshot queryRes = (QuerySnapshot) query.getResult();
            for(QueryDocumentSnapshot docRes : queryRes){
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
        newOffer.put("acceptence",null);
        try{
            firestore.collection("offers").document(checkingUsername).set(newOffer);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static String CheckOffer(TripInformation request){
        Task query = firestore.collection("offers").whereEqualTo("request", request).get();
        while (!query.isComplete());
        DocumentSnapshot queryRes = (DocumentSnapshot) query.getResult();
        String acceptanceState = (String) queryRes.getData().get("acceptance");
        return acceptanceState;
    }

    protected static TripInformation CheckOffersToMe(String username){
        Task query = firestore.collection("offers").whereEqualTo("username", username).get();
        while (!query.isComplete());
        DocumentSnapshot queryRes = (DocumentSnapshot) query.getResult();
        TripInformation trip = queryRes.toObject(TripInformation.class);
        return trip;
    }

    protected static void RemoveOffer(String username){
        firestore.collection("offers").document(username).delete();
    }

    protected static void ChangeOfferAcceptanceState(String username, boolean state){
        try{
        Task change = firestore.collection("offers").document(username).update("acceptance",state);
        while (!change.isComplete());
        }
        catch(Exception E){
            throw E;
        }
    }
}
