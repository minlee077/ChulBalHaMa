package com.example.middemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;



import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.middemo.MainActivity.StartWorker;

public class TestWorker extends Worker {
    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        Log.d("workManager", "TestWorker::doWork() called ");
        // Start new worker

        // 작업 수행
        showNotification();
        StartWorker(); // 재귀 생성

        //needs to be retried at a later time via Result.retry() 실패시 처리 필요 예상

        return Result.success();

    }

    private void showNotification()
    {
        Context context = getApplicationContext();
        //Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        // Intent being called when clicking on the notification
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(context.getString(R.string.app_name));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Create notification
        Notification notification = new NotificationCompat.Builder(context, "c1")
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
                .setSubText( "This is subtitle")
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
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager == null)
        {
            return;
        }

        // Here, messages get stacked
        //notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification);

        // Replace notifications
        notificationManager.notify(1, notification);
    }
}
