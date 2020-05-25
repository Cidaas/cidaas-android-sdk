package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollFingerprintResponseDataEntityTest {

    EnrollFingerprintResponseDataEntity enrollFingerprintResponseDataEntity = new EnrollFingerprintResponseDataEntity();

    @Test
    public void getSub() {
        enrollFingerprintResponseDataEntity.setSub("test");
        assertTrue(enrollFingerprintResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollFingerprintResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollFingerprintResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollFingerprintResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollFingerprintResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollFingerprintResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollFingerprintResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme