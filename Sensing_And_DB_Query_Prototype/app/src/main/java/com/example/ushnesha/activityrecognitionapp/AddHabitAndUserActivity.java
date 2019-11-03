package com.example.ushnesha.activityrecognitionapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;

public class AddHabitAndUserActivity extends AppCompatActivity  {


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
            // 입력 완료 DB 추가 로직
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into habits (habit_name, description) values(?,?)",new Object[]{habitName, description} );
            Log.e("DB", "habit name : "+habitName+" desc:"+description);
            db.close();
            finish();

        }
    }

    public void addUserButtonHandler(View v)
    {
        String achi = start_coordinateView.getText().toString();
        String hName = start_coordinateView.getText().toString();
        String depT = other_infoView.getText().toString();
        String arrT =  other_infoView.getText().toString();
        String idleT = other_infoView.getText().toString();
        int hId=-1;
        if (achi == null || achi.equals("")) {
            Toast t = Toast.makeText(this, "위치를 입력하세요.", Toast.LENGTH_SHORT);
            t.show();
        }else{
            // 입력 완료 DB 추가로직
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select _id from habits where ?=habit_name", new String[]{hName});
            Log.e("DB add", " habit name: "+hName);

            while(cursor.moveToNext()){
                hId=cursor.getInt(0);
                Log.e("DB HistAdd", "found habit id : "+hId+" habit name:"+hName);

            }
            if(hId==-1) // habit에 없는 habit 이름 입력시에
            {
                Toast t = Toast.makeText(this, "등록되지 않은 습관입니다.", Toast.LENGTH_SHORT);
                t.show();
                db.close();
            }
            else{
                db.execSQL("insert into history (achievement, departure_time,arrival_time,idle_time, habit_id ) " +
                        "values(?,?,?,?,?)",new Object[]{achi,depT, arrT,idleT,hId} );
                Log.e("DB HistAdd", "DB insertion Success : "+hId+" habit name:"+hName);
                db.close();
                finish();

            }


        }
    }


}
