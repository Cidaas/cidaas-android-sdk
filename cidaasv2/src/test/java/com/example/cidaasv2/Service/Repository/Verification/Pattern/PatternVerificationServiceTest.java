package com.example.cidaasv2.Service.Repository.Verification.Pattern;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class PatternVerificationServiceTest {
    Context context;
    PatternVerificationService patternVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        patternVerificationService=new PatternVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        PatternVerificationService result = PatternVerificationService.getShared(null);

        Assert.assertTrue(result instanceof PatternVerificationService);
    }

    @Test
    public void testSetupPattern() throws Exception {

        patternVerificationService.setupPattern("baseurl", "accessToken", "codeChallenge", new SetupPatternMFARequestEntity(), new Result<SetupPatternMFAResponseEntity>() {
            @Override
            public void success(SetupPatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedPattern() throws Exception {

        patternVerificationService.scannedPattern("baseurl", "usagePass", "statusId", "AccessToken", new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollPattern() throws Exception {

        patternVerificationService.enrollPattern("baseurl", "accessToken", null, new Result<EnrollPatternMFAResponseEntity>() {
            @Override
            public void success(EnrollPatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiatePattern() throws Exception {

        patternVerificationService.initiatePattern("baseurl", "codeChallenge", null, new Result<InitiatePatternMFAResponseEntity>() {
            @Override
            public void success(InitiatePatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticatePattern() throws Exception {

        patternVerificationService.authenticatePattern("baseurl", null, new Result<AuthenticatePatternResponseEntity>() {
            @Override
            public void success(AuthenticatePatternResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
