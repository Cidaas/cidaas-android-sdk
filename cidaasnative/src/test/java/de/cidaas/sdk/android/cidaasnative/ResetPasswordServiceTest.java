package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetNewPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetPasswordEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.ResetPassword.ResetPasswordService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;

@Ignore
@RunWith(RobolectricTestRunner.class)
public class ResetPasswordServiceTest {

    Context context;
    ResetPasswordService resetPasswordService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        resetPasswordService = new ResetPasswordService(context);
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();

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

        resetPasswordService.initiateresetPassword(new ResetPasswordRequestEntity(), "baseurl", null, new EventResult<ResetPasswordResponseEntity>() {
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

        resetPasswordService.initiateresetPassword(new ResetPasswordRequestEntity(), "", null, new EventResult<ResetPasswordResponseEntity>() {
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

        resetPasswordService.resetPasswordValidateCode(null, "baseurl", new EventResult<ResetPasswordValidateCodeResponseEntity>() {
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

        resetPasswordService.resetPasswordValidateCode(null, "", new EventResult<ResetPasswordValidateCodeResponseEntity>() {
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


        resetPasswordService.resetNewPassword(new ResetPasswordEntity(), "baseurl", new EventResult<ResetNewPasswordResponseEntity>() {
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


        resetPasswordService.resetNewPassword(new ResetPasswordEntity(), "", new EventResult<ResetNewPasswordResponseEntity>() {
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
