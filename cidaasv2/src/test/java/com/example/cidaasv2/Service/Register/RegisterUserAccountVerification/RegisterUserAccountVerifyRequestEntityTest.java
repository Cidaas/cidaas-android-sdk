package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import junit.framework.Assert;

import org.junit.Test;

public class RegisterUserAccountVerifyRequestEntityTest {


    @Test
    public void setACCvid()
    {
        RegisterUserAccountVerifyRequestEntity registerUserAccountInitiateResponseDataEntity=new  RegisterUserAccountVerifyRequestEntity();

        registerUserAccountInitiateResponseDataEntity.setAccvid("TestData");
        Assert.assertEquals("TestData",registerUserAccountInitiateResponseDataEntity.getAccvid());
    }


    @Test
    public void setCode()
    {
        RegisterUserAccountVerifyRequestEntity registerUserAccountInitiateResponseDataEntity=new  RegisterUserAccountVerifyRequestEntity();

        registerUserAccountInitiateResponseDataEntity.setCode("TestData");
        Assert.assertEquals("TestData",registerUserAccountInitiateResponseDataEntity.getCode());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme