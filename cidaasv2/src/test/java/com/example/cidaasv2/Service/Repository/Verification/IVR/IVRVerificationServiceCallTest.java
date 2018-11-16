package com.example.cidaasv2.Service.Repository.Verification.IVR;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.HelperClass;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.IVR.IVRVerificationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class IVRVerificationServiceCallTest {

    CidaassdkService service;
    IVRVerificationService ivrVerificationService;
    DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
    Dictionary<String, String> savedProperties=new Hashtable<>();

    CountDownLatch latch=new CountDownLatch(1);




    Context context;

    @Before
    public void setUp() throws Exception{
        context= Mockito.mock(Context.class);
        service=new CidaassdkService();
        MockitoAnnotations.initMocks(this);
        ivrVerificationService=new IVRVerificationService(context);

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");


        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge","codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

    }

//INITITATE

    @Test
    public void testWebClient() throws  Exception{

        //  Whitebox.set
        try {
            Timber.e("Success");



            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/verification-srv/backupcode/setup");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),deviceInfoEntity ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());

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
    public void testWebClinetDevicenull() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),null ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
                            "        \"requestId_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),deviceInfoEntity ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
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
                            "                        \"error\": \"Invalid requestId\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),deviceInfoEntity ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
                            "     \"error\": \"Invalid requestId\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");

            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),deviceInfoEntity ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
                            "     \"error\": \"Invalid requestId \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");

            ivrVerificationService.initiateIVRMFA(loginProperties.get("DomainURL"), new InitiateIVRMFARequestEntity(),deviceInfoEntity ,new Result<InitiateIVRMFAResponseEntity>() {
                @Override
                public void success(InitiateIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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

    ///SETUP

    @Test
    public void testSetup() throws  Exception{

        //  Whitebox.set
        try {
            Timber.e("Success");



            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/verification-srv/backupcode/setup");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",deviceInfoEntity ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());

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
    public void testSetupDevicenull() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",null ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testSetupFor202() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"requestId_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");




            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",deviceInfoEntity ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Service failure but successful response",error.getErrorMessage());
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
    public void testSetupFor401() throws  Exception{

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
                            "                        \"error\": \"Invalid requestId\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",deviceInfoEntity ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testSetupFaliureError() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",deviceInfoEntity ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testSetupFaliureException() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.setupIVRMFA(loginProperties.get("DomainURL"),"AccessToken",deviceInfoEntity ,new Result<SetupIVRMFAResponseEntity>() {
                @Override
                public void success(SetupIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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

    //ENROLL
    @Test
    public void testEnroll() throws  Exception{

        //  Whitebox.set
        try {
            Timber.e("Success");



            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/verification-srv/backupcode/setup");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),deviceInfoEntity ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());

                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer",error.getErrorMessage());
                    latch.countDown();
                }
            });
   /* @Test
    public void testSetupIVRMFA() throws Exception {

        iVRVerificationService.setupIVRMFA("baseurl", "accessToken", null,new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollIVRMFA() throws Exception {

        iVRVerificationService.enrollIVRMFA("baseurl", "accessToken", new EnrollIVRMFARequestEntity(), null,new Result<EnrollIVRMFAResponseEntity>() {
            @Override
            public void success(EnrollIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateIVRMFA() throws Exception {

        iVRVerificationService.initiateIVRMFA("baseurl", new InitiateIVRMFARequestEntity(), null,new Result<InitiateIVRMFAResponseEntity>() {
            @Override
            public void success(InitiateIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateIVRMFA() throws Exception {

        iVRVerificationService.authenticateIVRMFA("baseurl", new AuthenticateIVRRequestEntity(), null,new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }*/
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
    public void testEnrollDevicenull() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),null ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testEnrollFor202() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"requestId_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");




            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),deviceInfoEntity ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
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
    public void testEnrollFor401() throws  Exception{

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
                            "                        \"error\": \"Invalid requestId\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),deviceInfoEntity ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testEnrollFaliureError() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),deviceInfoEntity ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testEnrollFaliureException() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.enrollIVRMFA(loginProperties.get("DomainURL"), "accs",new EnrollIVRMFARequestEntity(),deviceInfoEntity ,new Result<EnrollIVRMFAResponseEntity>() {
                @Override
                public void success(EnrollIVRMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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

    //AUTHENTICATE
    @Test
    public void testAuthenticate() throws  Exception{

        //  Whitebox.set
        try {
            Timber.e("Success");



            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/verification-srv/backupcode/setup");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),deviceInfoEntity ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());

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
    public void testAuthenticateDevicenull() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),null ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testAuthenticateFor202() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"requestId_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");



            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),deviceInfoEntity ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
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
    public void testAuthenticateFor401() throws  Exception{

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
                            "                        \"error\": \"Invalid requestId\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");


            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),deviceInfoEntity ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testAuthenticateFaliureError() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");

            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),deviceInfoEntity ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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
    public void testAuthenticateFaliureException() throws  Exception{

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;

            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL",HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID","ClientID");
            loginProperties.put("RedirectURL","RedirectURL");

            ivrVerificationService.authenticateIVRMFA(loginProperties.get("DomainURL"), new AuthenticateIVRRequestEntity(),deviceInfoEntity ,new Result<AuthenticateIVRResponseEntity>() {
                @Override
                public void success(AuthenticateIVRResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId",error.getErrorMessage());
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


}
