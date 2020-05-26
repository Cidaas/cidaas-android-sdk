package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Email;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class EnrollEmailResponseDataEntityTest {
    EnrollEmailResponseDataEntity enrollEmailResponseDataEntity = new EnrollEmailResponseDataEntity();

    @Test
    public void getSub() {
        enrollEmailResponseDataEntity.setSub("test");
        assertTrue(enrollEmailResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollEmailResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollEmailResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollEmailResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollEmailResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollEmailResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollEmailResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme