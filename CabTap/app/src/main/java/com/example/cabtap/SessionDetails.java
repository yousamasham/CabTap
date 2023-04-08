package com.example.cabtap;

import java.io.Serializable;
import java.util.ArrayList;

public class SessionDetails implements Serializable {

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

    protected String getSessionRewardsBalance(){
        return this.account.get(ProfileField.REWARDSBAL.ordinal());
    }

    protected String getSessionRating(){
        return this.account.get(ProfileField.RATING.ordinal());
    }

    protected String getSessionTripsCompleted(){
        return this.account.get(ProfileField.TRIPSCOMPLETED.ordinal());
    }
}