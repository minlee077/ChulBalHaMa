package com.example.middemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    //정민
    static long countTime = 0;
    static long activityTimes[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int lastAction = -1;

    public static final String TAG = MainActivity.class.getSimpleName();
    protected GoogleApiClient googleApiClient; // activity recognition handle
    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;

    private int receiverCreation = 0;


    //정민 끝
    LocationListener gpsLocationListener;
    Button start_service;
    Button show_map;
    TextView locationText;

    MapFragment mapFragment;

    LocationManager lm;

    // 집 위치 정보
    public UserLocation homeLocation;
    // 학교 위치 정보
    public UserLocation schoolLocation;

    public UserLocation currentLocation;

    double lastLongitude;
    double lastLatitude;

    int tmpcount = 0;

    String userState = "HOME";
    String lastState = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        buildGoogleApiClient();
        googleApiClient.connect();//추가

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        get_location1 = (Button)findViewById(R.id.get_location1);
////        get_location2 = (Button)findViewById(R.id.get_location2);
////        get_location3 = (Button)findViewById(R.id.get_location3);
        start_service = (Button) findViewById(R.id.start_service);
        show_map = (Button) findViewById(R.id.show_map);
        locationText = (TextView) findViewById(R.id.locationText);
        currentLocation = new UserLocation(0, 0);


        homeLocation = new UserLocation(126.959992, 37.508427);
        schoolLocation = new UserLocation(126.957025, 37.503464);


        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
                }
            }
        });

        start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schoolLocation == null || homeLocation == null) {
                    Toast.makeText(getApplicationContext(), "먼저 위치를 등록 해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    getLocation();
                }
            }
        });
        // 위치 주기적 업데이트
        gpsLocationListener = new LocationListener() {
            String resultActivityString = "";

            public void onLocationChanged(Location location) {

                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                //정민 String

                longitude = formattingPoint(longitude);
                latitude = formattingPoint(latitude);
                if (distance(schoolLocation.getLatitude(), schoolLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(), "meter") < 50 && userState == "OnTheRoad") {
                    //TODO 이 때 시간이 시작시간보다 늦으면 지각이므로 알람을 안띄우게
                    userState = "SCHOOL";
                    Toast.makeText(getApplicationContext(), "학교에 거의 도착했습니다!", Toast.LENGTH_SHORT).show();

                    //정민시작
                    if (receiverCreation == 1)
                        receiverCreation = 0;
                    resultActivityString = removeActivityUpdates();

                    System.out.println("Activity Remove!!!!!!!!!!!");

                    //정민끝
                } else if (distance(homeLocation.getLatitude(), homeLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(), "meter") > 50 && userState == "HOME") {
                    userState = "OnTheRoad";
                    //Toast.makeText(getApplicationContext(), "집에서 나왔습니다!", Toast.LENGTH_SHORT).show();
                    //TODO 여기에 ~초이상 머무르면 뜨는 알람 설정 (사용자가 집에서 나왔다.)
                    //정민시작
                    requestActivityUpdates();

                    //정민끝


                } else if (distance(homeLocation.getLatitude(), homeLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(), "meter") < 50) {
                    //유저가 집에 있다.
                    userState = "HOME";
                    if (tmpcount == 3) {
                        Toast.makeText(getApplicationContext(), "책을 가지고 나가세요!", Toast.LENGTH_SHORT).show();
                    }
                    tmpcount++;
                }
                locationText.setText("위치정보 : " + provider + "\n" +
                        "현재 위치는 " + userState + "\n" +
                        "------------------고정 좌표----------------" + "\n" +
                        "집 위도 : " + homeLocation.getLongitude() + "\n" +
                        "집 경도 : " + homeLocation.getLatitude() + "\n\n" +
                        "학교 위도 : " + schoolLocation.getLongitude() + "\n" +
                        "학교 경도 : " + schoolLocation.getLatitude() + "\n\n" +
                        "지정 위치 위도 : " + currentLocation.getLongitude() + "\n" +
                        "지정 위치 경도 : " + currentLocation.getLatitude() + "\n\n" +

                        "실측위치, 위도 : " + longitude + "\n" +
                        "실측위치, 경도 : " + latitude + "\n" +
                        "오차 값 :" + distance(latitude, longitude, lastLatitude, lastLongitude, "meter") + "미터\n\n" +

                        "집 - 지정위치 거리 : " + distance(homeLocation.getLatitude(), homeLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(), "meter") + "\n" +
                        "학교 - 지정위치 거리 : " + distance(schoolLocation.getLatitude(), schoolLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(), "meter") + "\n" +
                        "집 - 학교사이 거리 : " + distance(homeLocation.getLatitude(), homeLocation.getLongitude(), schoolLocation.getLatitude(), schoolLocation.getLongitude(), "meter") + "\n" +
                        resultActivityString);

                lastLatitude = latitude;
                lastLongitude = longitude;

//                if(lastState=="" && userState=="HOME"){
//                    Toast.makeText(getApplicationContext(),"책 ㄱㄱ",Toast.LENGTH_SHORT).show();
//                } else if (lastState=="HOME" && userState=="OnTheRoad"){
//                    Toast.makeText(getApplicationContext(),"책 ??",Toast.LENGTH_SHORT).show();
//                } else if (lastState=="OnTheRoad" && userState=="SCHOOL"){
//                    Toast.makeText(getApplicationContext(),"책 읽음?",Toast.LENGTH_SHORT).show();
//                }
                lastState = userState;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

        };

        StartWorker();// worker 생성
    }

    //정민 시작


    public void requestActivityUpdates() {
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(this);

        Toast.makeText(this, "Request Generated. Connection success", Toast.LENGTH_SHORT).show();
        ActivityRecognition.ActivityRecognitionApi
                .requestActivityUpdates(googleApiClient, Constants.DETECTION_INTERVAL_IN_MILLISECONDS, getActivityPendingIntent())
                .setResultCallback(this);


        countTime = System.currentTimeMillis();
        lastAction = -1;
    }


    public String removeActivityUpdates() {//지정된 PendingIntent 에 대한 모든 활동 업데이트를 제거.
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return null;
        }
        Toast.makeText(this, "Remove Connection", Toast.LENGTH_SHORT).show();

        ActivityRecognition
                .ActivityRecognitionApi
                .removeActivityUpdates(googleApiClient, getActivityPendingIntent())
                .setResultCallback(this);

        String showingMsg = "";
        for (int i = 0; activityTimes.length > i; i++) {
            if (i == 6)
                continue;
            if (i == 3) {
                if (lastAction == i) {
                    activityTimes[i + 1] += activityTimes[i] + (System.currentTimeMillis() - countTime);
                } else {
                    activityTimes[i + 1] += activityTimes[i];
                }
                continue;
            }
            if (lastAction == i)
                showingMsg += getActivityString(i) + " : " + (activityTimes[i] + (System.currentTimeMillis() - countTime)) + "ms \n";
            else
                showingMsg += getActivityString(i) + " : " + activityTimes[i] + "ms \n";
            activityTimes[i] = 0;
        }

        showingMsg += "학교에 도착하셨습니다. 설문을 수행하시겠습니까? (Y/N)";
        return showingMsg;

    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {// 리시버
        protected static final String TAG = "receiver";
        private int firstTimeCall = 0;
        float[] confidenceOfActivity = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int maxIdx = 0;
        long accumulatedTime = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> updatedActivities = intent.getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);
            String strStatus = "";
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
                strStatus += "일정시간 이상 정지하고 계십니다. 독서(습관)를 수행하세요. \n";
                lastAction = maxIdx;
            }
            if(firstTimeCall==0)
            {
                strStatus += "집에서 나왔습니다!";
                firstTimeCall=1;
            }
            Log.e(TAG, strStatus);
            Toast.makeText(getApplicationContext(), strStatus, Toast.LENGTH_SHORT).show();//appcontext에 토스트
            //detectedActivities.setText(strStatus);


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

    public synchronized void buildGoogleApiClient() { // 빌더
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API) // Activity Recognition API request
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)//google API client instance 와 리스너를 연관시킴.
                .build();
    }

    public PendingIntent getActivityPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
    //정민끝

    public double formattingPoint(double target) {
        target *= 1000000;
        int tmp_int = (int) target;
        double result_double = (double) tmp_int / 1000000;
        return result_double;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if (unit == "meter") {
            dist = dist * 1609.344;
        }

        Log.d("거리 : ", "미터 " + dist);
        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void getLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();

            // 위치 정보 얻기 누른 시점 위도, 경도
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            longitude = formattingPoint(longitude);
            latitude = formattingPoint(latitude);
            lastLatitude = latitude;
            lastLongitude = longitude;

            Log.d("최초 위치 정보", "위도 : " + longitude + ", 경도 : " + latitude);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    3000,
                    0,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    3000,
                    0,
                    gpsLocationListener);
        }
    }

    public void registerLocation(UserLocation u) {
        this.currentLocation.setLatitude(formattingPoint(u.getLatitude()));
        this.currentLocation.setLongitude(formattingPoint(u.getLongitude()));
    }

    public void hideFragment() {
        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
    }


    static void StartWorker()
    {
        Log.d("mwm", "MainActivity::StartWorker()");


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //ENQUEUED -> RUNNING -> ENQUEUED. 정의에 따르면 주기적 작업은 되풀이되어야하므로 성공 또는 실패 상태에서 종료 될 수 없음.
        //명시적 취소 (혹은 retry)로만 종료 가능
        PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(TestWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        //종료
        //WorkRequest.Builder.setBackoffCriteria(BackoffPolicy, 시간, 시간단위).
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(TestWorker.class)
                .setConstraints(constraints)
                .addTag("myRequest")
                .setInitialDelay(2, TimeUnit.SECONDS)
                .build();

        //        .setBackoffCriteria(
        //                BackoffPolicy.LINEAR,
        //                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
        //                TimeUnit.MILLISECONDS)
        //Cancle :
        //WorkManager.cancelWorkById(UUID)
        //WorkManager.cancelWorkById(workRequest.getId());


        //.setInputData(imageData)
        //Data imageData = new Data.Builder
        //                .putString(Constants.KEY_IMAGE_URI, imageUriString)
        //                .build();
        //.addTag("xx")

        //work policy : https://developer.android.com/reference/androidx/work/ExistingWorkPolicy.html
        WorkManager.getInstance().enqueueUniqueWork("uniqueWork", ExistingWorkPolicy.REPLACE,workRequest);
    }

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Notification notification;
    NotificationManager manager;
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID) //CHANNEL_ID 채널에 지정한 아이디
                    .setContentTitle("background machine")
                    .setContentText("알림입니다")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true).build();

            manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }


}
