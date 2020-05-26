package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import org.junit.Test;


import static junit.framework.TestCase.assertTrue;

public class AuthenticateFIDOResponseDataEntityTest {
    AuthenticateFIDOResponseDataEntity authenticateFIDOResponseDataEntity = new AuthenticateFIDOResponseDataEntity();

    @Test
    public void getSub() {
        authenticateFIDOResponseDataEntity.setSub("test");
        assertTrue(authenticateFIDOResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateFIDOResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateFIDOResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateFIDOResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateFIDOResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateFIDOResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateFIDOResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme