package com.example.widasrnarayanan.cidaas_sdk_androidv2.FCM;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.example.cidaasv2.Controller.Cidaas;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "Refreshed token: " + refreshedToken);

        Cidaas cidaas=new Cidaas(this);
        cidaas.setFCMToken(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        int version = Build.VERSION.SDK_INT;
        String name = Build.VERSION.CODENAME;
        String deviceName = Build.MODEL;
        name = name + ":" + version;

        String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);


    }

}
