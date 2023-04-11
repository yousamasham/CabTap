package com.example.cabtap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecyclerAdapter";
    ArrayList<TripInformation> openRides; // get list of available requests from dispatcher and store in list

    public RecyclerAdapter(ArrayList<TripInformation> openRides){
        this.openRides = openRides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // map data inside each item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dropOffTextView.setText(openRides.get(position).destination); //dispatcher offers list[1] (will be the dropoff locations)
        holder.approxTimeTextView.setText(openRides.get(position).rideTime); //dispatcher offers list[2] (will be the approx time)
        holder.approxSavingsTextView.setText(openRides.get(position).rideFare); //dispatcher offers list[3] (will be the approx savings)
    }

    @Override
    public int getItemCount() {
        return openRides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener

        TextView pickupTextView;
        TextView dropOffTextView;
        TextView approxTimeTextView;
        TextView approxSavingsTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pickupTextView = itemView.findViewById(R.id.pickupTextView);
            dropOffTextView = itemView.findViewById((R.id.dropOffTextView));
            approxTimeTextView = itemView.findViewById((R.id.approxTimeTextView));
            approxSavingsTextView = itemView.findViewById((R.id.approxSavingsTextView));

            //itemView.setOnClickListener(this);
        }

    }

}
