package de.cidaas.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode;

import junit.framework.Assert;

import org.junit.Test;

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