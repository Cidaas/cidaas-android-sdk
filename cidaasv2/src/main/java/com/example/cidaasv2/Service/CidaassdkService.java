package com.example.cidaasv2.Service;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Helper.Genral.DBHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

/**
 * Created by widasrnarayanan on 17/1/18.
 */

public class CidaassdkService {


    private static Context mcontext;


    public void setContext(Context context)
    {
        mcontext=context;
    }

    public ICidaasSDKService getInstance()
    {

        String baseurl= CidaasHelper.baseurl;

        if(baseurl==null || baseurl.equals(""))
        {
            baseurl="https://www.google.com";
        }



        ICidaasSDKService iCidaasSDKService=null;
        OkHttpClient okHttpClient=null;

        final String HEADER_USER_AGENT = "User-Agent";
     /*   final String HEADER_LOCATION_LATITUDE="Lat";
        final String HEADER_LOCATION_LONGITUDE="Long";*/
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .header(HEADER_USER_AGENT, createCustomUserAgent(originalRequest))
                                 /*.addHeader(HEADER_LOCATION_LATITUDE,getLat())
                                 .header(HEADER_LOCATION_LONGITUDE,getLong())*/
                                .build();
                        for (int i = 0; i < requestWithUserAgent.headers().size(); i++) {
                            Timber.d("User-Agent : "+String.format("%s: %s", requestWithUserAgent.headers().name(i), requestWithUserAgent.headers().value(i)));
                            DBHelper.getShared().setUserAgent("User-Agent : "+String.format("%s: %s", requestWithUserAgent.headers().name(i), requestWithUserAgent.headers().value(i)));
                        }

                        return chain.proceed(requestWithUserAgent);
                    }
                })
                .build();



        Retrofit retrofit=new Retrofit.Builder()
               // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                .baseUrl(baseurl)//done Get Base URL
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
          iCidaasSDKService=retrofit.create(ICidaasSDKService.class);
        return iCidaasSDKService;
    }

    private String createCustomUserAgent(Request originalRequest) {
        // App name can be also retrieved programmatically, but no need to do it for this sample needs
        String ua = "Cidaas-"+CidaasHelper.APP_NAME;
        String baseUa = System.getProperty("http.agent");
        if (baseUa != null) {
            ua = ua + "/" + CidaasHelper.APP_VERSION+"_"+ BuildConfig.VERSION_NAME+ " " + baseUa;
        }
        return ua;
    }

}
