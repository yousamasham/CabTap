package com.example.cabtap;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class RegistrationController {

    private boolean validateCredentials(EditText username, EditText phoneNumber, EditText password){

        //TODO VALIDATE USERNAME AND PHONENUMBER
        CharSequence number = phoneNumber.getText().toString();
        return (!TextUtils.isEmpty(number) && Patterns.PHONE.matcher(number).matches());
        }
    }
}
