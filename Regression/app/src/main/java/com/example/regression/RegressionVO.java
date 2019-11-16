package com.example.regression;

import android.util.Log;

import java.io.Serializable;

public class RegressionVO implements Serializable {

    public double a;
    public double b;
    public double c;

    public void setParameters(double a, double b, double c)
    {
        this.a=a;
        this.b=b;
        this.c=c;
    }

    public double estimate(int day) {
        return a - b * Math.exp(-c * day);
    }
    public double estimate(double x) {
        return a - b * Math.exp(-c * x);
    }

    public int formationDateEstimate()
    {
        int date =0;
        double score=0.0;
        do{
            date++;
            score=estimate(date);
        }while(date<300 && date<21);
        return date;
    }
}
