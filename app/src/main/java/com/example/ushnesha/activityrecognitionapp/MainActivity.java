package com.example.ushnesha.activityrecognitionapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {


    static long countTime = 0;
    static long activityTimes[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int lastAction = -1;

    public static final String TAG = MainActivity.class.getSimpleName();
    protected GoogleApiClient googleApiClient; // activity recognition handle
    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;

    @Bind(R.id.detectedActivities)
    TextView detectedActivities;
    @Bind(R.id.remove_activity_updates_button)
    Button RemoveActivityBtn;
    @Bind(R.id.request_activity_updates_button)
    Button RequestActivityBtn;
    @Bind(R.id.view_habits)
    Button ViewActivityBtn;
    @Bind(R.id.fab)
    Button FabBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        buildGoogleApiClient();
        googleApiClient.connect();//추가

    }

    public synchronized void buildGoogleApiClient() { // 빌더
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API) // Activity Recognition API request
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)//google API client instance 와 리스너를 연관시킴.
                .build();
    }

    public void requestActivityUpdatesButtonHandler(View view) {
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Request Generated. Connection success", Toast.LENGTH_SHORT).show();


        System.out.println(this);

        ActivityRecognition.ActivityRecognitionApi
                .requestActivityUpdates(googleApiClient, Constants.DETECTION_INTERVAL_IN_MILLISECONDS, getActivityPendingIntent())
                .setResultCallback(this);
        RequestActivityBtn.setEnabled(false);
        RemoveActivityBtn.setEnabled(true);
        countTime = System.currentTimeMillis();
        lastAction = -1;
    }

    public void viewHabitListButtonHandler(View view) {
        Intent intent = new Intent(this, HabitListActivity.class);
        startActivity(intent);

    }

    public void addHabitButtonHandler(View view) {

        Intent intent = new Intent(this, AddHabitAndUserActivity.class);
        startActivity(intent);
    }

    public void removeActivityUpdatesButtonHandler(View view) {//지정된 PendingIntent 에 대한 모든 활동 업데이트를 제거.
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Remove Connection", Toast.LENGTH_SHORT).show();

        ActivityRecognition
                .ActivityRecognitionApi
                .removeActivityUpdates(googleApiClient, getActivityPendingIntent())
                .setResultCallback(this);
        RequestActivityBtn.setEnabled(true);
        RemoveActivityBtn.setEnabled(false);

        String showingMsg = "";
        for (int i = 0; activityTimes.length > i; i++) {
            if (i == 6)
                continue;
            if (lastAction == i)
                showingMsg += getActivityString(i) + " : " + (activityTimes[i] + (System.currentTimeMillis() - countTime)) + "ms \n";
            else
                showingMsg += getActivityString(i) + " : " + activityTimes[i] + "ms \n";
            activityTimes[i] = 0;
        }

        detectedActivities.setText(showingMsg);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {  // 모든게 잘동작할때, normal activity recognition call
        Log.e(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionSuspended(int i) { //  sensor 커넥션에 문제가 생길시에 호출
        Log.e(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { // google play connection 에 실패할시에 호출
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode()= " + connectionResult.getErrorCode());
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Log.e(TAG, "Successfully added activity detection.");
        } else {
            Log.e(TAG, "Error adding or removing activity detection.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {// 리시버
        protected static final String TAG = "receiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> updatedActivities = intent.getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);
            String strStatus = "";
            float[] confidenceOfActivity = new float[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            int maxIdx = 0;
            long accumulatedTime = 0;
            for (DetectedActivity activity : updatedActivities) {
                int activityType = activity.getType(); // activity 타입 추출
                if (activityType == 6)
                    continue;
                confidenceOfActivity[activityType] = activity.getConfidence(); // confidence 기록
                maxIdx = confidenceOfActivity[activityType] > confidenceOfActivity[maxIdx] ? activityType : maxIdx; // 더크다면 큰걸로 기록
                strStatus += getActivityString(activity.getType()) + activity.getConfidence() + "%\n";
            }
            if (maxIdx != -1) {
                accumulatedTime = System.currentTimeMillis() - countTime;
                countTime = System.currentTimeMillis();
                strStatus += "Possibly what you act : " + getActivityString(maxIdx) + confidenceOfActivity[maxIdx] + "% \n";
                if (lastAction != -1)
                    activityTimes[lastAction] += accumulatedTime;
                if (
                        (lastAction == 3 || lastAction == 0) &&  //이전 행동이 멈춰있거나 교통수단에 탑승한 상태 일떄
                                (maxIdx == 3 || maxIdx == 0)  //멈춰있거나 교통수단에 탑승한 상태 일때.
                )
                    strStatus += "It's been 10 seconds since you stopped. Do your habits \n";
                lastAction = maxIdx;
            }


            Log.e(TAG, strStatus);
            detectedActivities.setText(strStatus);

        }
    }

    public String getActivityString(int detectedActivityType) {
        Resources resources = this.getResources();
        switch (detectedActivityType) {
            case DetectedActivity
                    .IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity
                    .ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity
                    .ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity
                    .RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity
                    .STILL:
                return resources.getString(R.string.still);
            case DetectedActivity
                    .TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity
                    .UNKNOWN:
                return resources.getString(R.string.unknown);
            case DetectedActivity
                    .WALKING:
                return resources.getString(R.string.walking);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }

    public PendingIntent getActivityPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null)
            googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
    }
}
