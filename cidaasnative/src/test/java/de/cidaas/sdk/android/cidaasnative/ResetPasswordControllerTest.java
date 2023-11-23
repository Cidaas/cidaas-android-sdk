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
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.resetpassword.ResetPasswordController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;


@RunWith(RobolectricTestRunner.class)
@Ignore
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
        resetPasswordController.initiateresetPasswordService("baseurl", new ResetPasswordRequestEntity(),"en",null);
    }

    @Test
    public void testResetPasswordValidateCode() throws Exception {
        resetPasswordController.resetPasswordValidateCode("verificationCode", "rprq", new EventResult<ResetPasswordValidateCodeResponseEntity>() {
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