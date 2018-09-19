package com.example.cidaasv2.Controller;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Repository.Tenant.TenantService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;




public class SampleTest {
    Sample sampleClass = new Sample();
    private final CountDownLatch latch = new CountDownLatch(1);


    Context context= RuntimeEnvironment.application;


    @Test
    public void testOnclick() throws Exception {




        final MockResponse response = new MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\n" +
                        "    \"success\": true,\n" +
                        "    \"status\": 200,\n" +
                        "    \"data\": {\n" +
                        "        \"tenant_name\": \"Raj Dev\",\n" +
                        "        \"allowLoginWith\": [\n" +
                        "            \"Raja\",\n" +
                        "            \"MOBILE\",\n" +
                        "            \"USER_NAME\"\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}");

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/tenantinfo/basic");




        server.enqueue(response);



       // sampleClass.onclick(domainURL+"public-srv/tenantinfo/basic");

        Retrofit retrofit=new Retrofit.Builder()
                // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                .baseUrl(domainURL)//done Get Base URL
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        //ICidaasSDKService client =  retrofit.create(ICidaasSDKService.class);

        CidaassdkService service=new CidaassdkService();


        ICidaasSDKService client =   service.getInstance();

        client.getTenantInfo(domainURL+"public-srv/tenantinfo/basic").enqueue(new Callback<TenantInfoEntity>() {
            @Override
            public void onResponse(Call<TenantInfoEntity> call, Response<TenantInfoEntity> response) {


                System.out.println("Success "+response.body().getData().getTenant_name());
                latch.countDown();

            }

            @Override
            public void onFailure(Call<TenantInfoEntity> call, Throwable t) {
               latch.countDown();
                System.out.println("Failed");
            }
        });
        latch.await();
        Thread.sleep(3000);


    }

    @Test
    public void testOnclickFail() throws Exception {




        final MockResponse response = new MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\n" +
                        "    \"success\": true,\n" +
                        "    \"status\": 200,\n" +
                        "    \"data\": {\n" +
                        "        \"tenant_name\": \"Raj Dev\",\n" +
                        "        \"allowLoginWith\": [\n" +
                        "            \"Raja\",\n" +
                        "            \"MOBILE\",\n" +
                        "            \"USER_NAME\"\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}");

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/tenantinfo/basic");
        server.enqueue(response);


        TenantService tenantService=new TenantService(context);
        tenantService.getTenantInfo(domainURL + "public-srv/tenantinfo/basic", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {

                System.out.println("Success and win"+result.getData().getTenant_name());
                latch.countDown();
            }

            @Override
            public void failure(WebAuthError error) {
                latch.countDown();
                System.out.println("Failed");
            }
        });
        // latch.await();
        Thread.sleep(3000);

        server.enqueue(new MockResponse().setResponseCode(401));

       // sampleClass.onclick("pprivate-srv/tenantinfo");
        Thread.sleep(3000);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme