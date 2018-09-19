package com.example.cidaasv2.Service.Repository.Verification.TOTP;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class TOTPVerificationServiceTest {
    Context context;
    TOTPVerificationService tOTPVerificationService;

    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        tOTPVerificationService=new TOTPVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        TOTPVerificationService result = TOTPVerificationService.getShared(null);
        Assert.assertTrue(result instanceof TOTPVerificationService);
    }

    @Test
    public void testScannedTOTP() throws Exception {

        tOTPVerificationService.scannedTOTP("baseurl", "usagePass", "statusId", "AccessToken", new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupTOTP() throws Exception {

        tOTPVerificationService.setupTOTP("baseurl", "accessToken", "codeChallenge", new SetupTOTPMFARequestEntity(), new Result<SetupTOTPMFAResponseEntity>() {
            @Override
            public void success(SetupTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollTOTP() throws Exception {
               tOTPVerificationService.enrollTOTP("baseurl", "accessToken", new EnrollTOTPMFARequestEntity()
                       , new Result<EnrollTOTPMFAResponseEntity>() {
                   @Override
                   public void success(EnrollTOTPMFAResponseEntity result) {

                   }

                   @Override
                   public void failure(WebAuthError error) {

                   }
               });
    }

    @Test
    public void testInitiateTOTP() throws Exception {

        tOTPVerificationService.initiateTOTP("baseurl", "codeChallenge", new InitiateTOTPMFARequestEntity(), new Result<InitiateTOTPMFAResponseEntity>() {
            @Override
            public void success(InitiateTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateTOTP() throws Exception {

        tOTPVerificationService.authenticateTOTP("baseurl", new AuthenticateTOTPRequestEntity(), new Result<AuthenticateTOTPResponseEntity>() {
            @Override
            public void success(AuthenticateTOTPResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
