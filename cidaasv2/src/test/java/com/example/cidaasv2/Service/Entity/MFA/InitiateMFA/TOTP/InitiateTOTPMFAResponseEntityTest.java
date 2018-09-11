package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class InitiateTOTPMFAResponseEntityTest {

    InitiateTOTPMFAResponseDataEntity data;

    InitiateTOTPMFAResponseEntity initiateTOTPMFAResponseEntity;

    @Before
    public void setUp() {
       initiateTOTPMFAResponseEntity=new InitiateTOTPMFAResponseEntity();
    }

    @Test
    public void setSuccess(){
        initiateTOTPMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateTOTPMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        initiateTOTPMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,initiateTOTPMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new InitiateTOTPMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateTOTPMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",initiateTOTPMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme