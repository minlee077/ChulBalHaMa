package com.example.leeseungchan.chulbalhama.Adpater;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.ItemHabit;
import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private ArrayList<ItemHabit> mDataSet = new ArrayList<>();

    // ViewHolder
    public static class HabitViewHolder extends RecyclerView.ViewHolder{
        public TextView habitName;
        public TextView habitDescription;

        public HabitViewHolder(@NonNull final View v){
            super(v);

            habitName = v.findViewById(R.id.item_habit_name);
            habitDescription = v.findViewById(R.id.item_habit_description);
        }

        public void setHabit(ItemHabit itemHabit){
            habitName.setText(itemHabit.getName());
            habitDescription.setText(itemHabit.getDescription());
        }
    }

    public HabitAdapter(){
        // empty constructor
    }

    public void addHabit(ItemHabit itemHabit){
        mDataSet.add(itemHabit);
    }

    @Override
    public HabitAdapter.HabitViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType){

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_habit, parent, false);
        HabitViewHolder vh = new HabitViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position){
        ItemHabit item = mDataSet.get(position);
        holder.setHabit(item);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }
}
