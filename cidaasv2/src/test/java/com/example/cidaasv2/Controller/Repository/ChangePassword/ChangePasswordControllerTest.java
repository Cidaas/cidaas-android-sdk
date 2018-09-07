package com.example.cidaasv2.Controller.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
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
        changePasswordController.changePassword("baseurl", new ChangePasswordRequestEntity(), null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme