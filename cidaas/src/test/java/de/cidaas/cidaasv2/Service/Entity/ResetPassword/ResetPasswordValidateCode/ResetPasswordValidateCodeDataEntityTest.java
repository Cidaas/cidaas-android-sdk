package de.cidaas.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode;

import junit.framework.Assert;

import org.junit.Test;

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