package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class InitiatePatternMFAResponseEntityTest {
    InitiatePatternMFAResponseDataEntity data;
    InitiatePatternMFAResponseEntity initiatePatternMFAResponseEntity;

    @Before
    public void setUp() {
       initiatePatternMFAResponseEntity=new InitiatePatternMFAResponseEntity();
    }

    @Test
    public void setSuccess(){
        initiatePatternMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiatePatternMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        initiatePatternMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,initiatePatternMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new InitiatePatternMFAResponseDataEntity();

        data.setStatusId("Test");
        initiatePatternMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",initiatePatternMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme