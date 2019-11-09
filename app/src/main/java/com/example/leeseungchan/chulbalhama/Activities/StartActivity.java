package com.example.leeseungchan.chulbalhama.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.habit_list.HabitListFragment;
import com.example.leeseungchan.chulbalhama.UI.personal_info.PersonalInfoFragment;
import com.example.leeseungchan.chulbalhama.UI.start.StartFragment;

public class StartActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final StartFragment startFragment = new StartFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.start_fragment, startFragment).commitAllowingStateLoss();


    }

}
