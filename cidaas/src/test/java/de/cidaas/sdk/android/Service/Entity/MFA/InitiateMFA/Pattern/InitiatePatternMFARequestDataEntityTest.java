package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Pattern;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Pattern.InitiatePatternMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiatePatternMFARequestDataEntityTest {
    InitiatePatternMFARequestDataEntity initiatePatternMFARequestDataEntity = new InitiatePatternMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiatePatternMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiatePatternMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme