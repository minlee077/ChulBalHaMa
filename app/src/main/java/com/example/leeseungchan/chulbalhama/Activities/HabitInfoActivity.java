package com.example.leeseungchan.chulbalhama.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.Adpater.PrepareAdapter;
import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.DayDialog;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.components.CustomSevenDayInfo;
import com.example.leeseungchan.chulbalhama.UI.habit.HabitChangeFragment;
import com.example.leeseungchan.chulbalhama.UI.habit.HabitHistoryFragment;
import com.example.leeseungchan.chulbalhama.VO.HabitsVO;

import java.util.ArrayList;

public class HabitInfoActivity extends AppCompatActivity {

    private HabitsVO habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_habit);
        habit = (HabitsVO) getIntent().getSerializableExtra("habit");
        int type = getIntent().getIntExtra("type", 0);
        
        Bundle bundle = new Bundle();
        bundle.putSerializable("habit", habit);
        

        // set up toolbar on top
        Toolbar toolbarMain = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMain);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setTitle(habit.getHabitName());
    
        HabitChangeFragment habitChangeFragment = HabitChangeFragment.newInstance(bundle);
        HabitHistoryFragment habitHistoryFragment = HabitHistoryFragment.newInstance(bundle);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(type == 0) {
            transaction.replace(R.id.nav_host_fragment, habitChangeFragment)
                .commitAllowingStateLoss();
        
        } else {
            transaction.replace(R.id.nav_host_fragment, habitHistoryFragment)
                .commitAllowingStateLoss();
        
        }
    }
    
    
    public void setTitle(String id){
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText(id);
    }

}
