package com.example.cidaasv2.Service.Repository.Verification.Pattern;

import android.content.Context;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatternVerificationServiceTest {
    Context context;
    PatternVerificationService patternVerificationService;

    @Before
    public void setUp() {
        patternVerificationService=new PatternVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        PatternVerificationService result = PatternVerificationService.getShared(null);
        Assert.assertEquals(new PatternVerificationService(null), result);
    }

    @Test
    public void testSetupPattern() throws Exception {

        patternVerificationService.setupPattern("baseurl", "accessToken", "codeChallenge", new SetupPatternMFARequestEntity(), null);
    }

    @Test
    public void testScannedPattern() throws Exception {

        patternVerificationService.scannedPattern("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testEnrollPattern() throws Exception {

        patternVerificationService.enrollPattern("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiatePattern() throws Exception {

        patternVerificationService.initiatePattern("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticatePattern() throws Exception {

        patternVerificationService.authenticatePattern("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme