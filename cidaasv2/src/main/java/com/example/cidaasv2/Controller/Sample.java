package com.example.cidaasv2.Controller;

import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Sample {
    public void onclick(String url)
    {
        String API_BASE_URL = "https://api.github.com/";

        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

      /*  Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create())
                        .client(
                                okHttpClient
                        );

        Retrofit retrofit = builder.build();*/


        Retrofit retrofit=new Retrofit.Builder()
                // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                .baseUrl(API_BASE_URL)//done Get Base URL
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        CidaassdkService service=new CidaassdkService();


        ICidaasSDKService client =   service.getInstance();

        client.getTenantInfo(url).enqueue(new Callback<TenantInfoEntity>() {
            @Override
            public void onResponse(Call<TenantInfoEntity> call, Response<TenantInfoEntity> response) {


                System.out.println("Success "+response.body().getData().getTenant_name());

            }

            @Override
            public void onFailure(Call<TenantInfoEntity> call, Throwable t) {

                System.out.println("Failed");
            }
        });

    }

}
