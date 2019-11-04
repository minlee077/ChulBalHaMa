package com.example.leeseungchan.chulbalhama.UI.components;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.LocationInfoActivity;
import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class CustomSevenDayInfo {
    private ArrayList<LinearLayout> dayInputs = new ArrayList<>();
    private View view;
    private ArrayList<String> dayName = new ArrayList<>();

    public CustomSevenDayInfo(View v){
        this.view = v;

        dayInputs.add((LinearLayout) v.findViewById(R.id.s_mon));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_the));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_wes));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_thr));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_fri));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_sat));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_sun));
        dayName.add("월");
        dayName.add("화");
        dayName.add("수");
        dayName.add("목");
        dayName.add("금");
        dayName.add("토");
        dayName.add("일");
        setDays();
        setTimeData();
    }

    private void setDays(){
        for(int i = 0; i < 7; i++){
            TextView v = dayInputs.get(i).findViewById(R.id.day_name);
            v.setText(dayName.get(i));
        }
    }

    public void setTempTimeData(ArrayList<String> timeData){
        for(int i = 0; i < timeData.size(); i++){
            TextView temp;
            if(timeData.get(i) != null){
                temp = dayInputs.get(i).findViewById(R.id.day_time);
                temp.setText(timeData.get(i));
            }
        }
    }

    public void setPlace(){
        for (int i = 0; i < 7; i++) {
            dayInputs.get(i).findViewById(R.id.day_place).setVisibility(View.VISIBLE);
            //@todo db 연동으로 글자 띄워와야함.
        }
    }

    private void setTimeData(){
        DBHelper helper = new DBHelper(view.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select departure_time from day_of_week ", new String[]{});

        for(int i = 0; i < 7; i++){
            cursor.moveToNext();
            String time = cursor.getString(0);
            if(time != null) {
                TextView temp = dayInputs.get(i).findViewById(R.id.day_time);
                temp.setText(time);
                Log.e("DB add", " time: " + time);
            }
        }
        db.close();
    }

    public void setTime(final ArrayList<Boolean> selectedDays,
                        final ArrayList<Integer> time){
        if(selectedDays.size() != 0) {
            String timeInput = time.get(0).toString() + ":" + time.get(1).toString();
            for (int i = 0; i < 7; i++) {
                TextView v;

                if (selectedDays.get(i)) {
                    v = dayInputs.get(i).findViewById(R.id.day_time);
                    v.setText(timeInput);
                }
            }
        }
    }
    public void pickDay(final ArrayList<Boolean> selectedDays){
        if(selectedDays.size() != 0) {
            for (int i = 0; i < 7; i++) {
                if (selectedDays.get(i)) {
                    dayInputs.get(i).setBackgroundColor(
                            view.getResources().getColor(R.color.colorTertiary));
                }else{
                    dayInputs.get(i).setBackgroundColor(
                            view.getResources().getColor(R.color.common_google_signin_btn_text_dark_default)
                    );
                }
            }
        }
    }

    public void storeTimeToDays(ArrayList<Boolean> selectable, ArrayList<String> times){
        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sqlToGetDestID = "(select _id from destinations order by _id DESC limit 1)";
        String sqlToUpdate = "update day_of_week set ";
        for(int i = 0; i < 7; i++){
            Log.e("times", i +"and"+times.get(i));
            if(selectable.get(i)){
                String time = "\"" + times.get(i) +"\"";
                db.execSQL(sqlToUpdate + "departure_time=" + time + ", destination_id="
                        + sqlToGetDestID + " where day=" + "\""+dayName.get(i)+ "\"");
            }
        }
        db.close();
    }

    public void setTimeToTime(ArrayList<String> times){
        for(int i = 0; i < 7; i++){
            TextView time = dayInputs.get(i).findViewById(R.id.day_time);
            String actualTime = time.getText().toString();
            if(actualTime != null){
                times.add(actualTime);
            }else{
                times.add(null);
            }
        }
    }
}
