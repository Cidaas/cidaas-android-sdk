package com.example.cidaasv2.Controller.Repository.Configuration.SMS;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SMSConfigurationControllerTest {
    Context context;
    SMSConfigurationController shared;
    SMSConfigurationController sMSConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        sMSConfigurationController=new SMSConfigurationController(context);
    }

    @Test
    public void testConfigureSMS() throws Exception {
        sMSConfigurationController.configureSMS("sub", "baseurl", null);
    }

    @Test
    public void testEnrollSMSMFA() throws Exception {
        sMSConfigurationController.enrollSMSMFA("code", "StatusId", "baseurl", null);
    }

    @Test
    public void testGetShared() throws Exception {
        SMSConfigurationController result = SMSConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof SMSConfigurationController);
    }

    @Test
    public void testLoginWithSMS() throws Exception {
        sMSConfigurationController.loginWithSMS("baseurl", "trackId", "clientId", "requestId", null, null);
    }

    @Test
    public void testVerifySMS() throws Exception {
        sMSConfigurationController.verifySMS("baseurl", "clientId", new AuthenticateSMSRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSMSMFA() throws Exception {
        sMSConfigurationController.setupSMSMFA("AccessToken", "baseurl", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme