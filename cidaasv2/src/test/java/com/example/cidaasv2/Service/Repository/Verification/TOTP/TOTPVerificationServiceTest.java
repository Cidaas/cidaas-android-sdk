package com.example.cidaasv2.Service.Repository.Verification.TOTP;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TOTPVerificationServiceTest {
    Context context;
    TOTPVerificationService tOTPVerificationService;

    @Before
    public void setUp() {
        tOTPVerificationService=new TOTPVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        TOTPVerificationService result = TOTPVerificationService.getShared(null);
        Assert.assertEquals(new TOTPVerificationService(null), result);
    }

    @Test
    public void testScannedTOTP() throws Exception {

        tOTPVerificationService.scannedTOTP("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testSetupTOTP() throws Exception {

        tOTPVerificationService.setupTOTP("baseurl", "accessToken", "codeChallenge", null, null);
    }

    @Test
    public void testEnrollTOTP() throws Exception {
               tOTPVerificationService.enrollTOTP("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateTOTP() throws Exception {

        tOTPVerificationService.initiateTOTP("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticateTOTP() throws Exception {

        tOTPVerificationService.authenticateTOTP("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme