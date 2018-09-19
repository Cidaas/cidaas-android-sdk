package com.example.cidaasv2.Service.Repository.ResetPassword;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class ResetPasswordServiceTest {

    Context context;
    ResetPasswordService resetPasswordService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
           resetPasswordService=new ResetPasswordService(context);
           DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");

        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);
    }

    @Test
    public void testGetShared() throws Exception {
        ResetPasswordService result = ResetPasswordService.getShared(null);

        Assert.assertTrue(result instanceof ResetPasswordService);
    }

    @Test
    public void testInitiateresetPassword() throws Exception {

        resetPasswordService.initiateresetPassword(new ResetPasswordRequestEntity(), "baseurl", new Result<ResetPasswordResponseEntity>() {
            @Override
            public void success(ResetPasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateresetPasswordnull() throws Exception {

        resetPasswordService.initiateresetPassword(new ResetPasswordRequestEntity(), "", new Result<ResetPasswordResponseEntity>() {
            @Override
            public void success(ResetPasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testResetPasswordValidateCode() throws Exception {

        resetPasswordService.resetPasswordValidateCode(null, "baseurl", new Result<ResetPasswordValidateCodeResponseEntity>() {
            @Override
            public void success(ResetPasswordValidateCodeResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testResetPasswordValidateCodenull() throws Exception {

        resetPasswordService.resetPasswordValidateCode(null, "", new Result<ResetPasswordValidateCodeResponseEntity>() {
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


        resetPasswordService.resetNewPassword(new ResetPasswordEntity(), "baseurl", new Result<ResetNewPasswordResponseEntity>() {
            @Override
            public void success(ResetNewPasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testResetNewPasswordnull() throws Exception {


        resetPasswordService.resetNewPassword(new ResetPasswordEntity(), "", new Result<ResetNewPasswordResponseEntity>() {
            @Override
            public void success(ResetNewPasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
