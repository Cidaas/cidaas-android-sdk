package com.example.cidaasv2.Service.Repository.Verification.IVR;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Pattern.PatternVerificationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class IVRVerificationServiceTest {
    Context context;
    IVRVerificationService iVRVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
       iVRVerificationService=new IVRVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        IVRVerificationService result = IVRVerificationService.getShared(null);

        Assert.assertTrue(result instanceof IVRVerificationService);
    }

    @Test
    public void testSetupIVRMFA() throws Exception {

        iVRVerificationService.setupIVRMFA("baseurl", "accessToken", new Result<SetupIVRMFAResponseEntity>() {
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

        iVRVerificationService.enrollIVRMFA("baseurl", "accessToken", new EnrollIVRMFARequestEntity(), new Result<EnrollIVRMFAResponseEntity>() {
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

        iVRVerificationService.initiateIVRMFA("baseurl", new InitiateIVRMFARequestEntity(), new Result<InitiateIVRMFAResponseEntity>() {
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

        iVRVerificationService.authenticateIVRMFA("baseurl", new AuthenticateIVRRequestEntity(), new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
