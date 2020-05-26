package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class EnrollSMSResponseDataEntityTest {
    EnrollSMSResponseDataEntity enrollSMSResponseDataEntity = new EnrollSMSResponseDataEntity();

    @Test
    public void getSub() {
        enrollSMSResponseDataEntity.setSub("test");
        assertTrue(enrollSMSResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollSMSResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollSMSResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollSMSResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollSMSResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollSMSResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollSMSResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme