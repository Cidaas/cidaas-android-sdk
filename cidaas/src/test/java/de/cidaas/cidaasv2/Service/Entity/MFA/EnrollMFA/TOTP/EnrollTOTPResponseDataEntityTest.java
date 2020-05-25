package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollTOTPResponseDataEntityTest {
    EnrollTOTPResponseDataEntity enrollTOTPResponseDataEntity = new EnrollTOTPResponseDataEntity();

    @Test
    public void getSub() {
        enrollTOTPResponseDataEntity.setSub("test");
        assertTrue(enrollTOTPResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollTOTPResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollTOTPResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollTOTPResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollTOTPResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollTOTPResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollTOTPResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme