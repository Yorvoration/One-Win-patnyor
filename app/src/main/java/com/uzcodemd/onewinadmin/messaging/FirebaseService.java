package com.uzcodemd.onewinadmin.messaging;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uzcodemd.onewinadmin.MainActivity;
import com.uzcodemd.onewinadmin.R;

import java.util.Random;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseService extends FirebaseMessagingService {
    private final String CHANNEL_ID = "channel_id";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationid = new Random().nextInt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent intent1 = PendingIntent.getActivities(this,0,new Intent[]{intent},PendingIntent.FLAG_ONE_SHOT);
        Notification notification;

            notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setSmallIcon(R.drawable.logonetkash)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();
        manager.notify(notificationid,notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager manager) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channelName",NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My description");
        channel.enableLights(true);
        channel.setLightColor(Color.WHITE);
        manager.createNotificationChannel(channel);

    }
}
