package com.buns.mgadmin.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.buns.mgadmin.Activities.HomeActivity.HomeActivity;

import com.buns.mgadmin.R;


public class NotificationManager {
    private final Context context;
    private static NotificationManager instance;

    public static NotificationManager getInstance(Context context) {
        if (instance == null || context != null) {
            instance = new NotificationManager(context);
        }
        return instance;
    }

    private NotificationManager(@NonNull Context context) {
        this.context = context;
    }

    public void createNotification(final int size, final int id, @NonNull String desc) {
        PendingIntent intent = getPendingIntent(context);
        Notification notification = createNotification(context, size +" "+ desc).setContentIntent(intent).build();
        NotificationManagerCompat.from(context).notify(id, notification);
    }


    private NotificationCompat.Builder createNotification(@NonNull Context context, @NonNull String content) {
        return new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setContentTitle("New Transaction")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX);
    }

    private PendingIntent getPendingIntent(@NonNull Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

}
