package com.example.ushnesha.activityrecognitionapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;

public class AddHabitAndUserActivity extends AppCompatActivity  {


    Button RequestActivityBtn;
    @Bind(R.id.habit_add_btn)
    Button addHabit;
    @Bind(R.id.user_add_btn)
    Button addUser;
    EditText start_coordinateView;
    EditText other_infoView;
    EditText habit_nameView;
    EditText descriptionView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_and_user);
        start_coordinateView = (EditText) findViewById(R.id.start_coordinate);
        other_infoView = (EditText) findViewById(R.id.other_info);
        habit_nameView = (EditText) findViewById(R.id.habit_name);
        descriptionView = (EditText) findViewById(R.id.description);


    }

    public void addHabitButtonHandler(View v){
        String habitName = habit_nameView.getText().toString();
        String description = descriptionView.getText().toString();
        if (habitName == null || habitName.equals("")) {
            Toast t = Toast.makeText(this, "습관명을 추가하세요.", Toast.LENGTH_SHORT);
            t.show();
        } else {
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into habits (habit_name, description) values(?,?)",new String[]{habitName, description} );
            db.close();
            finish();

        }
    }

    public void addUserButtonHandler(View v)
    {
        String startCoordinate = start_coordinateView.getText().toString();
        String otherInfo = other_infoView.getText().toString();

        if (startCoordinate == null || startCoordinate.equals("")) {
            Toast t = Toast.makeText(this, "위치를 입력하세요.", Toast.LENGTH_SHORT);
            t.show();
        }else{
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into user (starting_coordinate, other_Information) values(?,?)",new String[]{startCoordinate, otherInfo} );
            db.close();
            finish();
        }
    }


}
