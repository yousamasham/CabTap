package com.example.cabtap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModifyProfilePage extends Fragment {
    TextView fieldNameTV;
    TextView oldValTV;
    EditText newValET;
    EditText newValRePassword;
    EditText oldValPassword;
    Button yes;
    Button no;
    Button submit;

    public static ModifyProfilePage newInstance(ProfileField field, SessionDetails sessionDetails) {
        ModifyProfilePage fragment = new ModifyProfilePage();
        Bundle args = new Bundle();
        switch (field){
            case LEGALNAME:
                args.putString("legalname", sessionDetails.getSessionLegalName());
                break;
            case PHONENUMBER:
                args.putString("phonenumber", sessionDetails.getSessionPhoneNumber());
                break;
            case PASSWORD:
                args.putString("password", sessionDetails.getSessionUsername());
        }
        args.putString("field", field.toString().toLowerCase().trim());
        args.putString("username", sessionDetails.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }

    public static ModifyProfilePage newInstance(boolean delete, SessionDetails sessionDetails){
        ModifyProfilePage fragment = new ModifyProfilePage();
        Bundle args = new Bundle();
        args.putBoolean("delete", delete);
        args.putString("username", sessionDetails.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fieldNameTV = (TextView) getView().findViewById(R.id.pageTitle);
        oldValTV = (TextView) getView().findViewById(R.id.oldProp);
        submit = (Button) getView().findViewById(R.id.btn_submit);
        newValET = (EditText) getView().findViewById(R.id.newProp);
        newValRePassword = (EditText) getView().findViewById(R.id.newPropRePassword);
        oldValPassword = (EditText) getView().findViewById(R.id.oldPropPassword);
        yes = (Button) getView().findViewById(R.id.btn_yes);
        no = (Button) getView().findViewById(R.id.btn_no);
        Bundle args = getArguments();

        if (args.getString("field") != null){
            switch (args.getString("field")){
                case "legalname":
                    fieldNameTV.setText("Changing Legal Name");
                    oldValPassword.setVisibility(View.INVISIBLE);
                    newValRePassword.setVisibility(View.INVISIBLE);
                    newValET.setHint("New Legal Name");
                    oldValTV.setText("Current Legal Name: "+args.getString("legalname"));
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                    break;
                case "phonenumber":
                    fieldNameTV.setText("Changing Phone Number");
                    oldValPassword.setVisibility(View.INVISIBLE);
                    newValRePassword.setVisibility(View.INVISIBLE);
                    newValET.setHint("New Phone Number");
                    oldValTV.setText("Current Phone Number: "+args.getString("phonenumber"));
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                    break;
                case "password":
                    fieldNameTV.setText("Changing Password");
                    newValET.setTransformationMethod(new PasswordTransformationMethod());
                    oldValPassword.setHint("Old password");
                    newValET.setHint("New password");
                    oldValTV.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
            }
        }

        else{
            if (args.getBoolean("delete")) {
                fieldNameTV.setText("Deleting Profile");
                oldValPassword.setVisibility(View.INVISIBLE);
                newValRePassword.setVisibility(View.INVISIBLE);
                newValET.setVisibility(View.INVISIBLE);
                oldValTV.setText("Are you sure you would like to delete your CabTap profile?");
                submit.setVisibility(View.INVISIBLE);
            }
            else{
                fieldNameTV.setText("Pausing Profile");
                oldValPassword.setVisibility(View.INVISIBLE);
                newValRePassword.setVisibility(View.INVISIBLE);
                newValET.setVisibility(View.INVISIBLE);
                oldValTV.setText("Are you sure you would like to pause your CabTap profile?");
                submit.setVisibility(View.INVISIBLE);
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ProfileDatabase db = new ProfileDatabase();
                    switch (args.getString("field")){
                        case "legalname":
                            db.ModifyProfile(args.getString("username"), ProfileField.LEGALNAME, newValET.getText().toString());
                            Toast.makeText(getContext(), "Legal name changed successfully!", Toast.LENGTH_SHORT).show();
                            break;
                        case "phonenumber":
                            db.ModifyProfile(args.getString("username"), ProfileField.PHONENUMBER, newValET.getText().toString());
                            Toast.makeText(getContext(), "Phone number changed successfully!", Toast.LENGTH_SHORT).show();
                            break;
                        case "password":
                            String oldPassword = db.RetrieveProfile(args.getString("username")).get(ProfileField.PASSWORD.ordinal());
                            String oldPasswordEntered = oldValPassword.getText().toString();
                            if (!oldValPassword.getText().toString().equals(oldPassword)){
                                oldValPassword.setError("Incorrect password!");
                                throw new Exception("Exception Message");
                            }
                            if (!newValET.getText().toString().equals(newValRePassword.getText().toString())){
                                newValRePassword.setError("Passwords do not match!");
                                throw new Exception("Exception Message");
                            }
                            db.ModifyProfile(args.getString("username"), ProfileField.PASSWORD, newValET.getText().toString());
                            Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<String> profile = db.RetrieveProfile(args.getString("username"));
                    Fragment fragment = ProfilePage.newInstance(new SessionDetails(profile));
                    replaceFragment(fragment);
                }
                catch (Exception E){}
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ProfileDatabase db = new ProfileDatabase();
                    if (args.getBoolean("delete")) {
                        db.DeleteProfile(args.getString("username"));
                        db.SignalLogout(args.getString("username"));
                        Toast.makeText(getContext(), "Profile deleted successfully", Toast.LENGTH_LONG).show();
                        Thread.sleep(1500);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        db.SignalLogout(args.getString("username"));
                        db.SignalPause(args.getString("username"));
                        Toast.makeText(getContext(), "Profile paused successfully, you will now be logged out", Toast.LENGTH_LONG).show();
                        Thread.sleep(2500);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception E){}
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ProfileDatabase db = new ProfileDatabase();
                    ArrayList<String> profile = db.RetrieveProfile(args.getString("username"));
                    Fragment fragment = ProfilePage.newInstance(new SessionDetails(profile));
                    replaceFragment(fragment);
                }
                catch(Exception E){}
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifyprofile, container, false);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}