package com.example.cabtap;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileDatabase {
    private static ArrayList<ArrayList<String>> encryptedUserList;
    private static ArrayList<String> usersLoggedIn;
    private static EncryptionController encryptor;
    private static ArrayList<String> usersPaused;

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

        /*//find profile
        ArrayList<String> resultProfile = new ArrayList<String>();
        for (ArrayList<String> encProfile : encryptedUserList){
            if (encProfile.get(ProfileField.USERNAME.ordinal()).equals(username)){
                //construct results
                ArrayList<String> decProfile = encryptor.getDecryption(encProfile);
                resultProfile.add((String)decProfile.get(ProfileField.LEGALNAME.ordinal()));
                resultProfile.add((String)decProfile.get(ProfileField.USERNAME.ordinal()));
                resultProfile.add((String)decProfile.get(ProfileField.PHONENUMBER.ordinal()));
                break;
            }
        }
        //return results
        if (resultProfile.size() > 0)
            return resultProfile;
        else{
            throw new Exception("Username not found within database");
        }*/
    }

    private static boolean VerifyLogin(String username){
        return usersLoggedIn.contains(username);
    }

    private static boolean VerifyPaused(String username){
        return usersPaused.contains(username);
    }

    protected static boolean DeleteProfile(String username) throws Exception{
        if(!VerifyLogin(username))
            throw new Exception("User is not logged in");
        if(VerifyPaused(username))
            throw new Exception("Please ensure that user profile is not paused before deletion");

        int index = findDBIndex(username);

        encryptedUserList.remove(index);

        return true;
    }

    protected static boolean SignalLogin(String username){
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("signedin", true);
        firestore.collection("currentlyLoggedIn").document(username).set(map);
        return true;
    }

    protected static void SignalLogout(String username){
        usersLoggedIn.remove(username);
    }

    protected static void SignalPause(String username){
        usersPaused.add(username);
    }

    protected static void SignalUnpause(String username){
        usersPaused.remove(username);
    }

    private static int findDBIndex(String username){
        for (int i = 0; i < encryptedUserList.size(); i++){
            if (encryptedUserList.get(i).get(ProfileField.USERNAME.ordinal()).equals(username))
                return i;
        }
        return -1;
    }

    protected static boolean modifyProfile(String username, ProfileField field, String newVal) throws Exception{
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
        int DBindex = findDBIndex(username);
        if (DBindex == -1){
            throw new Exception("Cannot find the user with supplied username");
        }

        if(field.ordinal() == 0){
            encryptedUserList.get(DBindex).set(field.ordinal(), newVal);
        }
        else if (field.ordinal() == 1){
            throw new Exception("Cannot edit username");
        }
        else{
            ArrayList<String> decryptedUserDetails = encryptor.getDecryption(encryptedUserList.get(DBindex));
            ArrayList<String> pseudoNewUser = new ArrayList<String>(){
                {
                    add((String)decryptedUserDetails.get(ProfileField.LEGALNAME.ordinal()));
                    add((String)decryptedUserDetails.get(ProfileField.USERNAME.ordinal()));
                }
            };

            if (field.ordinal() == 2){ //User requesting to modify Password
                pseudoNewUser.add(newVal);
                pseudoNewUser.add((String)decryptedUserDetails.get(ProfileField.PHONENUMBER.ordinal()));
            }
            else{ //User requesting to modify PhoneNumber
                pseudoNewUser.add((String)decryptedUserDetails.get(ProfileField.PASSWORD.ordinal()));
                pseudoNewUser.add(newVal);
            }

            ArrayList<String> encryptedPseudoNewUser = encryptor.getEncryption(pseudoNewUser);
            encryptedUserList.remove(DBindex);
            encryptedUserList.add(encryptedPseudoNewUser);
        }
        return true;
    }


}