package com.example.cabtap;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;


public class DisplayOpenRidesPage extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<TripInformation> openRides;
    SwipeRefreshLayout swipeRefreshLayout;
    DispatcherController controller = new DispatcherController();

    public static DisplayOpenRidesPage newInstance(SessionDetails profile) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString("username", profile.getSessionUsername());
        fragment.setArguments(args);
        return fragment;
    } 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_displayopenrides);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(openRides);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration((dividerItemDecoration));
        
        swipeRefreshLayout = findViewById((R.id.swipeRefreshLayout));
       
        swipeRefreshLayout.setOnRefreshListener((new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    public void updateRides(TripInformation trip){
        openRides.add(trip);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String username = args.getString("username");
            switch (direction) {
                //request this ride = swipe left
                case ItemTouchHelper.LEFT:
                    Intent intent = new Intent(getActivity(), waitforaccept.class);
                    startActivity(intent);
                    if(controller.pairRiders(username, openRides.get(position))){
                        Intent intentTransit = new Intent(getActivity(), intransit.class);
                        startActivity(intentTransit);
                    }
                    else{
                        Intent intentRides = new Intent(getActivity(), displayopenrides.class);
                        startActivity(intentRides);
                    }
                    break;
                //reject = swipe right
                case ItemTouchHelper.RIGHT:
                    openRides.remove(position);
                    recyclerAdapter.notifyItemRemoved(position);
                    break;
            }
        }
    };
}
