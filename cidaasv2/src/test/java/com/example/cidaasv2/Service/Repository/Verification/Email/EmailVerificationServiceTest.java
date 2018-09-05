package com.example.cidaasv2.Service.Repository.Verification.Email;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailVerificationServiceTest {
    Context context;
    EmailVerificationService emailVerificationService;

    @Before
    public void setUp() {
      emailVerificationService=new EmailVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        EmailVerificationService result = EmailVerificationService.getShared(null);
        Assert.assertEquals(new EmailVerificationService(null), result);
    }

    @Test
    public void testSetupEmailMFA() throws Exception {

        emailVerificationService.setupEmailMFA("baseurl", "accessToken", null);
    }

    @Test
    public void testEnrollEmailMFA() throws Exception {

        emailVerificationService.enrollEmailMFA("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateEmailMFA() throws Exception {

        emailVerificationService.initiateEmailMFA("baseurl", null, null);
    }

    @Test
    public void testAuthenticateEmailMFA() throws Exception {

        emailVerificationService.authenticateEmailMFA("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme