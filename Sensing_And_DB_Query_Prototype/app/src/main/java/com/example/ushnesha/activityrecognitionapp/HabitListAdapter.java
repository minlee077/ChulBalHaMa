package com.example.ushnesha.activityrecognitionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


//Caller : HabitListActivity ( ListView구성 )
public class HabitListAdapter extends ArrayAdapter<HistoryVO> {
    Context context;
    ArrayList<HistoryVO> data;
    int resId; // 항목하나에 대한 layout resource Id

    public HabitListAdapter(Context context, int resId, ArrayList<HistoryVO> data) {
        super(context, resId);
        this.context = context;
        this.data = data;
        this.resId = resId;
    }

    // 항목 갯수
    @Override
    public int getCount() {
        return data.size();
    }

    //항목 하나를 구성하기위해 자동으로 call되는 함수
    // position : 항목 번호,
    // convertView : Null일시, getView는 최초 호출, Null이 아닐시에는 이전호출때 리턴된 View
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) // view 생성필요
        {
            //항목을 위한 layout xml파일 초기화
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);

            // 성능을 고려안한다면, 이 자리에서 findViewById 를 구현해도 되지만, 성능이슈 때문에 별도에 Wrapper를 호출하여 처리
            // 찾은 개체는 한번만 Find 하기 위함
            HabitListWrapper wrapper = new HabitListWrapper(convertView);
            convertView.setTag(wrapper);//리턴하고자하는 객체에 개발자데이터 추가


        }

        HabitListWrapper wrapper =(HabitListWrapper)convertView.getTag();

        TextView habitsView = wrapper.habitsView;//inner class에서 사용시 final 선언필요

        final HistoryVO vo = data.get(position);
        habitsView.setText("habit id: "+vo.habitId +" idle Time:"+ vo.idelTime +" id:"+ vo.id);
        return convertView;
    }
}