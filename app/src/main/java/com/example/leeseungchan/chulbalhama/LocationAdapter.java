package com.example.leeseungchan.chulbalhama;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {

    private ArrayList<ItemLocation> mDataSet = new ArrayList<>();

    // ViewHolder
    public static class LocationHolder extends RecyclerView.ViewHolder{
        public TextView locationName;
        public TextView destDesc;

        public LocationHolder(@NonNull final View v){
            super(v);

            locationName = v.findViewById(R.id.item_destination_name);
            destDesc = v.findViewById(R.id.item_destination_description);
        }

        public void setLocation(ItemLocation itemLocation){
            locationName.setText(itemLocation.getName());
            destDesc.setText(itemLocation.getDescription());
        }
    }

    public LocationAdapter(){
        // empty constructor
    }

    public void addHabit(ItemLocation itemLocation){
        mDataSet.add(itemLocation);
    }

    @Override
    public LocationAdapter.LocationHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType){

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habit, parent, false);
        LocationHolder vh = new LocationHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LocationHolder holder, int position){
        ItemLocation item = mDataSet.get(position);
        holder.setLocation(item);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }
}
