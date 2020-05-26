package de.cidaas.cidaasv2.Controller.Repository.ResetPassword;

import android.content.Context;

import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class ResetPasswordControllerTest {
    Context context;
    ResetPasswordController shared;
    ResetPasswordController resetPasswordController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        resetPasswordController = new ResetPasswordController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ResetPasswordController result = ResetPasswordController.getShared(null);
        Assert.assertTrue(result instanceof ResetPasswordController);
    }

    @Test
    public void testInitiateresetPasswordService() throws Exception {
        resetPasswordController.initiateresetPasswordService("baseurl", new ResetPasswordRequestEntity(), null);
    }

    @Test
    public void testResetPasswordValidateCode() throws Exception {
        resetPasswordController.resetPasswordValidateCode("verificationCode", "rprq", new Result<ResetPasswordValidateCodeResponseEntity>() {
            @Override
            public void success(ResetPasswordValidateCodeResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testResetNewPassword() throws Exception {
        resetPasswordController.resetNewPassword("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme