package com.example.cidaasv2.Controller.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)

public class LoginControllerTest {
    Context context;
    LoginController loginController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        loginController=new LoginController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        LoginController result = LoginController.getShared(null);
        Assert.assertTrue(result instanceof LoginController);
    }

    @Test
    public void testLoginWithCredentials() throws Exception {

        LoginEntity loginEntity=new LoginEntity();
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
                Assert.assertTrue(error.getErrorMessage().contains("One of the property is missing"));
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

    /**f (resumeLoginRequestEntity.getTrack_id() != null && !resumeLoginRequestEntity.getTrack_id().equals("") &&
     resumeLoginRequestEntity.getClient_id() != null && !resumeLoginRequestEntity.getClient_id().equals("") &&
     resumeLoginRequestEntity.getSub() != null && !resumeLoginRequestEntity.getSub().equals("") &&
     resumeLoginRequestEntity.getTrackingCode() != null && !resumeLoginRequestEntity.getTrackingCode().equals("") &&
     resumeLoginRequestEntity.getVerificationType() != null && !resumeLoginRequestEntity.getVerificationType().equals("") &&
     baseurl != null && !baseurl.equals("")) {**/
}