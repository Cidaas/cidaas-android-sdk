package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.SMS;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS.InitiateSMSMFAResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;


public class InitiateSMSMFAResponseEntityTest {
    @Mock
    InitiateSMSMFAResponseDataEntity data;
    @InjectMocks
    InitiateSMSMFAResponseEntity initiateSMSMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess() {
        initiateSMSMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateSMSMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateSMSMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateSMSMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateSMSMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateSMSMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateSMSMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme