package com.example.cidaasv2.Service.Repository.Verification.SmartPush;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class SmartPushVerificationServiceTest {

    SmartPushVerificationService smartPushVerificationService;
    Context context;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
     smartPushVerificationService=new SmartPushVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        SmartPushVerificationService result = SmartPushVerificationService.getShared(null);

        Assert.assertTrue(result instanceof SmartPushVerificationService);
    }

    @Test
    public void testScannedSmartPush() throws Exception {

        smartPushVerificationService.scannedSmartPush("baseurl", "usagePass", "statusId", "AccessToken", new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSmartPush() throws Exception {

        smartPushVerificationService.setupSmartPush("baseurl", "accessToken", "codeChallenge", new SetupSmartPushMFARequestEntity(), new Result<SetupSmartPushMFAResponseEntity>() {
            @Override
            public void success(SetupSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSmartPush() throws Exception {

        smartPushVerificationService.enrollSmartPush("baseurl", "accessToken", new EnrollSmartPushMFARequestEntity(), new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSmartPush() throws Exception {

        smartPushVerificationService.initiateSmartPush("baseurl", "codeChallenge", new InitiateSmartPushMFARequestEntity(), new Result<InitiateSmartPushMFAResponseEntity>() {
            @Override
            public void success(InitiateSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateSmartPush() throws Exception {

        smartPushVerificationService.authenticateSmartPush("baseurl", new AuthenticateSmartPushRequestEntity(), new Result<AuthenticateSmartPushResponseEntity>() {
            @Override
            public void success(AuthenticateSmartPushResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
