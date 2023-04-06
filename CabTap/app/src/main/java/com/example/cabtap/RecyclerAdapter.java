package com.example.cabtap;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecyclerAdapter";
    int count = 0;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // map data inside each item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.pickupTextView.setText(); //dispatcher offers list[0] (will be the pickup locations)
        //holder.dropOffTextView.setText(); //dispatcher offers list[1] (will be the dropoff locations)
        //holder.approxTimeTextView.setText(); //dispatcher offers list[2] (will be the approx time)
        //holder.approxSavingsTextView.setText(); //dispatcher offers list[3] (will be the approx savings)
    }

    @Override
    public int getItemCount() {
        // return number of requested rides from dispatcher
        // function to show the number of items needed.
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        }
    }

}
