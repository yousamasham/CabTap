package com.example.cabtap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PresentOfferPage extends Fragment{
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presentofferpage);
        recyclerView.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    //private void dispalyRequestRides(){
    //ArrayList<TripInformation> rides = DispatcherController.getRides();
    //display all options, create an accept and reject button for each
    //if (isNull(rides)){
    // show message saying no offers yet
    // }
    //}

    // called when accept button is pressed
    private void acceptRide(){
        // calls dispatcher and dispatcher pairs riders together
    }

    // called when reject button is pressed
    private void rejectRide(){
        // dispatcher removes from list of requests for the rider.
    }

}