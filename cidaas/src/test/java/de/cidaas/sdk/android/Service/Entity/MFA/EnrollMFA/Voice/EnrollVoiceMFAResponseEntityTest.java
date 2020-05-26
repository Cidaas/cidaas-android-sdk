package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.Voice;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Voice.EnrollVoiceResponseDataEntity;


public class EnrollVoiceMFAResponseEntityTest {
    @Mock
    EnrollVoiceResponseDataEntity data;
    @InjectMocks
    EnrollVoiceMFAResponseEntity enrollVoiceMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess() {
        enrollVoiceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollVoiceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollVoiceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollVoiceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollVoiceResponseDataEntity();

        data.setSub("Test");
        enrollVoiceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollVoiceMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme