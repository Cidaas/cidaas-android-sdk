package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode;

import org.junit.Test;


import static junit.framework.TestCase.assertTrue;

public class AuthenticateBackupCodeResponseDataEntityTest {

    AuthenticateBackupCodeResponseDataEntity authenticateBackupCodeResponseDataEntity = new AuthenticateBackupCodeResponseDataEntity();

    @Test
    public void getSub() {
        authenticateBackupCodeResponseDataEntity.setSub("test");
        assertTrue(authenticateBackupCodeResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateBackupCodeResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateBackupCodeResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateBackupCodeResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateBackupCodeResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateBackupCodeResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateBackupCodeResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme