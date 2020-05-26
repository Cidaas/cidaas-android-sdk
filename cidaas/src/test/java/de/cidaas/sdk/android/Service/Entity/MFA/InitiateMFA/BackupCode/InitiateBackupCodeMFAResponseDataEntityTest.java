package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.BackupCode;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateBackupCodeMFAResponseDataEntityTest {
    InitiateBackupCodeMFAResponseDataEntity initiateBackupCodeMFAResponseDataEntity = new InitiateBackupCodeMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateBackupCodeMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateBackupCodeMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme