package com.example.cidaasv2.Service.Repository.Verification.SMS;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class SMSVerificationServiceTest {

    CidaassdkService service;

    Context context;

    SMSVerificationService sMSVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
      sMSVerificationService=new SMSVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        SMSVerificationService result = SMSVerificationService.getShared(null);
        Assert.assertTrue(result instanceof SMSVerificationService);
    }

    @Test
    public void testSetupSMSMFA() throws Exception {

        sMSVerificationService.setupSMSMFA("baseurl", "accessToken", new Result<SetupSMSMFAResponseEntity>() {
            @Override
            public void success(SetupSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSMSMFA() throws Exception {

        sMSVerificationService.enrollSMSMFA("baseurl", "accessToken", null, new Result<EnrollSMSMFAResponseEntity>() {
            @Override
            public void success(EnrollSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSMSMFA() throws Exception {

        sMSVerificationService.initiateSMSMFA("baseurl", null, new Result<InitiateSMSMFAResponseEntity>() {
            @Override
            public void success(InitiateSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateSMSMFA() throws Exception {

        sMSVerificationService.authenticateSMSMFA("baseurl", null, new Result<AuthenticateSMSResponseEntity>() {
            @Override
            public void success(AuthenticateSMSResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
