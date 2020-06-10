package de.cidaas.sdk.android.cidaasnative.ResetNewPassword.ResetPasswordValidateCode;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeRequestEntity;

public class ResetPasswordValidateCodeRequestEntityTest {

    ResetPasswordValidateCodeRequestEntity requestEntity = new ResetPasswordValidateCodeRequestEntity();


    @Test
    public void TestResult() {
        requestEntity.setCode("Code");
        Assert.assertEquals("Code", requestEntity.getCode());
    }

    @Test
    public void settest() {
        requestEntity.setResetRequestId("ResetId");
        Assert.assertEquals("ResetId", requestEntity.getResetRequestId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme