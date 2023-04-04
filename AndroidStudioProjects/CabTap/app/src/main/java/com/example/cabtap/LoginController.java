package com.example.cabtap;

import static android.text.TextUtils.isEmpty;

import android.widget.EditText;

public class LoginController {

    public boolean checkUsername(EditText user, EditText pass) {

        CharSequence username = user.getText().toString();
        boolean isValid = true;
        if (isEmpty(username)) {
            user.setError("You must enter username to login!");
            isValid = false;
        }

        CharSequence password = pass.getText().toString();
        if (isEmpty(password)) {
            pass.setError("You must enter password to login!");
            isValid = false;
        }

        if (isValid) {
            //TODO
        }
    }
}
