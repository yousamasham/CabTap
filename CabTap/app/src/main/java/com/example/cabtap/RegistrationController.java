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
            rePassword.setError("Password does not match");
            throw new Exception("Exception message");
        }

        try{
            ProfileDatabase db = new ProfileDatabase();
            ArrayList<String> res = db.RetrieveProfile(usernameStr.toString());
            if (!(res == null || res.isEmpty())){
                userName.setError("Username already exists. Please sign in or pick a different username!");
                throw new Exception("Exception message");
            }
            db.InsertProfile(legalName.getText().toString(), userName.getText().toString(), password.getText().toString(), phoneNumber.getText().toString());
            db.SignalLogin(userName.getText().toString());
            return db.RetrieveProfile(userName.getText().toString());
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
