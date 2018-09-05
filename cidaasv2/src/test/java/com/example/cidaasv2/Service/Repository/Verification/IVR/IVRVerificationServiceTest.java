package com.example.cidaasv2.Service.Repository.Verification.IVR;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IVRVerificationServiceTest {
    Context context;
    IVRVerificationService iVRVerificationService;

    @Before
    public void setUp() {
       iVRVerificationService=new IVRVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        IVRVerificationService result = IVRVerificationService.getShared(null);
        Assert.assertEquals(new IVRVerificationService(null), result);
    }

    @Test
    public void testSetupIVRMFA() throws Exception {

        iVRVerificationService.setupIVRMFA("baseurl", "accessToken", null);
    }

    @Test
    public void testEnrollIVRMFA() throws Exception {

        iVRVerificationService.enrollIVRMFA("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateIVRMFA() throws Exception {

        iVRVerificationService.initiateIVRMFA("baseurl", null, null);
    }

    @Test
    public void testAuthenticateIVRMFA() throws Exception {

        iVRVerificationService.authenticateIVRMFA("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme