package com.example.cabtap;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;


public class DisplayOpenRidesPage extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    static ArrayList<TripInformation> openRides;
    SwipeRefreshLayout swipeRefreshLayout;
    DispatcherController controller = new DispatcherController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayopenrides);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(openRides);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        // now adds lines to seperate each item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration((dividerItemDecoration));

        // get list of available requests from dispatcher and store in the requestedRides list

        swipeRefreshLayout = findViewById((R.id.swipeRefreshLayout));
        swipeRefreshLayout.setOnRefreshListener((new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // get list of open rides and their info from dispatcher again (call dispatcher to
                // to give info and add it into openRides.
                // openRides.add(Dispatcher.getRides())
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    public static void updateRides(TripInformation trip){
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
            String username = getIntent().getStringExtra("username");
            switch (direction) {
                //request this ride = swipe left
                case ItemTouchHelper.LEFT:
                    openRides.remove(position);
                    // display popup
                    if(controller.pairRiders(openRides.get(position), username)){
                        Intent intentTransit = new Intent(DisplayOpenRidesPage.this, InTransitPage.class);
                        startActivity(intentTransit);
                    }
                    else{
                        //remove popup
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
