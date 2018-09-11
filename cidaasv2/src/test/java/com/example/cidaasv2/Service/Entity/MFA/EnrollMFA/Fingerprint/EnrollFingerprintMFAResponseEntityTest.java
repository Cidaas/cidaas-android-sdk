package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class EnrollFingerprintMFAResponseEntityTest {
    @Mock
    EnrollFingerprintResponseDataEntity data;
    @InjectMocks
    EnrollFingerprintMFAResponseEntity enrollFingerprintMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess(){
        enrollFingerprintMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollFingerprintMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        enrollFingerprintMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,enrollFingerprintMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new EnrollFingerprintResponseDataEntity();

        data.setSub("Test");
        enrollFingerprintMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",enrollFingerprintMFAResponseEntity.getData().getSub());

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme