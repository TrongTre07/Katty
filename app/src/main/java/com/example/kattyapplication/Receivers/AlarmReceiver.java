package com.example.kattyapplication.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.kattyapplication.R;
import com.example.kattyapplication.fragment.RemindFragment;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String content = intent.getStringExtra("content");


//        Resources res = content.getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.KattyLogo );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Katty_Remind")
//                .setLargeIcon(R.mipmap.KattyLogo)
                .setSmallIcon(R.mipmap.katty_logo)
                .setContentTitle("Nhắc Nhở")
                .setContentText(content)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());


    }
}
