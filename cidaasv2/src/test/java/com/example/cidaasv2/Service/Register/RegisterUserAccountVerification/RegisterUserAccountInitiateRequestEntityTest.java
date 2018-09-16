package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import junit.framework.Assert;

import org.junit.Test;

public class RegisterUserAccountInitiateRequestEntityTest {
    RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity=new RegisterUserAccountInitiateRequestEntity();

    @Test
    public void setSub()
    {
        registerUserAccountInitiateRequestEntity.setSub("SUB");
        Assert.assertEquals("SUB",registerUserAccountInitiateRequestEntity.getSub());
    }

    @Test
    public void setprocessingtype()
    {
        registerUserAccountInitiateRequestEntity.setProcessingType("Test");
        Assert.assertEquals("Test",registerUserAccountInitiateRequestEntity.getProcessingType());
    }

    @Test
    public void setVerificationMedium()
    {
        registerUserAccountInitiateRequestEntity.setVerificationMedium("Test");
        Assert.assertEquals("Test",registerUserAccountInitiateRequestEntity.getVerificationMedium());
    }

    @Test
    public void setRequestId()
    {
        registerUserAccountInitiateRequestEntity.setRequestId("RequestId");
        Assert.assertEquals("RequestId",registerUserAccountInitiateRequestEntity.getRequestId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme