package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class EnrollIVRMFAResponseEntityTest {
    @Mock
    EnrollIVRResponseDataEntity data;
    @InjectMocks
    EnrollIVRMFAResponseEntity enrollIVRMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess() {
        enrollIVRMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollIVRMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollIVRMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollIVRMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollIVRResponseDataEntity();

        data.setSub("Test");
        enrollIVRMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollIVRMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme