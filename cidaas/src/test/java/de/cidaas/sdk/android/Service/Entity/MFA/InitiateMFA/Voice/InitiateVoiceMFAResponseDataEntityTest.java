package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Voice.InitiateVoiceMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateVoiceMFAResponseDataEntityTest {
    InitiateVoiceMFAResponseDataEntity initiateVoiceMFAResponseDataEntity = new InitiateVoiceMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateVoiceMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateVoiceMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme