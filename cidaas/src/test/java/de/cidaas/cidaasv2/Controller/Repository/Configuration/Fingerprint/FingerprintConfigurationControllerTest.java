package de.cidaas.cidaasv2.Controller.Repository.Configuration.Fingerprint;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class FingerprintConfigurationControllerTest {
    Context context;
    FingerprintConfigurationController shared;

    FingerprintConfigurationController fingerprintConfigurationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        fingerprintConfigurationController = new FingerprintConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        fingerprintConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        FingerprintConfigurationController result = FingerprintConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof FingerprintConfigurationController);
    }

    @Test
    public void testConfigureFingerprint() throws Exception {
        fingerprintConfigurationController.configureFingerprint("sub", "baseurl", new SetupFingerprintMFARequestEntity(), new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginWithFingerprint() throws Exception {
        fingerprintConfigurationController.LoginWithFingerprint("baseurl", "clientId", "trackId", "requestId", new InitiateFingerprintMFARequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme