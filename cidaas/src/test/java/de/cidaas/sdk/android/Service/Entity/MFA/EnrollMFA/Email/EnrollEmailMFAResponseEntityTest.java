package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.Email;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Email.EnrollEmailResponseDataEntity;


public class EnrollEmailMFAResponseEntityTest {
    @Mock
    EnrollEmailResponseDataEntity data;
    @InjectMocks
    EnrollEmailMFAResponseEntity enrollEmailMFAResponseEntity;

    @Before
    public void setUp() {
        enrollEmailMFAResponseEntity = new EnrollEmailMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        enrollEmailMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollEmailMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollEmailMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollEmailMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollEmailResponseDataEntity();

        data.setSub("Test");
        enrollEmailMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollEmailMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme