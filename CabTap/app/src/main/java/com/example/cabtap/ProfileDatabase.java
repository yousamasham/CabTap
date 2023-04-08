package com.example.cabtap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileDatabase {
    private static EncryptionController encryptor;

    static {
        try {
            encryptor = new EncryptionController();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static FirebaseFirestore firestore;

    ProfileDatabase() throws Exception {
        //init encryptor
        try{
            firestore = FirebaseFirestore.getInstance();
        }
        catch(Exception E){
            throw E;
        }
    }

    protected static boolean InsertProfile(String ptLegalName, String ptUsername, String ptPassword, String ptPhoneNumber) throws Exception{

        ArrayList<String> ptNewProfile = new ArrayList<String>(){
            {
                add(ptLegalName);
                add(ptUsername);
                add(ptPassword);
                add(ptPhoneNumber);
            }
        };

        ArrayList<String> encProfileList = encryptor.getEncryption(ptNewProfile);

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("legalname", encProfileList.get(ProfileField.LEGALNAME.ordinal()));
        newUser.put("username", encProfileList.get(ProfileField.USERNAME.ordinal()));
        newUser.put("password", encProfileList.get(ProfileField.PASSWORD.ordinal()));
        newUser.put("phonenumber", encProfileList.get(ProfileField.PHONENUMBER.ordinal()));
        newUser.put("rewardsbal", 0);
        newUser.put("tripscompleted", 0);
        newUser.put("rating", 0);
        try{
            firestore.collection("profiles").document((String) newUser.get("username")).set(newUser);
        }
        catch (Exception E){
            throw E;
        }

        return true;
    }

    protected static ArrayList<String> RetrieveProfile(String username) throws Exception{

        Map<String, Object> map;
        try{
            Task query = firestore.collection("profiles").document(username).get();

            while (!query.isComplete());

            DocumentSnapshot mapRes = (DocumentSnapshot) query.getResult();

            map = mapRes.getData();
        }
        catch (Exception E){
            throw E;
        }

        ArrayList<String> resEnc = new ArrayList<String>(){
            {
                add(map.get("legalname").toString());
                add(map.get("username").toString());
                add(map.get("password").toString());
                add(map.get("phonenumber").toString());
            }
        };

        ArrayList<String> decRes = encryptor.getDecryption(resEnc);
        decRes.add(map.get("rewardsbal").toString());
        decRes.add(map.get("tripscompleted").toString());
        decRes.add(map.get("rating").toString());

        return decRes;
    }

    private static boolean VerifyLogin(String username){
        Task query = firestore.collection("currentlyLoggedIn").document(username).get();
        while (!query.isComplete());
        DocumentSnapshot mapRes = (DocumentSnapshot) query.getResult();
        Map<String, Object> map = mapRes.getData();
        return !map.isEmpty();
    }

    private static boolean VerifyPaused(String username){
        Task query = firestore.collection("currentlyPaused").document(username).get();
        while (!query.isComplete());
        DocumentSnapshot mapRes = (DocumentSnapshot) query.getResult();
        Map<String, Object> map = mapRes.getData();
        return !map.isEmpty();
    }

    protected static boolean DeleteProfile(String username) throws Exception{
        if(!VerifyLogin(username))
            throw new Exception("User is not logged in");
        if(VerifyPaused(username))
            throw new Exception("Please ensure that user profile is not paused before deletion");

        try{
            firestore.collection("currentlyPaused").document(username).delete();
        }
        catch (Exception E){
            throw E;
        }

        return true;
    }

    protected static boolean SignalLogin(String username){
        Map<String, Boolean> map = new HashMap<>();
        map.put("signedin", true);
        try{
            firestore.collection("currentlyLoggedIn").document(username).set(map);
        }
        catch (Exception E){
            throw E;
        }
        return true;
    }

    protected static boolean SignalLogout(String username){
        try{
            firestore.collection("currentlyLoggedIn").document(username).delete();
        }
        catch (Exception E){
            throw E;
        }
        return true;
    }

    protected static boolean SignalPause(String username){
        Map<String, Boolean> map = new HashMap<>();
        map.put("paused", true);
        try{
            firestore.collection("currentlyPaused").document(username).set(map);
        }
        catch (Exception E){
            throw E;
        }
        return true;
    }

    protected static boolean SignalUnpause(String username){
        try{
            firestore.collection("paused").document(username).delete();
        }
        catch (Exception E){
            throw E;
        }
        return true;
    }

    protected static boolean RateUser(String username, int rating) throws Exception{
        ArrayList<String> profile = RetrieveProfile(username);
        int tripsCompleted = Integer.valueOf(profile.get(ProfileField.TRIPSCOMPLETED.ordinal()));
        int previousRating = Integer.valueOf(profile.get(ProfileField.RATING.ordinal()));

        int newRating = (previousRating*(tripsCompleted-1) + rating)/tripsCompleted;

        try{
            Task task = firestore.collection("profiles").document(username).get();
            while(!task.isComplete());
            DocumentSnapshot mapRes = (DocumentSnapshot) task.getResult();
            Map<String, Object> encResult = mapRes.getData();
            encResult.put("rating", (Object) newRating);
            firestore.collection("profiles").document(username).set(encResult);
        }
        catch (Exception E){
            throw E;
        }
        return true;
    }

    protected static boolean modifyProfile(String username, ProfileField field, Object newVal) throws Exception{
        //verify user is logged in
        if (!VerifyLogin(username)){
            throw new Exception("User is not logged in");
        }

        if (VerifyPaused(username)){
            throw new Exception("User is paused");
        }

        if (field.ordinal() > 5){
            throw new Exception("Bad request, system cannot determine which field was requested to be modified");
        }

        Map<String, Object> encResult;

        try{
            Task query = firestore.collection("profiles").document(username).get();
            while(!query.isComplete());
            DocumentSnapshot mapRes = (DocumentSnapshot) query.getResult();
            encResult = mapRes.getData();
        }
        catch (Exception E){
            throw E;
        }
        
        if (encResult.isEmpty()){
            throw new Exception("Cannot find the user with supplied username");
        }

        if(field.ordinal() == 0){ //legalname
            encResult.put("legalname", newVal);
            firestore.collection("profiles").document(username).set(encResult);
        }
        
        else if(field.ordinal() == 4){
            encResult.put("rewardsbal", Integer.valueOf((String) newVal));
            firestore.collection("profiles").document(username).set(encResult);
        }

        else if (field.ordinal() == 5){
            encResult.put("tripscompleted", Integer.valueOf((String) newVal));
            firestore.collection("profiles").document(username).set(encResult);
        }
        
        else if (field.ordinal() == 1){ //username
            throw new Exception("Cannot edit username");
        }
        else{
            ArrayList<String> encList = new ArrayList<String>(){
                {
                    add((String)encResult.get("legalname"));
                    add((String) encResult.get("username"));
                    add((String) encResult.get("password"));
                    add((String) encResult.get("phonenumber"));
                }
            };

            ArrayList<String> decResult = encryptor.getDecryption(encList);
            ArrayList<String> pseudoNewUser = new ArrayList<String>(){
                {
                    add((String)decResult.get(ProfileField.LEGALNAME.ordinal()));
                    add((String)decResult.get(ProfileField.USERNAME.ordinal()));
                }
            };

            if (field.ordinal() == 2){ //User requesting to modify Password
                pseudoNewUser.add((String) newVal);
                pseudoNewUser.add((String)decResult.get(ProfileField.PHONENUMBER.ordinal()));
            }
            else{ //User requesting to modify PhoneNumber
                pseudoNewUser.add((String)decResult.get(ProfileField.PASSWORD.ordinal()));
                pseudoNewUser.add((String) newVal);
            }

            ArrayList<String> encryptedPseudoNewUser = encryptor.getEncryption(pseudoNewUser);
            encryptedPseudoNewUser.add(encResult.get("rewardsbal").toString());
            encryptedPseudoNewUser.add(encResult.get("tripscompleted").toString());
            encryptedPseudoNewUser.add(encResult.get("rating").toString());
            Map<String, Object> encProfile = new HashMap<String, Object>(){
                {
                    put("legalname", encryptedPseudoNewUser.get(ProfileField.LEGALNAME.ordinal()));
                    put("username", encryptedPseudoNewUser.get(ProfileField.USERNAME.ordinal()));
                    put("password", encryptedPseudoNewUser.get(ProfileField.PASSWORD.ordinal()));
                    put("phonenumber", encryptedPseudoNewUser.get(ProfileField.PHONENUMBER.ordinal()));
                    put("rewardsbal", Integer.getInteger(encryptedPseudoNewUser.get(ProfileField.REWARDSBAL.ordinal())));
                    put("tripscompleted", Integer.getInteger(encryptedPseudoNewUser.get(ProfileField.TRIPSCOMPLETED.ordinal())));
                    put("rating", Integer.getInteger(encryptedPseudoNewUser.get(ProfileField.RATING.ordinal())));
                }
            };
            try{
                firestore.collection("profiles").document(username).set(encryptedPseudoNewUser);
            }
            catch (Exception E){
                throw E;
            }
        }
        return true;
    }
}