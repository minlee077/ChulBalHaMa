package com.example.leeseungchan.chulbalhama.UI.habit_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leeseungchan.chulbalhama.HabitAdapter;
import com.example.leeseungchan.chulbalhama.ItemHabit;
import com.example.leeseungchan.chulbalhama.R;


public class HabitListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habit_list, container, false);

        // set up recycler view
        recyclerView = (RecyclerView) v.findViewById(R.id.list_habit);

        if(recyclerView == null){
            System.out.println("\nCan't find list habit");
        }

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new HabitAdapter();
        ((HabitAdapter) mAdapter).addHabit(new ItemHabit("운동", "스쿼트 100회하기"));
        ((HabitAdapter) mAdapter).addHabit(new ItemHabit("책 읽기", "하루 10쪽이상 읽고 독후감 쓰기"));
        recyclerView.setAdapter(mAdapter);

        return v;
    }
}
