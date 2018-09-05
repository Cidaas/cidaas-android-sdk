package com.example.cidaasv2.Service.Repository.Verification.SmartPush;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmartPushVerificationServiceTest {

    SmartPushVerificationService smartPushVerificationService;
    Context context;

    @Before
    public void setUp() {
     smartPushVerificationService=new SmartPushVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        SmartPushVerificationService result = SmartPushVerificationService.getShared(null);
        Assert.assertEquals(new SmartPushVerificationService(null), result);
    }

    @Test
    public void testScannedSmartPush() throws Exception {

        smartPushVerificationService.scannedSmartPush("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testSetupSmartPush() throws Exception {

        smartPushVerificationService.setupSmartPush("baseurl", "accessToken", "codeChallenge", null, null);
    }

    @Test
    public void testEnrollSmartPush() throws Exception {

        smartPushVerificationService.enrollSmartPush("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateSmartPush() throws Exception {

        smartPushVerificationService.initiateSmartPush("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticateSmartPush() throws Exception {

        smartPushVerificationService.authenticateSmartPush("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme