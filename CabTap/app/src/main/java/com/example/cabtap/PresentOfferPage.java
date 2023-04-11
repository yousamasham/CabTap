package com.example.cabtap;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class PresentOfferPage extends Fragment {

    Button accept;
    Button reject;
    DispatcherController controller = new DispatcherController();
    
    public static PresentOfferPage newInstance(SessionDetails profile) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    }    

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ){
        accept = (Button) getView().findViewById(R.id.btn_accept);
        reject = (Button) getView().findViewById(R.id.btn_reject);
        String username = args.getString("username");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DispatcherController controller = new DispatcherController();
                TripInformation ride = controller.CheckOffersToMe(username);

                Intent intent = new Intent(getActivity(), InTransitPage.class);
                startActivity(intent);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripInformation ride = controller.CheckOffersToMe(username);
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_offersharepage, container, false);}
    
}