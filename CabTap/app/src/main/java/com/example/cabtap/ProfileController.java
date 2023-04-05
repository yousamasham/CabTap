package com.example.cabtap;
import java.util.ArrayList;

public class ProfileController {

    protected static ArrayList<String> RequestRetrieveProfile(String username) throws Exception{
        try{
            return ProfileDatabase.RetrieveProfile(username);
        }

        catch (Exception E){
            throw E;
        }
    }

    protected static boolean RequestEditProfile(String username, String field, String newVal) throws Exception{
        ProfileField fieldToPass = ProfileField.LEGALNAME;
        switch (field.toLowerCase().trim()){
            case "phonenumber":
                fieldToPass = ProfileField.PHONENUMBER;
                break;
            case "legalname":
                break;
        }

        try{
            return ProfileDatabase.modifyProfile(username, fieldToPass, newVal);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static boolean RequestEditCredentials(String username, String newVal) throws Exception{
        ProfileField fieldToPass = ProfileField.PASSWORD;
        try{
            return ProfileDatabase.modifyProfile(username, fieldToPass, newVal);
        }
        catch (Exception E){
            throw E;
        }
    }

    protected static boolean RequestDeleteProfile(String username) throws Exception{
        try{
            return ProfileDatabase.DeleteProfile(username);
        }
        catch (Exception E){
            throw E;
        }
    }
}