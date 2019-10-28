package com.example.ushnesha.activityrecognitionapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HabitListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;

    ArrayList<HabitsVO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_habit);

        listView= (ListView)findViewById(R.id.habit_list_view);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ReadHabitActivity.class);
        intent.putExtra("id",data.get(position).id);//primary key값
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from habits order by habit_name", null);

        data=new ArrayList<>();
        while(cursor.moveToNext()){
            HabitsVO vo = new HabitsVO();
            vo.id = cursor.getInt(0);
            vo.habitName = cursor.getString(1);
            vo.description = cursor.getString(2);
            vo.achievementRate = cursor.getString(3);
            data.add(vo);
        }
        db.close();

        HabitListAdapter adapter = new HabitListAdapter(this, R.layout.habit_list_item,data);
        listView.setAdapter(adapter);

    }
}
