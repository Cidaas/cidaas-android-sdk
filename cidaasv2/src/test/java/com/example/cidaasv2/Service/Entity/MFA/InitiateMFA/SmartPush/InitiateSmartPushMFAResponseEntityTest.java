package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class InitiateSmartPushMFAResponseEntityTest {
    InitiateSmartPushMFAResponseDataEntity data;

    InitiateSmartPushMFAResponseEntity initiateSmartPushMFAResponseEntity;

    @Before
    public void setUp() {
       initiateSmartPushMFAResponseEntity=new InitiateSmartPushMFAResponseEntity();
    }

    @Test
    public void setSuccess(){
        initiateSmartPushMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateSmartPushMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        initiateSmartPushMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,initiateSmartPushMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new InitiateSmartPushMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateSmartPushMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",initiateSmartPushMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme