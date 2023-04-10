package com.example.cabtap;
import java.util.ArrayList;

public class ProfileController {

    protected static ArrayList<String> RequestRetrieveProfile(String username) throws Exception{
        try{
            ProfileDatabase db = new ProfileDatabase();
            return db.RetrieveProfile(username);
        }

        catch (Exception E){
            throw E;
        }
    }

    protected static boolean RequestEditProfile(String username, String field, String newVal) throws Exception{
        ProfileField fieldToPass;
        switch (field.toLowerCase().trim()){
            case "phonenumber":
                fieldToPass = ProfileField.PHONENUMBER;
                break;
            case "legalname":
                fieldToPass = ProfileField.LEGALNAME;
                break;
            default:
                fieldToPass = ProfileField.PASSWORD;
        }

        try{
            ProfileDatabase db = new ProfileDatabase();
            return db.ModifyProfile(username, fieldToPass, newVal);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static boolean RequestEditCredentials(String username, String newVal) throws Exception{
        ProfileField fieldToPass = ProfileField.PASSWORD;
        try{
            ProfileDatabase db = new ProfileDatabase();
            return db.ModifyProfile(username, fieldToPass, newVal);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected boolean RequestDeleteProfile(String username) throws Exception{
        try{
            ProfileDatabase db = new ProfileDatabase();
            return db.DeleteProfile(username);
        }
        catch (Exception E){
            throw E;
        }
    }
}