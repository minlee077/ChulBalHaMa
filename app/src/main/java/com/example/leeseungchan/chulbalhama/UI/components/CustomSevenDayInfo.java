package com.example.leeseungchan.chulbalhama.UI.components;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class CustomSevenDayInfo {
    private ArrayList<LinearLayout> dayInputs = new ArrayList<>();
    private View view;
    public CustomSevenDayInfo(View v){
        this.view = v;

        dayInputs.add((LinearLayout) v.findViewById(R.id.s_mon));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_the));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_wes));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_thr));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_fri));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_sat));
        dayInputs.add((LinearLayout) v.findViewById(R.id.s_sun));
        setDays();
    }

    private void setDays(){
        String[] dayName = {"월", "화","수", "목", "금", "토", "일"};
        for(int i = 0; i < 7; i++){
            TextView v = dayInputs.get(i).findViewById(R.id.day_name);
            v.setText(dayName[i]);
        }
    }

    public void setPlace(){
        for (int i = 0; i < 7; i++) {
            dayInputs.get(i).findViewById(R.id.day_place).setVisibility(View.VISIBLE);
            //@todo db 연동으로 글자 띄워와야함.
        }
    }

    private void setTimeData(){
        //@todo 시간 데이터랑 장소 데이터 받아와서 보여주어야함.
    }

    public void setTime(final ArrayList<Boolean> selectedDays, final ArrayList<Integer> time){
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
}
