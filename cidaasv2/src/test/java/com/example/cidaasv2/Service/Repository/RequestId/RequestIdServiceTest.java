package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
public class RequestIdServiceTest {

    Context context;
    RequestIdService requestIdService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
       requestIdService=new RequestIdService(context);
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");

        Dictionary<String, String> savedProperties=new Hashtable<>();

        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge","codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

        DBHelper.getShared().addChallengeProperties(savedProperties);

        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);
    }

    @Test
    public void testGetShared() throws Exception {
        RequestIdService result = RequestIdService.getShared(null);

        Assert.assertTrue(result instanceof RequestIdService);
    }

    @Test
    public void testGetRequestID() throws Exception {

        requestIdService.getRequestID(null,null,null, new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetRequestIDNotnull() throws Exception {
        Dictionary<String,String> loginProperties=new Hashtable<>();
        loginProperties.put("DomainURL","DomainURL");
        loginProperties.put("ClientId","ClientId");
        loginProperties.put("RedirectURL","RedirectURL");
        requestIdService.getRequestID(loginProperties,null,null, new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testGetRequestIDnull() throws Exception {
        Dictionary<String,String> loginProperties=new Hashtable<>();
        loginProperties.put("DomainURL","");
        loginProperties.put("ClientId","");
        loginProperties.put("RedirectURL","RedirectURL");
        requestIdService.getRequestID(loginProperties,null,null, new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetRequestIDFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;

        Dictionary<String,String> loginProperties=new Hashtable<>();
        loginProperties.put("DomainURL","localhost:234235");
        loginProperties.put("ClientId","");
        loginProperties.put("RedirectURL","RedirectURL");

        requestIdService.getRequestID(loginProperties, null,null, new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
