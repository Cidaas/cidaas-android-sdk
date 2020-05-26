package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFaceResponseDataEntityTest {
    AuthenticateFaceResponseDataEntity authenticateFaceResponseDataEntity = new AuthenticateFaceResponseDataEntity();

    @Test
    public void getSub() {
        authenticateFaceResponseDataEntity.setSub("test");
        assertTrue(authenticateFaceResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateFaceResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateFaceResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateFaceResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateFaceResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateFaceResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateFaceResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme