package com.example.cidaasv2.Service.Repository.Client;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import timber.log.Timber;

public class ClientServiceTestCall {

    private final CountDownLatch latch = new CountDownLatch(1);

    CidaassdkService service;
    ClientService clientService;

    Context context;

    @Before
    public void setUp() throws Exception{
        context= Mockito.mock(Context.class);
        service=new CidaassdkService();
        MockitoAnnotations.initMocks(this);
        clientService=new ClientService(context);
        // mockAPI=new AuthenticationAPI();


    }

    @Test
    public void testWebClient() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "\"success\": true,\n" +
                            "  \"status\": 200,\n" +
                            "  \"data\": {\n" +
                            "    \"passwordless_enabled\": true,\n" +
                            "    \"logo_uri\": \"https://www.cidaas.com/wp-content/uploads/2018/02/logo-black.png\",\n" +
                            "    \"login_providers\": [\n" +
                            "      \"facebook\",\n" +
                            "      \"google\",\n" +
                            "      \"linkedin\"\n" +
                            "    ],\n" +
                            "    \"policy_uri\": \"\",\n" +
                            "    \"tos_uri\": \"\",\n" +
                            "    \"client_name\": \"Single Page WebApp (Don't Edit)\"\n" +
                            "    }\n" +
                            "  }");


            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/Clientinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            clientService.getClientInfo("requestId",removeLastChar(Cidaas.baseurl), new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {

                    Assert.assertEquals("Single Page WebApp (Don't Edit)",result.getData().getClient_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer",error.getErrorMessage());
                    latch.countDown();
                }
            });
            latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }


    @Test
    public void testWebClientFor202() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"Client_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/Clientinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            clientService.getClientInfo("requestId",removeLastChar(Cidaas.baseurl), new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {

                    Assert.assertEquals("Service failure but successful response",result.getData().getClient_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Service failure but successful response",error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }


    @Test
    public void testWebClientFor401() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "   \"error\": {\n" +
                            "                \"code\": 24008,\n" +
                            "                        \"moreInfo\": \"\",\n" +
                            "                        \"type\": \"LoginException\",\n" +
                            "                        \"status\": 400,\n" +
                            "                        \"referenceNumber\": \"1537337364806\",\n" +
                            "                        \"error\": \"Invalid Client\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/Clientinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            clientService.getClientInfo("requestId",removeLastChar(Cidaas.baseurl), new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {

                    Assert.assertEquals("Service failure but successful response",result.getData().getClient_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid Client",error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }

    @Test
    public void testWebClientFaliureError() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid Client\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/Clientinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            clientService.getClientInfo("requestId",removeLastChar(Cidaas.baseurl), new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {

                    Assert.assertEquals("Service failure but successful response",result.getData().getClient_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid Client",error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }


    @Test
    public void testWebClientFaliureException() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid Client \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/Clientinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            clientService.getClientInfo("requestId",removeLastChar(Cidaas.baseurl), new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {

                    Assert.assertEquals("Service failure but successful response",result.getData().getClient_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid Client",error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(),true,true);
            Assert.assertFalse(e.getMessage(),true);
        }

    }

    @Test
    public void testWebClients() throws  Exception{

        try {

            context=null;

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"Client_name\": \"Cidaas Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            server.shutdown();
            server.start(2038);
            server.url("/public-srv/Clientinfo/basic");



            final Dispatcher dispatcher = new Dispatcher() {

                @Override
                public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                    if (request.getPath().equals("/public-srv/Clientinfo/basic")){
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

            Cidaas.baseurl="https://"+server.getHostName()+":203452/";

            clientService.getClientInfo("req","localhost:2007", new Result<ClientInfoEntity>() {
                @Override
                public void success(ClientInfoEntity result) {
                    Assert.assertEquals("Raja Developer",result.getData().getClient_name());
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


}
