package com.example.leeseungchan.chulbalhama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {

    public MainFragment(){
        //Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_main,container, false);

        Button addHabitbtn = (Button) v.findViewById(R.id.addHabitBtn);
        addHabitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), HabitAddActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


}
