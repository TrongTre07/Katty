package com.example.kattyapplication.Receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.kattyapplication.MainActivity;
import com.example.kattyapplication.R;

public class AlarmReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String content = intent.getStringExtra("content");

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_IMMUTABLE);

//        RemoteViews notificationLayout = new RemoteViews(context, R.layout.custom_notification_small);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Katty_Remind")
//                .setLargeIcon(R.mipmap.KattyLogo)
                .setSmallIcon(R.mipmap.katty_logo)
                .setContentTitle("Nhắc Nhở")
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());


    }

}
