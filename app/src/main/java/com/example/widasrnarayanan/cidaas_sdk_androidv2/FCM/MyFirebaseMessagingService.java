package com.example.widasrnarayanan.cidaas_sdk_androidv2.FCM;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.PushNotificationEntity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.ConfigureActivity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Timber.d("FCM push Message rcvd"+remoteMessage.getData());

           sendNotification(remoteMessage);
    }

    public int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_fingerprint : R.mipmap.ic_launcher;
    }


    @TargetApi(26)
    public void createChannel(NotificationManager notificationManager) {
        String name = "FileDownload";
        String description = "Notifications for download status";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(name, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        notificationManager.createNotificationChannel(mChannel);
    }

    public void sendNotification(RemoteMessage remoteMessage) {

        try {


            // Log.d("Push da notification",remoteMessage.getData().get("data"));
            String messageBody = null;

            if (remoteMessage != null && remoteMessage.getData() != null && remoteMessage.getData().get("data") != null)
                messageBody = remoteMessage.getData().get("data").toString();
            // onclick of notification if user already logged in goto dashboard page otherwise switch to login page
            int requestID = (int) System.currentTimeMillis();

            Intent intent = new Intent(this, ConfigureActivity.class);
            // @JsonIgnoreProperties(ignoreUnknown = true)
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PushNotificationEntity pushNotificationEntity = new PushNotificationEntity();


            if (remoteMessage != null && remoteMessage.getData() != null && remoteMessage.getData().get("data") != null) {
                pushNotificationEntity = objectMapper.readValue(remoteMessage.getData().get("data").toString(), PushNotificationEntity.class);
                // Log.d("firebase ", "sendNotification: "+pushNotificationEntity.getStatusId());
            }
            //   boolean userPresent = Helper.checkIfUserExists(pushNotificationEntity);


            intent.putExtra("NotificationData", pushNotificationEntity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //  if (userPresent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "cidaas";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);


                Notification notification =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(getNotificationIcon())
                                .setContentTitle(remoteMessage.getNotification().getTitle())
                                .setContentText(remoteMessage.getNotification().getBody())
                                .setChannelId(CHANNEL_ID)
                                .setAutoCancel(true)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))//Raja Changed to display Message in Notification
                                .setContentIntent(pIntent)
                                .setCategory(Notification.CATEGORY_PROMO)
                                .setSound(soundUri).setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setDefaults(Notification.DEFAULT_ALL).build();


                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(mChannel);

// Issue the notification.
                mNotificationManager.notify(notifyID, notification);
            } else {
                //Notification Builder
                NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle(remoteMessage.getNotification().getTitle())//Raja Changed to display Message in Notification
                        .setContentText(remoteMessage.getNotification().getBody())//Raja Changed to display Message in Notification
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))//Raja Changed to display Message in Notification
                        .setContentIntent(pIntent)
                        .setCategory(Notification.CATEGORY_PROMO)
                        .setSound(soundUri).setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH);
                if (Build.VERSION.SDK_INT >= 21) {
                    notificationbuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationbuilder.build());
            }

            //   }


        } catch (Exception e) {
            Timber.e(e.getMessage());

        }
    }

}