package com.example.cabtap;

import java.util.ArrayList;

public class SessionDetails {

    private ArrayList<String> account;

    public SessionDetails(ArrayList<String> account){
        this.account = account;
    }

    protected String getSessionUsername(){
        return this.account.get(ProfileField.USERNAME.ordinal());
    }

    protected String getSessionLegalName(){
        return this.account.get(ProfileField.LEGALNAME.ordinal());
    }

    protected String getSessionPhoneNumber(){
        return this.account.get(ProfileField.PHONENUMBER.ordinal());
    }
}
