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
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ){
        accept = (Button) getView().findViewById(R.id.btn_accept);
        reject = (Button) getView().findViewById(R.id.btn_reject);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DispatcherController controller = new DispatcherController();
                ArrayList<TripInformation> rides = new ArrayList<TripInformation>();
                // rides = controller.getRides();

                Intent intent = new Intent(getActivity(), InTransitPage.class);
                startActivity(intent);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rides = controller.getRides();
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.fragment_offersharepage, container, false);}
    
}