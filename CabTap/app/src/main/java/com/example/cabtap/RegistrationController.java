package com.example.cabtap;

import static android.text.TextUtils.isEmpty;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.ArrayList;

public class RegistrationController {

    public ArrayList<String> validateCredentials(
            EditText legalName,
            EditText userName,
            EditText phoneNumber,
            EditText password,
            EditText rePassword)
    throws Exception{

        CharSequence legalStr = legalName.getText().toString();
        if (isEmpty(legalStr)) {
            legalName.setError("You must enter username to login!");
            throw new Exception("Exception message");
        }

        CharSequence usernameStr = userName.getText().toString();
        if (isEmpty(usernameStr)) {
            userName.setError("You must enter username to login!");
            throw new Exception("Exception message");
        }

        CharSequence number = phoneNumber.getText().toString();
        if (isEmpty(number) || !Patterns.PHONE.matcher(number).matches()){
            phoneNumber.setError("You must enter a valid number to login!");
            throw new Exception("Exception message");
        }
        CharSequence passStr = password.getText().toString();
        CharSequence repassStr = rePassword.getText().toString();

        if (!passStr.equals(repassStr)){
            password.setError("Password does not match");
            rePassword.setError("Password does not match");
            throw new Exception("Exception message");
        }

        try{
            ProfileDatabase profileDB = new ProfileDatabase();
            profileDB.InsertProfile(legalName.getText().toString(), userName.getText().toString(),
                    password.getText().toString(), phoneNumber.getText().toString());
            profileDB.SignalLogin(userName.getText().toString());
            ArrayList<String> userDetails = profileDB.RetrieveProfile(userName.getText().toString());
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
