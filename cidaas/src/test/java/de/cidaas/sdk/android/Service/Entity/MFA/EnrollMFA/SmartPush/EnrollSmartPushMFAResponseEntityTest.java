package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.SmartPush;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.SmartPush.EnrollSmartPushResponseDataEntity;


public class EnrollSmartPushMFAResponseEntityTest {
    @Mock
    EnrollSmartPushResponseDataEntity data;
    @InjectMocks
    EnrollSmartPushMFAResponseEntity enrollSmartPushMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess() {
        enrollSmartPushMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollSmartPushMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollSmartPushMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollSmartPushMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollSmartPushResponseDataEntity();

        data.setSub("Test");
        enrollSmartPushMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollSmartPushMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme