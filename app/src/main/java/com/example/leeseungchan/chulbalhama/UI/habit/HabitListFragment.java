package com.example.leeseungchan.chulbalhama.UI.habit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leeseungchan.chulbalhama.Activities.AddHabitActivity;
import com.example.leeseungchan.chulbalhama.Adpater.HabitAdapter;
import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.VO.HabitsVO;

import java.util.ArrayList;


public class HabitListFragment extends Fragment {
    public static Context mContext;
    ArrayList <HabitsVO> habits = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habit_list, container, false);

        // set up recycler view
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter;
        mAdapter = new HabitAdapter(habits);
        recyclerView.setAdapter(mAdapter);

        retrieve();

        // set add button
        Button habitAddButton = v.findViewById(R.id.add);
        habitAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), AddHabitActivity.class);
                startActivity(intent);
            }
        });

        mContext = getContext();

        return v;
    }

    public void retrieve(){
        habits.clear();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from habits";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String desc = c.getString(2);
            String prepare = c.getString(3);

            HabitsVO h = new HabitsVO(id, name, desc, prepare, 0);
            habits.add(h);
        }
    }
}