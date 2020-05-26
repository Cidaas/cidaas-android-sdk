package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class EnrollIVRResponseDataEntityTest {

    EnrollIVRResponseDataEntity enrollIVRResponseDataEntity = new EnrollIVRResponseDataEntity();

    @Test
    public void getSub() {
        enrollIVRResponseDataEntity.setSub("test");
        assertTrue(enrollIVRResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollIVRResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollIVRResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollIVRResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollIVRResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollIVRResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollIVRResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme