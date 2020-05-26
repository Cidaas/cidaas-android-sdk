package de.cidaas.cidaasv2.Controller.Repository.Configuration.TOTP;

import android.content.Context;

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

        tOTPConfigurationController.LoginWithTOTP("baseurl", "clientId", null, null, null);
    }

    @Test
    public void testGenerateTOTP() throws Exception {
        TOTPEntity result = tOTPConfigurationController.generateTOTP("secret");
        Assert.assertTrue(result instanceof TOTPEntity);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme