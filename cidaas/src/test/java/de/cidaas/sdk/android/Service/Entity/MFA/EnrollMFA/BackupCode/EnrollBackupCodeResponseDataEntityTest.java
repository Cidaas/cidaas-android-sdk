package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.BackupCode;


import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.BackupCode.EnrollBackupCodeResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollBackupCodeResponseDataEntityTest {

    EnrollBackupCodeResponseDataEntity enrollBackupCodeResponseDataEntity = new EnrollBackupCodeResponseDataEntity();

    @Test
    public void getSub() {
        enrollBackupCodeResponseDataEntity.setSub("test");
        assertTrue(enrollBackupCodeResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollBackupCodeResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollBackupCodeResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollBackupCodeResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollBackupCodeResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollBackupCodeResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollBackupCodeResponseDataEntity.getVerificationType() == "verificationType");

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme