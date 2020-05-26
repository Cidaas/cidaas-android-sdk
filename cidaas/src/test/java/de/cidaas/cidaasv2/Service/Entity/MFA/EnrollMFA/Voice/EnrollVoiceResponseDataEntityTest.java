package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollVoiceResponseDataEntityTest {
    EnrollVoiceResponseDataEntity enrollVoiceResponseDataEntity = new EnrollVoiceResponseDataEntity();

    @Test
    public void getSub() {
        enrollVoiceResponseDataEntity.setSub("test");
        assertTrue(enrollVoiceResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollVoiceResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollVoiceResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollVoiceResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollVoiceResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollVoiceResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollVoiceResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme