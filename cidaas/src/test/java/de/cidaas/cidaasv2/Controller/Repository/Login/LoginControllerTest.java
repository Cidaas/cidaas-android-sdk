package de.cidaas.cidaasv2.Controller.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.LoginEntity;
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
    LoginController loginController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        loginController = new LoginController(context);
    }

    @Test
    public void testGetShared() {
        LoginController result = LoginController.getShared(null);
        Assert.assertTrue(result instanceof LoginController);
    }

    @Test
    public void testLoginWithCredentials() {

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
    public void testContinueMFA() {
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
    public void testContinuePasswordless() {
        loginController.continuePasswordless("baseurl", new ResumeLoginRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testContinueMFAMissingClientId() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setVerificationType("VerificationType");
        testEntity.setSub("sub");
        testEntity.setTrack_id("trackId");
        testEntity.setTrackingCode("trackingCode");

        loginController.continueMFA("baseurl", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFAMissingVerificationType() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id("ClientId");
        testEntity.setSub("sub");
        testEntity.setTrack_id("trackId");
        testEntity.setTrackingCode("trackingCode");

        loginController.continueMFA("baseurl", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFAMissingSub() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id("ClientId");
        testEntity.setVerificationType("VerificationType");
        testEntity.setTrack_id("trackId");
        testEntity.setTrackingCode("trackingCode");

        loginController.continueMFA("baseurl", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFAMissingTrackId() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id("ClientId");
        testEntity.setVerificationType("VerificationType");
        testEntity.setSub("sub");
        testEntity.setTrackingCode("trackingCode");

        loginController.continueMFA("baseurl", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFAMissingTrackingCode() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id("ClientId");
        testEntity.setVerificationType("VerificationType");
        testEntity.setSub("sub");
        testEntity.setTrack_id("trackId");

        loginController.continueMFA("baseurl", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFAMissingBaseUrl() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id("ClientId");
        testEntity.setVerificationType("VerificationType");
        testEntity.setSub("sub");
        testEntity.setTrack_id("trackId");
        testEntity.setTrackingCode("trackingCode");

        loginController.continueMFA("", testEntity, new Result<LoginCredentialsResponseEntity>() {
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
    public void testContinueMFANullValues() {
        ResumeLoginRequestEntity testEntity = new ResumeLoginRequestEntity();
        testEntity.setClient_id(null);
        testEntity.setVerificationType(null);
        testEntity.setSub(null);
        testEntity.setTrack_id(null);
        testEntity.setTrackingCode(null);

        loginController.continueMFA(null, testEntity, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue(error.getErrorMessage().contains("One of the property is missing"));
            }
        });
    }
}