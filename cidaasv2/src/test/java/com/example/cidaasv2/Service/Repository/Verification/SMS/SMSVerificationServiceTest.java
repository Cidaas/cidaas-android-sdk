package com.example.cidaasv2.Service.Repository.Verification.SMS;

import android.content.Context;

import com.example.cidaasv2.Service.CidaassdkService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

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
        Assert.assertEquals(new SMSVerificationService(null), result);
    }

    @Test
    public void testSetupSMSMFA() throws Exception {

        sMSVerificationService.setupSMSMFA("baseurl", "accessToken", null);
    }

    @Test
    public void testEnrollSMSMFA() throws Exception {

        sMSVerificationService.enrollSMSMFA("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateSMSMFA() throws Exception {

        sMSVerificationService.initiateSMSMFA("baseurl", null, null);
    }

    @Test
    public void testAuthenticateSMSMFA() throws Exception {

        sMSVerificationService.authenticateSMSMFA("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme