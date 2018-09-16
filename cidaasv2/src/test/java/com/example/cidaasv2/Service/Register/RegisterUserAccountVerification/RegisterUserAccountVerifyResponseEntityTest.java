package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import junit.framework.Assert;

import org.junit.Test;

public class RegisterUserAccountVerifyResponseEntityTest {

    RegisterUserAccountVerifyResponseEntity registerUserAccountVerifyResponseEntity=new RegisterUserAccountVerifyResponseEntity();

    @Test
    public void setSuccess(){
        registerUserAccountVerifyResponseEntity.setSuccess(true);
        Assert.assertTrue(registerUserAccountVerifyResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        registerUserAccountVerifyResponseEntity.setStatus(27);
        Assert.assertEquals(27,registerUserAccountVerifyResponseEntity.getStatus());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme