package com.FarmPe.Farmer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.FarmPe.Farmer.Activity.LandingPageActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage message) {
        System.out.println("Notificationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbb"+message.getNotification().getBody());

            sendMyNotification(message.getNotification().getBody());



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendMyNotification(String message) {

//
//        String stop = "stop";
//       // registerReceiver(stopReceiver, new IntentFilter(stop));
//        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
//                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
//        // Create the persistent notification
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        String channelId = getString(R.string.app_name);
//        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
//        notificationChannel.setDescription(channelId);
//        notificationChannel.setSound(null, null);
//        notificationManager.createNotificationChannel(notificationChannel);
//        Notification notification = new Notification.Builder(this, channelId)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText("llllllllllll")
//                .setOngoing(true)
//                .setContentIntent(broadcastIntent)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setPriority(Notification.PRIORITY_DEFAULT)
//                .build();
//        startForeground(0, notification);



    }
}
