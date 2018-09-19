package com.example.cidaasv2.Service.Repository.Verification.Email;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class EmailVerificationServiceTest {
    Context context;
    EmailVerificationService emailVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
      emailVerificationService=new EmailVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        EmailVerificationService result = EmailVerificationService.getShared(null);

        Assert.assertTrue(result instanceof EmailVerificationService);
    }

    @Test
    public void testSetupEmailMFA() throws Exception {

        emailVerificationService.setupEmailMFA("baseurl", "accessToken", new Result<SetupEmailMFAResponseEntity>() {
            @Override
            public void success(SetupEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollEmailMFA() throws Exception {

        emailVerificationService.enrollEmailMFA("baseurl", "accessToken", new EnrollEmailMFARequestEntity(), new Result<EnrollEmailMFAResponseEntity>() {
            @Override
            public void success(EnrollEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateEmailMFA() throws Exception {

        emailVerificationService.initiateEmailMFA("baseurl", new InitiateEmailMFARequestEntity(), new Result<InitiateEmailMFAResponseEntity>() {
            @Override
            public void success(InitiateEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateEmailMFA() throws Exception {

        emailVerificationService.authenticateEmailMFA("baseurl", new AuthenticateEmailRequestEntity(), new Result<AuthenticateEmailResponseEntity>() {
            @Override
            public void success(AuthenticateEmailResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
