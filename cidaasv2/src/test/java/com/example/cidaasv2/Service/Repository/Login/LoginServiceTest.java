package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;

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
        Assert.assertTrue(result instanceof LoginService);
    }

    @Test
    public void testLoginWithCredentials() throws Exception {

        loginService.loginWithCredentials("baseurl", new LoginCredentialsRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinueMFA() throws Exception {

       loginService.continueMFA("baseurl", null, new Result<ResumeLoginResponseEntity>() {
           @Override
           public void success(ResumeLoginResponseEntity result) {

           }

           @Override
           public void failure(WebAuthError error) {

           }
       });
    }

    @Test
    public void testContinuePasswordless() throws Exception {
        loginService.continuePasswordless("baseurl", null, new Result<ResumeLoginResponseEntity>() {
            @Override
            public void success(ResumeLoginResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme