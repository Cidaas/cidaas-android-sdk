/*
package com.example.cidaasv2.Service.Repository.Verification.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
public class BackupCodeVerificationServiceTest {

    Context context;
    BackupCodeVerificationService backupCodeVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceId("DeviceId");
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);


        Dictionary<String,String> savedProperties=new Hashtable<>();
        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "codeChallengeMethod");
        DBHelper.getShared().addChallengeProperties(savedProperties);
      backupCodeVerificationService=new BackupCodeVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        BackupCodeVerificationService result = BackupCodeVerificationService.getShared(null);

        Assert.assertTrue(result instanceof BackupCodeVerificationService);
    }

    @Test
    public void testSetupBackupCodeMFA() throws Exception {

        backupCodeVerificationService.setupBackupCodeMFA("baseurl", "accessToken", null, new Result<SetupBackupCodeMFAResponseEntity>() {
            @Override
            public void success(SetupBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testSetupBackupCodeMFANUll() throws Exception {

        backupCodeVerificationService.setupBackupCodeMFA("", "accessToken", null, new Result<SetupBackupCodeMFAResponseEntity>() {
            @Override
            public void success(SetupBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testsetupFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        backupCodeVerificationService.setupBackupCodeMFA("localhost:234235", "accessToken", null, new Result<SetupBackupCodeMFAResponseEntity>() {
            @Override
            public void success(SetupBackupCodeMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }

    @Test
    public void testInitiateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.initiateBackupCodeMFA("baseurl", new InitiateBackupCodeMFARequestEntity(), null, new Result<InitiateBackupCodeMFAResponseEntity>() {
            @Override
            public void success(InitiateBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInititateBackupCodeMFANUll() throws Exception {

        backupCodeVerificationService.initiateBackupCodeMFA("", new InitiateBackupCodeMFARequestEntity(), null, new Result<InitiateBackupCodeMFAResponseEntity>() {
            @Override
            public void success(InitiateBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testInititateFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        backupCodeVerificationService.initiateBackupCodeMFA("localhost:234235",  new InitiateBackupCodeMFARequestEntity(), null, new Result<InitiateBackupCodeMFAResponseEntity>() {
            @Override
            public void success(InitiateBackupCodeMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }

    @Test
    public void testAuthenticateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.authenticateBackupCodeMFA("baseurl", new AuthenticateBackupCodeRequestEntity(), null, new Result<AuthenticateBackupCodeResponseEntity>() {
            @Override
            public void success(AuthenticateBackupCodeResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

  */
/*  @Test
    public void testAuthenticateBackupCodeMFANUll() throws Exception {

        backupCodeVerificationService.authenticateBackupCodeMFA("", new AuthenticateBackupCodeRequestEntity(), null, new Result<AuthenticateBackupCodeResponseEntity>() {
            @Override
            public void success(AuthenticateBackupCodeResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
*//*


    @Test
    public void testAuthenticateFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        backupCodeVerificationService.authenticateBackupCodeMFA("localhost:234235", new AuthenticateBackupCodeRequestEntity(), null, new Result<AuthenticateBackupCodeResponseEntity>() {
            @Override
            public void success(AuthenticateBackupCodeResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
*/
