package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;

public class InitiateVoiceMFAResponseEntityTest {

    InitiateVoiceMFAResponseDataEntity data;

    InitiateVoiceMFAResponseEntity initiateVoiceMFAResponseEntity;

    @Before
    public void setUp() {
        initiateVoiceMFAResponseEntity = new InitiateVoiceMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        initiateVoiceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateVoiceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateVoiceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateVoiceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateVoiceMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateVoiceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateVoiceMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme