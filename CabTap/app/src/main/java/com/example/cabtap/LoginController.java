package com.example.cabtap;

import static android.text.TextUtils.isEmpty;

import android.widget.EditText;

import java.util.ArrayList;

public class LoginController {

    public ArrayList<String> login(EditText userName, EditText password) throws Exception {

        CharSequence userStr = userName.getText().toString();
        if (isEmpty(userStr)) {
            userName.setError("You must enter username to login!");
            throw new Exception("Exception message");
        }

        CharSequence passStr = password.getText().toString();
        if (isEmpty(passStr)) {
            password.setError("You must enter password to login!");
            throw new Exception("Exception message");
        }

        try{
            ProfileDatabase profileDB = new ProfileDatabase();
            ArrayList<String> userDetails = profileDB.RetrieveProfile(userName.getText().toString());
            if (userDetails.isEmpty()){
                userName.setError("Username does not exist!");
                throw new Exception("Exception message");
            }

            if (!userDetails.get(ProfileField.PASSWORD.ordinal()).equals(passStr)){
                password.setError("Username and password do not match!");
                throw new Exception("Exception message");
            }
            return userDetails;
        }
        catch(Exception E){
            try {
                throw E;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



}
