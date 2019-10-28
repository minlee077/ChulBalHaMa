package com.example.ushnesha.activityrecognitionapp;

import android.view.View;
import android.widget.TextView;

public class HabitListWrapper {

    public TextView habitsView;

    public HabitListWrapper(View root ){
        habitsView=(TextView)root.findViewById(R.id.habit_item_name);
    }

}


