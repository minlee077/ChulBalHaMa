package com.example.leeseungchan.chulbalhama.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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

import com.example.leeseungchan.chulbalhama.Adpater.HabitAdapter;
import com.example.leeseungchan.chulbalhama.Adpater.PrepareAdapter;
import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.DayDialog;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.components.CustomSevenDayInfo;
import com.example.leeseungchan.chulbalhama.VO.HabitsVO;

import java.util.ArrayList;

public class HabitSpecActivity  extends AppCompatActivity {

    private HabitsVO habit;
    private DBHelper dbHelper;
    private Context context;
    private ArrayList<String> prepare = new ArrayList<>();
    private ArrayList<Boolean> days = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_habit);
        dbHelper = new DBHelper(this);
        habit = (HabitsVO) getIntent().getSerializableExtra("habit");
        context = this;

        // set up toolbar on top
        Toolbar toolbarMain = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMain);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /* habit name */
        LinearLayout habitName = findViewById(R.id.name_setting);
        TextView nameText = habitName.findViewById(R.id.item_name);
        nameText.setText(habit.getHabitName());
        Button nameChange = habitName.findViewById(R.id.button_change);
        nameChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setNameDialog(context, v, "이름 바꾸기");
            }
        });
        Button nameDelete = habitName.findViewById(R.id.button_delete);
        nameDelete.setVisibility(View.GONE);

        /* habit desc */
        LinearLayout habitDesc = findViewById(R.id.desc_setting);
        TextView descText = habitDesc.findViewById(R.id.item_name);
        descText.setText(habit.getHabitName());
        Button descChange = habitDesc.findViewById(R.id.button_change);
        descChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setNameDialog(context, v, "설명 바꾸기");
            }
        });
        Button descDelete = habitDesc.findViewById(R.id.button_delete);
        descDelete.setVisibility(View.GONE);

        /* prepare list*/
        RecyclerView prepares = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prepares.setLayoutManager(layoutManager);

        RecyclerView.Adapter prepareAdapter = new PrepareAdapter(prepare);
        prepares.setAdapter(prepareAdapter);

        setPrepare(habit.getPrepare());

        /* habit days */
        LinearLayout habitday = findViewById(R.id.day_setting);


        final CustomSevenDayInfo customSevenDayInfo =
                new CustomSevenDayInfo(findViewById(R.id.add_habit_day));
        customSevenDayInfo.setPlace();

        TextView dayText = habitday.findViewById(R.id.item_name);
        dayText.setText(habit.getHabitName());
        Button dayChange = habitday.findViewById(R.id.button_change);
        dayChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DayDialog customDialog = new DayDialog(HabitSpecActivity.this);

                customDialog.callFunction(days, customSevenDayInfo);
            }
        });
        Button dayDelete = habitday.findViewById(R.id.button_delete);
        dayDelete.setVisibility(View.GONE);

    }
    public void setNameDialog(Context context, View v, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        View viewInflated =
                LayoutInflater.from(context)
                        .inflate(R.layout.dialog_edit_text, (ViewGroup) v, false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String newName = input.getText().toString();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                db.execSQL("update habits set habit_name=\""+ newName + "\" where _id=1");
                db.close();
                Log.e("name input", "onClick:" + newName);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setPrepare(String prepare){
        String[] prepares = prepare.split(",");

        this.prepare.clear();

        for(int i=0; i<prepares.length; i++){
            this.prepare.add(prepares[i]);
        }

    }

}
