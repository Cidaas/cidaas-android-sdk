package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollSmartPushResponseDataEntityTest {
    EnrollSmartPushResponseDataEntity enrollSmartPushResponseDataEntity = new EnrollSmartPushResponseDataEntity();

    @Test
    public void getSub() {
        enrollSmartPushResponseDataEntity.setSub("test");
        assertTrue(enrollSmartPushResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollSmartPushResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollSmartPushResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollSmartPushResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollSmartPushResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollSmartPushResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollSmartPushResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme