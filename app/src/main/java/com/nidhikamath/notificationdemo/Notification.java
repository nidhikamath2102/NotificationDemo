package com.nidhikamath.notificationdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import com.nidhikamath.notificationdemo.services.DownloadImageTask;
import com.nidhikamath.notificationdemo.services.FetchImageCallback;

import androidx.core.app.NotificationCompat;

public class Notification {

    private Context context;
    private static Notification instance;
    private final int NOTIFICATION_ID = 21;
    private final String NOTIFICATION_CHANNEL_ID = "21";
    private final String NOTIFICATION_CHANNEL_NAME = "NotificationDemo";

    private Notification(Context context) {
        this.context = context;
    }

    public static synchronized Notification getInstance(Context context) {
        if (instance == null) instance = new Notification(context);
        return instance;
    }

    public void setNotification(String title, String description, Bitmap bitMap){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setName(title);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher)
                //.setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitMap))
                .setContentText(description)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    public void cancelNotification() {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void onDestroy() {
        if(context != null) {
            context = null;
        }
    }
}
