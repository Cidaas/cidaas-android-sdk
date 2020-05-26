package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Pattern;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Pattern.AuthenticatePatternResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticatePatternResponseDataEntityTest {
    AuthenticatePatternResponseDataEntity authenticatePatternResponseDataEntity = new AuthenticatePatternResponseDataEntity();

    @Test
    public void getSub() {
        authenticatePatternResponseDataEntity.setSub("test");
        assertTrue(authenticatePatternResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticatePatternResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticatePatternResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticatePatternResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticatePatternResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticatePatternResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticatePatternResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme