package com.example.widasrnarayanan.cidaas_sdk_androidv2.FCM;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Timber.d("push Message rcvd");
        if(remoteMessage.getNotification()!=null)
        {
            //Toast.makeText(this, "Success"+remoteMessage.getMessageType(), Toast.LENGTH_SHORT).show();
            Cidaas cidaas=new Cidaas(this);
            if(remoteMessage.getData().get("intermediate_verifiation_id")!=null && remoteMessage.getData().get("intermediate_verifiation_id")!="") {
                cidaas.setremoteMessage(remoteMessage.getData());
            }
            else
            {
               //Toast.makeText(this, "Null Intermediate Id", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
           // Toast.makeText(this, "Error FCM", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, "SS"+remoteMessage.getData(), Toast.LENGTH_SHORT).show();
    }

    }
