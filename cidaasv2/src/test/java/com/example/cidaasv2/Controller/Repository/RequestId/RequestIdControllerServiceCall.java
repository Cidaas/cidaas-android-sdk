package com.example.cidaasv2.Controller.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.HelperClass;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class RequestIdControllerServiceCall {

    Context context= Mockito.mock(Context.class);
    RequestIdController requestIdController=new RequestIdController(context);

    DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
    Dictionary<String, String> savedProperties=new Hashtable<>();

    CountDownLatch latch=new CountDownLatch(1);


    @Test
    public void testWebClient() throws  Exception{

        try {
            Timber.e("Success");



            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"requestId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\""+
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL= server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");



            server.enqueue(response);
            Cidaas.baseurl=domainURL;


            Dictionary<String,String> loginProperties=new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientId","ClientId");
            loginProperties.put("RedirectURL","RedirectURL");

            deviceInfoEntity.setDeviceId("DeviceID");
            deviceInfoEntity.setDeviceVersion("DeviceVersion");
            deviceInfoEntity.setDeviceModel("DeviceModel");
            deviceInfoEntity.setDeviceMake("DeviceMake");
            deviceInfoEntity.setPushNotificationId("PushNotificationId");


            savedProperties.put("Verifier", "codeVerifier");
            savedProperties.put("Challenge","codeChallenge");
            savedProperties.put("Method", "ChallengeMethod");

            requestIdController.getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d",result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals(null,error.getErrorMessage());
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

}
