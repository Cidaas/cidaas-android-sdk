package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;


import junit.framework.Assert;

import org.junit.Test;

public class RegisterUserAccountInitiateResponseDataEntityTest {


    @Test
    public void setACCvid()
    {
        RegisterUserAccountInitiateResponseDataEntity registerUserAccountInitiateResponseDataEntity=new RegisterUserAccountInitiateResponseDataEntity();

        registerUserAccountInitiateResponseDataEntity.setAccvid("TestData");
        Assert.assertEquals("TestData",registerUserAccountInitiateResponseDataEntity.getAccvid());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme