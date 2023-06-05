package com.mradkingshop.vsssn.admin.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.Splash;
import com.mradkingshop.vsssn.user.activity.User_Donation_Payment_act;
import com.mradkingshop.vsssn.user.activity.User_splash;

public class PushNotificationService extends FirebaseMessagingService {

    @SuppressLint("NewApi")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        String value = remoteMessage.getData().get("key1");

        String CHANNEL_ID = "MESSAGE";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Message Notification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.icon)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());
        super.onMessageReceived(remoteMessage);


        Intent intent=new Intent(getApplicationContext(), User_splash.class);

        intent.putExtra("key",value);



        PendingIntent pendingIntent= PendingIntent.getActivity(
                getApplicationContext(),100,intent,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder=

                new NotificationCompat.Builder(getApplicationContext(), User_Donation_Payment_act.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentIntent(pendingIntent)
                        .setContentText(text)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getApplicationContext());

        notificationManagerCompat.notify(1,builder.build());




    }
}
