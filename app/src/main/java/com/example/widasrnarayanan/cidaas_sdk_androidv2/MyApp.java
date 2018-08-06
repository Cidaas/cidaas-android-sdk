package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.app.Application;

import timber.log.Timber;


/**
 * Created by widasrnarayanan on 3/3/18.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            Timber.plant(new Timber.DebugTree());


    }
}
