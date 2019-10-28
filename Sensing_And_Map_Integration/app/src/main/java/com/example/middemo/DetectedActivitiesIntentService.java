package com.example.middemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class DetectedActivitiesIntentService extends IntentService {

    public static final String TAG="DetectedActivityIS";

    public DetectedActivitiesIntentService() {
        super(TAG);
    }
    public DetectedActivitiesIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ActivityRecognitionResult result= ActivityRecognitionResult.extractResult(intent);
        Intent localIntent=new Intent(Constants.BROADCAST_ACTION);

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        Log.e(TAG, "activities detected");

        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }
}
