package de.cidaas.sdk.android.cidaasnative.ResetNewPassword.ResetPasswordValidateCode;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeDataEntity;

public class ResetPasswordValidateCodeDataEntityTest {

    ResetPasswordValidateCodeDataEntity resetPasswordValidateCodeDataEntity = new ResetPasswordValidateCodeDataEntity();


    @Test
    public void TestResult() {
        resetPasswordValidateCodeDataEntity.setExchangeId("ExchangeID");
        Assert.assertEquals("ExchangeID", resetPasswordValidateCodeDataEntity.getExchangeId());
    }

    @Test
    public void settest() {
        resetPasswordValidateCodeDataEntity.setResetRequestId("ResetId");
        Assert.assertEquals("ResetId", resetPasswordValidateCodeDataEntity.getResetRequestId());
    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme