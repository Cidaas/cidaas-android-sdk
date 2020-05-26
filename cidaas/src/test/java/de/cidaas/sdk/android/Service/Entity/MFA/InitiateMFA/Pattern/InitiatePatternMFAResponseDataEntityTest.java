package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Pattern;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Pattern.InitiatePatternMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiatePatternMFAResponseDataEntityTest {
    InitiatePatternMFAResponseDataEntity initiatePatternMFAResponseDataEntity = new InitiatePatternMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiatePatternMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiatePatternMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme