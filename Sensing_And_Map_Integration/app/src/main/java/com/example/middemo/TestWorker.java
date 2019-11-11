package com.example.middemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.middemo.MainActivity.StartWorker;
import static com.example.middemo.MainActivity.mainCreated;

public class TestWorker extends Worker {


    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Notification notification;
    NotificationManager manager;
    Context appContext;
    AppCompatActivity actContext;

//    MainActivity mainActivity;


    public static int called = 0;

    public TestWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        this.appContext = appContext;

    }
    //                        Intent intent = new Intent(appContext, PopUpScreen.class);
//                        intent.putExtra("data", "습관을 수행하세요");
//                        appContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    @NonNull
    @Override
    public Result doWork() {
        Log.d("workManager", "TestWorker::doWork() called ");
        // Start new worker

        // 작업 수행

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Run your task here
                String str = "BackGround Service running... " + called;
                Toast.makeText(appContext, str, Toast.LENGTH_SHORT).show();
                Log.e("workManager", "called : "+called);
                    if(!mainCreated)
                    {
                        Log.e("workManager", "if statements: "+called);
                        Toast.makeText(appContext, "앱이 종료되어 있음", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( appContext ,MainActivity.class);
                        appContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));



                    }else
                    {
                        Toast.makeText(appContext, "이미 앱이 켜져있음", Toast.LENGTH_SHORT).show();
                    }
//                     manager.notify(1, notification);
                }
        }, 0);
        //needs to be retried at a later time via Result.retry() 실패시 처리 필요 예상
        return Result.success();

    }

//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//
//            notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID) //CHANNEL_ID 채널에 지정한 아이디
//                    .setContentTitle("background machine")
//                    .setContentText("알림입니다")
//                    .setSmallIcon(R.mipmap.ic_launcher_round)
//                    .setOngoing(true).build();
//
//            manager = getApplicationContext().getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//    }

    public void showNotification() {
        Context context = getApplicationContext();
        //Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        // Intent being called when clicking on the notification
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(context.getString(R.string.app_name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Create notification
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                // Do not set this if we want silent notifications!
                .setDefaults(Notification.DEFAULT_SOUND)
                // be part of a group of notifications sharing the same key
                .setGroup(context.getString(R.string.app_name))
                // first line of text in the platform notification template
                .setContentTitle(context.getString(R.string.app_name))
                // "ticker" text, sent to accessibility services
                .setTicker(context.getString(R.string.app_name))
                // second line of text in the platform notification template
                .setContentText("This is description")
                // some additional information displayed in the notification, provided by the app
                .setSubText("This is subtitle")
                // Big messages
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Big message"))
                // small piece of additional information pertaining to this notification
                .setContentInfo("...")
                // Large icon on the notification content view
                //.setLargeIcon(icon)
                // This is also the icon shown in the status bar on top
                .setSmallIcon(R.drawable.ic_notify)
                // to be sent when the notification is clicked
                .setContentIntent(pendingIntent)
                // ongoing notifications cannot be dismissed by the user
                .setOngoing(false)
                // dismiss message after user clicks it
                .setAutoCancel(true)
                .build();

        // Send the notification. This will immediately display the notification icon in the notification bar.
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        if (notificationManager == null)
//        {
//            return;
//        }

        // Here, messages get stacked
        //notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification);

        // Replace notifications
//        notificationManager.notify(1, notification);
    }
}
