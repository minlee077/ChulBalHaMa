package com.example.leeseungchan.chulbalhama.UI.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class CustomDayCheckBox{
    private ArrayList<CheckBox> boxes = new ArrayList<>();
    
    public CustomDayCheckBox(View view){
        boxes.add((CheckBox) view.findViewById(R.id.mon));
        boxes.add((CheckBox) view.findViewById(R.id.the));
        boxes.add((CheckBox) view.findViewById(R.id.wes));
        boxes.add((CheckBox) view.findViewById(R.id.thr));
        boxes.add((CheckBox) view.findViewById(R.id.fri));
        boxes.add((CheckBox) view.findViewById(R.id.sat));
        boxes.add((CheckBox) view.findViewById(R.id.sun));
    }
    
    public ArrayList<Boolean> getResult(){
        ArrayList<Boolean> result = new ArrayList<>();
        for(int i = 0; i< 7; i++){
            if(boxes.get(i).isChecked()){
                result.add(true);
            }else{
                result.add(false);
            }
        }
        return result;
    }

}
