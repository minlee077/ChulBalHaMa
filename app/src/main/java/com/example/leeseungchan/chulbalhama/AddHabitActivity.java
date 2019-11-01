package com.example.leeseungchan.chulbalhama;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.Adpater.DatePlaceAdapter;
import com.example.leeseungchan.chulbalhama.Adpater.prepareAdapter;
import com.example.leeseungchan.chulbalhama.UI.components.CustomCheckBox;
import com.google.android.gms.vision.text.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddHabitActivity extends AppCompatActivity implements View.OnClickListener {

    // prepare
    private ArrayList<String> prepares = new ArrayList<>();
    // day and place
    private ArrayList<ArrayList<Boolean>> days = new ArrayList<>();
    private ArrayList<String> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        TextView guideText;
        // set up toolbar on top
        Toolbar toolbarMain = findViewById(R.id.toolbar_add_habit);
        setSupportActionBar(toolbarMain);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /* prepare */
        final LinearLayout prepare = findViewById(R.id.prepare);

        // prepare TextView guide text
        guideText = prepare.findViewById(R.id.guide_for_selection);
        guideText.setText(R.string.guide_ask_prepare);

        // prepare recycler view
        final RecyclerView prepareRecycle = prepare.findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prepareRecycle.setLayoutManager(layoutManager);

        final RecyclerView.Adapter prepareAdapter = new prepareAdapter(prepares);
        prepareRecycle.setAdapter(prepareAdapter);

        Button prepareInputButton = prepare.findViewById(R.id.button_for_selection);
        prepareInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = prepare.findViewById(R.id.input_for_selection);
                if(text.getText().toString().length() < 1){
                    text.setHint("1자 이상 입력해주세요");
                    text.setHintTextColor(Color.RED);
                }else {
                    prepares.add(text.getText().toString());
                    text.setText("");
                    text.setHint("여기에 입력해 주세요.");
                    text.setHintTextColor(Color.GRAY);
                    prepareAdapter.notifyDataSetChanged();
                }
            }
        });


        /* day and place input*/
        final LinearLayout dayPlace = findViewById(R.id.day_place);

        // day and place TextView guide text
        guideText = dayPlace.findViewById(R.id.guide_for_selection);
        guideText.setText(R.string.guide_date_and_place_intro);

        EditText text = dayPlace.findViewById(R.id.input_for_selection);
        text.setVisibility(View.INVISIBLE);

        // set recycler view
        final RecyclerView dayPlaceRecycle = dayPlace.findViewById(R.id.list);

        RecyclerView.LayoutManager placeLayoutManager;
        placeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dayPlaceRecycle.setLayoutManager(placeLayoutManager);

        final RecyclerView.Adapter dayPlaceAdapter = new DatePlaceAdapter(days, places);
        dayPlaceRecycle.setAdapter(dayPlaceAdapter);

        // day and place Button to add
        Button dayPlaceInputButton = dayPlace.findViewById(R.id.button_for_selection);
        dayPlaceInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayPlaceDialog customDialog = new DayPlaceDialog(AddHabitActivity.this);

                customDialog.callFunction(days, places, dayPlaceAdapter);
            }
        });

        Button store = findViewById(R.id.store_habit);
        store.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store_habit:
                //@todo store habits.
                finish();
                break;
        }
    }
}
