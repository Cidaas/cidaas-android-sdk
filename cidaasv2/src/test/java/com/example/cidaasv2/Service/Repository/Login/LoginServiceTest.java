package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginServiceTest {

    Context context;
    LoginService loginService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        loginService=new LoginService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        LoginService result = LoginService.getShared(null);
        Assert.assertEquals(new LoginService(null), result);
    }

    @Test
    public void testLoginWithCredentials() throws Exception {

        loginService.loginWithCredentials("baseurl", new LoginCredentialsRequestEntity(), null);
    }

    @Test
    public void testContinueMFA() throws Exception {

        loginService.continueMFA("baseurl", null, null);
    }

    @Test
    public void testContinuePasswordless() throws Exception {
        loginService.continuePasswordless("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme