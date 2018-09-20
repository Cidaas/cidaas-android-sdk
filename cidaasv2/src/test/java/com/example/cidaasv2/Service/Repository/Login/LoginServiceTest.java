package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.Client.ClientController;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginServiceTest {

    Context context;
    LoginService loginService;
    DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();


    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        loginService=new LoginService(context);
        DBHelper.setConfig(context);


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
        LoginService result = LoginService.getShared(null);
        Assert.assertTrue(result instanceof LoginService);
    }

    @Test
    public void testLoginWithCredentials() throws Exception {

        loginService.loginWithCredentials("baseurl", new LoginCredentialsRequestEntity(),null, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginWithCredentialsnull() throws Exception {

        loginService.loginWithCredentials("", new LoginCredentialsRequestEntity(),null, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinueMFA() throws Exception {

       loginService.continueMFA("baseurl", null, null,new Result<ResumeLoginResponseEntity>() {
           @Override
           public void success(ResumeLoginResponseEntity result) {

           }

           @Override
           public void failure(WebAuthError error) {

           }
       });
    }

    @Test
    public void testContinueMFANUll() throws Exception {
        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
        resumeLoginRequestEntity.setTrack_id("TrackId");

        loginService.continueMFA("baseurl", resumeLoginRequestEntity,null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testContinueMFAnul() throws Exception {

        loginService.continueMFA("", null, null,new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinuePasswordless() throws Exception {
        loginService.continuePasswordless("", null,null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinuePasswordles() throws Exception {

        loginService.continuePasswordless("base", null,null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinuePasswordlessn() throws Exception {
        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
        resumeLoginRequestEntity.setTrack_id("TrackId");


        loginService.continuePasswordless("base", resumeLoginRequestEntity,null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        loginService.loginWithCredentials("localhost:234235", new LoginCredentialsRequestEntity(),null, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }
    @Test
    public void testContiuneMFAfail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;



        loginService.continuePasswordless("localhost:234235", new ResumeLoginRequestEntity(),null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }

    @Test
    public void testGetClientInfoFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;



        loginService.continueMFA("localhost:234235", new ResumeLoginRequestEntity(),null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }




}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme