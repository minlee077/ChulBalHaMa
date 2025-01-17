package com.example.regression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.screen)
    TextView TextScreen;
    @Bind(R.id.view_Calculation)
    Button CalcBtn;
    @Bind(R.id.view_Result)
    Button PrintBtn;
    @Bind(R.id.view_Chart)
    Button ChartBtn;


    NonLinearRegression nonLinearRegression;
    RegressionVO regressionVO;
    DatasetVO datasetVO;

    ArrayList<Integer> days ;
    ArrayList<Double> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nonLinearRegression = new NonLinearRegression();
        regressionVO=new RegressionVO();
        datasetVO = new DatasetVO();

        days = new ArrayList<Integer>();
        scores = new ArrayList<Double>();

        for (int i = 1; i <= 12; i++)
            days.add(i);
        scores.add(1.0);scores.add(3.0);scores.add(4.0);scores.add(5.0);
        scores.add(5.0);scores.add(6.0);scores.add(7.0);scores.add(9.0);
        scores.add(10.0);scores.add(11.0);scores.add(14.0);scores.add(15.0);
//        scores.add(20.0);scores.add(24.0);scores.add(23.0);scores.add(25.0);
//        scores.add(26.0);scores.add(24.0);scores.add(20.0);scores.add(23.0);
//        scores.add(25.0);scores.add(26.0);scores.add(24.0);scores.add(23.0);
//        scores.add(25.0);scores.add(27.0);scores.add(26.0);scores.add(29.0);
//        scores.add(30.0);scores.add(31.0);scores.add(32.0);scores.add(30.0);
//        scores.add(33.0);scores.add(31.0);scores.add(34.0);scores.add(35.0);
//        scores.add(36.0);scores.add(39.0);scores.add(39.0);scores.add(39.0);
//        scores.add(40.0);scores.add(41.0);scores.add(41.0);scores.add(42.0);
//        scores.add(42.0);scores.add(40.0);scores.add(40.0);scores.add(39.0);
//        scores.add(40.0);scores.add(41.0);scores.add(41.0);scores.add(41.0);
//        scores.add(42.0);scores.add(42.0);scores.add(40.0);scores.add(40.0);
//        scores.add(39.0);scores.add(39.0);scores.add(39.0);scores.add(40.0);
//        scores.add(41.0);scores.add(41.0);scores.add(42.0);scores.add(41.0);
//        scores.add(40.0);scores.add(40.0);scores.add(42.0);scores.add(42.0);
//        scores.add(40.0);scores.add(40.0);scores.add(41.0);scores.add(41.0);
//        scores.add(42.0);scores.add(42.0);scores.add(40.0);
        datasetVO.setDaysNScores(days,scores);
    }

    public void viewChartHandler(View view) {
        Intent intent = new Intent(this,PlotLineChart.class);
        Bundle bundle = new Bundle();
        regressionVO.setParameters(
                nonLinearRegression.getParamenters()[0],
                nonLinearRegression.getParamenters()[1],
                nonLinearRegression.getParamenters()[2]
        );
        bundle.putSerializable("regressionModel",regressionVO);
        bundle.putSerializable("dataset",datasetVO);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void viewResultHandler(View view) {
        double score = nonLinearRegression.estimate(100);

        int date = nonLinearRegression.formationDateEstimate();
        Log.e("resultBtn", "resultClicked");
        Toast.makeText(this, "100일차 예측 점수 :" + score +
                "\n 예측되는 완성일 :" + date, Toast.LENGTH_LONG).show();
    }

    //viewChartHandler

    public void viewCalculateHandler(View view) {
        Log.e("calcBtn", "calcClicked");

        nonLinearRegression.optimize(days, scores);

        Toast.makeText(this, "Calc btn Test" + scores.size() + days.size(), Toast.LENGTH_LONG).show();
    }


}
