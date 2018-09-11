package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class EnrollSMSMFAResponseEntityTest {
    @Mock
    EnrollSMSResponseDataEntity data;
    @InjectMocks
    EnrollSMSMFAResponseEntity enrollSMSMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess(){
        enrollSMSMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollSMSMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        enrollSMSMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,enrollSMSMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new EnrollSMSResponseDataEntity();

        data.setSub("Test");
        enrollSMSMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",enrollSMSMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme