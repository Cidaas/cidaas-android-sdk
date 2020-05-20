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
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.widasrnarayanan.cidaas_sdk_androidv2.ConfigureActivity;
import com.example.widasrnarayanan.cidaas_sdk_androidv2.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;
import widas.cidaassdkv2.cidaasVerificationV2.View.CidaasVerification;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.PendingNotification.PushEntity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        //Timber.d("FCM push Message rcvd"+remoteMessage.getData());

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

            String BodyMessage="Empty Body Message";
            String TitleMessage="Empty Title Message";
            String ContentMessage="Empty Content Message";

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
            PushEntity pushNotificationEntity = new PushEntity();


            if (remoteMessage != null && remoteMessage.getData() != null && remoteMessage.getData().get("data") != null) {
                pushNotificationEntity =/*  //Timber
    implementation 'com.jakewharton.timber:timber:4.7.1'
*/ objectMapper.readValue(remoteMessage.getData().get("data").toString(), PushEntity.class);
                // Log.d("firebase ", "sendNotification: "+pushNotificationEntity.getStatusId());
            }
            //   boolean userPresent = Helper.checkIfUserExists(pushNotificationEntity);


            intent.putExtra("NotificationData", pushNotificationEntity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if(remoteMessage.getNotification()!=null) {

                TitleMessage = remoteMessage.getNotification().getTitle();
                BodyMessage = remoteMessage.getNotification().getBody();
            }
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
                                .setContentTitle("E"+TitleMessage)
                                .setContentText("E"+BodyMessage)
                                .setChannelId(CHANNEL_ID)
                                .setAutoCancel(true)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(BodyMessage))//Raja Changed to display Message in Notification
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
                        .setContentTitle("E"+TitleMessage)//Raja Changed to display Message in Notification
                        .setContentText("E"+BodyMessage)//Raja Changed to display Message in Notification
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

    @Override
    public void onNewToken(String token) {
        Timber.d( " FCM Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
         sendRegistrationToServer(token);
    }

    // [END refresh_token]
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        int version = Build.VERSION.SDK_INT;
        String name = Build.VERSION.CODENAME;
        String deviceName = Build.MODEL;
        name = name + ":" + version;

        String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);


        CidaasVerification.getInstance(getApplicationContext()).updateFCMToken(token);


    }
}