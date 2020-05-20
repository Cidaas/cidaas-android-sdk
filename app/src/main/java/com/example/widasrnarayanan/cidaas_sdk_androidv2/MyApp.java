package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.app.Application;
import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;

import timber.log.Timber;
import widas.cidaassdkv2.cidaasnativev2.View.CidaasNative;


/**
 * Created by widasrnarayanan on 3/3/18.
 */

public class MyApp extends Application {
    public static Context appContext;
    public static Cidaas cidaas;
    public static CidaasNative cidaasNative;
    @Override
    public void onCreate() {
        super.onCreate();
            Timber.plant(new Timber.DebugTree());
        appContext = this;
        appContext = getApplicationContext();
        cidaas = Cidaas.getInstance(appContext);
        cidaasNative = CidaasNative.getInstance(appContext);

    }


    public static Cidaas getCidaasInstance() {
        if (cidaas == null) {
            cidaas = Cidaas.getInstance(appContext);
        }
        return cidaas;
    }

    public static Cidaas getCidaasNativeInstance() {
        if (cidaasNative == null) {
            cidaasNative = CidaasNative.getInstance(appContext);
        }
        return cidaas;
    }

}
