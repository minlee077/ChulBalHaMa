package com.example.leeseungchan.chulbalhama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.FragmentTransitionSupport;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class MainActivity extends AppCompatActivity{

    private MainFragment mainFragment;
    private HistoryFragment historyFragment;
    private MySQLiteOpenHelper mydb;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    selectedFragment = mainFragment;
                    break;
                case R.id.navigation_history:
                    selectedFragment = historyFragment;
                    break;
                case R.id.navigation_test:
                    selectedFragment = mainFragment;
                    break;
                case R.id.navigation_menu:
                    selectedFragment = historyFragment;
                    break;
            }
            if(selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                        selectedFragment).commit();
                return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        historyFragment = new HistoryFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,mainFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
