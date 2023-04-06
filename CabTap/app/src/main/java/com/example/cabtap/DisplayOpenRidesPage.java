package com.example.cabtap;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.cabtap.TripInformation;

public class DisplayOpenRidesPage extends AppCompatActivity {
    // uses database of available rides and maps and requestride share page to find nearst available rides
    // and display it

    TextView text_view;
    ArrayList<TripInformation> availableRides = new ArrayList<>();
 

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.DisplayOpenRidesPage);

        //text_view = findViewById(R.id.text_view);
    }

    // content that will be displayed on the page (all the open rides with the accept and reject buttons)
    private void showRidesContent(){
        //call dispatcher to get array of rides offers

    }
}