package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SetupIVRMFAResponseEntityTest {
    @Mock
    SetupIVRMFAResponseDataEntity data;
    @InjectMocks
    SetupIVRMFAResponseEntity setupIVRMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setSuccess(){
        setupIVRMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupIVRMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        setupIVRMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,setupIVRMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new SetupIVRMFAResponseDataEntity();

        data.setStatusId("Test");
        setupIVRMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",setupIVRMFAResponseEntity.getData().getStatusId());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme