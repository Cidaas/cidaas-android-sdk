package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFingerprintResponseDataEntityTest {
    AuthenticateFingerprintResponseDataEntity authenticateFingerprintResponseDataEntity = new AuthenticateFingerprintResponseDataEntity();

    @Test
    public void getSub() {
        authenticateFingerprintResponseDataEntity.setSub("test");
        assertTrue(authenticateFingerprintResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateFingerprintResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateFingerprintResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateFingerprintResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateFingerprintResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateFingerprintResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateFingerprintResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme