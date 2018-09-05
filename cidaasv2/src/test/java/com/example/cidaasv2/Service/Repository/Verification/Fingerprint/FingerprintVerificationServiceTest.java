package com.example.cidaasv2.Service.Repository.Verification.Fingerprint;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FingerprintVerificationServiceTest {

    Context context;
    FingerprintVerificationService fingerprintVerificationService;

    @Before
    public void setUp() {
        fingerprintVerificationService=new FingerprintVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        FingerprintVerificationService result = FingerprintVerificationService.getShared(null);
        Assert.assertEquals(new FingerprintVerificationService(null), result);
    }

    @Test
    public void testScannedFingerprint() throws Exception {

        fingerprintVerificationService.scannedFingerprint("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testSetupFingerprint() throws Exception {

        fingerprintVerificationService.setupFingerprint("baseurl", "accessToken", "codeChallenge", null, null);
    }

    @Test
    public void testEnrollFingerprint() throws Exception {

        fingerprintVerificationService.enrollFingerprint("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateFingerprint() throws Exception {

        fingerprintVerificationService.initiateFingerprint("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticateFingerprint() throws Exception {

        fingerprintVerificationService.authenticateFingerprint("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme