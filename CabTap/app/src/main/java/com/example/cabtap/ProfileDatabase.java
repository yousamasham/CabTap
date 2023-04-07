package com.example.cabtap;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileDatabase {
    private static EncryptionController encryptor;
    private static FirebaseFirestore firestore;

    ProfileDatabase() throws Exception {
        //init encryptor
        try{
            encryptor = new EncryptionController();
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
        try{
            firestore.collection("profiles").document((String) newUser.get("username")).set(newUser);
        }
        catch (Exception E){
            throw E;
        }

        return true;
    }

    protected static ArrayList<String> RetrieveProfile(String username) throws Exception{

        Map<String, Object> mapResult = firestore.collection("profiles").document(username).get().getResult().getData();

        ArrayList<String> resEnc = new ArrayList<String>(){
            {
                add((String) mapResult.get("legalname"));
                add((String) mapResult.get("username"));
                add((String) mapResult.get("password"));
                add((String) mapResult.get("phonenumber"));
                add((String) mapResult.get("rewardsbal"));
            }
        };

        ArrayList<String> decRes = encryptor.getDecryption(resEnc);

        decRes.remove(ProfileField.PASSWORD.ordinal());

        return decRes;
    }

    private static boolean VerifyLogin(String username){
        Map<String, Object> mapResult = firestore.collection("currentlyLoggedIn").document(username).get().getResult().getData();
        return !mapResult.isEmpty();
    }

    private static boolean VerifyPaused(String username){
        Map<String, Object> mapResult = firestore.collection("currentlyPaused").document(username).get().getResult().getData();
        return !mapResult.isEmpty();
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
        Map<String, Boolean> map = new HashMap<String, Boolean>();
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
        Map<String, Boolean> map = new HashMap<String, Boolean>();
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

    protected static boolean modifyProfile(String username, ProfileField field, Object newVal) throws Exception{
        //verify user is logged in
        if (!VerifyLogin(username)){
            throw new Exception("User is not logged in");
        }

        if (VerifyPaused(username)){
            throw new Exception("User is paused");
        }

        if (field.ordinal() > 3){
            throw new Exception("Bad request, system cannot determine which field was requested to be modified");
        }

        Map<String, Object> encResult = firestore.collection("profiles").document(username).get().getResult().getData();
        
        if (encResult.isEmpty()){
            throw new Exception("Cannot find the user with supplied username");
        }

        if(field.ordinal() == 0){ //legalname
            encResult.put("legalname", newVal);
            firestore.collection("profiles").document(username).set(encResult);
        }
        
        else if(field.ordinal() == 4){
            encResult.put("rewardsbal", (int) newVal);
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
            Map<String, Object> encProfile = new HashMap<String, Object>(){
                {
                    put("legalname", encryptedPseudoNewUser.get(ProfileField.LEGALNAME.ordinal()));
                    put("username", encryptedPseudoNewUser.get(ProfileField.USERNAME.ordinal()));
                    put("password", encryptedPseudoNewUser.get(ProfileField.PASSWORD.ordinal()));
                    put("phonenumber", encryptedPseudoNewUser.get(ProfileField.PHONENUMBER.ordinal()));
                    put("rewardsbal", 0);
                }
            };
        }
        return true;
    }
}