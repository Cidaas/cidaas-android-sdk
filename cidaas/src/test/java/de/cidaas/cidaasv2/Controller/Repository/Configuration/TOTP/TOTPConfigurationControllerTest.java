package de.cidaas.cidaasv2.Controller.Repository.Configuration.TOTP;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.TOTPEntity.TOTPEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class TOTPConfigurationControllerTest {
    Context context;
    TOTPConfigurationController shared;
    TOTPConfigurationController tOTPConfigurationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        tOTPConfigurationController = new TOTPConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        tOTPConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        TOTPConfigurationController result = TOTPConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof TOTPConfigurationController);
    }

    @Test
    public void testConfigureTOTP() throws Exception {

        tOTPConfigurationController.configureTOTP("sub", "baseurl", new SetupTOTPMFARequestEntity(), new Result<EnrollTOTPMFAResponseEntity>() {
            @Override
            public void success(EnrollTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginWithTOTP() throws Exception {

        tOTPConfigurationController.LoginWithTOTP("baseurl", "clientId", "trackId", "requestId", null, null);
    }

    @Test
    public void testGenerateTOTP() throws Exception {
        TOTPEntity result = tOTPConfigurationController.generateTOTP("secret");
        Assert.assertTrue(result instanceof TOTPEntity);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme