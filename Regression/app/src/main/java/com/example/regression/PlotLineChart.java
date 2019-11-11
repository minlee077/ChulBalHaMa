package com.example.regression;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.vision.text.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
//implements OnChartGestureListener, OnChartValueSelectedListener
public class PlotLineChart extends AppCompatActivity  {


    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        mChart = (LineChart)findViewById(R.id.linechart);

        //        mChart.setOnChartGestureListener(PlotLineChart.this);// set on gesture
//        mChart.setOnChartValueSelectedListener(PlotLineChart.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValue = new ArrayList<>();


        yValue.add(new Entry(0,60f));
        yValue.add(new Entry(1,50f));
        yValue.add(new Entry(2,70f));
        yValue.add(new Entry(3,30f));
        yValue.add(new Entry(4,50f));
        yValue.add(new Entry(5,60f));
        yValue.add(new Entry(6,65f));
        LineDataSet set1 = new LineDataSet(yValue,"SRBAI Score");



        set1.setFillAlpha(150);//투명도
        set1.setColor(Color.RED);//라인 색상
        set1.setLineWidth(3f);//두께
        set1.setValueTextSize(10f);//데이터 지점 폰트크기


        LimitLine upper_limit = new LimitLine(70f, "최대 점수");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f,10f,0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(15f);

        LimitLine habit_gen_limit = new LimitLine(50f, "습관 형성 기준");
        habit_gen_limit.setLineWidth(4f);
        habit_gen_limit.enableDashedLine(10f,10f,0f);
        habit_gen_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        habit_gen_limit.setTextSize(15f);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(habit_gen_limit);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(25f);
        leftAxis.enableGridDashedLine(10f,10f,0);
        leftAxis.setDrawLimitLinesBehindData(true);

        //5분 30초 이어가기 https://www.youtube.com/watch?v=GM_T1gH4W2I
        //https://github.com/PhilJay/MPAndroidChart/wiki/DataSet-classes-in-detail wiki

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);




    }
}
