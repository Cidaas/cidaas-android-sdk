package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SetupTOTPMFAResponseEntityTest {
    @Mock
    SetupTOTPMFAResponseDataEntity data;
    @InjectMocks
    SetupTOTPMFAResponseEntity setupTOTPMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setSuccess(){
        setupTOTPMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupTOTPMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        setupTOTPMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,setupTOTPMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new SetupTOTPMFAResponseDataEntity();

        data.setStatusId("Test");
        setupTOTPMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",setupTOTPMFAResponseEntity.getData().getStatusId());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme