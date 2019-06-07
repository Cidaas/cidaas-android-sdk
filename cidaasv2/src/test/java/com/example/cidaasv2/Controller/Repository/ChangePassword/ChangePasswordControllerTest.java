package com.example.cidaasv2.Controller.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseDataEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)

public class ChangePasswordControllerTest {

    Context context;

    ChangePasswordController shared;

    ChangePasswordController changePasswordController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        changePasswordController=new ChangePasswordController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        changePasswordController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        ChangePasswordController result = ChangePasswordController.getShared(null);
        Assert.assertTrue(result instanceof ChangePasswordController);
    }

    @Test
    public void testChangePassword() throws Exception {

        ChangePasswordRequestEntity changePasswordRequestEntity=new ChangePasswordRequestEntity();
        changePasswordRequestEntity.setAccess_token("Access_Token");
        changePasswordRequestEntity.setOld_password("Old_Password");
        changePasswordRequestEntity.setConfirm_password("Password");
        changePasswordRequestEntity.setNew_password("Password");
        changePasswordRequestEntity.setIdentityId("IdentityId");
        changePasswordRequestEntity.setSub("Sub");
        changePasswordController.changePassword("baseurl", changePasswordRequestEntity, new Result<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testChangePasswordFalies() throws Exception {

        ChangePasswordRequestEntity changePasswordRequestEntity=new ChangePasswordRequestEntity();
        changePasswordRequestEntity.setAccess_token("Access_Token");
        changePasswordRequestEntity.setOld_password("Old_Password");
        changePasswordRequestEntity.setConfirm_password("Password");
        changePasswordRequestEntity.setSub("Sub");
        changePasswordController.changePassword("baseurl", changePasswordRequestEntity, new Result<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme