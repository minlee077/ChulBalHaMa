package com.example.leeseungchan.chulbalhama.UI.components;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class CustomSevenDayInfo {
    private ArrayList<LinearLayout> dayInputs = new ArrayList<>();
    public CustomSevenDayInfo(View v){
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

    public void setTime(final ArrayList<Boolean> selectedDays, final ArrayList<Integer> time){
        System.out.println("is called here?");
        if(selectedDays.size() != 0) {
            System.out.println("or here?");
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
}
