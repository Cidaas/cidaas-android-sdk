package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR;

import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class InitiateIVRMFAResponseEntityTest {

    InitiateIVRMFAResponseDataEntity data;

    InitiateIVRMFAResponseEntity initiateIVRMFAResponseEntity;

    @Before
    public void setUp() {
        initiateIVRMFAResponseEntity=new InitiateIVRMFAResponseEntity();
    }

    @Test
    public void setSuccess(){
        initiateIVRMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateIVRMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        initiateIVRMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,initiateIVRMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new InitiateIVRMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateIVRMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",initiateIVRMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme