package de.cidaas.cidaasv2.Controller.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.LoginEntity;

import de.cidaas.sdk.android.cidaas.Controller.Repository.Login.LoginController;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)

public class LoginControllerTest {
    Context context;
    LoginController shared;
    LoginController loginController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        loginController = new LoginController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        LoginController result = LoginController.getShared(null);
        Assert.assertTrue(result instanceof LoginController);
    }

    @Test
    public void testLoginwithCredentials() throws Exception {

        LoginEntity loginEntity = new LoginEntity();
        loginController.loginwithCredentials("requestId", loginEntity, new Result<LoginCredentialsResponseEntity>() {
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
        loginController.continueMFA("baseurl", new ResumeLoginRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinuePasswordless() throws Exception {
        loginController.continuePasswordless("baseurl", new ResumeLoginRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
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