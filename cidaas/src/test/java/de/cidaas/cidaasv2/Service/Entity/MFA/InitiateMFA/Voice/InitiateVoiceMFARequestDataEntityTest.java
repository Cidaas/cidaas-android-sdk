package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateVoiceMFARequestDataEntityTest {
    InitiateVoiceMFARequestDataEntity initiateVoiceMFARequestDataEntity = new InitiateVoiceMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateVoiceMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateVoiceMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme