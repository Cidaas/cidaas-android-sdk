package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSmartPushResponseDataEntityTest {
    AuthenticateSmartPushResponseDataEntity authenticateSmartPushResponseDataEntity = new AuthenticateSmartPushResponseDataEntity();

    @Test
    public void getSub() {
        authenticateSmartPushResponseDataEntity.setSub("test");
        assertTrue(authenticateSmartPushResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateSmartPushResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateSmartPushResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateSmartPushResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateSmartPushResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateSmartPushResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateSmartPushResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme