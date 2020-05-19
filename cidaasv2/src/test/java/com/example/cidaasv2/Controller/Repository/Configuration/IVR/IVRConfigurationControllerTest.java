package com.example.cidaasv2.Controller.Repository.Configuration.IVR;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class IVRConfigurationControllerTest {

    Context context;

    IVRConfigurationController shared;

    IVRConfigurationController iVRConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        iVRConfigurationController=new IVRConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        iVRConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        IVRConfigurationController result = IVRConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof IVRConfigurationController);
    }

    @Test
    public void testConfigureIVR() throws Exception {
        iVRConfigurationController.configureIVR("sub", "baseurl", null);
    }

    @Test
    public void testEnrollIVRMFA() throws Exception {
        iVRConfigurationController.enrollIVRMFA("code", "StatusId", "baseurl", null);
    }

    @Test
    public void testLoginWithIVR() throws Exception {
        iVRConfigurationController.loginWithIVR("baseurl", "trackId", "clientId", "requestId", null, null);
    }

    @Test
    public void testVerifyIVR() throws Exception {
        iVRConfigurationController.verifyIVR("baseurl", "clientId", new AuthenticateIVRRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
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