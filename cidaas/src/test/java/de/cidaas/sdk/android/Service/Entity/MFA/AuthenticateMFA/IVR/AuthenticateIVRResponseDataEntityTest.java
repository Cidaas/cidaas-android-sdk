package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.IVR;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.IVR.AuthenticateIVRResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateIVRResponseDataEntityTest {
    AuthenticateIVRResponseDataEntity authenticateIVRResponseDataEntity = new AuthenticateIVRResponseDataEntity();

    @Test
    public void getSub() {
        authenticateIVRResponseDataEntity.setSub("test");
        assertTrue(authenticateIVRResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateIVRResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateIVRResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateIVRResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateIVRResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateIVRResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateIVRResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme