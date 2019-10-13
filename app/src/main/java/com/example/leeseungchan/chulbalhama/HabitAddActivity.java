package com.example.leeseungchan.chulbalhama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HabitAddActivity extends AppCompatActivity {


    public HabitAddActivity(){

    }

    private Button.OnClickListener addHabitBtnListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view){
            finish();
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        Button addHabitBtn = findViewById(R.id.complete_submit);
        addHabitBtn.setOnClickListener(addHabitBtnListener);
    }
}
