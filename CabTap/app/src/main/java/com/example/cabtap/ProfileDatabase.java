package com.example.cabtap;

import java.util.ArrayList;

public class ProfileDatabase {
    private static ArrayList<ArrayList<String>> encryptedUserList;
    private static ArrayList<String> usersLoggedIn;
    private static EncryptionController encryptor;
    private static ArrayList<String> usersPaused;

    ProfileDatabase() throws Exception {
        //init encryptor
        try{
            encryptor = new EncryptionController();
        }
        catch(Exception E){
            throw E;
        }
        encryptedUserList = new ArrayList<ArrayList<String>>();
        usersLoggedIn = new ArrayList<String>();
        usersPaused = new ArrayList<String>();
    }

    protected static void InsertProfile(String ptLegalName, String ptUsername, String ptPassword, String ptPhoneNumber) throws Exception{
        //create profile arrayList
        ArrayList<String> encNewProfile = new ArrayList<String>();
        ArrayList<String> ptNewProfile = new ArrayList<String>(){
            {
                add(ptLegalName);
                add(ptUsername);
                add(ptPassword);
                add(ptPhoneNumber);
            }
        };

        encNewProfile = encryptor.getEncryption(ptNewProfile);

        //Ensure uniqueness
        for (ArrayList<String> encProfile : encryptedUserList) {
            if (encProfile.get(ProfileField.USERNAME.ordinal()).equals(encNewProfile.get(ProfileField.USERNAME.ordinal())))
                throw new Exception("Username already exists. Please select another one.");
        }

        //New profile is unique, add it to DB.
        encryptedUserList.add(encNewProfile);
    }

    protected static ArrayList<String> RetrieveProfile(String username) throws Exception{
        //find profile
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
        }
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

    protected static void SignalLogin(String username){
        usersLoggedIn.add(username);
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