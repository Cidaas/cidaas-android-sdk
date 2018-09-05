package com.example.cidaasv2.Service.Repository.ResetPassword;

import android.content.Context;

import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResetPasswordServiceTest {

    Context context;
    ResetPasswordService resetPasswordService;

    @Before
    public void setUp() {
           resetPasswordService=new ResetPasswordService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ResetPasswordService result = ResetPasswordService.getShared(null);
        Assert.assertEquals(new ResetPasswordService(null), result);
    }

    @Test
    public void testInitiateresetPassword() throws Exception {

        resetPasswordService.initiateresetPassword(new ResetPasswordRequestEntity(), "baseurl", null);
    }

    @Test
    public void testResetPasswordValidateCode() throws Exception {

        resetPasswordService.resetPasswordValidateCode(null, "baseurl", null);
    }

    @Test
    public void testResetNewPassword() throws Exception {


        resetPasswordService.resetNewPassword(null, "baseurl", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme