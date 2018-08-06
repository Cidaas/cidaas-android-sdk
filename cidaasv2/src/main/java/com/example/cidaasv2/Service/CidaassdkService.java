package com.example.cidaasv2.Service;

import android.content.SharedPreferences;

import com.example.cidaasv2.Helper.Genral.DBHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by widasrnarayanan on 17/1/18.
 */

public class CidaassdkService {

    public ICidaasSDKService getInstance()
    {

        ICidaasSDKService iCidaasSDKService=null;
        OkHttpClient okHttpClient=null;

        // HttpClient
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL")) //Todo Get Base URL
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
          iCidaasSDKService=retrofit.create(ICidaasSDKService.class);
        return iCidaasSDKService;
    }

}
