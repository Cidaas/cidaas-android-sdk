package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Dictionary;
import java.util.Hashtable;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginServiceTest {

    Context context;
    LoginService loginService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        loginService=new LoginService(context);
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
        LoginService result = LoginService.getShared(null);
        Assert.assertTrue(result instanceof LoginService);
    }

    @Test
    public void testLoginWithCredentials() throws Exception {

        loginService.loginWithCredentials("baseurl", new LoginCredentialsRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
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

        loginService.loginWithCredentials("", new LoginCredentialsRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
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

       loginService.continueMFA("baseurl", null, new Result<ResumeLoginResponseEntity>() {
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

        loginService.continueMFA("baseurl", resumeLoginRequestEntity, new Result<ResumeLoginResponseEntity>() {
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

        loginService.continueMFA("", null, new Result<ResumeLoginResponseEntity>() {
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
        loginService.continuePasswordless("", null, new Result<ResumeLoginResponseEntity>() {
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

        loginService.continuePasswordless("base", null, new Result<ResumeLoginResponseEntity>() {
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


        loginService.continuePasswordless("base", resumeLoginRequestEntity, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme