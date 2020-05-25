package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateVoiceResponseDataEntityTest {
    AuthenticateVoiceResponseDataEntity authenticateVoiceResponseDataEntity = new AuthenticateVoiceResponseDataEntity();

    @Test
    public void getSub() {
        authenticateVoiceResponseDataEntity.setSub("test");
        assertTrue(authenticateVoiceResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateVoiceResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateVoiceResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateVoiceResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateVoiceResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateVoiceResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateVoiceResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme