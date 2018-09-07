package com.example.cidaasv2.Service.Repository.Verification.Fingerprint;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class FingerprintVerificationServiceTest {

    Context context;
    FingerprintVerificationService fingerprintVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        fingerprintVerificationService=new FingerprintVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        FingerprintVerificationService result = FingerprintVerificationService.getShared(null);

        Assert.assertTrue(result instanceof FingerprintVerificationService);
    }

    @Test
    public void testScannedFingerprint() throws Exception {

        fingerprintVerificationService.scannedFingerprint("baseurl", "usagePass", "statusId", "AccessToken", new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupFingerprint() throws Exception {

        fingerprintVerificationService.setupFingerprint("baseurl", "accessToken", "codeChallenge", null, new Result<SetupFingerprintMFAResponseEntity>() {
            @Override
            public void success(SetupFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollFingerprint() throws Exception {

        fingerprintVerificationService.enrollFingerprint("baseurl", "accessToken", null, new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateFingerprint() throws Exception {

        fingerprintVerificationService.initiateFingerprint("baseurl", "codeChallenge", null, new Result<InitiateFingerprintMFAResponseEntity>() {
            @Override
            public void success(InitiateFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateFingerprint() throws Exception {

        fingerprintVerificationService.authenticateFingerprint("baseurl", null, new Result<AuthenticateFingerprintResponseEntity>() {
            @Override
            public void success(AuthenticateFingerprintResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
