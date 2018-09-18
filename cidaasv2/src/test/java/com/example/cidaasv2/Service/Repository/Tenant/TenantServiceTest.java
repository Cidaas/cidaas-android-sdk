package com.example.cidaasv2.Service.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoDataEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.util.AuthenticationAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(RobolectricTestRunner.class)
public class TenantServiceTest {

    CidaassdkService service;
    Context context;
    TenantService tenantService;

    AuthenticationAPI mockAPI;



    @Captor
    private ArgumentCaptor<Result<TenantInfoEntity>> cb;

    @Before
    public void setUp() throws Exception{
        context= RuntimeEnvironment.application;
        MockitoAnnotations.initMocks(this);
        tenantService=new TenantService(context);
        mockAPI=new AuthenticationAPI();

    }

    @Test
    public void testGetShared() throws Exception {
        TenantService result = TenantService.getShared(context);
        Assert.assertThat(new TenantService(context), samePropertyValuesAs(result));
    }


    @Test
    public void testGetTenantInfo() throws Exception {

        tenantService.getTenantInfo("baseurl", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Timber.e(result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e(error.ErrorMessage);
            }
        });
    }

    @Test
    public void testGetTenantInfoForEmptyBaseurl() throws Exception {
       TenantService sam=new TenantService(context);

        tenantService.getTenantInfo("", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Timber.e(result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e(error.ErrorMessage);
            }
        });
    }

    @Test
    public void testGetTenantInfoForSuccess() throws Exception {
        TenantService tenantService123=Mockito.mock(TenantService.class);

        TenantInfoEntity tenantInfoEntity=new TenantInfoEntity();
        TenantInfoDataEntity tenantInfoDataEntity=new TenantInfoDataEntity();
        tenantInfoDataEntity.setTenant_name("TenantName");
        tenantInfoEntity.setData(tenantInfoDataEntity);
        tenantInfoEntity.setStatus(200);
        tenantInfoEntity.setSuccess(true);


        tenantService123.getTenantInfo("https://nightlybuild.cidaas.de", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
             Assert.assertEquals("TenantName",result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
        Mockito.verify(tenantService123).getTenantInfo(eq("https://nightlybuild.cidaas.de"),cb.capture());


         cb.getValue().success(tenantInfoEntity);


    }


    @Test
    public void testGetTenantInfoForFailure() throws Exception {
        try{
        final MockResponse response = new MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("\"{\n" +
                        "    \"success\": true,\n" +
                        "    \"status\": 401,\n" +
                        "    \"error\": {\n" +
                        "        \"tenant_name\": \"Cidaas Developers\",\n" +
                        "\"errorMessage\":\"Mustnot be null\""+
                        "        \"allowLoginWith\": [\n" +
                        "            \"EMAIL\",\n" +
                        "            \"MOBILE\",\n" +
                        "            \"USER_NAME\"\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}\"");

        MockWebServer server = new MockWebServer();
            server.start(2716);
        String domainURL= server.url("").toString();


            server.shutdown();

        server.url("/public-srv/tenantinfo/basic");



        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/public-srv/tenantinfo/basic")){
                    return new MockResponse().setResponseCode(200).setBody("\"{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"tenant_name\": \"Cidaas Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}\"");
                } else if (request.getPath().equals("v1/check/version/")){
                    return response;
                } else if (request.getPath().equals("/v1/profile/info")) {
                    return new MockResponse().setResponseCode(200).setBody("{\\\"info\\\":{\\\"name\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);

        Cidaas.baseurl="https://"+server.getHostName()+":2716/";
        //server.start();


        //  RecordedRequest request=server.takeRequest();
        //       request.getBody();
        //  Assert.assertEquals("/public-srv/tenantinfo/basic",request.getRequestLine());

        tenantService.getTenantInfo("https://localhost:2716", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Assert.assertEquals("Cidaas developer",result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {
                //   Assert.assertEquals("Cidaas developer",error.getErrorMessage());
            }
        });




        server.getHostName();

    }
       catch (Exception e)
    {
        Assert.assertEquals(e.getMessage(),true,true);
        Assert.assertFalse(e.getMessage(),true);
    }


}

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Test
    public void testWeb() throws Exception{
        try{



            mockAPI.getDomain();
            mockAPI.willReturnTenant();
            Cidaas.baseurl=removeLastChar(mockAPI.getDomain());


            service=new CidaassdkService();

            Retrofit retrofit=new Retrofit.Builder()
                    // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                    .baseUrl(Cidaas.baseurl)//done Get Base URL
                    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();


            ICidaasSDKService cidaasSDKService =retrofit.create(ICidaasSDKService.class);
            cidaasSDKService.getTenantInfo(Cidaas.baseurl+"/public-srv/tenantinfo/basic").enqueue(new Callback<TenantInfoEntity>() {
                @Override
                public void onResponse(Call<TenantInfoEntity> call, retrofit2.Response<TenantInfoEntity> response) {
                    String res=response.body().toString();
                    Assert.assertEquals("Fine","Cidaas",response.body().getData().getTenant_name());

                }

                @Override
                public void onFailure(Call<TenantInfoEntity> call, Throwable t) {
                    String res=t.toString();
                }
            });


            /*tenantService.getTenantInfo(removeLastChar(mockAPI.getDomain()), new Result<TenantInfoEntity>() {
                @Override
                public void success(TenantInfoEntity result) {
                    Assert.assertEquals("Cidaas developer",result.getData().getTenant_name());
                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer",error.getErrorMessage());
                }
            });*/
        }
        catch (Exception e){
            Timber.e(e.getMessage());
        }
    }

    @Test
    public void testWebClient() throws  Exception{

       try {
           Timber.e("Success");

           final MockResponse response = new MockResponse().setResponseCode(204)
                   .addHeader("Content-Type", "application/json; charset=utf-8")
                   .setBody("\"{\n" +
                           "    \"success\": true,\n" +
                           "    \"status\": 200,\n" +
                           "    \"data\": {\n" +
                           "        \"tenant_name\": \"Cidaas Developers\",\n" +
                           "        \"allowLoginWith\": [\n" +
                           "            \"EMAIL\",\n" +
                           "            \"MOBILE\",\n" +
                           "            \"USER_NAME\"\n" +
                           "        ]\n" +
                           "    }\n" +
                           "}\"");

           MockWebServer server = new MockWebServer();
           String domainURL= server.url("").toString();
           server.url("/public-srv/tenantinfo/basic");



        /*   final Dispatcher dispatcher = new Dispatcher() {

               @Override
               public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                   if (request.getPath().equals("/public-srv/tenantinfo/basic")){
                       return new MockResponse().setResponseCode(202).setBody("\"{\n" +
                               "    \"success\": true,\n" +
                               "    \"status\": 202,\n" +
                               "    \"data\": {\n" +
                               "        \"tenant_name\": \"Cidaas Developers\",\n" +
                               "        \"allowLoginWith\": [\n" +
                               "            \"EMAIL\",\n" +
                               "            \"MOBILE\",\n" +
                               "            \"USER_NAME\"\n" +
                               "        ]\n" +
                               "    }\n" +
                               "}\"");
                   } else if (request.getPath().equals("v1/check/version/")){
                       return response;
                   } else if (request.getPath().equals("/v1/profile/info")) {
                       return new MockResponse().setResponseCode(200).setBody("{\\\"info\\\":{\\\"name\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}");
                   }
                   return new MockResponse().setResponseCode(404);
               }
           };*/
         //  server.setDispatcher(dispatcher);
           server.enqueue(response);
           Cidaas.baseurl=domainURL;

           /*tenantService.getTenantInfo(removeLastChar(Cidaas.baseurl), new Result<TenantInfoEntity>() {
               @Override
               public void success(TenantInfoEntity result) {
                   Assert.assertEquals("Cidaas developer",result.getData().getTenant_name());
               }

               @Override
               public void failure(WebAuthError error) {
                //   Assert.assertEquals("Cidaas developer",error.getErrorMessage());
               }
           });*/



           // final String responsebody=stringresponse(new OkHttpClient(),Cidaas.baseurl+"public-srv/tenantinfo/basic");

           Retrofit retrofit=new Retrofit.Builder()
                   // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                   .baseUrl(Cidaas.baseurl)//done Get Base URL
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   .addConverterFactory(JacksonConverterFactory.create())
                   .client(new OkHttpClient().newBuilder().build())
                   .build();


           ICidaasSDKService cidaasSDKService =retrofit.create(ICidaasSDKService.class);
           cidaasSDKService.getTenantInfo(Cidaas.baseurl+"public-srv/tenantinfo/basic").enqueue(new Callback<TenantInfoEntity>() {
               @Override
               public void onResponse(Call<TenantInfoEntity> call, retrofit2.Response<TenantInfoEntity> response) {

                   if(response.isSuccessful())
                   {
                       Timber.e("Success");
                   }
                   String res=response.body().toString();
                   Assert.assertEquals("Cidaas",response.body().getData().getTenant_name());

               }

               @Override
               public void onFailure(Call<TenantInfoEntity> call, Throwable t) {
                   String res=t.toString();
               }
           });
           Timber.e("Success");

       }
       catch (Exception e)
       {
           Assert.assertEquals(e.getMessage(),true,true);
           Assert.assertFalse(e.getMessage(),true);
       }

    }




    private String stringresponse(OkHttpClient okHttpClient,String baseurl) throws IOException {
        RequestBody requestBody=RequestBody.create(MediaType.parse("text/json"),"Hi there");
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url(baseurl)
                .build();

        Response response=okHttpClient.newCall(request).execute();
        return response.body().toString();
    }

    @Test
    public void testWebClients() throws  Exception{

        try {


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("\"{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"tenant_name\": \"Cidaas Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}\"");

            MockWebServer server = new MockWebServer();
            server.shutdown();
            server.start(2006);
            server.url("/public-srv/tenantinfo/basic");



            final Dispatcher dispatcher = new Dispatcher() {

                @Override
                public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                    if (request.getPath().equals("/public-srv/tenantinfo/basic")){
                        return response;
                    } else if (request.getPath().equals("v1/check/version/")){
                        return response;
                    } else if (request.getPath().equals("/v1/profile/info")) {
                        return new MockResponse().setResponseCode(200).setBody("{\\\"info\\\":{\\\"name\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}");
                    }
                    return new MockResponse().setResponseCode(404);
                }
            };
            server.setDispatcher(dispatcher);

            Cidaas.baseurl="https://"+server.getHostName()+":2006/";

            tenantService.getTenantInfo("localhost:2006", new Result<TenantInfoEntity>() {
                @Override
                public void success(TenantInfoEntity result) {
                    Assert.assertEquals("Cidaas developer",result.getData().getTenant_name());
                }

                @Override
                public void failure(WebAuthError error) {
                    //   Assert.assertEquals("Cidaas developer",error.getErrorMessage());
                }
            });




            server.getHostName();

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
